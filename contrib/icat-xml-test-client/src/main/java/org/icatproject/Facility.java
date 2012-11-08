
package org.icatproject;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for facility complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="facility">
 *   &lt;complexContent>
 *     &lt;extension base="{http://icatproject.org}entityBaseBean">
 *       &lt;sequence>
 *         &lt;element name="datafileFormats" type="{http://icatproject.org}datafileFormat" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="datasetTypes" type="{http://icatproject.org}datasetType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="daysUntilRelease" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="facilityCycles" type="{http://icatproject.org}facilityCycle" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="fullName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="instruments" type="{http://icatproject.org}instrument" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="investigationTypes" type="{http://icatproject.org}investigationType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="investigations" type="{http://icatproject.org}investigation" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="parameterTypes" type="{http://icatproject.org}parameterType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="sampleTypes" type="{http://icatproject.org}sampleType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="url" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "facility", propOrder = {
    "datafileFormats",
    "datasetTypes",
    "daysUntilRelease",
    "description",
    "facilityCycles",
    "fullName",
    "instruments",
    "investigationTypes",
    "investigations",
    "name",
    "parameterTypes",
    "sampleTypes",
    "url"
})
public class Facility
    extends EntityBaseBean
{

    @XmlElement(nillable = true)
    protected List<DatafileFormat> datafileFormats;
    @XmlElement(nillable = true)
    protected List<DatasetType> datasetTypes;
    protected Integer daysUntilRelease;
    protected String description;
    @XmlElement(nillable = true)
    protected List<FacilityCycle> facilityCycles;
    protected String fullName;
    @XmlElement(nillable = true)
    protected List<Instrument> instruments;
    @XmlElement(nillable = true)
    protected List<InvestigationType> investigationTypes;
    @XmlElement(nillable = true)
    protected List<Investigation> investigations;
    protected String name;
    @XmlElement(nillable = true)
    protected List<ParameterType> parameterTypes;
    @XmlElement(nillable = true)
    protected List<SampleType> sampleTypes;
    protected String url;

    /**
     * Gets the value of the datafileFormats property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the datafileFormats property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDatafileFormats().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DatafileFormat }
     * 
     * 
     */
    public List<DatafileFormat> getDatafileFormats() {
        if (datafileFormats == null) {
            datafileFormats = new ArrayList<DatafileFormat>();
        }
        return this.datafileFormats;
    }

    /**
     * Gets the value of the datasetTypes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the datasetTypes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDatasetTypes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DatasetType }
     * 
     * 
     */
    public List<DatasetType> getDatasetTypes() {
        if (datasetTypes == null) {
            datasetTypes = new ArrayList<DatasetType>();
        }
        return this.datasetTypes;
    }

    /**
     * Gets the value of the daysUntilRelease property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getDaysUntilRelease() {
        return daysUntilRelease;
    }

    /**
     * Sets the value of the daysUntilRelease property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setDaysUntilRelease(Integer value) {
        this.daysUntilRelease = value;
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
     * Gets the value of the facilityCycles property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the facilityCycles property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFacilityCycles().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FacilityCycle }
     * 
     * 
     */
    public List<FacilityCycle> getFacilityCycles() {
        if (facilityCycles == null) {
            facilityCycles = new ArrayList<FacilityCycle>();
        }
        return this.facilityCycles;
    }

    /**
     * Gets the value of the fullName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Sets the value of the fullName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFullName(String value) {
        this.fullName = value;
    }

    /**
     * Gets the value of the instruments property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the instruments property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInstruments().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Instrument }
     * 
     * 
     */
    public List<Instrument> getInstruments() {
        if (instruments == null) {
            instruments = new ArrayList<Instrument>();
        }
        return this.instruments;
    }

    /**
     * Gets the value of the investigationTypes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the investigationTypes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInvestigationTypes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InvestigationType }
     * 
     * 
     */
    public List<InvestigationType> getInvestigationTypes() {
        if (investigationTypes == null) {
            investigationTypes = new ArrayList<InvestigationType>();
        }
        return this.investigationTypes;
    }

    /**
     * Gets the value of the investigations property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the investigations property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInvestigations().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Investigation }
     * 
     * 
     */
    public List<Investigation> getInvestigations() {
        if (investigations == null) {
            investigations = new ArrayList<Investigation>();
        }
        return this.investigations;
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
     * Gets the value of the parameterTypes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the parameterTypes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getParameterTypes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ParameterType }
     * 
     * 
     */
    public List<ParameterType> getParameterTypes() {
        if (parameterTypes == null) {
            parameterTypes = new ArrayList<ParameterType>();
        }
        return this.parameterTypes;
    }

    /**
     * Gets the value of the sampleTypes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the sampleTypes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSampleTypes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SampleType }
     * 
     * 
     */
    public List<SampleType> getSampleTypes() {
        if (sampleTypes == null) {
            sampleTypes = new ArrayList<SampleType>();
        }
        return this.sampleTypes;
    }

    /**
     * Gets the value of the url property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets the value of the url property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUrl(String value) {
        this.url = value;
    }

}
