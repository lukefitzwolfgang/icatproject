
package org.icatproject;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for job complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="job">
 *   &lt;complexContent>
 *     &lt;extension base="{http://icatproject.org}entityBaseBean">
 *       &lt;sequence>
 *         &lt;element name="application" type="{http://icatproject.org}application" minOccurs="0"/>
 *         &lt;element name="inputDatafiles" type="{http://icatproject.org}inputDatafile" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="inputDatasets" type="{http://icatproject.org}inputDataset" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="outputDatafiles" type="{http://icatproject.org}outputDatafile" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="outputDatasets" type="{http://icatproject.org}outputDataset" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "job", propOrder = {
    "application",
    "inputDatafiles",
    "inputDatasets",
    "outputDatafiles",
    "outputDatasets"
})
public class Job
    extends EntityBaseBean
{

    protected Application application;
    @XmlElement(nillable = true)
    protected List<InputDatafile> inputDatafiles;
    @XmlElement(nillable = true)
    protected List<InputDataset> inputDatasets;
    @XmlElement(nillable = true)
    protected List<OutputDatafile> outputDatafiles;
    @XmlElement(nillable = true)
    protected List<OutputDataset> outputDatasets;

    /**
     * Gets the value of the application property.
     * 
     * @return
     *     possible object is
     *     {@link Application }
     *     
     */
    public Application getApplication() {
        return application;
    }

    /**
     * Sets the value of the application property.
     * 
     * @param value
     *     allowed object is
     *     {@link Application }
     *     
     */
    public void setApplication(Application value) {
        this.application = value;
    }

    /**
     * Gets the value of the inputDatafiles property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the inputDatafiles property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInputDatafiles().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InputDatafile }
     * 
     * 
     */
    public List<InputDatafile> getInputDatafiles() {
        if (inputDatafiles == null) {
            inputDatafiles = new ArrayList<InputDatafile>();
        }
        return this.inputDatafiles;
    }

    /**
     * Gets the value of the inputDatasets property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the inputDatasets property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInputDatasets().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InputDataset }
     * 
     * 
     */
    public List<InputDataset> getInputDatasets() {
        if (inputDatasets == null) {
            inputDatasets = new ArrayList<InputDataset>();
        }
        return this.inputDatasets;
    }

    /**
     * Gets the value of the outputDatafiles property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the outputDatafiles property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOutputDatafiles().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OutputDatafile }
     * 
     * 
     */
    public List<OutputDatafile> getOutputDatafiles() {
        if (outputDatafiles == null) {
            outputDatafiles = new ArrayList<OutputDatafile>();
        }
        return this.outputDatafiles;
    }

    /**
     * Gets the value of the outputDatasets property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the outputDatasets property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOutputDatasets().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OutputDataset }
     * 
     * 
     */
    public List<OutputDataset> getOutputDatasets() {
        if (outputDatasets == null) {
            outputDatasets = new ArrayList<OutputDataset>();
        }
        return this.outputDatasets;
    }

}
