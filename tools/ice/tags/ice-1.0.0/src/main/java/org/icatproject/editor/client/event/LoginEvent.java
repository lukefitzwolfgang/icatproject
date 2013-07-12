package org.icatproject.editor.client.event;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;

public class LoginEvent extends GwtEvent<LoginEventHandler> {

	public static Type<LoginEventHandler> TYPE = new Type<LoginEventHandler>();
	private String userName;
	private String sessionId;

	public LoginEvent(String userName, String sessionId) {
		this.userName = userName;
		this.sessionId = sessionId;
	}

	public static HandlerRegistration register(final EventBus eventBus, final LoginEventHandler handler) {
		return eventBus.addHandler(TYPE, handler);
	}

	@Override
	protected void dispatch(LoginEventHandler handler) {
		handler.onLogin(this);
	}

	@Override
	public GwtEvent.Type<LoginEventHandler> getAssociatedType() {
		return TYPE;
	}

	public String getUserName() {
		return userName;
	}

	public String getSessionId() {
		return sessionId;
	}

}