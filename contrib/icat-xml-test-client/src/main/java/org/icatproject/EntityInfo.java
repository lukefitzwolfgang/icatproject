
package org.icatproject;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for entityInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="entityInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="classComment" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="constraints" type="{http://icatproject.org}constraint" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="fields" type="{http://icatproject.org}entityField" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "entityInfo", propOrder = {
    "classComment",
    "constraints",
    "fields"
})
public class EntityInfo {

    protected String classComment;
    @XmlElement(nillable = true)
    protected List<Constraint> constraints;
    @XmlElement(nillable = true)
    protected List<EntityField> fields;

    /**
     * Gets the value of the classComment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClassComment() {
        return classComment;
    }

    /**
     * Sets the value of the classComment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClassComment(String value) {
        this.classComment = value;
    }

    /**
     * Gets the value of the constraints property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the constraints property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getConstraints().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Constraint }
     * 
     * 
     */
    public List<Constraint> getConstraints() {
        if (constraints == null) {
            constraints = new ArrayList<Constraint>();
        }
        return this.constraints;
    }

    /**
     * Gets the value of the fields property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the fields property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFields().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EntityField }
     * 
     * 
     */
    public List<EntityField> getFields() {
        if (fields == null) {
            fields = new ArrayList<EntityField>();
        }
        return this.fields;
    }

}
