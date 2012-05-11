package org.icatproject.core.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.Marshaller;

import org.apache.log4j.Logger;

@Comment("An experimental facility")
@SuppressWarnings("serial")
@Entity
public class Facility extends EntityBaseBean implements Serializable {

	private static Logger logger = Logger.getLogger(Facility.class);

	@Comment("The number of days before data is made freely available after collecting it.")
	private Integer daysUntilRelease;

	@Comment("A description of this facility")
	private String description;

	@Comment("The full name of the facility")
	private String fullName;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "facility")
	private List<Investigation> investigations = new ArrayList<Investigation>();
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "facility")
	private List<InvestigationType> investigationTypes = new ArrayList<InvestigationType>();

	public List<InvestigationType> getInvestigationTypes() {
		return investigationTypes;
	}

	public void setInvestigationTypes(List<InvestigationType> investigationTypes) {
		this.investigationTypes = investigationTypes;
	}

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "facility")
	private List<DatafileFormat> datafileFormats = new ArrayList<DatafileFormat>();

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "facility")
	private List<DatasetType> datasetTypes = new ArrayList<DatasetType>();

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "facility")
	private List<ParameterType> parameterTypes = new ArrayList<ParameterType>();
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "facility")
	private List<SampleType> sampleTypes = new ArrayList<SampleType>();

	public List<SampleType> getSampleTypes() {
		return sampleTypes;
	}

	public void setSampleTypes(List<SampleType> sampleTypes) {
		this.sampleTypes = sampleTypes;
	}

	public List<ParameterType> getParameterTypes() {
		return parameterTypes;
	}

	public void setParameterTypes(List<ParameterType> parameterTypes) {
		this.parameterTypes = parameterTypes;
	}

	public List<DatafileFormat> getDatafileFormats() {
		return datafileFormats;
	}

	public void setDatafileFormats(List<DatafileFormat> datafileFormats) {
		this.datafileFormats = datafileFormats;
	}

	public List<DatasetType> getDatasetTypes() {
		return datasetTypes;
	}

	public void setDatasetTypes(List<DatasetType> datasetTypes) {
		this.datasetTypes = datasetTypes;
	}

	@Comment("A short name identifying this facility")
	@Id
	private String name;

	@Comment("A URL associated with this facility")
	private String url;

	/* Needed for JPA */
	public Facility() {
	}

	public void beforeMarshal(Marshaller source) {
		logger.trace("Marshalling Investigation for " + this.includes);
		if (!this.includes.contains(Investigation.class)) {
			this.investigations = null;
		}
		if (!this.includes.contains(DatafileFormat.class)) {
			this.datafileFormats = null;
		}
		if (!this.includes.contains(DatasetType.class)) {
			this.datasetTypes = null;
		}
		if (!this.includes.contains(ParameterType.class)) {
			this.parameterTypes = null;
		}
		if (!this.includes.contains(SampleType.class)) {
			this.sampleTypes = null;
		}
	}

	public Integer getDaysUntilRelease() {
		return this.daysUntilRelease;
	}

	public String getDescription() {
		return this.description;
	}

	public String getFullName() {
		return this.fullName;
	}

	public List<Investigation> getInvestigations() {
		return this.investigations;
	}

	public String getName() {
		return this.name;
	}

	@Override
	public Object getPK() {
		return this.name;
	}

	public String getUrl() {
		return url;
	}

	public void setDaysUntilRelease(Integer daysUntilRelease) {
		this.daysUntilRelease = daysUntilRelease;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public void setInvestigations(List<Investigation> investigations) {
		this.investigations = investigations;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "Facility[name=" + this.name + "]";
	}

}
