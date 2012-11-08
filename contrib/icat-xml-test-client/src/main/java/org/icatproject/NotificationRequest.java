
package org.icatproject;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for notificationRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="notificationRequest">
 *   &lt;complexContent>
 *     &lt;extension base="{http://icatproject.org}entityBaseBean">
 *       &lt;sequence>
 *         &lt;element name="crudFlags" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="datatypes" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="destType" type="{http://icatproject.org}destType" minOccurs="0"/>
 *         &lt;element name="jmsOptions" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="what" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "notificationRequest", propOrder = {
    "crudFlags",
    "datatypes",
    "destType",
    "jmsOptions",
    "name",
    "what"
})
public class NotificationRequest
    extends EntityBaseBean
{

    protected String crudFlags;
    protected String datatypes;
    protected DestType destType;
    protected String jmsOptions;
    protected String name;
    protected String what;

    /**
     * Gets the value of the crudFlags property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCrudFlags() {
        return crudFlags;
    }

    /**
     * Sets the value of the crudFlags property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCrudFlags(String value) {
        this.crudFlags = value;
    }

    /**
     * Gets the value of the datatypes property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDatatypes() {
        return datatypes;
    }

    /**
     * Sets the value of the datatypes property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDatatypes(String value) {
        this.datatypes = value;
    }

    /**
     * Gets the value of the destType property.
     * 
     * @return
     *     possible object is
     *     {@link DestType }
     *     
     */
    public DestType getDestType() {
        return destType;
    }

    /**
     * Sets the value of the destType property.
     * 
     * @param value
     *     allowed object is
     *     {@link DestType }
     *     
     */
    public void setDestType(DestType value) {
        this.destType = value;
    }

    /**
     * Gets the value of the jmsOptions property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJmsOptions() {
        return jmsOptions;
    }

    /**
     * Sets the value of the jmsOptions property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJmsOptions(String value) {
        this.jmsOptions = value;
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
     * Gets the value of the what property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWhat() {
        return what;
    }

    /**
     * Sets the value of the what property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWhat(String value) {
        this.what = value;
    }

}
