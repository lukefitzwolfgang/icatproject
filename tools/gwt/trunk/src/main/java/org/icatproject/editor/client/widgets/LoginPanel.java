package org.icatproject.editor.client.widgets;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.icatproject.editor.client.IcatGwtServiceAsync;
import org.icatproject.editor.client.event.LoginEvent;
import org.icatproject.editor.client.event.TheBus;
import org.icatproject.editor.shared.CredType;
import org.icatproject.editor.shared.CredType.Visibility;
import org.icatproject.editor.shared.LoginResult;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class LoginPanel extends Composite {

	interface MyUiBinder extends UiBinder<Widget, LoginPanel> {
	}

	@UiField
	ListBox authnList;

	@UiField
	FlexTable credentials;

	@UiField
	Label messageLabel;

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);
	private static final IcatGwtServiceAsync icat = IcatGwtServiceAsync.Util.getInstance();

	private static final EventBus bus = TheBus.getInstance();

	private TextBox first;

	private Map<String, List<CredType>> credTypes;

	public LoginPanel() {
		initWidget(uiBinder.createAndBindUi(this));
		icat.getCredentialList(new AsyncCallback<Map<String, List<CredType>>>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Ice.processFailure(caught);
			}

			@Override
			public void onSuccess(Map<String, List<CredType>> credTypes) {
				LoginPanel.this.credTypes = credTypes;
				for (Entry<String, List<CredType>> entry : credTypes.entrySet()) {
					authnList.addItem(entry.getKey());
				}

				authnList.setSelectedIndex(0);
				buildForm();

			}
		});

	}

	@UiHandler("authnList")
	void handleAuthnClick(ClickEvent e) {
		buildForm();
	}

	private void buildForm() {
		String method = authnList.getValue(authnList.getSelectedIndex());
		List<CredType> creds = credTypes.get(method);
		credentials.removeAllRows();
		int i = 0;
		for (CredType cred : creds) {
			credentials.setWidget(i, 0, new Label(cred.getName()));
			if (cred.getVisibility() == Visibility.EXPOSED) {
				credentials.setWidget(i, 1, new TextBox());
			} else {
				credentials.setWidget(i, 1, new PasswordTextBox());
			}
			i++;
		}
		if (credentials.getRowCount() > 0) {
			TextBox widget = (TextBox) credentials.getWidget(i - 1, 1);
			widget.addKeyUpHandler(new KeyUpHandler() {
				@Override
				public void onKeyUp(KeyUpEvent event) {
					if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
						login();
					}
				}
			});
			first = (TextBox) credentials.getWidget(0, 1);
		} else {
			first = null;
		}
	}

	@UiHandler("login")
	void handleClick(ClickEvent e) {
		login();
	}

	void setMessageText(String messageText) {
		messageLabel.setText(messageText);
	}

	void handlePasswordEneter(KeyUpEvent event) {
		if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
			login();
		}
	}

	private void login() {
		String method = authnList.getValue(authnList.getSelectedIndex());
		Map<String, String> credMap = new HashMap<String, String>();
		for (int i = 0; i < credentials.getRowCount(); i++) {
			String key = ((Label) credentials.getWidget(i, 0)).getText();
			String value = ((TextBox) credentials.getWidget(i, 1)).getText();
			credMap.put(key, value);
		}

		icat.login(method, credMap, new AsyncCallback<LoginResult>() {

			@Override
			public void onFailure(Throwable caught) {
				messageLabel.setText("Error: " + caught.getMessage());
			}

			@Override
			public void onSuccess(LoginResult loginResult) {
				bus.fireEvent(new LoginEvent(loginResult.getUserName(), loginResult.getSessionId()));
				messageLabel.setText("");
			}

		});
	}

	public void focus() {
		if (first != null) {
			first.setFocus(true);
		}
	}

}
