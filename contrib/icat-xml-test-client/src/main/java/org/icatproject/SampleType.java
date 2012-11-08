
package org.icatproject;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for sampleType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="sampleType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://icatproject.org}entityBaseBean">
 *       &lt;sequence>
 *         &lt;element name="facility" type="{http://icatproject.org}facility" minOccurs="0"/>
 *         &lt;element name="molecularFormula" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="safetyInformation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="samples" type="{http://icatproject.org}sample" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "sampleType", propOrder = {
    "facility",
    "molecularFormula",
    "name",
    "safetyInformation",
    "samples"
})
public class SampleType
    extends EntityBaseBean
{

    protected Facility facility;
    protected String molecularFormula;
    protected String name;
    protected String safetyInformation;
    @XmlElement(nillable = true)
    protected List<Sample> samples;

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
     * Gets the value of the molecularFormula property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMolecularFormula() {
        return molecularFormula;
    }

    /**
     * Sets the value of the molecularFormula property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMolecularFormula(String value) {
        this.molecularFormula = value;
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
     * Gets the value of the safetyInformation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSafetyInformation() {
        return safetyInformation;
    }

    /**
     * Sets the value of the safetyInformation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSafetyInformation(String value) {
        this.safetyInformation = value;
    }

    /**
     * Gets the value of the samples property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the samples property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSamples().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Sample }
     * 
     * 
     */
    public List<Sample> getSamples() {
        if (samples == null) {
            samples = new ArrayList<Sample>();
        }
        return this.samples;
    }

}
