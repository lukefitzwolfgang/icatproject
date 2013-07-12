package org.icatproject.editor.client;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.icatproject.editor.client.event.LoginEvent;
import org.icatproject.editor.client.event.LoginEventHandler;
import org.icatproject.editor.client.event.TheBus;
import org.icatproject.editor.client.widgets.Creator;
import org.icatproject.editor.client.widgets.Deleter;
import org.icatproject.editor.client.widgets.Header;
import org.icatproject.editor.client.widgets.LoginPanel;
import org.icatproject.editor.client.widgets.Updater;
import org.icatproject.editor.shared.FieldShared;
import org.icatproject.editor.shared.KeyShared;
import org.icatproject.editor.shared.SessionException;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.StatusCodeException;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

public class Ice implements EntryPoint {
	
	final private static Logger logger = Logger.getLogger(Ice.class.getName());

	private Widget main;
	private DialogBox loginDialog;
	protected String sessionId;
	private LoginPanel loginPanel;
	private static final EventBus bus = TheBus.getInstance();

	public enum PanelType {
		CREATOR, NONE, UPDATER, DELETER
	};

	public void onModuleLoad() {

		IceResources.INSTANCE.css().ensureInjected();

		RootPanel.get().add(new Header(this));

		loginDialog = new DialogBox();
		loginDialog.setText("Login");
		loginDialog.setGlassEnabled(true);
		loginDialog.setAnimationEnabled(true);
		loginPanel = new LoginPanel();
		loginDialog.setWidget(loginPanel);
		loginDialog.center();
		loginPanel.focus();

		LoginEvent.register(bus, new LoginEventHandler() {
			@Override
			public void onLogin(LoginEvent event) {
				if (event.getUserName() == null) {
					sessionId = null;
					loginDialog.show();
					loginPanel.focus();
					setPanel(Ice.PanelType.NONE, null, null);
				} else {
					sessionId = event.getSessionId();
					loginDialog.hide();
				}
			}
		});
	}

	@SuppressWarnings("unchecked")
	public void setPanel(PanelType panelType, String beanName, List<?> things) {
		if (main != null) {
			RootPanel.get().remove(main);
		}
		if (panelType == PanelType.CREATOR) {
			main = new Creator(sessionId, beanName, (List<FieldShared>) things);
			RootPanel.get().add(main);
		} else if (panelType == PanelType.UPDATER) {
			main = new Updater(sessionId, beanName, (List<KeyShared>) things);
			RootPanel.get().add(main);
		} else if (panelType == PanelType.DELETER) {
			main = new Deleter(sessionId, beanName, (List<KeyShared>) things);
			RootPanel.get().add(main);
		}
	}

	public static String processFailure(Throwable caught) {
		if (caught instanceof StatusCodeException) {
			StatusCodeException statusCodeException = (StatusCodeException) caught;
			if (statusCodeException.getStatusCode() == 0) {
				GWT.log("Update on failure with status code 0. Please increase number of threads on servlet container");
			} else {
				GWT.log("Update on failure with StatusCodeException: " + statusCodeException.getMessage());
			}
			return "There was an unexpected internal exception - please note the time " + new Date() + " and report it";
		}
		if (caught instanceof SessionException) {
			bus.fireEvent(new LoginEvent(null, null));
		}
		logger.fine(caught.getMessage());
		return caught.getMessage();
	}

}
