
package org.icatproject;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for investigation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="investigation">
 *   &lt;complexContent>
 *     &lt;extension base="{http://icatproject.org}entityBaseBean">
 *       &lt;sequence>
 *         &lt;element name="datasets" type="{http://icatproject.org}dataset" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="doi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="endDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="facility" type="{http://icatproject.org}facility" minOccurs="0"/>
 *         &lt;element name="facilityCycle" type="{http://icatproject.org}facilityCycle" minOccurs="0"/>
 *         &lt;element name="instrument" type="{http://icatproject.org}instrument" minOccurs="0"/>
 *         &lt;element name="investigationUsers" type="{http://icatproject.org}investigationUser" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="keywords" type="{http://icatproject.org}keyword" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="parameters" type="{http://icatproject.org}investigationParameter" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="publications" type="{http://icatproject.org}publication" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="releaseDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="samples" type="{http://icatproject.org}sample" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="shifts" type="{http://icatproject.org}shift" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="startDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="studyInvestigations" type="{http://icatproject.org}studyInvestigation" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="summary" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="title" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="type" type="{http://icatproject.org}investigationType" minOccurs="0"/>
 *         &lt;element name="visitId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "investigation", propOrder = {
    "datasets",
    "doi",
    "endDate",
    "facility",
    "facilityCycle",
    "instrument",
    "investigationUsers",
    "keywords",
    "name",
    "parameters",
    "publications",
    "releaseDate",
    "samples",
    "shifts",
    "startDate",
    "studyInvestigations",
    "summary",
    "title",
    "type",
    "visitId"
})
public class Investigation
    extends EntityBaseBean
{

    @XmlElement(nillable = true)
    protected List<Dataset> datasets;
    protected String doi;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar endDate;
    protected Facility facility;
    protected FacilityCycle facilityCycle;
    protected Instrument instrument;
    @XmlElement(nillable = true)
    protected List<InvestigationUser> investigationUsers;
    @XmlElement(nillable = true)
    protected List<Keyword> keywords;
    protected String name;
    @XmlElement(nillable = true)
    protected List<InvestigationParameter> parameters;
    @XmlElement(nillable = true)
    protected List<Publication> publications;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar releaseDate;
    @XmlElement(nillable = true)
    protected List<Sample> samples;
    @XmlElement(nillable = true)
    protected List<Shift> shifts;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar startDate;
    @XmlElement(nillable = true)
    protected List<StudyInvestigation> studyInvestigations;
    protected String summary;
    protected String title;
    protected InvestigationType type;
    protected String visitId;

    /**
     * Gets the value of the datasets property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the datasets property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDatasets().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Dataset }
     * 
     * 
     */
    public List<Dataset> getDatasets() {
        if (datasets == null) {
            datasets = new ArrayList<Dataset>();
        }
        return this.datasets;
    }

    /**
     * Gets the value of the doi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDoi() {
        return doi;
    }

    /**
     * Sets the value of the doi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDoi(String value) {
        this.doi = value;
    }

    /**
     * Gets the value of the endDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEndDate() {
        return endDate;
    }

    /**
     * Sets the value of the endDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEndDate(XMLGregorianCalendar value) {
        this.endDate = value;
    }

    /**
     * Gets the value of the facility property.
     * 
     * @return
     *     possible object is
     *     {@link Facility }
     *     
     */
    public Facility getFacility() {
        return facility;
    }

    /**
     * Sets the value of the facility property.
     * 
     * @param value
     *     allowed object is
     *     {@link Facility }
     *     
     */
    public void setFacility(Facility value) {
        this.facility = value;
    }

    /**
     * Gets the value of the facilityCycle property.
     * 
     * @return
     *     possible object is
     *     {@link FacilityCycle }
     *     
     */
    public FacilityCycle getFacilityCycle() {
        return facilityCycle;
    }

    /**
     * Sets the value of the facilityCycle property.
     * 
     * @param value
     *     allowed object is
     *     {@link FacilityCycle }
     *     
     */
    public void setFacilityCycle(FacilityCycle value) {
        this.facilityCycle = value;
    }

    /**
     * Gets the value of the instrument property.
     * 
     * @return
     *     possible object is
     *     {@link Instrument }
     *     
     */
    public Instrument getInstrument() {
        return instrument;
    }

    /**
     * Sets the value of the instrument property.
     * 
     * @param value
     *     allowed object is
     *     {@link Instrument }
     *     
     */
    public void setInstrument(Instrument value) {
        this.instrument = value;
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
     * Gets the value of the keywords property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the keywords property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getKeywords().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Keyword }
     * 
     * 
     */
    public List<Keyword> getKeywords() {
        if (keywords == null) {
            keywords = new ArrayList<Keyword>();
        }
        return this.keywords;
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
     * Gets the value of the parameters property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the parameters property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getParameters().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InvestigationParameter }
     * 
     * 
     */
    public List<InvestigationParameter> getParameters() {
        if (parameters == null) {
            parameters = new ArrayList<InvestigationParameter>();
        }
        return this.parameters;
    }

    /**
     * Gets the value of the publications property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the publications property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPublications().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Publication }
     * 
     * 
     */
    public List<Publication> getPublications() {
        if (publications == null) {
            publications = new ArrayList<Publication>();
        }
        return this.publications;
    }

    /**
     * Gets the value of the releaseDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getReleaseDate() {
        return releaseDate;
    }

    /**
     * Sets the value of the releaseDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setReleaseDate(XMLGregorianCalendar value) {
        this.releaseDate = value;
    }

    /**
     * Gets the value of the samples property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the samples property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSamples().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Sample }
     * 
     * 
     */
    public List<Sample> getSamples() {
        if (samples == null) {
            samples = new ArrayList<Sample>();
        }
        return this.samples;
    }

    /**
     * Gets the value of the shifts property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the shifts property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getShifts().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Shift }
     * 
     * 
     */
    public List<Shift> getShifts() {
        if (shifts == null) {
            shifts = new ArrayList<Shift>();
        }
        return this.shifts;
    }

    /**
     * Gets the value of the startDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getStartDate() {
        return startDate;
    }

    /**
     * Sets the value of the startDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setStartDate(XMLGregorianCalendar value) {
        this.startDate = value;
    }

    /**
     * Gets the value of the studyInvestigations property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the studyInvestigations property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getStudyInvestigations().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link StudyInvestigation }
     * 
     * 
     */
    public List<StudyInvestigation> getStudyInvestigations() {
        if (studyInvestigations == null) {
            studyInvestigations = new ArrayList<StudyInvestigation>();
        }
        return this.studyInvestigations;
    }

    /**
     * Gets the value of the summary property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSummary() {
        return summary;
    }

    /**
     * Sets the value of the summary property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSummary(String value) {
        this.summary = value;
    }

    /**
     * Gets the value of the title property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the value of the title property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitle(String value) {
        this.title = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link InvestigationType }
     *     
     */
    public InvestigationType getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link InvestigationType }
     *     
     */
    public void setType(InvestigationType value) {
        this.type = value;
    }

    /**
     * Gets the value of the visitId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVisitId() {
        return visitId;
    }

    /**
     * Sets the value of the visitId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVisitId(String value) {
        this.visitId = value;
    }

}
