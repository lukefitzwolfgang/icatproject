package org.icatproject.editor.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

public interface IceResources extends ClientBundle {

	public static final IceResources INSTANCE = GWT.create(IceResources.class);

	@Source("Ice.css")
	CssStyle css();

	public interface CssStyle extends CssResource {
		String red();
	}

}
