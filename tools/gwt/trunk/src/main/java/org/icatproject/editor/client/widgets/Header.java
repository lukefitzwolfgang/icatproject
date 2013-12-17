package org.icatproject.editor.client.widgets;

import java.util.List;
import java.util.logging.Logger;

import org.icatproject.editor.client.IcatGwtServiceAsync;
import org.icatproject.editor.client.Ice;
import org.icatproject.editor.client.event.LoginEvent;
import org.icatproject.editor.client.event.LoginEventHandler;
import org.icatproject.editor.client.event.TheBus;
import org.icatproject.editor.shared.FieldShared;
import org.icatproject.editor.shared.KeyShared;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

public class Header extends Composite {

	private static final IcatGwtServiceAsync icat = IcatGwtServiceAsync.Util.getInstance();

	private static HeaderUiBinder uiBinder = GWT.create(HeaderUiBinder.class);

	final private static Logger logger = Logger.getLogger(Header.class.getName());

	interface HeaderUiBinder extends UiBinder<Widget, Header> {
	}

	private Ice ice;

	private String sessionId;

	private static final EventBus bus = TheBus.getInstance();

	public Header(Ice ice) {
		initWidget(uiBinder.createAndBindUi(this));
		this.ice = ice;

		LoginEvent.register(bus, new LoginEventHandler() {
			@Override
			public void onLogin(LoginEvent event) {
				if (event.getUserName() == null) {
					userName.setText("");
					sessionId = null;

				} else {
					userName.setText("Logged in as " + event.getUserName());
					sessionId = event.getSessionId();
				}
			}
		});

		icat.getBeanNames(new AsyncCallback<List<String>>() {

			@Override
			public void onFailure(Throwable caught) {
				Ice.processFailure(caught);
			}

			@Override
			public void onSuccess(List<String> beans) {
				for (String bean : beans) {
					logger.fine("Add " + bean);
					beanNames.addItem(bean);
				}
			}
		});
	}

	@UiField
	Button logoutButton;

	@UiField
	ListBox beanNames;

	@UiField
	ListBox action;

	@UiField
	Label userName;

	@UiHandler("action")
	void onActionSelect(ClickEvent e) {
		process();
	}

	@UiHandler("beanNames")
	void onbeanNamesSelect(ClickEvent e) {
		process();
	}

	@UiHandler("logoutButton")
	void onLogoutClick(ClickEvent e) {
		icat.logout(sessionId, new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				Ice.processFailure(caught);
			}

			@Override
			public void onSuccess(Void result) {
				bus.fireEvent(new LoginEvent(null, null));
			}
		});
	}

	void process() {
		final String beanName = beanNames.getValue(beanNames.getSelectedIndex());

		String op = action.getItemText(action.getSelectedIndex());
		if (op.equals("Create")) {

			icat.getCreateForm(sessionId, beanName, new AsyncCallback<List<FieldShared>>() {

				@Override
				public void onFailure(Throwable caught) {
					Window.alert(Ice.processFailure(caught));
				}

				@Override
				public void onSuccess(List<FieldShared> fields) {
					logger.fine("Create a creator panel");
					ice.setPanel(Ice.PanelType.CREATOR, beanName, fields);
				}
			});

		}

		else if (op.equals("Update")) {
			icat.getUpdateForm(sessionId, beanName, new AsyncCallback<List<KeyShared>>() {

				@Override
				public void onFailure(Throwable caught) {
					Window.alert(Ice.processFailure(caught));
				}

				@Override
				public void onSuccess(List<KeyShared> keys) {
					ice.setPanel(Ice.PanelType.UPDATER, beanName, keys);
				}
			});

		} else { // Delete
			icat.getDeleteForm(sessionId, beanName, new AsyncCallback<List<KeyShared>>() {

				@Override
				public void onFailure(Throwable caught) {
					Window.alert(Ice.processFailure(caught));
				}

				@Override
				public void onSuccess(List<KeyShared> keys) {
					ice.setPanel(Ice.PanelType.DELETER, beanName, keys);
				}
			});
		}
	}

}
