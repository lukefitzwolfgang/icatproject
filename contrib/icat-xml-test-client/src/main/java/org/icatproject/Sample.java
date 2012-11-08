
package org.icatproject;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for sample complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="sample">
 *   &lt;complexContent>
 *     &lt;extension base="{http://icatproject.org}entityBaseBean">
 *       &lt;sequence>
 *         &lt;element name="datasets" type="{http://icatproject.org}dataset" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="investigation" type="{http://icatproject.org}investigation" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="parameters" type="{http://icatproject.org}sampleParameter" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="type" type="{http://icatproject.org}sampleType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "sample", propOrder = {
    "datasets",
    "investigation",
    "name",
    "parameters",
    "type"
})
public class Sample
    extends EntityBaseBean
{

    @XmlElement(nillable = true)
    protected List<Dataset> datasets;
    protected Investigation investigation;
    protected String name;
    @XmlElement(nillable = true)
    protected List<SampleParameter> parameters;
    protected SampleType type;

    /**
     * Gets the value of the datasets property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the datasets property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDatasets().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Dataset }
     * 
     * 
     */
    public List<Dataset> getDatasets() {
        if (datasets == null) {
            datasets = new ArrayList<Dataset>();
        }
        return this.datasets;
    }

    /**
     * Gets the value of the investigation property.
     * 
     * @return
     *     possible object is
     *     {@link Investigation }
     *     
     */
    public Investigation getInvestigation() {
        return investigation;
    }

    /**
     * Sets the value of the investigation property.
     * 
     * @param value
     *     allowed object is
     *     {@link Investigation }
     *     
     */
    public void setInvestigation(Investigation value) {
        this.investigation = value;
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
     * {@link SampleParameter }
     * 
     * 
     */
    public List<SampleParameter> getParameters() {
        if (parameters == null) {
            parameters = new ArrayList<SampleParameter>();
        }
        return this.parameters;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link SampleType }
     *     
     */
    public SampleType getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link SampleType }
     *     
     */
    public void setType(SampleType value) {
        this.type = value;
    }

}
