
package org.icatproject;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for rule complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="rule">
 *   &lt;complexContent>
 *     &lt;extension base="{http://icatproject.org}entityBaseBean">
 *       &lt;sequence>
 *         &lt;element name="crudFlags" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="d" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="group" type="{http://icatproject.org}group" minOccurs="0"/>
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
@XmlType(name = "rule", propOrder = {
    "crudFlags",
    "d",
    "group",
    "what"
})
public class Rule
    extends EntityBaseBean
{

    protected String crudFlags;
    protected boolean d;
    protected Group group;
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
     * Gets the value of the d property.
     * 
     */
    public boolean isD() {
        return d;
    }

    /**
     * Sets the value of the d property.
     * 
     */
    public void setD(boolean value) {
        this.d = value;
    }

    /**
     * Gets the value of the group property.
     * 
     * @return
     *     possible object is
     *     {@link Group }
     *     
     */
    public Group getGroup() {
        return group;
    }

    /**
     * Sets the value of the group property.
     * 
     * @param value
     *     allowed object is
     *     {@link Group }
     *     
     */
    public void setGroup(Group value) {
        this.group = value;
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
