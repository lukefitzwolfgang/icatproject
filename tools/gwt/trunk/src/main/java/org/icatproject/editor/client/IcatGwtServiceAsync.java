package org.icatproject.editor.client;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.icatproject.editor.shared.CredType;
import org.icatproject.editor.shared.FieldShared;
import org.icatproject.editor.shared.KeyShared;
import org.icatproject.editor.shared.LoginResult;
import org.icatproject.editor.shared.UpdateShared;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface IcatGwtServiceAsync {

	/**
	 * Utility class for simplifying access to the instance of async service.
	 */
	public static class Util {
		private static IcatGwtServiceAsync instance;

		public static IcatGwtServiceAsync getInstance() {
			if (instance == null) {
				instance = GWT.create(IcatGwtService.class);
			}
			return instance;
		}
	}

	void getCreateForm(String sessionId, String beanName,
			AsyncCallback<List<FieldShared>> asyncCallback);

	void login(String plugin, Map<String, String> credentials,
			AsyncCallback<LoginResult> asyncCallback);

	void logout(String sessionId, AsyncCallback<Void> asyncCallback);

	void create(String sessionId, String beanName, Map<String, String> values,
			AsyncCallback<Void> asyncCallback);

	void getUpdateForm(String sessionId, String value, AsyncCallback<List<KeyShared>> asyncCallback);

	void getDeleteForm(String sessionId, String value, AsyncCallback<List<KeyShared>> asyncCallback);

	void delete(String sessionId, String beanName, List<Long> deletes,
			AsyncCallback<List<KeyShared>> asyncCallback);

	void getForUpdate(String sessionId, String beanName, Long id,
			AsyncCallback<UpdateShared> asyncCallback);

	void getBeanNames(AsyncCallback<List<String>> asyncCallback);

	void update(String sessionId, String beanName, Long id, Map<String, String> values,
			AsyncCallback<Void> asyncCallback);

	void getCredentialList(AsyncCallback<Map<String, List<CredType>>> asyncCallback);

}
