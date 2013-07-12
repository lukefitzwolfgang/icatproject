package org.icatproject.editor.shared;

import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.IsSerializable;

public class UpdateShared implements IsSerializable {

	private List<FieldShared> fields;

	private Map<String, String> values;

	public UpdateShared(List<FieldShared> fields, Map<String, String> values) {
		this.fields = fields;
		this.values = values;
	}

	/* For GWT */
	public UpdateShared() {
	}

	public List<FieldShared> getFields() {
		return fields;
	}

	public Map<String, String> getValues() {
		return values;
	}

}