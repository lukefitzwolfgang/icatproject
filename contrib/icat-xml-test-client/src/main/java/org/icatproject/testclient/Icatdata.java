package org.icatproject.testclient;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

import org.icatproject.Application;
import org.icatproject.Datafile;
import org.icatproject.DatafileFormat;
import org.icatproject.DatafileParameter;
import org.icatproject.Dataset;
import org.icatproject.DatasetParameter;
import org.icatproject.DatasetType;
import org.icatproject.EntityBaseBean;
import org.icatproject.Facility;
import org.icatproject.FacilityCycle;
import org.icatproject.Group;
import org.icatproject.InputDatafile;
import org.icatproject.InputDataset;
import org.icatproject.Instrument;
import org.icatproject.InstrumentScientist;
import org.icatproject.Investigation;
import org.icatproject.InvestigationParameter;
import org.icatproject.InvestigationType;
import org.icatproject.InvestigationUser;
import org.icatproject.Job;
import org.icatproject.Keyword;
import org.icatproject.NotificationRequest;
import org.icatproject.OutputDatafile;
import org.icatproject.OutputDataset;
import org.icatproject.ParameterType;
import org.icatproject.PermissibleStringValue;
import org.icatproject.Publication;
import org.icatproject.RelatedDatafile;
import org.icatproject.Rule;
import org.icatproject.Sample;
import org.icatproject.SampleParameter;
import org.icatproject.SampleType;
import org.icatproject.Shift;
import org.icatproject.Study;
import org.icatproject.StudyInvestigation;
import org.icatproject.User;
import org.icatproject.UserGroup;

/**
 * A data container for all icat datatypes so they can be serialized in xml.
 * 
 * It also contains configuration information for the icat client.
 * 
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Icatdata extends EntityBaseBean {

	@XmlAccessorType(XmlAccessType.FIELD)
	public static class Config {
		/**
		 * If true any exceptions stops the client, otherwise client continues.
		 */
		private boolean haltOnError = false;

		@XmlAccessorType(XmlAccessType.FIELD)
		public static class LocalIdRange {
			/**
			 * The min value for file local reference ids. By default Long.MIN_VALUE
			 */
			private Long min = Long.MIN_VALUE;

			/**
			 * The max value for file local reference ids. By default Long.MAX_VALUE
			 */
			private Long max = Long.MAX_VALUE;

			public Long getMin() {
				return min;
			}

			public void setMin(Long min) {
				this.min = (min == null ? Long.MIN_VALUE : min);
			}

			public Long getMax() {
				return max;
			}

			public void setMax(Long max) {
				this.max = (max == null ? Long.MAX_VALUE : max);
			}

			public boolean inRange(final Long id) {
				return (id >= this.min && id <= this.max);
			}
		}

		/**
		 * Define a range of local ids. I.e. ids which should not be used to retrieve objects from icat but only be used
		 * to define file local references.
		 */
		private LocalIdRange localIdRange = new LocalIdRange();

		@XmlAccessorType(XmlAccessType.FIELD)
		public static class SearchContainer {
			private Long id;

			private String query;

			public Long getId() {
				return id;
			}

			public void setId(Long id) {
				this.id = id;
			}

			public String getQuery() {
				return query;
			}

			public void setQuery(String query) {
				this.query = query;
			}
		}

		@XmlElementWrapper(name = "searchids")
		@XmlElement(name = "search")
		private List<SearchContainer> searchids = new ArrayList<SearchContainer>();

		/**
		 * All datatypes (tables) which should be cleaned (delete all entries) before the import.
		 */
		@XmlElementWrapper(name = "entities2Clean")
		@XmlElement(name = "query")
		private List<String> entities2clean = new ArrayList<String>();

		public boolean getHaltOnError() {
			return haltOnError;
		}

		public void setHaltOnError(boolean haltOnError) {
			this.haltOnError = haltOnError;
		}

		public LocalIdRange getLocalIdRange() {
			return localIdRange;
		}

		public void setLocalIdRange(LocalIdRange localIdRange) {
			this.localIdRange = localIdRange;
		}

		public List<SearchContainer> getSearchids() {
			return searchids;
		}

		public void setSearchids(List<SearchContainer> searchids) {
			this.searchids = searchids;
		}

		public List<String> getEntities2clean() {
			return entities2clean;
		}

		public void setEntities2clean(List<String> entities2clean) {
			this.entities2clean = entities2clean;
		}
	}

	/**
	 * The icat client config object. See it's properties for details.
	 */
	private Config config = new Config();

	public Config getConfig() {
		return config;
	}

	public void setConfig(Config config) {
		this.config = config;
	}

	/**
	 * The data container for a list of EntityBaseBeans. Can contain any number of any icat datatype.
	 */
	@XmlElements({ @XmlElement(name = "application", type = Application.class),
			@XmlElement(name = "datafile", type = Datafile.class),
			@XmlElement(name = "datafileFormat", type = DatafileFormat.class),
			@XmlElement(name = "dataset", type = Dataset.class),
			@XmlElement(name = "datasetType", type = DatasetType.class),
			@XmlElement(name = "facility", type = Facility.class),
			@XmlElement(name = "facilityCycle", type = FacilityCycle.class),
			@XmlElement(name = "group", type = Group.class),
			@XmlElement(name = "inputDatafile", type = InputDatafile.class),
			@XmlElement(name = "inputDataset", type = InputDataset.class),
			@XmlElement(name = "instrument", type = Instrument.class),
			@XmlElement(name = "instrumentScientist", type = InstrumentScientist.class),
			@XmlElement(name = "investigation", type = Investigation.class),
			@XmlElement(name = "investigationType", type = InvestigationType.class),
			@XmlElement(name = "investigationUser", type = InvestigationUser.class),
			@XmlElement(name = "job", type = Job.class), @XmlElement(name = "keyword", type = Keyword.class),
			@XmlElement(name = "notificationRequest", type = NotificationRequest.class),
			@XmlElement(name = "outputDatafile", type = OutputDatafile.class),
			@XmlElement(name = "outputDataset", type = OutputDataset.class),
			@XmlElement(name = "datafileParameter", type = DatafileParameter.class),
			@XmlElement(name = "datasetParameter", type = DatasetParameter.class),
			@XmlElement(name = "investigationParameter", type = InvestigationParameter.class),
			@XmlElement(name = "sampleParameter", type = SampleParameter.class),
			@XmlElement(name = "parameterType", type = ParameterType.class),
			@XmlElement(name = "permissibleStringValue", type = PermissibleStringValue.class),
			@XmlElement(name = "publication", type = Publication.class),
			@XmlElement(name = "relatedDatafile", type = RelatedDatafile.class),
			@XmlElement(name = "rule", type = Rule.class), @XmlElement(name = "sample", type = Sample.class),
			@XmlElement(name = "sampleType", type = SampleType.class), @XmlElement(name = "shift", type = Shift.class),
			@XmlElement(name = "study", type = Study.class),
			@XmlElement(name = "studyInvestigation", type = StudyInvestigation.class),
			@XmlElement(name = "user", type = User.class), @XmlElement(name = "userGroup", type = UserGroup.class) })
	private List<EntityBaseBean> beans = new ArrayList<EntityBaseBean>();

	public List<EntityBaseBean> getBeans() {
		return beans;
	}

	public void setBeans(List<EntityBaseBean> beans) {
		this.beans = beans;
	}

}