
package org.icatproject;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for user complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="user">
 *   &lt;complexContent>
 *     &lt;extension base="{http://icatproject.org}entityBaseBean">
 *       &lt;sequence>
 *         &lt;element name="fullName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="instrumentScientists" type="{http://icatproject.org}instrumentScientist" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="investigationUsers" type="{http://icatproject.org}investigationUser" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="studies" type="{http://icatproject.org}study" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="userGroups" type="{http://icatproject.org}userGroup" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "user", propOrder = {
    "fullName",
    "instrumentScientists",
    "investigationUsers",
    "name",
    "studies",
    "userGroups"
})
public class User
    extends EntityBaseBean
{

    protected String fullName;
    @XmlElement(nillable = true)
    protected List<InstrumentScientist> instrumentScientists;
    @XmlElement(nillable = true)
    protected List<InvestigationUser> investigationUsers;
    protected String name;
    @XmlElement(nillable = true)
    protected List<Study> studies;
    @XmlElement(nillable = true)
    protected List<UserGroup> userGroups;

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
     * Gets the value of the instrumentScientists property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the instrumentScientists property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInstrumentScientists().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InstrumentScientist }
     * 
     * 
     */
    public List<InstrumentScientist> getInstrumentScientists() {
        if (instrumentScientists == null) {
            instrumentScientists = new ArrayList<InstrumentScientist>();
        }
        return this.instrumentScientists;
    }

    /**
     * Gets the value of the investigationUsers property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the investigationUsers property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInvestigationUsers().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InvestigationUser }
     * 
     * 
     */
    public List<InvestigationUser> getInvestigationUsers() {
        if (investigationUsers == null) {
            investigationUsers = new ArrayList<InvestigationUser>();
        }
        return this.investigationUsers;
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
     * Gets the value of the studies property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the studies property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getStudies().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Study }
     * 
     * 
     */
    public List<Study> getStudies() {
        if (studies == null) {
            studies = new ArrayList<Study>();
        }
        return this.studies;
    }

    /**
     * Gets the value of the userGroups property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the userGroups property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUserGroups().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link UserGroup }
     * 
     * 
     */
    public List<UserGroup> getUserGroups() {
        if (userGroups == null) {
            userGroups = new ArrayList<UserGroup>();
        }
        return this.userGroups;
    }

}
