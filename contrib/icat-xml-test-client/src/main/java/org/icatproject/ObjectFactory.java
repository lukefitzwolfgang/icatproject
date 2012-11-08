
package org.icatproject;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.icatproject package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _CreateMany_QNAME = new QName("http://icatproject.org", "createMany");
    private final static QName _Create_QNAME = new QName("http://icatproject.org", "create");
    private final static QName _GetEntityInfoResponse_QNAME = new QName("http://icatproject.org", "getEntityInfoResponse");
    private final static QName _GetResponse_QNAME = new QName("http://icatproject.org", "getResponse");
    private final static QName _SearchResponse_QNAME = new QName("http://icatproject.org", "searchResponse");
    private final static QName _Get_QNAME = new QName("http://icatproject.org", "get");
    private final static QName _LoginResponse_QNAME = new QName("http://icatproject.org", "loginResponse");
    private final static QName _DatafileParameter_QNAME = new QName("http://icatproject.org", "datafileParameter");
    private final static QName _Datafile_QNAME = new QName("http://icatproject.org", "datafile");
    private final static QName _DummyResponse_QNAME = new QName("http://icatproject.org", "dummyResponse");
    private final static QName _GetUserName_QNAME = new QName("http://icatproject.org", "getUserName");
    private final static QName _DeleteMany_QNAME = new QName("http://icatproject.org", "deleteMany");
    private final static QName _DeleteManyResponse_QNAME = new QName("http://icatproject.org", "deleteManyResponse");
    private final static QName _GetApiVersionResponse_QNAME = new QName("http://icatproject.org", "getApiVersionResponse");
    private final static QName _Dummy_QNAME = new QName("http://icatproject.org", "dummy");
    private final static QName _SampleParameter_QNAME = new QName("http://icatproject.org", "sampleParameter");
    private final static QName _GetRemainingMinutesResponse_QNAME = new QName("http://icatproject.org", "getRemainingMinutesResponse");
    private final static QName _Dataset_QNAME = new QName("http://icatproject.org", "dataset");
    private final static QName _GetUserNameResponse_QNAME = new QName("http://icatproject.org", "getUserNameResponse");
    private final static QName _DeleteResponse_QNAME = new QName("http://icatproject.org", "deleteResponse");
    private final static QName _GetRemainingMinutes_QNAME = new QName("http://icatproject.org", "getRemainingMinutes");
    private final static QName _Logout_QNAME = new QName("http://icatproject.org", "logout");
    private final static QName _InvestigationParameter_QNAME = new QName("http://icatproject.org", "investigationParameter");
    private final static QName _Update_QNAME = new QName("http://icatproject.org", "update");
    private final static QName _IcatException_QNAME = new QName("http://icatproject.org", "IcatException");
    private final static QName _GetApiVersion_QNAME = new QName("http://icatproject.org", "getApiVersion");
    private final static QName _LogoutResponse_QNAME = new QName("http://icatproject.org", "logoutResponse");
    private final static QName _CreateResponse_QNAME = new QName("http://icatproject.org", "createResponse");
    private final static QName _Delete_QNAME = new QName("http://icatproject.org", "delete");
    private final static QName _GetEntityInfo_QNAME = new QName("http://icatproject.org", "getEntityInfo");
    private final static QName _Login_QNAME = new QName("http://icatproject.org", "login");
    private final static QName _DatasetParameter_QNAME = new QName("http://icatproject.org", "datasetParameter");
    private final static QName _CreateManyResponse_QNAME = new QName("http://icatproject.org", "createManyResponse");
    private final static QName _Search_QNAME = new QName("http://icatproject.org", "search");
    private final static QName _UpdateResponse_QNAME = new QName("http://icatproject.org", "updateResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.icatproject
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Search }
     * 
     */
    public Search createSearch() {
        return new Search();
    }

    /**
     * Create an instance of {@link User }
     * 
     */
    public User createUser() {
        return new User();
    }

    /**
     * Create an instance of {@link DummyResponse }
     * 
     */
    public DummyResponse createDummyResponse() {
        return new DummyResponse();
    }

    /**
     * Create an instance of {@link Update }
     * 
     */
    public Update createUpdate() {
        return new Update();
    }

    /**
     * Create an instance of {@link Study }
     * 
     */
    public Study createStudy() {
        return new Study();
    }

    /**
     * Create an instance of {@link OutputDatafile }
     * 
     */
    public OutputDatafile createOutputDatafile() {
        return new OutputDatafile();
    }

    /**
     * Create an instance of {@link Constraint }
     * 
     */
    public Constraint createConstraint() {
        return new Constraint();
    }

    /**
     * Create an instance of {@link GetResponse }
     * 
     */
    public GetResponse createGetResponse() {
        return new GetResponse();
    }

    /**
     * Create an instance of {@link FacilityCycle }
     * 
     */
    public FacilityCycle createFacilityCycle() {
        return new FacilityCycle();
    }

    /**
     * Create an instance of {@link GetUserName }
     * 
     */
    public GetUserName createGetUserName() {
        return new GetUserName();
    }

    /**
     * Create an instance of {@link Get }
     * 
     */
    public Get createGet() {
        return new Get();
    }

    /**
     * Create an instance of {@link InputDataset }
     * 
     */
    public InputDataset createInputDataset() {
        return new InputDataset();
    }

    /**
     * Create an instance of {@link StudyInvestigation }
     * 
     */
    public StudyInvestigation createStudyInvestigation() {
        return new StudyInvestigation();
    }

    /**
     * Create an instance of {@link Application }
     * 
     */
    public Application createApplication() {
        return new Application();
    }

    /**
     * Create an instance of {@link GetApiVersionResponse }
     * 
     */
    public GetApiVersionResponse createGetApiVersionResponse() {
        return new GetApiVersionResponse();
    }

    /**
     * Create an instance of {@link NotificationRequest }
     * 
     */
    public NotificationRequest createNotificationRequest() {
        return new NotificationRequest();
    }

    /**
     * Create an instance of {@link UpdateResponse }
     * 
     */
    public UpdateResponse createUpdateResponse() {
        return new UpdateResponse();
    }

    /**
     * Create an instance of {@link IcatException }
     * 
     */
    public IcatException createIcatException() {
        return new IcatException();
    }

    /**
     * Create an instance of {@link Instrument }
     * 
     */
    public Instrument createInstrument() {
        return new Instrument();
    }

    /**
     * Create an instance of {@link InvestigationUser }
     * 
     */
    public InvestigationUser createInvestigationUser() {
        return new InvestigationUser();
    }

    /**
     * Create an instance of {@link InputDatafile }
     * 
     */
    public InputDatafile createInputDatafile() {
        return new InputDatafile();
    }

    /**
     * Create an instance of {@link InstrumentScientist }
     * 
     */
    public InstrumentScientist createInstrumentScientist() {
        return new InstrumentScientist();
    }

    /**
     * Create an instance of {@link DeleteManyResponse }
     * 
     */
    public DeleteManyResponse createDeleteManyResponse() {
        return new DeleteManyResponse();
    }

    /**
     * Create an instance of {@link PermissibleStringValue }
     * 
     */
    public PermissibleStringValue createPermissibleStringValue() {
        return new PermissibleStringValue();
    }

    /**
     * Create an instance of {@link DatasetType }
     * 
     */
    public DatasetType createDatasetType() {
        return new DatasetType();
    }

    /**
     * Create an instance of {@link SampleParameter }
     * 
     */
    public SampleParameter createSampleParameter() {
        return new SampleParameter();
    }

    /**
     * Create an instance of {@link GetRemainingMinutesResponse }
     * 
     */
    public GetRemainingMinutesResponse createGetRemainingMinutesResponse() {
        return new GetRemainingMinutesResponse();
    }

    /**
     * Create an instance of {@link DatafileFormat }
     * 
     */
    public DatafileFormat createDatafileFormat() {
        return new DatafileFormat();
    }

    /**
     * Create an instance of {@link GetRemainingMinutes }
     * 
     */
    public GetRemainingMinutes createGetRemainingMinutes() {
        return new GetRemainingMinutes();
    }

    /**
     * Create an instance of {@link SearchResponse }
     * 
     */
    public SearchResponse createSearchResponse() {
        return new SearchResponse();
    }

    /**
     * Create an instance of {@link Dataset }
     * 
     */
    public Dataset createDataset() {
        return new Dataset();
    }

    /**
     * Create an instance of {@link LoginResponse }
     * 
     */
    public LoginResponse createLoginResponse() {
        return new LoginResponse();
    }

    /**
     * Create an instance of {@link GetUserNameResponse }
     * 
     */
    public GetUserNameResponse createGetUserNameResponse() {
        return new GetUserNameResponse();
    }

    /**
     * Create an instance of {@link DatasetParameter }
     * 
     */
    public DatasetParameter createDatasetParameter() {
        return new DatasetParameter();
    }

    /**
     * Create an instance of {@link RelatedDatafile }
     * 
     */
    public RelatedDatafile createRelatedDatafile() {
        return new RelatedDatafile();
    }

    /**
     * Create an instance of {@link UserGroup }
     * 
     */
    public UserGroup createUserGroup() {
        return new UserGroup();
    }

    /**
     * Create an instance of {@link Shift }
     * 
     */
    public Shift createShift() {
        return new Shift();
    }

    /**
     * Create an instance of {@link CreateResponse }
     * 
     */
    public CreateResponse createCreateResponse() {
        return new CreateResponse();
    }

    /**
     * Create an instance of {@link Datafile }
     * 
     */
    public Datafile createDatafile() {
        return new Datafile();
    }

    /**
     * Create an instance of {@link Job }
     * 
     */
    public Job createJob() {
        return new Job();
    }

    /**
     * Create an instance of {@link Login.Credentials.Entry }
     * 
     */
    public Login.Credentials.Entry createLoginCredentialsEntry() {
        return new Login.Credentials.Entry();
    }

    /**
     * Create an instance of {@link Delete }
     * 
     */
    public Delete createDelete() {
        return new Delete();
    }

    /**
     * Create an instance of {@link GetEntityInfo }
     * 
     */
    public GetEntityInfo createGetEntityInfo() {
        return new GetEntityInfo();
    }

    /**
     * Create an instance of {@link EntityField }
     * 
     */
    public EntityField createEntityField() {
        return new EntityField();
    }

    /**
     * Create an instance of {@link InvestigationType }
     * 
     */
    public InvestigationType createInvestigationType() {
        return new InvestigationType();
    }

    /**
     * Create an instance of {@link Logout }
     * 
     */
    public Logout createLogout() {
        return new Logout();
    }

    /**
     * Create an instance of {@link ParameterType }
     * 
     */
    public ParameterType createParameterType() {
        return new ParameterType();
    }

    /**
     * Create an instance of {@link Login.Credentials }
     * 
     */
    public Login.Credentials createLoginCredentials() {
        return new Login.Credentials();
    }

    /**
     * Create an instance of {@link InvestigationParameter }
     * 
     */
    public InvestigationParameter createInvestigationParameter() {
        return new InvestigationParameter();
    }

    /**
     * Create an instance of {@link CreateMany }
     * 
     */
    public CreateMany createCreateMany() {
        return new CreateMany();
    }

    /**
     * Create an instance of {@link Publication }
     * 
     */
    public Publication createPublication() {
        return new Publication();
    }

    /**
     * Create an instance of {@link OutputDataset }
     * 
     */
    public OutputDataset createOutputDataset() {
        return new OutputDataset();
    }

    /**
     * Create an instance of {@link CreateManyResponse }
     * 
     */
    public CreateManyResponse createCreateManyResponse() {
        return new CreateManyResponse();
    }

    /**
     * Create an instance of {@link Facility }
     * 
     */
    public Facility createFacility() {
        return new Facility();
    }

    /**
     * Create an instance of {@link Investigation }
     * 
     */
    public Investigation createInvestigation() {
        return new Investigation();
    }

    /**
     * Create an instance of {@link Login }
     * 
     */
    public Login createLogin() {
        return new Login();
    }

    /**
     * Create an instance of {@link DeleteMany }
     * 
     */
    public DeleteMany createDeleteMany() {
        return new DeleteMany();
    }

    /**
     * Create an instance of {@link DatafileParameter }
     * 
     */
    public DatafileParameter createDatafileParameter() {
        return new DatafileParameter();
    }

    /**
     * Create an instance of {@link LogoutResponse }
     * 
     */
    public LogoutResponse createLogoutResponse() {
        return new LogoutResponse();
    }

    /**
     * Create an instance of {@link GetApiVersion }
     * 
     */
    public GetApiVersion createGetApiVersion() {
        return new GetApiVersion();
    }

    /**
     * Create an instance of {@link Keyword }
     * 
     */
    public Keyword createKeyword() {
        return new Keyword();
    }

    /**
     * Create an instance of {@link GetEntityInfoResponse }
     * 
     */
    public GetEntityInfoResponse createGetEntityInfoResponse() {
        return new GetEntityInfoResponse();
    }

    /**
     * Create an instance of {@link Rule }
     * 
     */
    public Rule createRule() {
        return new Rule();
    }

    /**
     * Create an instance of {@link Group }
     * 
     */
    public Group createGroup() {
        return new Group();
    }

    /**
     * Create an instance of {@link Create }
     * 
     */
    public Create createCreate() {
        return new Create();
    }

    /**
     * Create an instance of {@link DeleteResponse }
     * 
     */
    public DeleteResponse createDeleteResponse() {
        return new DeleteResponse();
    }

    /**
     * Create an instance of {@link SampleType }
     * 
     */
    public SampleType createSampleType() {
        return new SampleType();
    }

    /**
     * Create an instance of {@link EntityInfo }
     * 
     */
    public EntityInfo createEntityInfo() {
        return new EntityInfo();
    }

    /**
     * Create an instance of {@link Sample }
     * 
     */
    public Sample createSample() {
        return new Sample();
    }

    /**
     * Create an instance of {@link Dummy }
     * 
     */
    public Dummy createDummy() {
        return new Dummy();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateMany }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://icatproject.org", name = "createMany")
    public JAXBElement<CreateMany> createCreateMany(CreateMany value) {
        return new JAXBElement<CreateMany>(_CreateMany_QNAME, CreateMany.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Create }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://icatproject.org", name = "create")
    public JAXBElement<Create> createCreate(Create value) {
        return new JAXBElement<Create>(_Create_QNAME, Create.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetEntityInfoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://icatproject.org", name = "getEntityInfoResponse")
    public JAXBElement<GetEntityInfoResponse> createGetEntityInfoResponse(GetEntityInfoResponse value) {
        return new JAXBElement<GetEntityInfoResponse>(_GetEntityInfoResponse_QNAME, GetEntityInfoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://icatproject.org", name = "getResponse")
    public JAXBElement<GetResponse> createGetResponse(GetResponse value) {
        return new JAXBElement<GetResponse>(_GetResponse_QNAME, GetResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SearchResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://icatproject.org", name = "searchResponse")
    public JAXBElement<SearchResponse> createSearchResponse(SearchResponse value) {
        return new JAXBElement<SearchResponse>(_SearchResponse_QNAME, SearchResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Get }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://icatproject.org", name = "get")
    public JAXBElement<Get> createGet(Get value) {
        return new JAXBElement<Get>(_Get_QNAME, Get.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LoginResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://icatproject.org", name = "loginResponse")
    public JAXBElement<LoginResponse> createLoginResponse(LoginResponse value) {
        return new JAXBElement<LoginResponse>(_LoginResponse_QNAME, LoginResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DatafileParameter }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://icatproject.org", name = "datafileParameter")
    public JAXBElement<DatafileParameter> createDatafileParameter(DatafileParameter value) {
        return new JAXBElement<DatafileParameter>(_DatafileParameter_QNAME, DatafileParameter.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Datafile }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://icatproject.org", name = "datafile")
    public JAXBElement<Datafile> createDatafile(Datafile value) {
        return new JAXBElement<Datafile>(_Datafile_QNAME, Datafile.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DummyResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://icatproject.org", name = "dummyResponse")
    public JAXBElement<DummyResponse> createDummyResponse(DummyResponse value) {
        return new JAXBElement<DummyResponse>(_DummyResponse_QNAME, DummyResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUserName }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://icatproject.org", name = "getUserName")
    public JAXBElement<GetUserName> createGetUserName(GetUserName value) {
        return new JAXBElement<GetUserName>(_GetUserName_QNAME, GetUserName.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteMany }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://icatproject.org", name = "deleteMany")
    public JAXBElement<DeleteMany> createDeleteMany(DeleteMany value) {
        return new JAXBElement<DeleteMany>(_DeleteMany_QNAME, DeleteMany.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteManyResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://icatproject.org", name = "deleteManyResponse")
    public JAXBElement<DeleteManyResponse> createDeleteManyResponse(DeleteManyResponse value) {
        return new JAXBElement<DeleteManyResponse>(_DeleteManyResponse_QNAME, DeleteManyResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetApiVersionResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://icatproject.org", name = "getApiVersionResponse")
    public JAXBElement<GetApiVersionResponse> createGetApiVersionResponse(GetApiVersionResponse value) {
        return new JAXBElement<GetApiVersionResponse>(_GetApiVersionResponse_QNAME, GetApiVersionResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Dummy }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://icatproject.org", name = "dummy")
    public JAXBElement<Dummy> createDummy(Dummy value) {
        return new JAXBElement<Dummy>(_Dummy_QNAME, Dummy.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SampleParameter }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://icatproject.org", name = "sampleParameter")
    public JAXBElement<SampleParameter> createSampleParameter(SampleParameter value) {
        return new JAXBElement<SampleParameter>(_SampleParameter_QNAME, SampleParameter.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetRemainingMinutesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://icatproject.org", name = "getRemainingMinutesResponse")
    public JAXBElement<GetRemainingMinutesResponse> createGetRemainingMinutesResponse(GetRemainingMinutesResponse value) {
        return new JAXBElement<GetRemainingMinutesResponse>(_GetRemainingMinutesResponse_QNAME, GetRemainingMinutesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Dataset }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://icatproject.org", name = "dataset")
    public JAXBElement<Dataset> createDataset(Dataset value) {
        return new JAXBElement<Dataset>(_Dataset_QNAME, Dataset.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUserNameResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://icatproject.org", name = "getUserNameResponse")
    public JAXBElement<GetUserNameResponse> createGetUserNameResponse(GetUserNameResponse value) {
        return new JAXBElement<GetUserNameResponse>(_GetUserNameResponse_QNAME, GetUserNameResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://icatproject.org", name = "deleteResponse")
    public JAXBElement<DeleteResponse> createDeleteResponse(DeleteResponse value) {
        return new JAXBElement<DeleteResponse>(_DeleteResponse_QNAME, DeleteResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetRemainingMinutes }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://icatproject.org", name = "getRemainingMinutes")
    public JAXBElement<GetRemainingMinutes> createGetRemainingMinutes(GetRemainingMinutes value) {
        return new JAXBElement<GetRemainingMinutes>(_GetRemainingMinutes_QNAME, GetRemainingMinutes.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Logout }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://icatproject.org", name = "logout")
    public JAXBElement<Logout> createLogout(Logout value) {
        return new JAXBElement<Logout>(_Logout_QNAME, Logout.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InvestigationParameter }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://icatproject.org", name = "investigationParameter")
    public JAXBElement<InvestigationParameter> createInvestigationParameter(InvestigationParameter value) {
        return new JAXBElement<InvestigationParameter>(_InvestigationParameter_QNAME, InvestigationParameter.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Update }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://icatproject.org", name = "update")
    public JAXBElement<Update> createUpdate(Update value) {
        return new JAXBElement<Update>(_Update_QNAME, Update.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IcatException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://icatproject.org", name = "IcatException")
    public JAXBElement<IcatException> createIcatException(IcatException value) {
        return new JAXBElement<IcatException>(_IcatException_QNAME, IcatException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetApiVersion }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://icatproject.org", name = "getApiVersion")
    public JAXBElement<GetApiVersion> createGetApiVersion(GetApiVersion value) {
        return new JAXBElement<GetApiVersion>(_GetApiVersion_QNAME, GetApiVersion.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LogoutResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://icatproject.org", name = "logoutResponse")
    public JAXBElement<LogoutResponse> createLogoutResponse(LogoutResponse value) {
        return new JAXBElement<LogoutResponse>(_LogoutResponse_QNAME, LogoutResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://icatproject.org", name = "createResponse")
    public JAXBElement<CreateResponse> createCreateResponse(CreateResponse value) {
        return new JAXBElement<CreateResponse>(_CreateResponse_QNAME, CreateResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Delete }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://icatproject.org", name = "delete")
    public JAXBElement<Delete> createDelete(Delete value) {
        return new JAXBElement<Delete>(_Delete_QNAME, Delete.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetEntityInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://icatproject.org", name = "getEntityInfo")
    public JAXBElement<GetEntityInfo> createGetEntityInfo(GetEntityInfo value) {
        return new JAXBElement<GetEntityInfo>(_GetEntityInfo_QNAME, GetEntityInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Login }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://icatproject.org", name = "login")
    public JAXBElement<Login> createLogin(Login value) {
        return new JAXBElement<Login>(_Login_QNAME, Login.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DatasetParameter }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://icatproject.org", name = "datasetParameter")
    public JAXBElement<DatasetParameter> createDatasetParameter(DatasetParameter value) {
        return new JAXBElement<DatasetParameter>(_DatasetParameter_QNAME, DatasetParameter.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateManyResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://icatproject.org", name = "createManyResponse")
    public JAXBElement<CreateManyResponse> createCreateManyResponse(CreateManyResponse value) {
        return new JAXBElement<CreateManyResponse>(_CreateManyResponse_QNAME, CreateManyResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Search }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://icatproject.org", name = "search")
    public JAXBElement<Search> createSearch(Search value) {
        return new JAXBElement<Search>(_Search_QNAME, Search.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://icatproject.org", name = "updateResponse")
    public JAXBElement<UpdateResponse> createUpdateResponse(UpdateResponse value) {
        return new JAXBElement<UpdateResponse>(_UpdateResponse_QNAME, UpdateResponse.class, null, value);
    }

}
