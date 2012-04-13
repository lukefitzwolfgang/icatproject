package uk.icat3.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.log4j.Logger;

import uk.icat3.exceptions.BadParameterException;
import uk.icat3.exceptions.IcatInternalException;
import uk.icat3.exceptions.NoSuchObjectFoundException;
import uk.icat3.exceptions.ValidationException;
import uk.icat3.parser.Input;
import uk.icat3.parser.LexerException;
import uk.icat3.parser.ParserException;
import uk.icat3.parser.RestrictedBean;
import uk.icat3.parser.Token;
import uk.icat3.parser.Tokenizer;

@Comment("Registers a request for a JMS notification to be sent out")
@SuppressWarnings("serial")
@Entity
@TableGenerator(name = "notificationRequestGenerator", pkColumnValue = "NotificationRequest")
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "NAME" }) })
@NamedQueries({
		@NamedQuery(name = "NotificationRequest.CreateQuery", query = "SELECT DISTINCT nr FROM NotificationRequest nr WHERE nr.bean = :bean AND nr.c = TRUE"),
		@NamedQuery(name = "NotificationRequest.ReadQuery", query = "SELECT DISTINCT nr FROM NotificationRequest nr WHERE nr.bean = :bean AND nr.r = TRUE "),
		@NamedQuery(name = "NotificationRequest.UpdateQuery", query = "SELECT DISTINCT nr FROM NotificationRequest nr WHERE nr.bean = :bean AND nr.u = TRUE "),
		@NamedQuery(name = "NotificationRequest.DeleteQuery", query = "SELECT DISTINCT nr FROM NotificationRequest nr WHERE nr.bean = :bean AND nr.d = TRUE"),
		@NamedQuery(name = "NotificationRequest.SearchQuery", query = "SELECT DISTINCT nr FROM NotificationRequest nr WHERE nr.bean = :bean AND nr.r = TRUE AND nr.crudJPQL IS NULL") })
public class NotificationRequest extends EntityBaseBean implements Serializable {

	public enum DestType {
		PUBSUB, P2P
	}

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "notificationRequestGenerator")
	private Long id;

	private final static Logger logger = Logger.getLogger(NotificationRequest.class);

	public static final String CREATE_QUERY = "NotificationRequest.CreateQuery";

	public static final String READ_QUERY = "NotificationRequest.ReadQuery";

	public static final String UPDATE_QUERY = "NotificationRequest.UpdateQuery";

	public static final String DELETE_QUERY = "NotificationRequest.DeleteQuery";

	public static final String SEARCH_QUERY = "NotificationRequest.SearchQuery";

	@Comment("A unique name for the notification request. It is not the key so it can be updated.")
	@Column(name = "NAME", nullable = false)
	private String name;

	@Comment("An enum which may be PUBSUB or P2P")
	@Column(nullable = false)
	private DestType destType;

	@Comment("Contains letters from the set \"CRUD\"")
	@Column(nullable = false, length=4)
	private String crudFlags;

	@Comment("When to send notifications")
	@Column(nullable = false)
	private String what;

	@XmlTransient
	private String bean;

	@Column
	private String jmsOptions;

	@XmlTransient
	private boolean c;

	@XmlTransient
	private boolean r;

	@XmlTransient
	private boolean u;

	@XmlTransient
	private boolean d;

	@XmlTransient
	private String crudJPQL;

	private String datatypes;

	@XmlTransient
	private boolean useridWanted;

	@XmlTransient
	private boolean entityNameWanted;

	@XmlTransient
	private boolean keyWanted;

	@XmlTransient
	private boolean argsWanted;

	@XmlTransient
	private boolean notificationNameWanted;

	public NotificationRequest() {
	}

	private void fixup() throws BadParameterException, IcatInternalException {
		this.crudFlags = this.crudFlags.toUpperCase().trim();
		if (this.crudFlags.isEmpty()) {
			throw new BadParameterException("CrudFlags is empty");
		}
		for (int i = 0; i < this.crudFlags.length(); i++) {
			final char ch = this.crudFlags.charAt(i);
			if (ch == 'C') {
				this.c = true;
			} else if (ch == 'R') {
				this.r = true;
			} else if (ch == 'U') {
				this.u = true;
			} else if (ch == 'D') {
				this.d = true;
			} else {
				throw new BadParameterException("CrudFlags value " + this.crudFlags + " contains " + ch);
			}
		}

		List<Token> tokens = null;
		try {
			tokens = Tokenizer.getTokens(this.what);
		} catch (final LexerException e) {
			throw new BadParameterException(e.getMessage());
		}
		final Input input = new Input(tokens);
		RestrictedBean r;
		try {
			r = new RestrictedBean(input);
		} catch (final ParserException e) {
			throw new BadParameterException(e.getMessage());
		}
		this.crudJPQL = r.getQuery();
		this.bean = r.getBean();

		if (this.datatypes != null) {
			for (final String datatype : this.datatypes.toUpperCase().split("\\s+")) {
				if (datatype.equals("NOTIFICATIONNAME")) {
					this.notificationNameWanted = true;
				} else if (datatype.equals("USERID")) {
					this.useridWanted = true;
				} else if (datatype.equals("ENTITYNAME")) {
					this.entityNameWanted = true;
				} else if (datatype.equals("ENTITYKEY")) {
					this.keyWanted = true;
				} else if (datatype.equals("CALLARGS")) {
					this.argsWanted = true;
				} else {
					throw new BadParameterException("Datatypes value " + this.datatypes + " contains " + datatype);
				}
			}
		}
		if (this.jmsOptions != null) {
			for (final String jmsOption : this.jmsOptions.toUpperCase().split("\\s+")) {
				if (jmsOption.equals("PTP")) {
				} else {
					throw new BadParameterException("JmsOptions value " + this.jmsOptions + " contains " + jmsOption);
				}
			}
		}

	}

	@XmlTransient
	public String getBean() {
		return this.bean;
	}

	public String getCrudFlags() {
		return this.crudFlags;
	}

	@XmlTransient
	public String getCrudJPQL() {
		return this.crudJPQL;
	}

	public String getDatatypes() {
		return this.datatypes;
	}

	public DestType getDestType() {
		return this.destType;
	}

	public Long getId() {
		return this.id;
	}

	public String getJmsOptions() {
		return this.jmsOptions;
	}

	public String getName() {
		return this.name;
	}

	@Override
	public Object getPK() {
		return this.id;
	}

	public String getWhat() {
		return this.what;
	}

	@XmlTransient
	public boolean isArgsWanted() {
		return this.argsWanted;
	}

	@XmlTransient
	public boolean isC() {
		return this.c;
	}

	@XmlTransient
	public boolean isD() {
		return this.d;
	}

	@XmlTransient
	public boolean isEntityNameWanted() {
		return this.entityNameWanted;
	}

	@XmlTransient
	public boolean isKeyWanted() {
		return this.keyWanted;
	}

	@XmlTransient
	public boolean isNotificationNameWanted() {
		return this.notificationNameWanted;
	}

	@XmlTransient
	public boolean isR() {
		return this.r;
	}

	@XmlTransient
	public boolean isU() {
		return this.u;
	}

	@XmlTransient
	public boolean isUseridWanted() {
		return this.useridWanted;
	}

	@Override
	public void postMergeFixup(EntityManager manager) throws NoSuchObjectFoundException, BadParameterException,
			IcatInternalException, ValidationException {
		super.postMergeFixup(manager);
		this.c = false;
		this.r = false;
		this.u = false;
		this.d = false;
		this.notificationNameWanted = false;
		this.useridWanted = false;
		this.entityNameWanted = false;
		this.keyWanted = false;
		this.argsWanted = false;
		this.fixup();
		logger.debug("postMergeFixup of NotificationRequest " + this.name + " for " + this.crudFlags + " of "
				+ this.what);
	}

	@Override
	public void preparePersist(String modId, EntityManager manager) throws NoSuchObjectFoundException,
			BadParameterException, IcatInternalException, ValidationException {
		super.preparePersist(modId, manager);
		this.fixup();
		logger.debug("PreparePersist of NotificationRequest " + this.name + " for " + this.crudFlags + " of "
				+ this.what);
	}

	public void setArgsWanted(boolean argsWanted) {
		this.argsWanted = argsWanted;
	}

	public void setBean(String bean) {
		this.bean = bean;
	}

	public void setC(boolean c) {
		this.c = c;
	}

	public void setCrudFlags(String crudFlags) {
		this.crudFlags = crudFlags;
	}

	public void setCrudJPQL(String crudJPQL) {
		this.crudJPQL = crudJPQL;
	}

	public void setD(boolean d) {
		this.d = d;
	}

	public void setDatatypes(String datatypes) {
		this.datatypes = datatypes;

	}

	public void setDestType(DestType destType) {
		this.destType = destType;
	}

	public void setEntityNameWanted(boolean entityNameWanted) {
		this.entityNameWanted = entityNameWanted;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setJmsOptions(String jmsOptions) {
		this.jmsOptions = jmsOptions;
	}

	public void setKeyWanted(boolean keyWanted) {
		this.keyWanted = keyWanted;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNameWanted(boolean nameWanted) {
		this.entityNameWanted = nameWanted;
	}

	public void setNotificationNameWanted(boolean notificationNameWanted) {
		this.notificationNameWanted = notificationNameWanted;
	}

	public void setR(boolean r) {
		this.r = r;
	}

	public void setU(boolean u) {
		this.u = u;
	}

	public void setUserid(boolean userid) {
		this.useridWanted = userid;
	}

	public void setUseridWanted(boolean useridWanted) {
		this.useridWanted = useridWanted;
	}

	public void setWhat(String what) {
		this.what = what;
	}

}
