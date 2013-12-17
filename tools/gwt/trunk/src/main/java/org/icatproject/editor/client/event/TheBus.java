package org.icatproject.editor.client.event;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;

public class TheBus {
	private static EventBus instance; 

	public static EventBus getInstance() {
		// No need for synchronization - this is all single threaded
		if (instance == null) {
			instance = new SimpleEventBus();
		}
		return instance;
	}

}