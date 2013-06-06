package org.icatproject.exposed;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.UserTransaction;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import org.apache.log4j.Logger;
import org.icatproject.authentication.Authenticator;
import org.icatproject.core.IcatException;
import org.icatproject.core.PropertyHandler;
import org.icatproject.core.entity.Application;
import org.icatproject.core.entity.Datafile;
import org.icatproject.core.entity.DatafileFormat;
import org.icatproject.core.entity.DatafileParameter;
import org.icatproject.core.entity.Dataset;
import org.icatproject.core.entity.DatasetParameter;
import org.icatproject.core.entity.DatasetType;
import org.icatproject.core.entity.EntityBaseBean;
import org.icatproject.core.entity.Facility;
import org.icatproject.core.entity.FacilityCycle;
import org.icatproject.core.entity.Group;
import org.icatproject.core.entity.InputDatafile;
import org.icatproject.core.entity.InputDataset;
import org.icatproject.core.entity.Instrument;
import org.icatproject.core.entity.InstrumentScientist;
import org.icatproject.core.entity.Investigation;
import org.icatproject.core.entity.InvestigationType;
import org.icatproject.core.entity.InvestigationUser;
import org.icatproject.core.entity.Job;
import org.icatproject.core.entity.Keyword;
import org.icatproject.core.entity.Log;
import org.icatproject.core.entity.OutputDatafile;
import org.icatproject.core.entity.OutputDataset;
import org.icatproject.core.entity.ParameterType;
import org.icatproject.core.entity.Publication;
import org.icatproject.core.entity.RelatedDatafile;
import org.icatproject.core.entity.Sample;
import org.icatproject.core.entity.SampleParameter;
import org.icatproject.core.entity.Shift;
import org.icatproject.core.entity.Study;
import org.icatproject.core.entity.Study.StudyStatus;
import org.icatproject.core.entity.StudyInvestigation;
import org.icatproject.core.entity.User;
import org.icatproject.core.entity.UserGroup;
import org.icatproject.core.manager.BeanManager;
import org.icatproject.core.manager.CreateResponse;
import org.icatproject.core.manager.EntityInfo;
import org.icatproject.core.manager.LuceneSingleton;
import org.icatproject.core.manager.NotificationMessage;

@Stateless
@WebService(targetNamespace = "http://icatproject.org")
@TransactionManagement(TransactionManagementType.BEAN)
public class ICAT {

	private static Logger logger = Logger.getLogger(ICAT.class);

	@PersistenceContext(unitName = "icat")
	protected EntityManager manager;

	@EJB
	Transmitter transmitter;

	@Resource
	private UserTransaction userTransaction;

	@Resource
	WebServiceContext webServiceContext;

	private Map<String, Authenticator> authPlugins = new HashMap<String, Authenticator>();

	private int lifetimeMinutes;

	private LuceneSingleton lucene;

	@PostConstruct
	private void init() {

		PropertyHandler p = PropertyHandler.getInstance();
		authPlugins = p.getAuthPlugins();
		lifetimeMinutes = p.getLifetimeMinutes();
		if (p.getLuceneDirectory() != null) {
			lucene = LuceneSingleton.getInstance();
		}
	}

	@PreDestroy
	private void exit() {
		if (lucene != null) {
			lucene.close();
		}
	}

	@WebMethod
	public long create(@WebParam(name = "sessionId") String sessionId,
			@WebParam(name = "bean") EntityBaseBean bean) throws IcatException {
		try {
			String userId = getUserName(sessionId);
			CreateResponse createResponse = BeanManager.create(userId, bean, manager,
					userTransaction, lucene);
			transmitter.processMessage(createResponse.getNotificationMessage());
			return createResponse.getPk();
		} catch (IcatException e) {
			reportIcatException(e);
			throw e;
		} catch (Throwable e) {
			reportThrowable(e);
			throw new IcatException(IcatException.IcatExceptionType.INTERNAL, e.getMessage());
		}
	}

	@WebMethod
	public List<Long> createMany(@WebParam(name = "sessionId") String sessionId,
			@WebParam(name = "beans") List<EntityBaseBean> beans) throws IcatException {
		try {
			String userId = getUserName(sessionId);
			List<CreateResponse> createResponses = BeanManager.createMany(userId, beans, manager,
					userTransaction);
			List<Long> lo = new ArrayList<Long>();
			for (CreateResponse createResponse : createResponses) {
				transmitter.processMessage(createResponse.getNotificationMessage());
				lo.add(createResponse.getPk());
			}
			return lo;
		} catch (IcatException e) {
			reportIcatException(e);
			throw e;
		} catch (Throwable e) {
			reportThrowable(e);
			throw new IcatException(IcatException.IcatExceptionType.INTERNAL, e.getMessage());
		}
	}

	@WebMethod
	public void delete(@WebParam(name = "sessionId") String sessionId,
			@WebParam(name = "bean") EntityBaseBean bean) throws IcatException {
		try {
			String userId = getUserName(sessionId);
			transmitter.processMessage(BeanManager.delete(userId, bean, manager, userTransaction));
		} catch (IcatException e) {
			reportIcatException(e);
			throw e;
		} catch (Throwable e) {
			reportThrowable(e);
			throw new IcatException(IcatException.IcatExceptionType.INTERNAL, e.getMessage());
		}
	}

	@WebMethod
	public void deleteMany(@WebParam(name = "sessionId") String sessionId,
			@WebParam(name = "beans") List<EntityBaseBean> beans) throws IcatException {
		try {
			String userId = getUserName(sessionId);
			List<NotificationMessage> nms = BeanManager.deleteMany(userId, beans, manager,
					userTransaction);
			for (NotificationMessage nm : nms) {
				transmitter.processMessage(nm);
			}
		} catch (IcatException e) {
			reportIcatException(e);
			throw e;
		} catch (Throwable e) {
			reportThrowable(e);
			throw new IcatException(IcatException.IcatExceptionType.INTERNAL, e.getMessage());
		}

	}

	@WebMethod
	public void dummy(@WebParam Datafile datafile, @WebParam DatafileFormat datafileFormat,
			@WebParam DatafileParameter datafileParameter, @WebParam Dataset dataset,
			@WebParam DatasetParameter datasetParameter, @WebParam DatasetType datasetType,
			@WebParam Facility facility, @WebParam FacilityCycle facilityCycle,
			@WebParam InstrumentScientist facilityInstrumentScientist, @WebParam User user,
			@WebParam Instrument instrument, @WebParam Investigation investigation,
			@WebParam InvestigationType investigationType,
			@WebParam InvestigationUser investigator, @WebParam Keyword keyword,
			@WebParam ParameterType parameter, @WebParam Publication publication,
			@WebParam RelatedDatafile relatedDatafile, @WebParam Sample sample,
			@WebParam SampleParameter sampleParameter, @WebParam Shift shift,
			@WebParam Study study, @WebParam StudyInvestigation studyInvestigation,
			@WebParam StudyStatus studyStatus, @WebParam Application application,
			@WebParam Job job, @WebParam InputDataset inputDataset,
			@WebParam OutputDataset outputDataset, @WebParam InputDatafile inputDatafile,
			@WebParam OutputDatafile outputDatafile, @WebParam Group group,
			@WebParam UserGroup userGroup, @WebParam Log log) {
	}

	@WebMethod
	public EntityBaseBean get(@WebParam(name = "sessionId") String sessionId,
			@WebParam(name = "query") String query, @WebParam(name = "primaryKey") long primaryKey)
			throws IcatException {
		try {
			String userId = getUserName(sessionId);
			return BeanManager.get(userId, query, primaryKey, manager, userTransaction);
		} catch (IcatException e) {
			reportIcatException(e);
			throw e;
		} catch (Throwable e) {
			reportThrowable(e);
			throw new IcatException(IcatException.IcatExceptionType.INTERNAL, e.getMessage());
		}
	}

	@WebMethod()
	public String getApiVersion() throws IcatException {
		return Constants.API_VERSION;
	}

	@WebMethod
	public EntityInfo getEntityInfo(@WebParam(name = "beanName") String beanName)
			throws IcatException {
		return BeanManager.getEntityInfo(beanName);
	}

	@WebMethod()
	public double getRemainingMinutes(@WebParam(name = "sessionId") String sessionId)
			throws IcatException {
		return BeanManager.getRemainingMinutes(sessionId, manager);
	}

	@WebMethod
	public String getUserName(@WebParam(name = "sessionId") String sessionId) throws IcatException {
		return BeanManager.getUserName(sessionId, manager);
	}

	@WebMethod
	public String login(@WebParam(name = "plugin") String plugin,
			@WebParam(name = "credentials") Map<String, String> credentials) throws IcatException {
		MessageContext msgCtxt = webServiceContext.getMessageContext();
		HttpServletRequest req = (HttpServletRequest) msgCtxt.get(MessageContext.SERVLET_REQUEST);
		Authenticator authenticator = authPlugins.get(plugin);
		if (authenticator == null) {
			throw new IcatException(IcatException.IcatExceptionType.SESSION,
					"Authenticator mnemonic " + plugin + " not recognised");
		}

		logger.debug("Using " + plugin + " to authenticate");
		String userName = authenticator.authenticate(credentials, req.getRemoteAddr())
				.getUserName();
		return BeanManager.login(userName, lifetimeMinutes, manager, userTransaction);
	}

	@WebMethod
	public void logout(@WebParam(name = "sessionId") String sessionId) throws IcatException {
		BeanManager.logout(sessionId, manager, userTransaction);
	}

	@WebMethod
	public void refresh(@WebParam(name = "sessionId") String sessionId) throws IcatException {
		BeanManager.refresh(sessionId, lifetimeMinutes, manager, userTransaction);
	}

	@WebMethod
	public List<?> search(@WebParam(name = "sessionId") String sessionId,
			@WebParam(name = "query") String query) throws IcatException {
		try {
			String userId = getUserName(sessionId);
			return BeanManager.search(userId, query, manager, userTransaction);
		} catch (IcatException e) {
			reportIcatException(e);
			throw e;
		} catch (Throwable e) {
			reportThrowable(e);
			throw new IcatException(IcatException.IcatExceptionType.INTERNAL, e.getMessage());
		}
	}

	@WebMethod
	public void testCreate(@WebParam(name = "sessionId") String sessionId,
			@WebParam(name = "bean") EntityBaseBean bean) throws IcatException {
		String userId = getUserName(sessionId);
		BeanManager.testCreate(userId, bean, manager, userTransaction);
	}

	@WebMethod
	public void testDelete(@WebParam(name = "sessionId") String sessionId,
			@WebParam(name = "bean") EntityBaseBean bean) throws IcatException {
		String userId = getUserName(sessionId);
		BeanManager.testDelete(userId, bean, manager, userTransaction);
	}

	@WebMethod
	public void testUpdate(@WebParam(name = "sessionId") String sessionId,
			@WebParam(name = "bean") EntityBaseBean bean) throws IcatException {
		String userId = getUserName(sessionId);
		BeanManager.testUpdate(userId, bean, manager, userTransaction);
	}

	@WebMethod
	public void update(@WebParam(name = "sessionId") String sessionId,
			@WebParam(name = "bean") EntityBaseBean bean) throws IcatException {
		try {
			String userId = getUserName(sessionId);
			transmitter.processMessage(BeanManager.update(userId, bean, manager, userTransaction));
		} catch (IcatException e) {
			reportIcatException(e);
			throw e;
		} catch (Throwable e) {
			reportThrowable(e);
			throw new IcatException(IcatException.IcatExceptionType.INTERNAL, e.getMessage());
		}
	}

	@AroundInvoke
	private Object logMethods(InvocationContext ctx) throws IcatException {

		String className = ctx.getTarget().getClass().getName();
		String methodName = ctx.getMethod().getName();
		String target = className + "." + methodName + "()";

		long start = System.currentTimeMillis();

		logger.debug("Invoking " + target);
		try {
			return ctx.proceed();
		} catch (IcatException e) {
			throw e;
		} catch (Exception e) {
			throw new IcatException(IcatException.IcatExceptionType.INTERNAL, e.getMessage());
		} finally {
			long time = System.currentTimeMillis() - start;
			logger.debug("Method " + target + " took " + time / 1000f + "s to execute");
		}
	}

	private void reportIcatException(IcatException e) throws IcatException {
		logger.debug("IcatException " + e.getType() + " " + e.getMessage()
				+ (e.getOffset() >= 0 ? " at offset " + e.getOffset() : ""));
	}

	private void reportThrowable(Throwable e) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream s = new PrintStream(baos);
		e.printStackTrace(s);
		s.close();
		logger.error("Unexpected failure in Java "
				+ System.getProperties().getProperty("java.version") + " " + baos);
	}

}
