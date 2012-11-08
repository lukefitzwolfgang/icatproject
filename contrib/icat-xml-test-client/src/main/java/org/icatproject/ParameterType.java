
package org.icatproject;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for parameterType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="parameterType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://icatproject.org}entityBaseBean">
 *       &lt;sequence>
 *         &lt;element name="applicableToDatafile" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="applicableToDataset" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="applicableToInvestigation" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="applicableToSample" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="datafileParameters" type="{http://icatproject.org}datafileParameter" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="datasetParameters" type="{http://icatproject.org}datasetParameter" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="enforced" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="facility" type="{http://icatproject.org}facility" minOccurs="0"/>
 *         &lt;element name="investigationParameters" type="{http://icatproject.org}investigationParameter" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="maximumNumericValue" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="minimumNumericValue" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="permissibleStringValues" type="{http://icatproject.org}permissibleStringValue" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="sampleParameters" type="{http://icatproject.org}sampleParameter" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="units" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="unitsFullName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="valueType" type="{http://icatproject.org}parameterValueType" minOccurs="0"/>
 *         &lt;element name="verified" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "parameterType", propOrder = {
    "applicableToDatafile",
    "applicableToDataset",
    "applicableToInvestigation",
    "applicableToSample",
    "datafileParameters",
    "datasetParameters",
    "description",
    "enforced",
    "facility",
    "investigationParameters",
    "maximumNumericValue",
    "minimumNumericValue",
    "name",
    "permissibleStringValues",
    "sampleParameters",
    "units",
    "unitsFullName",
    "valueType",
    "verified"
})
public class ParameterType
    extends EntityBaseBean
{

    protected boolean applicableToDatafile;
    protected boolean applicableToDataset;
    protected boolean applicableToInvestigation;
    protected boolean applicableToSample;
    @XmlElement(nillable = true)
    protected List<DatafileParameter> datafileParameters;
    @XmlElement(nillable = true)
    protected List<DatasetParameter> datasetParameters;
    protected String description;
    protected boolean enforced;
    protected Facility facility;
    @XmlElement(nillable = true)
    protected List<InvestigationParameter> investigationParameters;
    protected Double maximumNumericValue;
    protected Double minimumNumericValue;
    protected String name;
    @XmlElement(nillable = true)
    protected List<PermissibleStringValue> permissibleStringValues;
    @XmlElement(nillable = true)
    protected List<SampleParameter> sampleParameters;
    protected String units;
    protected String unitsFullName;
    protected ParameterValueType valueType;
    protected boolean verified;

    /**
     * Gets the value of the applicableToDatafile property.
     * 
     */
    public boolean isApplicableToDatafile() {
        return applicableToDatafile;
    }

    /**
     * Sets the value of the applicableToDatafile property.
     * 
     */
    public void setApplicableToDatafile(boolean value) {
        this.applicableToDatafile = value;
    }

    /**
     * Gets the value of the applicableToDataset property.
     * 
     */
    public boolean isApplicableToDataset() {
        return applicableToDataset;
    }

    /**
     * Sets the value of the applicableToDataset property.
     * 
     */
    public void setApplicableToDataset(boolean value) {
        this.applicableToDataset = value;
    }

    /**
     * Gets the value of the applicableToInvestigation property.
     * 
     */
    public boolean isApplicableToInvestigation() {
        return applicableToInvestigation;
    }

    /**
     * Sets the value of the applicableToInvestigation property.
     * 
     */
    public void setApplicableToInvestigation(boolean value) {
        this.applicableToInvestigation = value;
    }

    /**
     * Gets the value of the applicableToSample property.
     * 
     */
    public boolean isApplicableToSample() {
        return applicableToSample;
    }

    /**
     * Sets the value of the applicableToSample property.
     * 
     */
    public void setApplicableToSample(boolean value) {
        this.applicableToSample = value;
    }

    /**
     * Gets the value of the datafileParameters property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the datafileParameters property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDatafileParameters().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DatafileParameter }
     * 
     * 
     */
    public List<DatafileParameter> getDatafileParameters() {
        if (datafileParameters == null) {
            datafileParameters = new ArrayList<DatafileParameter>();
        }
        return this.datafileParameters;
    }

    /**
     * Gets the value of the datasetParameters property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the datasetParameters property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDatasetParameters().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DatasetParameter }
     * 
     * 
     */
    public List<DatasetParameter> getDatasetParameters() {
        if (datasetParameters == null) {
            datasetParameters = new ArrayList<DatasetParameter>();
        }
        return this.datasetParameters;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the enforced property.
     * 
     */
    public boolean isEnforced() {
        return enforced;
    }

    /**
     * Sets the value of the enforced property.
     * 
     */
    public void setEnforced(boolean value) {
        this.enforced = value;
    }

    /**
     * Gets the value of the facility property.
     * 
     * @return
     *     possible object is
     *     {@link Facility }
     *     
     */
    public Facility getFacility() {
        return facility;
    }

    /**
     * Sets the value of the facility property.
     * 
     * @param value
     *     allowed object is
     *     {@link Facility }
     *     
     */
    public void setFacility(Facility value) {
        this.facility = value;
    }

    /**
     * Gets the value of the investigationParameters property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the investigationParameters property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInvestigationParameters().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InvestigationParameter }
     * 
     * 
     */
    public List<InvestigationParameter> getInvestigationParameters() {
        if (investigationParameters == null) {
            investigationParameters = new ArrayList<InvestigationParameter>();
        }
        return this.investigationParameters;
    }

    /**
     * Gets the value of the maximumNumericValue property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getMaximumNumericValue() {
        return maximumNumericValue;
    }

    /**
     * Sets the value of the maximumNumericValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setMaximumNumericValue(Double value) {
        this.maximumNumericValue = value;
    }

    /**
     * Gets the value of the minimumNumericValue property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getMinimumNumericValue() {
        return minimumNumericValue;
    }

    /**
     * Sets the value of the minimumNumericValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setMinimumNumericValue(Double value) {
        this.minimumNumericValue = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the permissibleStringValues property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the permissibleStringValues property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPermissibleStringValues().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PermissibleStringValue }
     * 
     * 
     */
    public List<PermissibleStringValue> getPermissibleStringValues() {
        if (permissibleStringValues == null) {
            permissibleStringValues = new ArrayList<PermissibleStringValue>();
        }
        return this.permissibleStringValues;
    }

    /**
     * Gets the value of the sampleParameters property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the sampleParameters property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSampleParameters().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SampleParameter }
     * 
     * 
     */
    public List<SampleParameter> getSampleParameters() {
        if (sampleParameters == null) {
            sampleParameters = new ArrayList<SampleParameter>();
        }
        return this.sampleParameters;
    }

    /**
     * Gets the value of the units property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnits() {
        return units;
    }

    /**
     * Sets the value of the units property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnits(String value) {
        this.units = value;
    }

    /**
     * Gets the value of the unitsFullName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnitsFullName() {
        return unitsFullName;
    }

    /**
     * Sets the value of the unitsFullName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnitsFullName(String value) {
        this.unitsFullName = value;
    }

    /**
     * Gets the value of the valueType property.
     * 
     * @return
     *     possible object is
     *     {@link ParameterValueType }
     *     
     */
    public ParameterValueType getValueType() {
        return valueType;
    }

    /**
     * Sets the value of the valueType property.
     * 
     * @param value
     *     allowed object is
     *     {@link ParameterValueType }
     *     
     */
    public void setValueType(ParameterValueType value) {
        this.valueType = value;
    }

    /**
     * Gets the value of the verified property.
     * 
     */
    public boolean isVerified() {
        return verified;
    }

    /**
     * Sets the value of the verified property.
     * 
     */
    public void setVerified(boolean value) {
        this.verified = value;
    }

}
