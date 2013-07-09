package org.icatproject.editor.client;

import java.util.List;
import java.util.Map;

import org.icatproject.editor.shared.CredType;
import org.icatproject.editor.shared.EditorException;
import org.icatproject.editor.shared.FieldShared;
import org.icatproject.editor.shared.KeyShared;
import org.icatproject.editor.shared.LoginResult;
import org.icatproject.editor.shared.SessionException;
import org.icatproject.editor.shared.UpdateShared;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("IcatGwtService")
public interface IcatGwtService extends RemoteService {

	List<FieldShared> getCreateForm(String sessionId, String beanName) throws EditorException,
			SessionException;

	void logout(String sessionId);

	void create(String sessionId, String beanName, Map<String, String> values)
			throws EditorException, SessionException;

	List<KeyShared> getUpdateForm(String sessionId, String value) throws EditorException,
			SessionException;

	List<KeyShared> getDeleteForm(String sessionId, String value) throws EditorException,
			SessionException;

	List<KeyShared> delete(String sessionId, String beanName, List<Long> deletes)
			throws EditorException, SessionException;

	UpdateShared getForUpdate(String sessionId, String beanName, Long update)
			throws EditorException, SessionException;

	LoginResult login(String plugin, Map<String, String> credentials) throws EditorException,
			SessionException;

	List<String> getBeanNames();

	void update(String sessionId, String beanName, Long id, Map<String, String> values)
			throws EditorException, SessionException;

	Map<String, List<CredType>> getCredentialList();

}
