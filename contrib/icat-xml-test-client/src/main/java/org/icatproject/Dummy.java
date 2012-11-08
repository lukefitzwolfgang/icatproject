
package org.icatproject;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for dummy complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="dummy">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="arg0" type="{http://icatproject.org}datafile" minOccurs="0"/>
 *         &lt;element name="arg1" type="{http://icatproject.org}datafileFormat" minOccurs="0"/>
 *         &lt;element name="arg2" type="{http://icatproject.org}datafileParameter" minOccurs="0"/>
 *         &lt;element name="arg3" type="{http://icatproject.org}dataset" minOccurs="0"/>
 *         &lt;element name="arg4" type="{http://icatproject.org}datasetParameter" minOccurs="0"/>
 *         &lt;element name="arg5" type="{http://icatproject.org}datasetType" minOccurs="0"/>
 *         &lt;element name="arg6" type="{http://icatproject.org}facility" minOccurs="0"/>
 *         &lt;element name="arg7" type="{http://icatproject.org}facilityCycle" minOccurs="0"/>
 *         &lt;element name="arg8" type="{http://icatproject.org}instrumentScientist" minOccurs="0"/>
 *         &lt;element name="arg9" type="{http://icatproject.org}user" minOccurs="0"/>
 *         &lt;element name="arg10" type="{http://icatproject.org}instrument" minOccurs="0"/>
 *         &lt;element name="arg11" type="{http://icatproject.org}investigation" minOccurs="0"/>
 *         &lt;element name="arg12" type="{http://icatproject.org}investigationType" minOccurs="0"/>
 *         &lt;element name="arg13" type="{http://icatproject.org}investigationUser" minOccurs="0"/>
 *         &lt;element name="arg14" type="{http://icatproject.org}keyword" minOccurs="0"/>
 *         &lt;element name="arg15" type="{http://icatproject.org}parameterType" minOccurs="0"/>
 *         &lt;element name="arg16" type="{http://icatproject.org}publication" minOccurs="0"/>
 *         &lt;element name="arg17" type="{http://icatproject.org}relatedDatafile" minOccurs="0"/>
 *         &lt;element name="arg18" type="{http://icatproject.org}sample" minOccurs="0"/>
 *         &lt;element name="arg19" type="{http://icatproject.org}sampleParameter" minOccurs="0"/>
 *         &lt;element name="arg20" type="{http://icatproject.org}shift" minOccurs="0"/>
 *         &lt;element name="arg21" type="{http://icatproject.org}study" minOccurs="0"/>
 *         &lt;element name="arg22" type="{http://icatproject.org}studyInvestigation" minOccurs="0"/>
 *         &lt;element name="arg23" type="{http://icatproject.org}studyStatus" minOccurs="0"/>
 *         &lt;element name="arg24" type="{http://icatproject.org}application" minOccurs="0"/>
 *         &lt;element name="arg25" type="{http://icatproject.org}job" minOccurs="0"/>
 *         &lt;element name="arg26" type="{http://icatproject.org}inputDataset" minOccurs="0"/>
 *         &lt;element name="arg27" type="{http://icatproject.org}outputDataset" minOccurs="0"/>
 *         &lt;element name="arg28" type="{http://icatproject.org}inputDatafile" minOccurs="0"/>
 *         &lt;element name="arg29" type="{http://icatproject.org}outputDatafile" minOccurs="0"/>
 *         &lt;element name="arg30" type="{http://icatproject.org}notificationRequest" minOccurs="0"/>
 *         &lt;element name="arg31" type="{http://icatproject.org}group" minOccurs="0"/>
 *         &lt;element name="arg32" type="{http://icatproject.org}userGroup" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dummy", propOrder = {
    "arg0",
    "arg1",
    "arg2",
    "arg3",
    "arg4",
    "arg5",
    "arg6",
    "arg7",
    "arg8",
    "arg9",
    "arg10",
    "arg11",
    "arg12",
    "arg13",
    "arg14",
    "arg15",
    "arg16",
    "arg17",
    "arg18",
    "arg19",
    "arg20",
    "arg21",
    "arg22",
    "arg23",
    "arg24",
    "arg25",
    "arg26",
    "arg27",
    "arg28",
    "arg29",
    "arg30",
    "arg31",
    "arg32"
})
public class Dummy {

    protected Datafile arg0;
    protected DatafileFormat arg1;
    protected DatafileParameter arg2;
    protected Dataset arg3;
    protected DatasetParameter arg4;
    protected DatasetType arg5;
    protected Facility arg6;
    protected FacilityCycle arg7;
    protected InstrumentScientist arg8;
    protected User arg9;
    protected Instrument arg10;
    protected Investigation arg11;
    protected InvestigationType arg12;
    protected InvestigationUser arg13;
    protected Keyword arg14;
    protected ParameterType arg15;
    protected Publication arg16;
    protected RelatedDatafile arg17;
    protected Sample arg18;
    protected SampleParameter arg19;
    protected Shift arg20;
    protected Study arg21;
    protected StudyInvestigation arg22;
    protected StudyStatus arg23;
    protected Application arg24;
    protected Job arg25;
    protected InputDataset arg26;
    protected OutputDataset arg27;
    protected InputDatafile arg28;
    protected OutputDatafile arg29;
    protected NotificationRequest arg30;
    protected Group arg31;
    protected UserGroup arg32;

    /**
     * Gets the value of the arg0 property.
     * 
     * @return
     *     possible object is
     *     {@link Datafile }
     *     
     */
    public Datafile getArg0() {
        return arg0;
    }

    /**
     * Sets the value of the arg0 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Datafile }
     *     
     */
    public void setArg0(Datafile value) {
        this.arg0 = value;
    }

    /**
     * Gets the value of the arg1 property.
     * 
     * @return
     *     possible object is
     *     {@link DatafileFormat }
     *     
     */
    public DatafileFormat getArg1() {
        return arg1;
    }

    /**
     * Sets the value of the arg1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link DatafileFormat }
     *     
     */
    public void setArg1(DatafileFormat value) {
        this.arg1 = value;
    }

    /**
     * Gets the value of the arg2 property.
     * 
     * @return
     *     possible object is
     *     {@link DatafileParameter }
     *     
     */
    public DatafileParameter getArg2() {
        return arg2;
    }

    /**
     * Sets the value of the arg2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link DatafileParameter }
     *     
     */
    public void setArg2(DatafileParameter value) {
        this.arg2 = value;
    }

    /**
     * Gets the value of the arg3 property.
     * 
     * @return
     *     possible object is
     *     {@link Dataset }
     *     
     */
    public Dataset getArg3() {
        return arg3;
    }

    /**
     * Sets the value of the arg3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Dataset }
     *     
     */
    public void setArg3(Dataset value) {
        this.arg3 = value;
    }

    /**
     * Gets the value of the arg4 property.
     * 
     * @return
     *     possible object is
     *     {@link DatasetParameter }
     *     
     */
    public DatasetParameter getArg4() {
        return arg4;
    }

    /**
     * Sets the value of the arg4 property.
     * 
     * @param value
     *     allowed object is
     *     {@link DatasetParameter }
     *     
     */
    public void setArg4(DatasetParameter value) {
        this.arg4 = value;
    }

    /**
     * Gets the value of the arg5 property.
     * 
     * @return
     *     possible object is
     *     {@link DatasetType }
     *     
     */
    public DatasetType getArg5() {
        return arg5;
    }

    /**
     * Sets the value of the arg5 property.
     * 
     * @param value
     *     allowed object is
     *     {@link DatasetType }
     *     
     */
    public void setArg5(DatasetType value) {
        this.arg5 = value;
    }

    /**
     * Gets the value of the arg6 property.
     * 
     * @return
     *     possible object is
     *     {@link Facility }
     *     
     */
    public Facility getArg6() {
        return arg6;
    }

    /**
     * Sets the value of the arg6 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Facility }
     *     
     */
    public void setArg6(Facility value) {
        this.arg6 = value;
    }

    /**
     * Gets the value of the arg7 property.
     * 
     * @return
     *     possible object is
     *     {@link FacilityCycle }
     *     
     */
    public FacilityCycle getArg7() {
        return arg7;
    }

    /**
     * Sets the value of the arg7 property.
     * 
     * @param value
     *     allowed object is
     *     {@link FacilityCycle }
     *     
     */
    public void setArg7(FacilityCycle value) {
        this.arg7 = value;
    }

    /**
     * Gets the value of the arg8 property.
     * 
     * @return
     *     possible object is
     *     {@link InstrumentScientist }
     *     
     */
    public InstrumentScientist getArg8() {
        return arg8;
    }

    /**
     * Sets the value of the arg8 property.
     * 
     * @param value
     *     allowed object is
     *     {@link InstrumentScientist }
     *     
     */
    public void setArg8(InstrumentScientist value) {
        this.arg8 = value;
    }

    /**
     * Gets the value of the arg9 property.
     * 
     * @return
     *     possible object is
     *     {@link User }
     *     
     */
    public User getArg9() {
        return arg9;
    }

    /**
     * Sets the value of the arg9 property.
     * 
     * @param value
     *     allowed object is
     *     {@link User }
     *     
     */
    public void setArg9(User value) {
        this.arg9 = value;
    }

    /**
     * Gets the value of the arg10 property.
     * 
     * @return
     *     possible object is
     *     {@link Instrument }
     *     
     */
    public Instrument getArg10() {
        return arg10;
    }

    /**
     * Sets the value of the arg10 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Instrument }
     *     
     */
    public void setArg10(Instrument value) {
        this.arg10 = value;
    }

    /**
     * Gets the value of the arg11 property.
     * 
     * @return
     *     possible object is
     *     {@link Investigation }
     *     
     */
    public Investigation getArg11() {
        return arg11;
    }

    /**
     * Sets the value of the arg11 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Investigation }
     *     
     */
    public void setArg11(Investigation value) {
        this.arg11 = value;
    }

    /**
     * Gets the value of the arg12 property.
     * 
     * @return
     *     possible object is
     *     {@link InvestigationType }
     *     
     */
    public InvestigationType getArg12() {
        return arg12;
    }

    /**
     * Sets the value of the arg12 property.
     * 
     * @param value
     *     allowed object is
     *     {@link InvestigationType }
     *     
     */
    public void setArg12(InvestigationType value) {
        this.arg12 = value;
    }

    /**
     * Gets the value of the arg13 property.
     * 
     * @return
     *     possible object is
     *     {@link InvestigationUser }
     *     
     */
    public InvestigationUser getArg13() {
        return arg13;
    }

    /**
     * Sets the value of the arg13 property.
     * 
     * @param value
     *     allowed object is
     *     {@link InvestigationUser }
     *     
     */
    public void setArg13(InvestigationUser value) {
        this.arg13 = value;
    }

    /**
     * Gets the value of the arg14 property.
     * 
     * @return
     *     possible object is
     *     {@link Keyword }
     *     
     */
    public Keyword getArg14() {
        return arg14;
    }

    /**
     * Sets the value of the arg14 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Keyword }
     *     
     */
    public void setArg14(Keyword value) {
        this.arg14 = value;
    }

    /**
     * Gets the value of the arg15 property.
     * 
     * @return
     *     possible object is
     *     {@link ParameterType }
     *     
     */
    public ParameterType getArg15() {
        return arg15;
    }

    /**
     * Sets the value of the arg15 property.
     * 
     * @param value
     *     allowed object is
     *     {@link ParameterType }
     *     
     */
    public void setArg15(ParameterType value) {
        this.arg15 = value;
    }

    /**
     * Gets the value of the arg16 property.
     * 
     * @return
     *     possible object is
     *     {@link Publication }
     *     
     */
    public Publication getArg16() {
        return arg16;
    }

    /**
     * Sets the value of the arg16 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Publication }
     *     
     */
    public void setArg16(Publication value) {
        this.arg16 = value;
    }

    /**
     * Gets the value of the arg17 property.
     * 
     * @return
     *     possible object is
     *     {@link RelatedDatafile }
     *     
     */
    public RelatedDatafile getArg17() {
        return arg17;
    }

    /**
     * Sets the value of the arg17 property.
     * 
     * @param value
     *     allowed object is
     *     {@link RelatedDatafile }
     *     
     */
    public void setArg17(RelatedDatafile value) {
        this.arg17 = value;
    }

    /**
     * Gets the value of the arg18 property.
     * 
     * @return
     *     possible object is
     *     {@link Sample }
     *     
     */
    public Sample getArg18() {
        return arg18;
    }

    /**
     * Sets the value of the arg18 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Sample }
     *     
     */
    public void setArg18(Sample value) {
        this.arg18 = value;
    }

    /**
     * Gets the value of the arg19 property.
     * 
     * @return
     *     possible object is
     *     {@link SampleParameter }
     *     
     */
    public SampleParameter getArg19() {
        return arg19;
    }

    /**
     * Sets the value of the arg19 property.
     * 
     * @param value
     *     allowed object is
     *     {@link SampleParameter }
     *     
     */
    public void setArg19(SampleParameter value) {
        this.arg19 = value;
    }

    /**
     * Gets the value of the arg20 property.
     * 
     * @return
     *     possible object is
     *     {@link Shift }
     *     
     */
    public Shift getArg20() {
        return arg20;
    }

    /**
     * Sets the value of the arg20 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Shift }
     *     
     */
    public void setArg20(Shift value) {
        this.arg20 = value;
    }

    /**
     * Gets the value of the arg21 property.
     * 
     * @return
     *     possible object is
     *     {@link Study }
     *     
     */
    public Study getArg21() {
        return arg21;
    }

    /**
     * Sets the value of the arg21 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Study }
     *     
     */
    public void setArg21(Study value) {
        this.arg21 = value;
    }

    /**
     * Gets the value of the arg22 property.
     * 
     * @return
     *     possible object is
     *     {@link StudyInvestigation }
     *     
     */
    public StudyInvestigation getArg22() {
        return arg22;
    }

    /**
     * Sets the value of the arg22 property.
     * 
     * @param value
     *     allowed object is
     *     {@link StudyInvestigation }
     *     
     */
    public void setArg22(StudyInvestigation value) {
        this.arg22 = value;
    }

    /**
     * Gets the value of the arg23 property.
     * 
     * @return
     *     possible object is
     *     {@link StudyStatus }
     *     
     */
    public StudyStatus getArg23() {
        return arg23;
    }

    /**
     * Sets the value of the arg23 property.
     * 
     * @param value
     *     allowed object is
     *     {@link StudyStatus }
     *     
     */
    public void setArg23(StudyStatus value) {
        this.arg23 = value;
    }

    /**
     * Gets the value of the arg24 property.
     * 
     * @return
     *     possible object is
     *     {@link Application }
     *     
     */
    public Application getArg24() {
        return arg24;
    }

    /**
     * Sets the value of the arg24 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Application }
     *     
     */
    public void setArg24(Application value) {
        this.arg24 = value;
    }

    /**
     * Gets the value of the arg25 property.
     * 
     * @return
     *     possible object is
     *     {@link Job }
     *     
     */
    public Job getArg25() {
        return arg25;
    }

    /**
     * Sets the value of the arg25 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Job }
     *     
     */
    public void setArg25(Job value) {
        this.arg25 = value;
    }

    /**
     * Gets the value of the arg26 property.
     * 
     * @return
     *     possible object is
     *     {@link InputDataset }
     *     
     */
    public InputDataset getArg26() {
        return arg26;
    }

    /**
     * Sets the value of the arg26 property.
     * 
     * @param value
     *     allowed object is
     *     {@link InputDataset }
     *     
     */
    public void setArg26(InputDataset value) {
        this.arg26 = value;
    }

    /**
     * Gets the value of the arg27 property.
     * 
     * @return
     *     possible object is
     *     {@link OutputDataset }
     *     
     */
    public OutputDataset getArg27() {
        return arg27;
    }

    /**
     * Sets the value of the arg27 property.
     * 
     * @param value
     *     allowed object is
     *     {@link OutputDataset }
     *     
     */
    public void setArg27(OutputDataset value) {
        this.arg27 = value;
    }

    /**
     * Gets the value of the arg28 property.
     * 
     * @return
     *     possible object is
     *     {@link InputDatafile }
     *     
     */
    public InputDatafile getArg28() {
        return arg28;
    }

    /**
     * Sets the value of the arg28 property.
     * 
     * @param value
     *     allowed object is
     *     {@link InputDatafile }
     *     
     */
    public void setArg28(InputDatafile value) {
        this.arg28 = value;
    }

    /**
     * Gets the value of the arg29 property.
     * 
     * @return
     *     possible object is
     *     {@link OutputDatafile }
     *     
     */
    public OutputDatafile getArg29() {
        return arg29;
    }

    /**
     * Sets the value of the arg29 property.
     * 
     * @param value
     *     allowed object is
     *     {@link OutputDatafile }
     *     
     */
    public void setArg29(OutputDatafile value) {
        this.arg29 = value;
    }

    /**
     * Gets the value of the arg30 property.
     * 
     * @return
     *     possible object is
     *     {@link NotificationRequest }
     *     
     */
    public NotificationRequest getArg30() {
        return arg30;
    }

    /**
     * Sets the value of the arg30 property.
     * 
     * @param value
     *     allowed object is
     *     {@link NotificationRequest }
     *     
     */
    public void setArg30(NotificationRequest value) {
        this.arg30 = value;
    }

    /**
     * Gets the value of the arg31 property.
     * 
     * @return
     *     possible object is
     *     {@link Group }
     *     
     */
    public Group getArg31() {
        return arg31;
    }

    /**
     * Sets the value of the arg31 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Group }
     *     
     */
    public void setArg31(Group value) {
        this.arg31 = value;
    }

    /**
     * Gets the value of the arg32 property.
     * 
     * @return
     *     possible object is
     *     {@link UserGroup }
     *     
     */
    public UserGroup getArg32() {
        return arg32;
    }

    /**
     * Sets the value of the arg32 property.
     * 
     * @param value
     *     allowed object is
     *     {@link UserGroup }
     *     
     */
    public void setArg32(UserGroup value) {
        this.arg32 = value;
    }

}
