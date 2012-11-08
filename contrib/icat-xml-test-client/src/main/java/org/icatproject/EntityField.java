
package org.icatproject;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for entityField complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="entityField">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cascaded" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="comment" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="notNullable" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="relType" type="{http://icatproject.org}relType" minOccurs="0"/>
 *         &lt;element name="stringLength" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "entityField", propOrder = {
    "cascaded",
    "comment",
    "name",
    "notNullable",
    "relType",
    "stringLength",
    "type"
})
public class EntityField {

    protected Boolean cascaded;
    protected String comment;
    protected String name;
    protected boolean notNullable;
    protected RelType relType;
    protected Integer stringLength;
    protected String type;

    /**
     * Gets the value of the cascaded property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isCascaded() {
        return cascaded;
    }

    /**
     * Sets the value of the cascaded property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setCascaded(Boolean value) {
        this.cascaded = value;
    }

    /**
     * Gets the value of the comment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComment() {
        return comment;
    }

    /**
     * Sets the value of the comment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComment(String value) {
        this.comment = value;
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
     * Gets the value of the notNullable property.
     * 
     */
    public boolean isNotNullable() {
        return notNullable;
    }

    /**
     * Sets the value of the notNullable property.
     * 
     */
    public void setNotNullable(boolean value) {
        this.notNullable = value;
    }

    /**
     * Gets the value of the relType property.
     * 
     * @return
     *     possible object is
     *     {@link RelType }
     *     
     */
    public RelType getRelType() {
        return relType;
    }

    /**
     * Sets the value of the relType property.
     * 
     * @param value
     *     allowed object is
     *     {@link RelType }
     *     
     */
    public void setRelType(RelType value) {
        this.relType = value;
    }

    /**
     * Gets the value of the stringLength property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getStringLength() {
        return stringLength;
    }

    /**
     * Sets the value of the stringLength property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setStringLength(Integer value) {
        this.stringLength = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

}
