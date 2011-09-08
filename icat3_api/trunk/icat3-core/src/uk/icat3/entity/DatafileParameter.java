/*
 * DatafileParameter.java
 *
 * Created on 08 February 2007, 10:04
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package uk.icat3.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.log4j.Logger;

import uk.icat3.exceptions.IcatInternalException;
import uk.icat3.exceptions.ValidationException;
import uk.icat3.manager.ManagerUtil;
import uk.icat3.util.ParameterValueType;

/**
 * Entity class DatafileParameter
 * 
 * @author gjd37
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "DATAFILE_PARAMETER")
@NamedQueries({
		@NamedQuery(name = "DatafileParameter.findByDatafileId", query = "SELECT d FROM DatafileParameter d WHERE d.datafileParameterPK.datafileId = :datafileId"),
		@NamedQuery(name = "DatafileParameter.findByName", query = "SELECT d FROM DatafileParameter d WHERE d.datafileParameterPK.name = :name"),
		@NamedQuery(name = "DatafileParameter.findByUnits", query = "SELECT d FROM DatafileParameter d WHERE d.datafileParameterPK.units = :units"),
		@NamedQuery(name = "DatafileParameter.findByStringValue", query = "SELECT d FROM DatafileParameter d WHERE d.stringValue = :stringValue"),
		@NamedQuery(name = "DatafileParameter.findByNumericValue", query = "SELECT d FROM DatafileParameter d WHERE d.numericValue = :numericValue"),
		@NamedQuery(name = "DatafileParameter.findByDateTimeValue", query = "SELECT d FROM DatafileParameter d WHERE d.dateTimeValue = :dateTimeValue"),
		@NamedQuery(name = "DatafileParameter.findByRangeTop", query = "SELECT d FROM DatafileParameter d WHERE d.rangeTop = :rangeTop"),
		@NamedQuery(name = "DatafileParameter.findByRangeBottom", query = "SELECT d FROM DatafileParameter d WHERE d.rangeBottom = :rangeBottom"),
		@NamedQuery(name = "DatafileParameter.findByError", query = "SELECT d FROM DatafileParameter d WHERE d.error = :error"),
		@NamedQuery(name = "DatafileParameter.findByDescription", query = "SELECT d FROM DatafileParameter d WHERE d.description = :description"),
		@NamedQuery(name = "DatafileParameter.findByModTime", query = "SELECT d FROM DatafileParameter d WHERE d.modTime = :modTime"),
		@NamedQuery(name = "DatafileParameter.findByModId", query = "SELECT d FROM DatafileParameter d WHERE d.modId = :modId") })
@XmlRootElement
public class DatafileParameter extends EntityBaseBean implements Serializable {
	
	private static Logger logger = Logger.getLogger(DatafileParameter.class);

	/**
	 * EmbeddedId primary key field
	 */
	@EmbeddedId
	protected DatafileParameterPK datafileParameterPK;

	@Column(name = "STRING_VALUE")
	private String stringValue;

	@Column(name = "NUMERIC_VALUE")
	private Double numericValue;

	@Column(name = "DATETIME_VALUE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateTimeValue;

	@Column(name = "RANGE_TOP")
	private String rangeTop;

	@Column(name = "RANGE_BOTTOM")
	private String rangeBottom;

	@Column(name = "ERROR")
	private String error;

	@Column(name = "DESCRIPTION")
	private String description;

	@JoinColumn(name = "DATAFILE_ID", referencedColumnName = "ID", insertable = false, updatable = false)
	@ManyToOne
	@XmlTransient
	private Datafile datafile;

	@JoinColumns(value = {
			@JoinColumn(name = "NAME", referencedColumnName = "NAME", insertable = false, updatable = false),
			@JoinColumn(name = "UNITS", referencedColumnName = "UNITS", insertable = false, updatable = false) })
	@ManyToOne
	@XmlTransient
	private Parameter parameter;

	@Transient
	protected transient ParameterValueType valueType;

	/** Creates a new instance of DatafileParameter */
	public DatafileParameter() {
	}

	/**
	 * Creates a new instance of DatafileParameter with the specified values.
	 * 
	 * @param datafileParameterPK
	 *            the datafileParameterPK of the DatafileParameter
	 */
	public DatafileParameter(DatafileParameterPK datafileParameterPK) {
		this.datafileParameterPK = datafileParameterPK;
	}

	/**
	 * Creates a new instance of DatafileParameterPK with the specified values.
	 * 
	 * @param units
	 *            the units of the DatafileParameterPK
	 * @param name
	 *            the name of the DatafileParameterPK
	 * @param datafileId
	 *            the datafileId of the DatafileParameterPK
	 */
	public DatafileParameter(String units, String name, Long datafileId) {
		this.datafileParameterPK = new DatafileParameterPK(units, name, datafileId);
	}

	/**
	 * Gets the datafileParameterPK of this DatafileParameter.
	 * 
	 * @return the datafileParameterPK
	 */
	public DatafileParameterPK getDatafileParameterPK() {
		return this.datafileParameterPK;
	}

	/**
	 * Sets the datafileParameterPK of this DatafileParameter to the specified value.
	 * 
	 * @param datafileParameterPK
	 *            the new datafileParameterPK
	 */
	public void setDatafileParameterPK(DatafileParameterPK datafileParameterPK) {
		this.datafileParameterPK = datafileParameterPK;
	}

	/**
	 * Gets the stringValue of this DatafileParameter.
	 * 
	 * @return the stringValue
	 */
	public String getStringValue() {
		return this.stringValue;
	}

	/**
	 * Sets the stringValue of this DatafileParameter to the specified value.
	 * 
	 * @param stringValue
	 *            the new stringValue
	 */
	public void setStringValue(String stringValue) {
		this.stringValue = stringValue;
	}

	/**
	 * Gets the numericValue of this DatafileParameter.
	 * 
	 * @return the numericValue
	 */
	public Double getNumericValue() {
		return this.numericValue;
	}

	/**
	 * Sets the numericValue of this DatafileParameter to the specified value.
	 * 
	 * @param numericValue
	 *            the new numericValue
	 */
	public void setNumericValue(Double numericValue) {
		this.numericValue = numericValue;
	}

	/**
	 * Gets the data time of the DatafileParameter
	 * 
	 * @return Date in milliseconds.
	 */
	public Date getDateTimeValue() {
		return dateTimeValue;
	}

	/**
	 * Sets the dataTimeValue of this DatafileParameter to the specified value. the time can be set
	 * in milliseconds.
	 * 
	 * @param dateTimeValue
	 *            the new date time value
	 */
	public void setDateTimeValue(Date dateTimeValue) {
		this.dateTimeValue = dateTimeValue;
	}

	/**
	 * Gets the rangeTop of this DatafileParameter.
	 * 
	 * @return the rangeTop
	 */
	public String getRangeTop() {
		return this.rangeTop;
	}

	/**
	 * Sets the rangeTop of this DatafileParameter to the specified value.
	 * 
	 * @param rangeTop
	 *            the new rangeTop
	 */
	public void setRangeTop(String rangeTop) {
		this.rangeTop = rangeTop;
	}

	/**
	 * Gets the rangeBottom of this DatafileParameter.
	 * 
	 * @return the rangeBottom
	 */
	public String getRangeBottom() {
		return this.rangeBottom;
	}

	/**
	 * Sets the rangeBottom of this DatafileParameter to the specified value.
	 * 
	 * @param rangeBottom
	 *            the new rangeBottom
	 */
	public void setRangeBottom(String rangeBottom) {
		this.rangeBottom = rangeBottom;
	}

	/**
	 * Gets the error of this DatafileParameter.
	 * 
	 * @return the error
	 */
	public String getError() {
		return this.error;
	}

	/**
	 * Sets the error of this DatafileParameter to the specified value.
	 * 
	 * @param error
	 *            the new error
	 */
	public void setError(String error) {
		this.error = error;
	}

	/**
	 * Gets the description of this DatafileParameter.
	 * 
	 * @return the description
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * Sets the description of this DatafileParameter to the specified value.
	 * 
	 * @param description
	 *            the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the datafile of this DatafileParameter.
	 * 
	 * @return the datafile
	 */
	@XmlTransient
	public Datafile getDatafile() {
		return this.datafile;
	}

	/**
	 * Sets the datafile of this DatafileParameter to the specified value.
	 * 
	 * @param datafile
	 *            the new datafile
	 */

	public void setDatafile(Datafile datafile) {
		this.datafile = datafile;
	}

	/**
	 * Gets the parameter of this DatafileParameter.
	 * 
	 * @return the parameter
	 */
	@XmlTransient
	public Parameter getParameter() {
		return this.parameter;
	}

	/**
	 * Sets the parameter of this DatafileParameter to the specified value.
	 * 
	 * @param parameter
	 *            the new parameter
	 */
	public void setParameter(Parameter parameter) {
		this.parameter = parameter;
	}

	// /**
	// * Gets the numeric of this DatafileParameter.
	// * @return the parameter
	// */
	// public boolean isNumeric() {
	// if(stringValue != null && numericValue == null) return false;
	// else if(numericValue != null && stringValue == null) return true;
	// else return false;
	// }

	/**
	 * Gets the numeric of this DatafileParameter.
	 * 
	 * @return the parameter
	 */
	public ParameterValueType getValueType() {
		if (stringValue != null && numericValue == null)
			return ParameterValueType.STRING;
		else if (numericValue != null && stringValue == null)
			return ParameterValueType.NUMERIC;
		else if (numericValue == null && stringValue == null && dateTimeValue != null)
			return ParameterValueType.DATE_AND_TIME;
		return ParameterValueType.STRING;
	}

	/**
	 * Sets the numeric of this DatafileParameter to the specified value.
	 * 
	 * @param numeric
	 *            the new parameter
	 */
	public void setValueType(ParameterValueType valueType) {
		this.valueType = valueType;
	}

	/**
	 * Returns a hash code value for the object. This implementation computes a hash code value
	 * based on the id fields in this object.
	 * 
	 * @return a hash code value for this object.
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (this.datafileParameterPK != null ? this.datafileParameterPK.hashCode() : 0);
		return hash;
	}

	/**
	 * Determines whether another object is equal to this DatafileParameter. The result is
	 * <code>true</code> if and only if the argument is not null and is a DatafileParameter object
	 * that has the same id field values as this object.
	 * 
	 * @param object
	 *            the reference object with which to compare
	 * @return <code>true</code> if this object is the same as the argument; <code>false</code>
	 *         otherwise.
	 */
	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof DatafileParameter)) {
			return false;
		}
		DatafileParameter other = (DatafileParameter) object;
		if (this.datafileParameterPK != other.datafileParameterPK
				&& (this.datafileParameterPK == null || !this.datafileParameterPK.equals(other.datafileParameterPK)))
			return false;
		return true;
	}

	/**
	 * Returns a string representation of the object. This implementation constructs that
	 * representation based on the id fields.
	 * 
	 * @return a string representation of the object.
	 */
	@Override
	public String toString() {
		return "DatafileParameter[datafileParameterPK=" + datafileParameterPK + "]";
	}

	/**
	 * Overrides the isValid function, checks that the parameters and valid for the datafile and is
	 * set to numeric or string
	 * 
	 * @throws ValidationException
	 * @return
	 * @throws IcatInternalException 
	 */
	@Override
	public void isValid(EntityManager manager, boolean deepValidation) throws ValidationException, IcatInternalException {
		super.isValid(manager, deepValidation);
		if (datafileParameterPK == null)
			throw new ValidationException(this + " primary key cannot be null");

		// check private key
		datafileParameterPK.isValid();

		// check valid
		String paramName = this.getDatafileParameterPK().getName();
		String paramUnits = this.getDatafileParameterPK().getUnits();

		// check if this name is parameter table
		ParameterPK paramPK = new ParameterPK();
		paramPK.setName(paramName);
		paramPK.setUnits(paramUnits);

		Parameter parameterDB = manager.find(Parameter.class, paramPK);

		// check paramPK is in the parameter table
		if (parameterDB == null) {
			logger.info(datafileParameterPK
					+ " is not in the parameter table as a data file parameter so been marked as unverified and inserting new row in Parameter table");
			// add new parameter into database
			parameterDB = ManagerUtil.addParameter(this.modId, manager, paramName, paramUnits, getValueType());
			if (parameterDB == null)
				throw new ValidationException("Parameter: " + paramName + " with units: " + paramUnits
						+ " cannot be inserted into the Parameter table.");
		}

		// check that it is a dataset parameter
		if (!parameterDB.isDatafileParameter())
			throw new ValidationException("DatafileParameter: " + paramName + " with units: " + paramUnits
					+ " is not a data file parameter.");

		// check is numeric
		if (parameterDB.isNumeric()) {
			if (this.getStringValue() != null)
				throw new ValidationException("DatafileParameter: " + paramName + " with units: " + paramUnits
						+ " must be a numeric value only.");
		}

		// check if string
		if (!parameterDB.isNumeric()) {
			if (this.getNumericValue() != null)
				throw new ValidationException("DatafileParameter: " + paramName + " with units: " + paramUnits
						+ " must be a string value only.");
		}

		// check if datafile parameter is already in DB
		DatafileParameter paramDB = manager.find(DatafileParameter.class, datafileParameterPK);
		if (paramDB != null && !paramDB.getDatafileParameterPK().equals(datafileParameterPK))
			throw new ValidationException("DatafileParameter: " + paramName + " with units: " + paramUnits
					+ " is already is a parameter of the datafile.");

		// check that the parameter datafile id is the same as actual datafile id
		if (!datafileParameterPK.getDatafileId().equals(getDatafile().getId())) {
			throw new ValidationException("DatafileParameter: " + paramName + " with units: " + paramUnits
					+ " has datafile id: " + datafileParameterPK.getDatafileId()
					+ " that does not corresponds to its parent datafile id: " + getDatafile().getId());
		}
	}

	public void preparePersist(String modId, EntityManager manager) {
		this.modId = modId;
	}

	@Override
	public Object getPK() {
		return datafileParameterPK;
	}

}
