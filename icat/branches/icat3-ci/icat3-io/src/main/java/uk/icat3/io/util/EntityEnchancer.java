package uk.icat3.io.util;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import uk.icat3.io.entity.ExtractedDataBean;
import uk.icat3.io.entity.FileId;
import uk.icat3.io.ids.IDSDownloader;

public class EntityEnchancer {

	Set<Object> processedEntities = new HashSet<Object>();

	public ExtractedDataBean enchance(List<?> resultList) {
		ExtractedDataBean dataBean = new ExtractedDataBean();
		dataBean.setResultList(resultList.toArray());
		
		Set<FileId> fileNames = new FileNameExtractor(resultList).getFileNames();
		Map<FileId, byte[]> files = new IDSDownloader().extractFiles(fileNames);
		dataBean.setFiles(files);
		
		return dataBean;
	}



}
