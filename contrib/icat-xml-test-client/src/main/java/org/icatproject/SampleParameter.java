
package org.icatproject;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for sampleParameter complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="sampleParameter">
 *   &lt;complexContent>
 *     &lt;extension base="{http://icatproject.org}parameter">
 *       &lt;sequence>
 *         &lt;element name="sample" type="{http://icatproject.org}sample" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "sampleParameter", propOrder = {
    "sample"
})
public class SampleParameter
    extends Parameter
{

    protected Sample sample;

    /**
     * Gets the value of the sample property.
     * 
     * @return
     *     possible object is
     *     {@link Sample }
     *     
     */
    public Sample getSample() {
        return sample;
    }

    /**
     * Sets the value of the sample property.
     * 
     * @param value
     *     allowed object is
     *     {@link Sample }
     *     
     */
    public void setSample(Sample value) {
        this.sample = value;
    }

}
