
package uk.icat3.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for addInvestigator complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="addInvestigator">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="sessionId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="investigator" type="{client.icat3.uk}investigator" minOccurs="0"/>
 *         &lt;element name="investigationId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "addInvestigator", propOrder = {
    "sessionId",
    "investigator",
    "investigationId"
})
public class AddInvestigator {

    protected String sessionId;
    protected Investigator investigator;
    protected Long investigationId;

    /**
     * Gets the value of the sessionId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSessionId() {
        return sessionId;
    }

    /**
     * Sets the value of the sessionId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSessionId(String value) {
        this.sessionId = value;
    }

    /**
     * Gets the value of the investigator property.
     * 
     * @return
     *     possible object is
     *     {@link Investigator }
     *     
     */
    public Investigator getInvestigator() {
        return investigator;
    }

    /**
     * Sets the value of the investigator property.
     * 
     * @param value
     *     allowed object is
     *     {@link Investigator }
     *     
     */
    public void setInvestigator(Investigator value) {
        this.investigator = value;
    }

    /**
     * Gets the value of the investigationId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getInvestigationId() {
        return investigationId;
    }

    /**
     * Sets the value of the investigationId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setInvestigationId(Long value) {
        this.investigationId = value;
    }

}
