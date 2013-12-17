package org.icatproject.editor.client;

import java.util.List;
import java.util.Map;

import org.icatproject.editor.shared.CredType;
import org.icatproject.editor.shared.EditorException;
import org.icatproject.editor.shared.LoginResult;
import org.icatproject.editor.shared.SessionException;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("IcatGwtService")
public interface IcatGwtService extends RemoteService {

	Map<String, List<CredType>> getCredentialList();

	LoginResult login(String plugin, Map<String, String> credentials) throws EditorException,
			SessionException;

	void logout(String sessionId);

}
