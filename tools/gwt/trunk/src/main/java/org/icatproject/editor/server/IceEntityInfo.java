package org.icatproject.editor.server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.icatproject.Constraint;
import org.icatproject.EntityField;

public class IceEntityInfo {
	private List<Constraint> constraints;
	private Map<String, EntityField> fields = new HashMap<String, EntityField>();

	public List<Constraint> getConstraints() {
		return constraints;
	}

	public Map<String, EntityField> getFields() {
		return fields;
	}

	public void setConstraints(List<Constraint> constraints) {
		this.constraints = constraints;
	}

}
