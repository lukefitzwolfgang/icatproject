package org.icatproject.editor.client;

import java.util.List;
import java.util.Map;

import org.icatproject.editor.shared.CredType;
import org.icatproject.editor.shared.LoginResult;

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

	void getCredentialList(AsyncCallback<Map<String, List<CredType>>> asyncCallback);

	void login(String plugin, Map<String, String> credentials, AsyncCallback<LoginResult> callback);

	void logout(String sessionId, AsyncCallback<Void> callback);

}
