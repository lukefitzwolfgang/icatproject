package uk.icat3.io.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import uk.icat3.entity.Investigation;

@XmlRootElement
@XmlSeeAlso(Investigation.class)
public class ExtractedDataBean implements Serializable {

	private static final long serialVersionUID = 376309830832903672L;
	
	private Object[] resultList;
	
	private Map<FileId, byte[]> files;

	
	public void setResultList(Object[] resultList) {
		this.resultList = resultList;
	}

	public Object[] getResultList() {
		return resultList;
	}

	public void setFiles(Map<FileId, byte[]> files) {
		this.files = files;
	}

	public Map<FileId, byte[]> getFiles() {
		return files;
	}

}
