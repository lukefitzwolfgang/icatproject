
package org.icatproject;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for datafile complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="datafile">
 *   &lt;complexContent>
 *     &lt;extension base="{http://icatproject.org}entityBaseBean">
 *       &lt;sequence>
 *         &lt;element name="checksum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="datafileCreateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="datafileFormat" type="{http://icatproject.org}datafileFormat" minOccurs="0"/>
 *         &lt;element name="datafileModTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element ref="{http://icatproject.org}dataset" minOccurs="0"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="destDatafiles" type="{http://icatproject.org}relatedDatafile" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="doi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fileSize" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="inputDatafiles" type="{http://icatproject.org}inputDatafile" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="location" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="outputDatafiles" type="{http://icatproject.org}outputDatafile" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="parameters" type="{http://icatproject.org}datafileParameter" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="sourceDatafiles" type="{http://icatproject.org}relatedDatafile" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "datafile", propOrder = {
    "checksum",
    "datafileCreateTime",
    "datafileFormat",
    "datafileModTime",
    "dataset",
    "description",
    "destDatafiles",
    "doi",
    "fileSize",
    "inputDatafiles",
    "location",
    "name",
    "outputDatafiles",
    "parameters",
    "sourceDatafiles"
})
public class Datafile
    extends EntityBaseBean
{

    protected String checksum;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar datafileCreateTime;
    protected DatafileFormat datafileFormat;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar datafileModTime;
    @XmlElement(namespace = "http://icatproject.org")
    protected Dataset dataset;
    protected String description;
    @XmlElement(nillable = true)
    protected List<RelatedDatafile> destDatafiles;
    protected String doi;
    protected Long fileSize;
    @XmlElement(nillable = true)
    protected List<InputDatafile> inputDatafiles;
    protected String location;
    protected String name;
    @XmlElement(nillable = true)
    protected List<OutputDatafile> outputDatafiles;
    @XmlElement(nillable = true)
    protected List<DatafileParameter> parameters;
    @XmlElement(nillable = true)
    protected List<RelatedDatafile> sourceDatafiles;

    /**
     * Gets the value of the checksum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChecksum() {
        return checksum;
    }

    /**
     * Sets the value of the checksum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChecksum(String value) {
        this.checksum = value;
    }

    /**
     * Gets the value of the datafileCreateTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDatafileCreateTime() {
        return datafileCreateTime;
    }

    /**
     * Sets the value of the datafileCreateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDatafileCreateTime(XMLGregorianCalendar value) {
        this.datafileCreateTime = value;
    }

    /**
     * Gets the value of the datafileFormat property.
     * 
     * @return
     *     possible object is
     *     {@link DatafileFormat }
     *     
     */
    public DatafileFormat getDatafileFormat() {
        return datafileFormat;
    }

    /**
     * Sets the value of the datafileFormat property.
     * 
     * @param value
     *     allowed object is
     *     {@link DatafileFormat }
     *     
     */
    public void setDatafileFormat(DatafileFormat value) {
        this.datafileFormat = value;
    }

    /**
     * Gets the value of the datafileModTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDatafileModTime() {
        return datafileModTime;
    }

    /**
     * Sets the value of the datafileModTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDatafileModTime(XMLGregorianCalendar value) {
        this.datafileModTime = value;
    }

    /**
     * Gets the value of the dataset property.
     * 
     * @return
     *     possible object is
     *     {@link Dataset }
     *     
     */
    public Dataset getDataset() {
        return dataset;
    }

    /**
     * Sets the value of the dataset property.
     * 
     * @param value
     *     allowed object is
     *     {@link Dataset }
     *     
     */
    public void setDataset(Dataset value) {
        this.dataset = value;
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
     * Gets the value of the destDatafiles property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the destDatafiles property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDestDatafiles().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RelatedDatafile }
     * 
     * 
     */
    public List<RelatedDatafile> getDestDatafiles() {
        if (destDatafiles == null) {
            destDatafiles = new ArrayList<RelatedDatafile>();
        }
        return this.destDatafiles;
    }

    /**
     * Gets the value of the doi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDoi() {
        return doi;
    }

    /**
     * Sets the value of the doi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDoi(String value) {
        this.doi = value;
    }

    /**
     * Gets the value of the fileSize property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getFileSize() {
        return fileSize;
    }

    /**
     * Sets the value of the fileSize property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setFileSize(Long value) {
        this.fileSize = value;
    }

    /**
     * Gets the value of the inputDatafiles property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the inputDatafiles property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInputDatafiles().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InputDatafile }
     * 
     * 
     */
    public List<InputDatafile> getInputDatafiles() {
        if (inputDatafiles == null) {
            inputDatafiles = new ArrayList<InputDatafile>();
        }
        return this.inputDatafiles;
    }

    /**
     * Gets the value of the location property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the value of the location property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocation(String value) {
        this.location = value;
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
     * Gets the value of the outputDatafiles property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the outputDatafiles property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOutputDatafiles().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OutputDatafile }
     * 
     * 
     */
    public List<OutputDatafile> getOutputDatafiles() {
        if (outputDatafiles == null) {
            outputDatafiles = new ArrayList<OutputDatafile>();
        }
        return this.outputDatafiles;
    }

    /**
     * Gets the value of the parameters property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the parameters property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getParameters().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DatafileParameter }
     * 
     * 
     */
    public List<DatafileParameter> getParameters() {
        if (parameters == null) {
            parameters = new ArrayList<DatafileParameter>();
        }
        return this.parameters;
    }

    /**
     * Gets the value of the sourceDatafiles property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the sourceDatafiles property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSourceDatafiles().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RelatedDatafile }
     * 
     * 
     */
    public List<RelatedDatafile> getSourceDatafiles() {
        if (sourceDatafiles == null) {
            sourceDatafiles = new ArrayList<RelatedDatafile>();
        }
        return this.sourceDatafiles;
    }

}
