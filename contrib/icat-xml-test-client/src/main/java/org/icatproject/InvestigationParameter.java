
package org.icatproject;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for investigationParameter complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="investigationParameter">
 *   &lt;complexContent>
 *     &lt;extension base="{http://icatproject.org}parameter">
 *       &lt;sequence>
 *         &lt;element name="investigation" type="{http://icatproject.org}investigation" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "investigationParameter", propOrder = {
    "investigation"
})
public class InvestigationParameter
    extends Parameter
{

    protected Investigation investigation;

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

}
