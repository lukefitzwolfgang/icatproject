
package org.icatproject;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for studyInvestigation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="studyInvestigation">
 *   &lt;complexContent>
 *     &lt;extension base="{http://icatproject.org}entityBaseBean">
 *       &lt;sequence>
 *         &lt;element name="investigation" type="{http://icatproject.org}investigation" minOccurs="0"/>
 *         &lt;element name="study" type="{http://icatproject.org}study" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "studyInvestigation", propOrder = {
    "investigation",
    "study"
})
public class StudyInvestigation
    extends EntityBaseBean
{

    protected Investigation investigation;
    protected Study study;

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
     * Gets the value of the study property.
     * 
     * @return
     *     possible object is
     *     {@link Study }
     *     
     */
    public Study getStudy() {
        return study;
    }

    /**
     * Sets the value of the study property.
     * 
     * @param value
     *     allowed object is
     *     {@link Study }
     *     
     */
    public void setStudy(Study value) {
        this.study = value;
    }

}
