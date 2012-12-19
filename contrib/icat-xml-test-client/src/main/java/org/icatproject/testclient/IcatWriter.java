package org.icatproject.testclient;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.icatproject.EntityBaseBean;
import org.icatproject.ICAT;

/**
 * Writes xml icat data to the icat DB.
 * 
 */
public class IcatWriter {

	/**
	 * The parsed xml data to load. Contains also configuration information.
	 */
	private Icatdata data;

	/**
	 * The icat web service client instance.
	 */
	private ICAT icat;

	/**
	 * The icat session id for authorization.
	 */
	private String sessionId;

	/**
	 * A map key class to store icat datatype objects under their datatype and id or searchId.
	 * 
	 * @author SSCHULZE
	 * 
	 */
	public static class DataTypeID<T> {
		/**
		 * The icat datatype.
		 */
		private String dataType;

		/**
		 * The icat id.
		 */
		private T id;

		public DataTypeID() {
		};

		public DataTypeID(final String dataType, final T id) {
			this.dataType = dataType;
			this.id = id;
		}

		@Override
		public int hashCode() {
			int result = 17;
			result = 31 * result + (this.id == null ? 0 : this.id.hashCode());
			result = 31 * result + (this.dataType == null ? 0 : this.dataType.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (!(obj instanceof DataTypeID<?>))
				return false;
			DataTypeID<T> o = (DataTypeID<T>) obj;
			return ((this.id == null ? o.id == null : this.id.equals(o.id)) && (this.dataType == null ? o.dataType == null
					: this.dataType.equals(o.dataType)));
		}

		@Override
		public String toString() {
			StringBuffer result = new StringBuffer("{dataType=");
			result.append(this.dataType == null ? "null" : this.dataType);
			result.append(", id=");
			result.append(this.id == null ? "null" : this.id.toString());
			result.append("}");
			return result.toString();
		}

	}

	/**
	 * The local id cache.
	 */
	private Map<DataTypeID<Long>, EntityBaseBean> localObjMap;

	/**
	 * The initial id cache.
	 */
	private Map<DataTypeID<Long>, EntityBaseBean> searchedObjMap;

	/**
	 * The icat id cache.
	 */
	private Map<DataTypeID<Long>, EntityBaseBean> icatObjMap;

	/**
	 * The icat search id query cache.
	 */
	private Map<DataTypeID<String>, EntityBaseBean> searchIdQueryMap;

	public IcatWriter(Icatdata data) {
		this.data = data;
	}

	public IcatWriter(Icatdata data, Map<DataTypeID<Long>, EntityBaseBean> searchedObjMap) {
		this.data = data;
		this.searchedObjMap = searchedObjMap;
	}

	/**
	 * Writes the icat data to the icat DB through the web service client.
	 * 
	 * @param icat
	 * @param sessionId
	 * @throws Exception
	 */
	synchronized public void write(final ICAT icat, final String sessionId) throws Exception {
		// init
		this.icat = icat;
		this.sessionId = sessionId;
		this.localObjMap = new HashMap<DataTypeID<Long>, EntityBaseBean>();
		if (this.searchedObjMap != null) {
			this.localObjMap.putAll(searchedObjMap);
		}
		this.icatObjMap = new HashMap<DataTypeID<Long>, EntityBaseBean>();
		this.searchIdQueryMap = new HashMap<DataTypeID<String>, EntityBaseBean>();
		this.create(this.data);
		// dumpCache(this.localObjMap);
	}

	private void dumpCache(final Map<DataTypeID<Long>, EntityBaseBean> cache) throws Exception {
		for (Map.Entry<DataTypeID<Long>, EntityBaseBean> entry : cache.entrySet()) {
			System.out.println(entry.getKey().toString() + ": " + objToStr(entry.getValue()));
		}
	}

	private String objToStr(Object obj) throws Exception {
		StringBuffer result = new StringBuffer("{");
		Method[] ms = obj.getClass().getMethods();
		boolean first = true;
		for (Method m : ms) {
			if (m.getName().startsWith("get") && m.getParameterTypes().length == 0) {
				if (!first)
					result.append(", ");
				Object value = m.invoke(obj);
				result.append(m.getName().substring(3) + "=" + (value == null ? "null" : value.toString()));
				first = false;
			}
		}
		result.append("}");
		return result.toString();
	}

	/**
	 * Fetch the entity base object either from the local map or ICAT (cached also locally). The search key is the id or the search id query.
	 * If no object is found null is returned.
	 * 
	 * @param ebIn
	 * @return
	 * @throws Exception
	 */
	private EntityBaseBean getEbForIdOrSearchId(final EntityBaseBean ebIn) throws Exception {
		if (ebIn == null)
			return null;
		
		if(ebIn.getSearchId() != null && ebIn.getSearchId().trim().length() > 0) {
			return this.getEbForSearchId(ebIn);
		}
		
		if (ebIn.getId() != null) {
			return this.getEbForId(ebIn);
		}
		
		return null;
	}
	
	/**
	 * Get entity base bean from local references or ICAT id.
	 * @param ebIn
	 * @return
	 * @throws Exception
	 */
	private EntityBaseBean getEbForId(final EntityBaseBean ebIn) throws Exception {
		EntityBaseBean ebOut = null;
		DataTypeID<Long> did = new DataTypeID<Long>(ebIn.getClass().getSimpleName(), ebIn.getId());
		if (this.data.getConfig().getLocalIdRange().inRange(did.id)) {
			return this.localObjMap.get(did);
		}
		ebOut = this.icatObjMap.get(did);
		if (ebOut == null) {
			try {
				ebOut = icat.get(sessionId, did.dataType, did.id);
				this.icatObjMap.put(did, ebOut);
			} catch (Exception e) {
				if (this.data.getConfig().getHaltOnError())
					throw e;
				System.err.println("ERROR: " + e.toString());
				e.printStackTrace(System.err);
				System.err.println("Continuing import...");
			}
		}
		return ebOut;
	}
	
	/**
	 * Get entity base bean either from local query cache or from icat.
	 * @param ebIn
	 * @return
	 * @throws Exception
	 */
	private EntityBaseBean getEbForSearchId(final EntityBaseBean ebIn) throws Exception {
		EntityBaseBean ebOut = null;
		ebIn.setSearchId(ebIn.getSearchId().trim());
		// check if object is already in cache
		DataTypeID<String> dsid = new DataTypeID<String>(ebIn.getClass().getSimpleName(), ebIn.getSearchId());
		if(this.searchIdQueryMap.containsKey(dsid)) {
			return this.searchIdQueryMap.get(dsid);
		}
		// search object in icat
		try {
			List<Object> results = icat.search(sessionId, ebIn.getSearchId());
			if (results != null && results.size() > 1)
				throw new ArrayIndexOutOfBoundsException("Found more than one entity base bean for query="
						+ ebIn.getSearchId());
			ebOut = (EntityBaseBean) results.get(0);
			// cache with query
			this.searchIdQueryMap.put(dsid, ebOut);
			if (ebIn.getId() != null) {
				// cache since locally referenced
				this.localObjMap.put(new DataTypeID<Long>(ebIn.getClass().getSimpleName(), ebIn.getId()), ebOut);
			}
			
		} catch (Exception e) {
			if (this.data.getConfig().getHaltOnError())
				throw e;
			System.err.println("ERROR: " + e.toString());
			e.printStackTrace(System.err);
			System.err.println("Continuing import...");
		}
		return ebOut;
	}

	/**
	 * Cache locally referenced entity base objects
	 * 
	 * @param eb
	 */
	private void cacheEb(final EntityBaseBean eb) {
		if (eb == null || eb.getId() == null)
			return;
		DataTypeID<Long> did = new DataTypeID<Long>(eb.getClass().getSimpleName(), eb.getId());
		if (!this.data.getConfig().getLocalIdRange().inRange(did.id))
			return;
		this.localObjMap.put(did, eb);
	}

	/**
	 * Creates new objects in icat (or retreives them if they are refernced with an icat id). Is called recursively on
	 * the icat xml data container.
	 * 
	 * @param obj
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private void create(Object obj) throws Exception {
		if (obj == null)
			return;
		// manage lists of icat datatype objects
		if (obj instanceof List) {
			@SuppressWarnings("rawtypes")
			List l = (List) obj;
			for (int i = 0; i < l.size(); i++) {
				if (l.get(i) instanceof EntityBaseBean) {
					EntityBaseBean refEb = this.getEbForIdOrSearchId((EntityBaseBean) l.get(i));
					if (refEb != null) {
						// replace parsed object by cached object
						l.remove(i);
						l.add(i, refEb);
						continue;
					}
				}
				try {
					this.create(l.get(i));
				} catch(Exception e) {
					if (this.data.getConfig().getHaltOnError())
						throw e;
					System.err.println("ERROR: " + e.toString());
					e.printStackTrace(System.err);
					System.err.println("Continuing import...");
				}
			}
			return;
		}
		EntityBaseBean eb = (EntityBaseBean) obj;
		// if we get to here we have a new object so put it in the cache
		this.cacheEb(eb);
		Method[] ms = eb.getClass().getMethods();
		// check all properties to set them from the import data
		for (Method m : ms) {
			if (m.getName().startsWith("get") && m.getParameterTypes().length == 0) {
				// we have a getter...
				if (EntityBaseBean.class.isAssignableFrom(m.getReturnType())) {
					// check if we should take a stored object
					EntityBaseBean ebProp = (EntityBaseBean) m.invoke(eb);
					if (ebProp == null)
						continue;
					EntityBaseBean refEb = this.getEbForIdOrSearchId(ebProp);
					if (refEb != null) {
						try {
							Method setter = eb.getClass().getMethod("s" + m.getName().substring(1), ebProp.getClass());
							setter.invoke(eb, refEb);
							continue;
						} catch(NoSuchMethodException e) {
							NoSuchMethodException e2 = new NoSuchMethodException(e.getMessage() + " (Cannot set property " + m.getName().substring(3) + " on icat object of type " + eb.getClass().getSimpleName());
							e2.initCause(e.getCause());
							e2.setStackTrace(e.getStackTrace());
							throw e2;
						} catch(IllegalArgumentException e) {
							IllegalArgumentException e2 = new IllegalArgumentException(e.getMessage() + " (Cannot set property " + m.getName().substring(3) + " on icat object of type " + eb.getClass().getSimpleName());
							e2.initCause(e.getCause());
							e2.setStackTrace(e.getStackTrace());
							throw e2;
						} catch(InvocationTargetException e) {
							InvocationTargetException e2 = new InvocationTargetException(e.getCause(), e.getMessage() + " (Cannot set property " + m.getName().substring(3) + " on icat object of type " + eb.getClass().getSimpleName());
							e2.setStackTrace(e.getStackTrace());
							throw e2;
						}
					}
					// we have a new object so create it
					this.create(ebProp);
				} else if (List.class.isAssignableFrom(m.getReturnType())) {
					// we have a list of objects so create it
					this.create(m.invoke(eb));
				}
			}
		}
		// check if finished
		if (eb instanceof Icatdata)
			return;
		// no we create a new object in icat so remove any ids since they are only file local
		DataTypeID<Long> oldDid = new DataTypeID<Long>(eb.getClass().getSimpleName(), eb.getId());
		eb.setId(null);
		try {
			// create the object
			Long icatId = icat.create(sessionId, eb);
			// set the icat id
			eb.setId(icatId);
			System.out.println("Created " + eb.getClass().getSimpleName() + ": " + this.objToStr(eb));
		} catch (Exception e) {
			if (this.data.getConfig().getHaltOnError())
				throw e;
			System.err.println("ERROR: " + e.toString());
			e.printStackTrace(System.err);
			System.err.println("Continuing import...");
			// in case of error remove the locally cached object since it cannot be used
			this.localObjMap.remove(oldDid);
		}
	}
}