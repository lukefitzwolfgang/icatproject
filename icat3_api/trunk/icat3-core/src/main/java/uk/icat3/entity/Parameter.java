package uk.icat3.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.Marshaller;

import org.apache.log4j.Logger;

import uk.icat3.entity.ParameterType.ParameterValueType;
import uk.icat3.exceptions.BadParameterException;
import uk.icat3.exceptions.IcatInternalException;
import uk.icat3.exceptions.NoSuchObjectFoundException;
import uk.icat3.exceptions.ValidationException;

@SuppressWarnings("serial")
@MappedSuperclass
@NamedQuery(name = "Parameter.psv", query = "SELECT DISTINCT p.value FROM PermissibleStringValue p LEFT JOIN p.type t WHERE t.id = :tid")
public abstract class Parameter extends EntityBaseBean implements Serializable {

	private static Logger logger = Logger.getLogger(Parameter.class);

	@Comment("The type of the parameter")
	@JoinColumn(name = "PARAMETER_TYPE_ID", nullable = false)
	@ManyToOne
	ParameterType type;

	@Comment("The value if the parameter is a string")
	@Column(name = "STRING_VALUE")
	private String stringValue;

	@Comment("The value if the parameter is numeric")
	@Column(name = "NUMERIC_VALUE")
	private Double numericValue;

	@Comment("The value if the parameter is a date")
	@Column(name = "DATETIME_VALUE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateTimeValue;

	@Comment("The maximum value of the numeric parameter that was observed during the measurement period")
	private Double rangeTop;

	@Comment("The minimum value of the numeric parameter that was observed during the measurement period")
	private Double rangeBottom;

	@Comment("The error of the numeric parameter")
	private Double error;

	public void beforeMarshal(Marshaller source) {
		logger.trace("Marshalling Parameter for " + includes);
		if (!this.includes.contains(ParameterType.class)) {
			this.type = null;
		}
	}

	public ParameterType getType() {
		return type;
	}

	public void setType(ParameterType type) {
		this.type = type;
	}

	public String getStringValue() {
		return stringValue;
	}

	public void setStringValue(String stringValue) {
		this.stringValue = stringValue;
	}

	public Double getNumericValue() {
		return numericValue;
	}

	public void setNumericValue(Double numericValue) {
		this.numericValue = numericValue;
	}

	public Date getDateTimeValue() {
		return dateTimeValue;
	}

	public void setDateTimeValue(Date dateTimeValue) {
		this.dateTimeValue = dateTimeValue;
	}

	public Double getRangeTop() {
		return rangeTop;
	}

	public void setRangeTop(Double rangeTop) {
		this.rangeTop = rangeTop;
	}

	public Double getRangeBottom() {
		return rangeBottom;
	}

	public void setRangeBottom(Double rangeBottom) {
		this.rangeBottom = rangeBottom;
	}

	public Double getError() {
		return error;
	}

	public void setError(Double error) {
		this.error = error;
	}

	@Override
	public void preparePersist(String modId, EntityManager manager)
			throws NoSuchObjectFoundException, BadParameterException,
			IcatInternalException, ValidationException {
		super.preparePersist(modId, manager);
		check(manager);
	}
	
	private void check( EntityManager manager) throws ValidationException {
		if (type == null) {
			throw new ValidationException("Type of parameter is not set");
		}
		logger.debug("PreparePersist of type " + type.getName() + " "
				+ type.isEnforced() + " " + type.getValueType());
		if (!type.isEnforced()) {
			return;
		}
		ParameterValueType pvt = type.getValueType();
		if (pvt == ParameterValueType.NUMERIC) {
			logger.debug("Parameter of type " + type.getName()
					+ " has numeric value " + numericValue + " to be checked");
			Double min = type.getMinimumNumericValue();
			Double max = type.getMaximumNumericValue();
			if (min != null && numericValue < min) {
				throw new ValidationException("Parameter of type "
						+ type.getName() + " has value " + numericValue + " < "
						+ min);
			}
			if (max != null && numericValue > max) {
				throw new ValidationException("Parameter of type "
						+ type.getName() + " has value " + numericValue + " > "
						+ max);
			}
		} else if (pvt == ParameterValueType.STRING) {
			logger.debug("Parameter of type " + type.getName()
					+ " has string value " + stringValue + " to be checked");
			// The query is used because the ParameterType passed in may not
			// include its PermissibleStringValues
			List<String> values = manager
					.createNamedQuery("Parameter.psv", String.class)
					.setParameter("tid", type.getId()).getResultList();
			if (!values.isEmpty() && values.indexOf(stringValue) < 0) {
				throw new ValidationException("Parameter of type "
						+ type.getName() + " has value " + stringValue
						+ " not in allowed set " + values);
			}
		}
	}
	
	@Override
	public void postMergeFixup(EntityManager manager) throws NoSuchObjectFoundException, BadParameterException, IcatInternalException, ValidationException  {
		 super.postMergeFixup(manager);
		 check(manager);
	}

}
