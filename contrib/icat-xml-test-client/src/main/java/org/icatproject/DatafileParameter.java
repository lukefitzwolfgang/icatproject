package org.icatproject;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for datafileParameter complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="datafileParameter">
 *   &lt;complexContent>
 *     &lt;extension base="{http://icatproject.org}parameter">
 *       &lt;sequence>
 *         &lt;element ref="{http://icatproject.org}datafile" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "datafileParameter", propOrder = { "datafile" })
public class DatafileParameter extends Parameter {

	protected Datafile datafile;

	/**
	 * Gets the value of the datafile property.
	 * 
	 * @return possible object is {@link Datafile }
	 * 
	 */
	public Datafile getDatafile() {
		return datafile;
	}

	/**
	 * Sets the value of the datafile property.
	 * 
	 * @param value
	 *            allowed object is {@link Datafile }
	 * 
	 */
	public void setDatafile(Datafile value) {
		this.datafile = value;
	}

}
