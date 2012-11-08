package org.icatproject;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for datasetParameter complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="datasetParameter">
 *   &lt;complexContent>
 *     &lt;extension base="{http://icatproject.org}parameter">
 *       &lt;sequence>
 *         &lt;element ref="{http://icatproject.org}dataset" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "datasetParameter", propOrder = { "dataset" })
public class DatasetParameter extends Parameter {

	protected Dataset dataset;

	/**
	 * Gets the value of the dataset property.
	 * 
	 * @return possible object is {@link Dataset }
	 * 
	 */
	public Dataset getDataset() {
		return dataset;
	}

	/**
	 * Sets the value of the dataset property.
	 * 
	 * @param value
	 *            allowed object is {@link Dataset }
	 * 
	 */
	public void setDataset(Dataset value) {
		this.dataset = value;
	}

}
