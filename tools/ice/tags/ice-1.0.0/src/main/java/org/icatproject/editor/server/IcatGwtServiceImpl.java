package org.icatproject.editor.server;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;

import org.icatproject.Constraint;
import org.icatproject.EntityBaseBean;
import org.icatproject.EntityField;
import org.icatproject.EntityInfo;
import org.icatproject.ICAT;
import org.icatproject.ICATService;
import org.icatproject.IcatExceptionType;
import org.icatproject.IcatException_Exception;
import org.icatproject.Login.Credentials;
import org.icatproject.RelType;
import org.icatproject.editor.client.IcatGwtService;
import org.icatproject.editor.shared.Constants;
import org.icatproject.editor.shared.CredType;
import org.icatproject.editor.shared.CredType.Type;
import org.icatproject.editor.shared.CredType.Visibility;
import org.icatproject.editor.shared.EditorException;
import org.icatproject.editor.shared.FieldShared;
import org.icatproject.editor.shared.KeyShared;
import org.icatproject.editor.shared.LoginResult;
import org.icatproject.editor.shared.SessionException;
import org.icatproject.editor.shared.UpdateShared;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.rl.esc.catutils.CheckedProperties;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class IcatGwtServiceImpl extends RemoteServiceServlet implements IcatGwtService {

	private DatatypeFactory datatypeFactory;

	private DateFormat dfout;

	private static final String formatString = "yyyy-MM-dd' 'HH:mm:ss";

	private static final Logger logger = LoggerFactory.getLogger(IcatGwtServiceImpl.class);

	private Map<String, IceEntityInfo> eiMap = new HashMap<String, IceEntityInfo>();

	private ICAT icatEP;

	private List<String> beanNames;

	private Map<String, List<CredType>> credentialList = new LinkedHashMap<String, List<CredType>>();

	@Override
	public void init(ServletConfig conf) throws ServletException {
		super.init(conf);

		try {
			CheckedProperties props = new CheckedProperties();
			props.loadFromFile("ice.properties");

			URL icatUrl = new URL(props.getURL("icat.url") + "/ICATService/ICAT?wsdl");

			ICATService icatService = new ICATService(icatUrl, new QName("http://icatproject.org",
					"ICATService"));
			icatEP = icatService.getICATPort();

			datatypeFactory = DatatypeFactory.newInstance();
			dfout = new SimpleDateFormat(formatString);
			dfout.setLenient(false);

			beanNames = new ArrayList<String>(Arrays.asList(props.getString("bean.list").split(
					"\\s+")));
			for (String beanName : beanNames) {
				/* This will detect any invalid names */
				icatEP.getEntityInfo(beanName);
			}

			ArrayList<String> authnNames = new ArrayList<String>(Arrays.asList(props.getString(
					"authn.list").split("\\s+")));
			for (String authnName : authnNames) {
				List<CredType> credTypes = new ArrayList<CredType>();
				credentialList.put(authnName, credTypes);
				ArrayList<String> authnOneList = new ArrayList<String>(Arrays.asList(props
						.getString("authn." + authnName + ".list").split("\\s+")));
				for (String authnOne : authnOneList) {
					if (!authnOne.isEmpty()) {
						Visibility visibility = Visibility.EXPOSED;
						String key = "authn." + authnName + "." + authnOne + ".visible";
						if (props.has(key)) {
							if (props.getString(key).equals("false")) {
								visibility = Visibility.HIDDEN;
							}
						}
						CredType credType = new CredType(authnOne, visibility, Type.STRING);
						credTypes.add(credType);
					}
				}
			}

		} catch (Exception e) {
			logger.error(e.getClass() + " " + e.getMessage());
			throw new ServletException(e.getClass() + " " + e.getMessage());
		}

		logger.info("Init of IcatGwtServiceImpl succesful");
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<FieldShared> getCreateForm(String sessionId, String beanName)
			throws EditorException, SessionException {
		logger.debug("getCreateForm for " + beanName + " started");
		IceEntityInfo ei = getEntityInfo(beanName);
		List<FieldShared> fields = new ArrayList<FieldShared>();

		for (EntityField f : ei.getFields().values()) {
			if ("id".equals(f.getName())) {
				continue;
			}
			if (f.getRelType() == RelType.MANY) {
				continue;
			}
			List<KeyShared> keys = new ArrayList<KeyShared>();
			logger.debug("getEntityForm looking at field " + f.getName() + " of " + beanName);
			if (f.getRelType() == RelType.ONE) {
				if (!f.isNotNullable()) {
					KeyShared keyShared = new KeyShared();
					keyShared.setDisplay("");
					keyShared.setId(null);
					keys.add(keyShared);
				}
				keys.addAll(getEntityLabels(sessionId, f.getType()));
				if (f.isNotNullable() && keys.isEmpty()) {
					throw new EditorException("You must create a " + f.getType()
							+ " before creating a " + beanName);
				}
			}
			String type = f.getType();
			List<String> permValues = null;
			try {
				Class<?> typeClass = Class.forName(Constants.ENTITY_PREFIX + type);
				if (typeClass.isEnum()) {
					permValues = new ArrayList<String>();
					for (Object enumValue : typeClass.getEnumConstants()) {
						permValues.add(((Enum) enumValue).name());
					}
				}
			} catch (ClassNotFoundException e) {
				// Ignore - it's a String or something simple
			}

			FieldShared fs = new FieldShared();
			fs.setKeys(keys);
			fs.setPermValues(permValues);
			String name = f.getName();
			fs.setName(name);
			fs.setType(type);
			fs.setNotNullable(f.isNotNullable());
			fs.setComment(f.getComment());
			fs.setStringLength(f.getStringLength());
			fs.setRelType(f.getRelType().value());
			fields.add(fs);
		}
		logger.debug("getEntityForm returned " + fields.size() + " fields for " + beanName);
		return fields;

	}

	private List<Object> search(String sessionId, String query) throws EditorException,
			SessionException {
		try {
			return icatEP.search(sessionId, query);
		} catch (IcatException_Exception e) {
			IcatExceptionType type = e.getFaultInfo().getType();
			if (type == IcatExceptionType.BAD_PARAMETER) {
				throw new EditorException("BadParameterException " + e.getMessage());
			} else if (type == IcatExceptionType.INSUFFICIENT_PRIVILEGES) {
				return Collections.emptyList();
			} else if (type == IcatExceptionType.SESSION) {
				throw new SessionException(e.getMessage());
			} else {
				throw new EditorException("IcatInternalException " + e.getMessage());
			}
		}
	}

	private EntityBaseBean get(String sessionId, String query, long key) throws EditorException,
			SessionException {
		try {
			return icatEP.get(sessionId, query, key);
		} catch (IcatException_Exception e) {
			IcatExceptionType type = e.getFaultInfo().getType();
			if (type == IcatExceptionType.BAD_PARAMETER) {
				throw new EditorException("BadParameterException " + e.getMessage());
			} else if (type == IcatExceptionType.INSUFFICIENT_PRIVILEGES) {
				return null;
			} else if (type == IcatExceptionType.SESSION) {
				throw new SessionException(e.getMessage());
			} else {
				throw new EditorException("IcatInternalException " + e.getMessage());
			}
		}
	}

	private IceEntityInfo getEntityInfo(String beanName) throws EditorException {
		IceEntityInfo ei = null;
		synchronized (eiMap) {
			ei = eiMap.get(beanName);
		}
		if (ei == null) {
			try {
				EntityInfo plainEi = icatEP.getEntityInfo(beanName);
				ei = new IceEntityInfo();
				for (EntityField ef : plainEi.getFields()) {
					ei.getFields().put(ef.getName(), ef);
				}
				ei.setConstraints(plainEi.getConstraints());
			} catch (IcatException_Exception e) {
				if (e.getFaultInfo().getType() == IcatExceptionType.BAD_PARAMETER) {
					throw new EditorException("BadParameterException " + e.getMessage());
				} else {
					throw new EditorException("IcatInternalException " + e.getMessage());
				}
			}
			synchronized (eiMap) {
				eiMap.put(beanName, ei);
			}
		}
		return ei;
	}

	@Override
	public LoginResult login(String plugin, Map<String, String> map) throws EditorException,
			SessionException {
		logger.debug("Plugin is " + plugin);
		try {
			Credentials credentials = new Credentials();
			for (Entry<String, String> e : map.entrySet()) {
				Credentials.Entry cred = new Credentials.Entry();
				cred.setKey(e.getKey());
				cred.setValue(e.getValue());
				credentials.getEntry().add(cred);
			}
			String sessionId = icatEP.login(plugin, credentials);
			String userName = icatEP.getUserName(sessionId);
			logger.debug("Logged in as " + userName);
			return new LoginResult(sessionId, userName);
		} catch (IcatException_Exception e) {
			if (e.getFaultInfo().getType() == IcatExceptionType.SESSION) {
				throw new SessionException(e.getMessage());
			} else {
				throw new EditorException("IcatInternalException " + e.getMessage());
			}
		}
	}

	@Override
	public void logout(String sessionId) {
		try {
			icatEP.logout(sessionId);
		} catch (IcatException_Exception e) {
			// Ignore it
		}
		logger.debug("User with sessionId " + sessionId + " logged out");
	}

	@Override
	public void create(String sessionId, String beanName, Map<String, String> values)
			throws EditorException, SessionException {
		try {
			logger.debug("Create " + beanName + " with values " + values);
			EntityBaseBean bean = makeBean(sessionId, beanName, values);
			icatEP.create(sessionId, bean);
		} catch (IcatException_Exception e) {
			IcatExceptionType type = e.getFaultInfo().getType();
			if (type == IcatExceptionType.BAD_PARAMETER) {
				throw new EditorException("BadParameterException " + e.getMessage());
			} else if (type == IcatExceptionType.INSUFFICIENT_PRIVILEGES) {
				throw new EditorException("InsufficientPrivilegesException " + e.getMessage());
			} else if (type == IcatExceptionType.SESSION) {
				throw new SessionException(e.getMessage());
			} else if (type == IcatExceptionType.VALIDATION) {
				throw new EditorException("ValidationException " + e.getMessage());
			} else if (type == IcatExceptionType.OBJECT_ALREADY_EXISTS) {
				throw new EditorException("ObjectAlreadyExistsException " + e.getMessage());
			} else {
				throw new EditorException("IcatInternalException " + e.getMessage());
			}
		}
	}

	private EntityBaseBean makeBean(String sessionId, String beanName, Map<String, String> values)
			throws EditorException, SessionException, IcatException_Exception {
		try {
			@SuppressWarnings("unchecked")
			Class<? extends EntityBaseBean> beanClass = (Class<? extends EntityBaseBean>) Class
					.forName(Constants.ENTITY_PREFIX + beanName);
			Constructor<?> cons = beanClass.getConstructor();
			EntityBaseBean bean = (EntityBaseBean) cons.newInstance();
			IceEntityInfo ei = getEntityInfo(beanName);
			for (EntityField field : ei.getFields().values()) {
				String name = field.getName();
				String value = values.get(name);
				if (value != null && !value.isEmpty()) {
					String type = field.getType();
					logger.debug("Setting field " + name + " of " + beanName + " of type " + type
							+ " to " + value);
					String setterName = "set" + name.substring(0, 1).toUpperCase()
							+ name.substring(1);

					if (field.getRelType() == RelType.ATTRIBUTE) {
						if (type.equals("String")) {
							Method setter = beanClass.getMethod(setterName, String.class);
							setter.invoke(bean, value);
						} else if (type.equals("Integer")) {
							Method setter = beanClass.getMethod(setterName, Integer.class);
							try {
								setter.invoke(bean, Integer.parseInt(value));
							} catch (NumberFormatException e) {
								throw new EditorException("Attribute of type " + type + " in "
										+ beanName + " has bad representation " + value);
							}
						} else if (type.equals("Date")) {
							Method setter = beanClass.getMethod(setterName,
									XMLGregorianCalendar.class);
							try {
								GregorianCalendar gc = new GregorianCalendar();
								Date date;
								try {
									synchronized (dfout) {
										date = dfout.parse(value);
									}
								} catch (ParseException e) {
									throw new EditorException("Attribute of type " + type + " in "
											+ beanName + " has bad representation " + value
											+ " It must conform to "
											+ formatString.replace("'", ""));
								}
								gc.setTimeInMillis(date.getTime());
								setter.invoke(bean, datatypeFactory.newXMLGregorianCalendar(gc));
							} catch (NumberFormatException e) {
								throw new EditorException("Attribute of type " + type + " in "
										+ beanName + " has bad representation " + value);
							}
						} else if (type.equals("boolean")) {
							Method setter = beanClass.getMethod(setterName, boolean.class);
							setter.invoke(bean, Boolean.parseBoolean(value));
						} else {
							Class<?> typeClass = Class.forName(Constants.ENTITY_PREFIX + type);
							if (typeClass.isEnum()) {
								logger.debug("Got an enum " + typeClass);
								Method setter = beanClass.getMethod(setterName, typeClass);
								Method meth = typeClass.getMethod("valueOf", String.class);
								logger.debug("Method " + meth);
								try {
									Object val = meth.invoke(null, value);
									setter.invoke(bean, val);
								} catch (InvocationTargetException e) {
									logger.debug("Failed with " + e);
									throw e;
								}
							} else {
								throw new EditorException(
										"Don't know how to handle attributes of type " + type
												+ " in " + beanName);
							}
						}
					} else if (field.getRelType() == RelType.ONE) {
						if (value != null) {
							try {
								EntityBaseBean oneKey = icatEP.get(sessionId, type,
										Long.parseLong(value));
								Method setter = beanClass.getMethod(setterName,
										Class.forName(Constants.ENTITY_PREFIX + type));
								setter.invoke(bean, oneKey);
								logger.debug("Set to " + oneKey.getId());

							} catch (NumberFormatException e) {
								throw new EditorException("Can't happen");
							}
						}
					}
				}
			}
			return bean;
		} catch (ClassNotFoundException e) {
			throw new EditorException("ClassNotFoundException " + e.getMessage() + " in "
					+ System.getProperty("java.class.path"));
		} catch (NoSuchMethodException e) {
			throw new EditorException("NoSuchMethodException " + e.getMessage());
		} catch (IllegalAccessException e) {
			throw new EditorException("IllegalAccessException " + e.getMessage());
		} catch (InvocationTargetException e) {
			throw new EditorException("InvocationTargetException " + e.getMessage());
		} catch (InstantiationException e) {
			throw new EditorException("InstantiationException " + e.getMessage());
		}
	}

	@Override
	public void update(String sessionId, String beanName, Long id, Map<String, String> values)
			throws EditorException, SessionException {
		logger.debug("Update of " + beanName + " " + id + " with values " + values);
		try {
			EntityBaseBean bean = makeBean(sessionId, beanName, values);
			bean.setId(id);
			icatEP.update(sessionId, bean);
		} catch (IcatException_Exception e) {
			IcatExceptionType type = e.getFaultInfo().getType();
			if (type == IcatExceptionType.BAD_PARAMETER) {
				throw new EditorException("BadParameterException " + e.getMessage());
			} else if (type == IcatExceptionType.INSUFFICIENT_PRIVILEGES) {
				throw new EditorException("InsufficientPrivilegesException " + e.getMessage());
			} else if (type == IcatExceptionType.SESSION) {
				throw new SessionException(e.getMessage());
			} else if (type == IcatExceptionType.VALIDATION) {
				throw new EditorException("ValidationException " + e.getMessage());
			} else if (type == IcatExceptionType.NO_SUCH_OBJECT_FOUND) {
				throw new EditorException("NoSuchObjectFoundException " + e.getMessage());
			} else {
				throw new EditorException("IcatInternalException " + e.getMessage());
			}
		}

	}

	private List<KeyShared> getEntityLabels(String sessionId, String beanName)
			throws EditorException, SessionException {
		List<KeyShared> keys = new ArrayList<KeyShared>();
		IceEntityInfo eiOne = getEntityInfo(beanName);

		logger.debug("getKeys process generated key for " + beanName);
		List<Constraint> constraints = eiOne.getConstraints();
		if (constraints.size() == 0) {
			throw new EditorException(beanName + " has a generated key but without constraints.");
		}

		/* Find shortest constraint */
		Constraint theConstraint = null;
		for (Constraint constraint : constraints) {
			if (theConstraint == null) {
				theConstraint = constraint;
			} else if (constraint.getFieldNames().size() < theConstraint.getFieldNames().size()) {
				theConstraint = constraint;
			}
		}

		Map<String, Method> methods = new HashMap<String, Method>();
		ArrayList<String> names = new ArrayList<String>(theConstraint.getFieldNames());
		for (String fieldName : names) {
			EntityField field = eiOne.getFields().get(fieldName);
			Class<?> beanOneClass;
			try {
				beanOneClass = Class.forName(Constants.ENTITY_PREFIX + beanName);
			} catch (ClassNotFoundException e) {
				throw new EditorException("ClassNotFoundException " + e.getMessage() + " in "
						+ System.getProperty("java.class.path"));
			}
			String getterName = ((field.getType().equals("boolean")) ? "is" : "get")
					+ fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
			try {
				logger.debug("getKeys look up getter for " + fieldName + " of " + beanName);
				Method getter = beanOneClass.getMethod(getterName);
				methods.put(fieldName, getter);
			} catch (SecurityException e) {
				throw new EditorException("SecurityException " + e.getMessage());
			} catch (NoSuchMethodException e) {
				throw new EditorException("NoSuchMethodException " + e.getMessage());
			}
		}
		List<Object> beanObjects = search(sessionId, beanName + " INCLUDE 1");
		for (Object beanObject : beanObjects) {
			StringBuilder sb = null;
			try {
				for (String fieldName : theConstraint.getFieldNames()) {
					Method method = methods.get(fieldName);
					Object value = method.invoke(beanObject);
					if (value instanceof EntityBaseBean) {
						value = getEntityLabel(sessionId, (EntityBaseBean) value);
					}
					if (sb == null) {
						sb = new StringBuilder();
					} else {
						sb.append(", ");
					}
					sb.append(fieldName + "=" + value);
				}
				KeyShared keyShared = new KeyShared();
				Long value = ((EntityBaseBean) beanObject).getId();
				keyShared.setId(value);
				keyShared.setDisplay(sb.toString());
				keys.add(keyShared);
			} catch (IllegalArgumentException e) {
				throw new EditorException("IllegalArgumentException " + e.getMessage());
			} catch (IllegalAccessException e) {
				throw new EditorException("IllegalAccessException " + e.getMessage());
			} catch (InvocationTargetException e) {
				throw new EditorException("InvocationTargetException " + e.getMessage());
			}
		}
		return keys;
	}

	private String getEntityLabel(String sessionId, EntityBaseBean bean) throws EditorException,
			SessionException {
		String beanName = bean.getClass().getSimpleName();
		IceEntityInfo eiOne = getEntityInfo(beanName);

		List<Constraint> constraints = eiOne.getConstraints();
		if (constraints.size() == 0) {
			throw new EditorException(beanName + " has a generated key but without constraints.");
		}

		/* Find shortest constraint */
		Constraint theConstraint = null;
		for (Constraint constraint : constraints) {
			if (theConstraint == null) {
				theConstraint = constraint;
			} else if (constraint.getFieldNames().size() < theConstraint.getFieldNames().size()) {
				theConstraint = constraint;
			}
		}

		Map<String, Method> methods = new HashMap<String, Method>();
		ArrayList<String> names = new ArrayList<String>(theConstraint.getFieldNames());
		for (String fieldName : names) {
			EntityField field = eiOne.getFields().get(fieldName);
			Class<?> beanOneClass;
			try {
				beanOneClass = Class.forName(Constants.ENTITY_PREFIX + beanName);
			} catch (ClassNotFoundException e) {
				throw new EditorException("ClassNotFoundException " + e.getMessage() + " in "
						+ System.getProperty("java.class.path"));
			}
			String getterName = ((field.getType().equals("boolean")) ? "is" : "get")
					+ fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
			try {
				logger.debug("getKeys look up getter for " + fieldName + " of " + beanName);
				Method getter = beanOneClass.getMethod(getterName);
				methods.put(fieldName, getter);
			} catch (NoSuchMethodException e) {
				throw new EditorException("NoSuchMethodException " + e.getMessage());
			}
		}
		EntityBaseBean beanObject = get(sessionId, beanName + " INCLUDE 1", bean.getId());

		StringBuilder sb = null;
		try {
			for (String fieldName : theConstraint.getFieldNames()) {
				Method method = methods.get(fieldName);
				Object value = method.invoke(beanObject);
				if (value instanceof EntityBaseBean) {
					value = getEntityLabel(sessionId, (EntityBaseBean) value);
				}
				if (sb == null) {
					sb = new StringBuilder("(");
				} else {
					sb.append(", ");
				}
				sb.append(fieldName + "=" + value);
			}
			sb.append(')');
			return sb.toString();
		} catch (IllegalAccessException e) {
			throw new EditorException("IllegalAccessException " + e.getMessage());
		} catch (InvocationTargetException e) {
			throw new EditorException("InvocationTargetException " + e.getMessage());
		}

	}

	@Override
	public List<KeyShared> getDeleteForm(String sessionId, String beanName) throws EditorException,
			SessionException {
		return getEntityLabels(sessionId, beanName);
	}

	@Override
	public List<KeyShared> getUpdateForm(String sessionId, String beanName) throws EditorException,
			SessionException {
		return getEntityLabels(sessionId, beanName);
	}

	@Override
	public List<KeyShared> delete(String sessionId, String beanName, List<Long> deletes)
			throws EditorException, SessionException {
		try {
			for (Long pk : deletes) {
				EntityBaseBean bean = icatEP.get(sessionId, beanName, pk);
				icatEP.delete(sessionId, bean);
			}
			return getEntityLabels(sessionId, beanName);
		} catch (IcatException_Exception e) {
			if (e.getFaultInfo().getType() == IcatExceptionType.SESSION) {
				throw new SessionException(e.getMessage());
			} else {
				throw new EditorException("BadParameterException " + e.getMessage());
			}
		}
	}

	@Override
	public UpdateShared getForUpdate(String sessionId, String beanName, Long pk)
			throws EditorException, SessionException {
		logger.debug("getForUpdate for " + beanName + " " + pk);
		List<FieldShared> fields = getCreateForm(sessionId, beanName);
		Class<?> beanOneClass;
		try {
			beanOneClass = Class.forName(Constants.ENTITY_PREFIX + beanName);
		} catch (ClassNotFoundException e) {
			throw new EditorException("ClassNotFoundException " + e.getMessage() + " in "
					+ System.getProperty("java.class.path"));
		}
		try {
			EntityBaseBean bean = icatEP.get(sessionId, beanName + " INCLUDE 1", pk);
			IceEntityInfo ei = getEntityInfo(beanName);
			Map<String, String> valueMap = new HashMap<String, String>();
			for (EntityField f : ei.getFields().values()) {
				if (f.getRelType() != RelType.MANY) {
					String fieldName = f.getName();
					String getterName = ((f.getType().equals("boolean")) ? "is" : "get")
							+ fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
					logger.debug(getterName + " " + f.getType());
					Method getter = beanOneClass.getMethod(getterName);
					Object value = getter.invoke(bean);
					if (value != null) {
						if (value instanceof EntityBaseBean) {
							value = ((EntityBaseBean) value).getId();
						} else if (value instanceof XMLGregorianCalendar) {
							synchronized (dfout) {
								value = dfout.format(((XMLGregorianCalendar) value)
										.toGregorianCalendar().getTime());
							}
						}
						valueMap.put(fieldName, value.toString());
						logger.debug(fieldName + " = " + value.toString());
					}
				}
			}
			return new UpdateShared(fields, valueMap);
		} catch (IcatException_Exception e) {
			if (e.getFaultInfo().getType() == IcatExceptionType.SESSION) {
				throw new SessionException(e.getMessage());
			} else {
				throw new EditorException("BadParameterException " + e.getMessage());
			}
		} catch (NoSuchMethodException e) {
			throw new EditorException("NoSuchMethodException " + e.getMessage());
		} catch (SecurityException e) {
			throw new EditorException("SecurityException " + e.getMessage());
		} catch (IllegalAccessException e) {
			throw new EditorException("IllegalAccessException " + e.getMessage());
		} catch (InvocationTargetException e) {
			throw new EditorException("InvocationTargetException " + e.getMessage());
		} catch (Throwable e) {
			throw new EditorException("Internal error " + e.getClass() + " " + e.getMessage());
		}
	}

	@Override
	public List<String> getBeanNames() {
		return beanNames;
	}

	@Override
	public Map<String, List<CredType>> getCredentialList() {
		return credentialList;
	}

}