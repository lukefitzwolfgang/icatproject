package uk.icat3.io.beans;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.xml.bind.annotation.XmlSeeAlso;

import uk.icat3.entity.Investigation;
import uk.icat3.io.entity.ExtractedDataBean;
import uk.icat3.io.entity.FileId;
import uk.icat3.io.ids.IDSUploader;
import uk.icat3.io.util.EntityEnchancer;
import uk.icat3.io.util.EntitySerializer;
import uk.icat3.io.util.SessionUtil;
import uk.icat3.util.Queries;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@WebService(portName = "ICATIOServicePort", endpointInterface = "uk.icat3.io.beans.ICATIOWs", serviceName = "ICATIOService", targetNamespace = "io.icat3.uk")
@XmlSeeAlso({ Investigation.class })
public class ICATIOService implements ICATIOLocal, ICATIOWs {

	@PersistenceContext(unitName = "icat3-io-test")
	protected EntityManager manager;

	EntitySerializer serializer = new EntitySerializer();
	EntityEnchancer enchancer = new EntityEnchancer();

	@Override
	public ExtractedDataBean downloadData(String query) {
		try {

			Query q;
			if (query.contains("Investigation")) {
				q = manager.createQuery(Queries.RETURN_ALL_INVESTIGATIONS_JPQL);
			} else {
				q = manager.createQuery(Queries.RETURN_ALL_DATASETS_JPQL);
			}

			List resultList = q.getResultList();
			System.err.println(resultList);
			ExtractedDataBean enchance = enchancer.enchance(resultList);
			return enchance;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ExtractedDataBean();
	}

	@Override
	public void uploadData(String sessionId, ExtractedDataBean data) throws Exception {

//		ObjectInputStream ois;
		try {
//			ois = new ObjectInputStream(new ByteArrayInputStream(data));
//			Object object = ois.readObject();
			// manager.joinTransaction();
			// EntityTransaction transaction = manager.getTransaction();
			// transaction.begin();
			/*
			 * Investigation investigation = new Investigation(); investigation.setTitle(String.valueOf(new Random().nextInt())); investigation.setInvNumber(String.valueOf(new Random().nextInt()));
			 * investigation.setInvType("experiment"); investigation.setFacility("CLF");
			 */
			
//			ExtractedDataBean dataBean = (ExtractedDataBean) object;

			IDSUploader idsUploader = new IDSUploader(sessionId);
			
			Map<FileId, byte[]> files = data.getFiles();
			for (FileId fileId : files.keySet()) {
				byte[] content = files.get(fileId);
				idsUploader.uploadFile(fileId, content);
			}
			
//			manager.persist(i);
			
			
			// transaction.commit();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

}
