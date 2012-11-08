
package org.icatproject;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for relatedDatafile complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="relatedDatafile">
 *   &lt;complexContent>
 *     &lt;extension base="{http://icatproject.org}entityBaseBean">
 *       &lt;sequence>
 *         &lt;element name="destDatafile" type="{http://icatproject.org}datafile" minOccurs="0"/>
 *         &lt;element name="relation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sourceDatafile" type="{http://icatproject.org}datafile" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "relatedDatafile", propOrder = {
    "destDatafile",
    "relation",
    "sourceDatafile"
})
public class RelatedDatafile
    extends EntityBaseBean
{

    protected Datafile destDatafile;
    protected String relation;
    protected Datafile sourceDatafile;

    /**
     * Gets the value of the destDatafile property.
     * 
     * @return
     *     possible object is
     *     {@link Datafile }
     *     
     */
    public Datafile getDestDatafile() {
        return destDatafile;
    }

    /**
     * Sets the value of the destDatafile property.
     * 
     * @param value
     *     allowed object is
     *     {@link Datafile }
     *     
     */
    public void setDestDatafile(Datafile value) {
        this.destDatafile = value;
    }

    /**
     * Gets the value of the relation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRelation() {
        return relation;
    }

    /**
     * Sets the value of the relation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRelation(String value) {
        this.relation = value;
    }

    /**
     * Gets the value of the sourceDatafile property.
     * 
     * @return
     *     possible object is
     *     {@link Datafile }
     *     
     */
    public Datafile getSourceDatafile() {
        return sourceDatafile;
    }

    /**
     * Sets the value of the sourceDatafile property.
     * 
     * @param value
     *     allowed object is
     *     {@link Datafile }
     *     
     */
    public void setSourceDatafile(Datafile value) {
        this.sourceDatafile = value;
    }

}
