package org.icatproject.editor.shared;

import java.io.Serializable;

@SuppressWarnings("serial")
public class KeyShared implements Serializable {

	private Long id;
	private String display;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

}
