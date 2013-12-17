package org.icatproject.editor.shared;

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class FieldShared implements IsSerializable {

	private String name;
	private String type;
	private boolean notNullable;
	private String comment;
	private Integer stringLength;
	private String relType;
	private List<KeyShared> keys;
	private List<String> permValues;

	public void setName(String name) {
		this.name = name;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public void setNotNullable(boolean notNullable) {
		this.notNullable = notNullable;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void setStringLength(Integer stringLength) {
		this.stringLength =stringLength;
	}

	public boolean isNotNullable() {
		return notNullable;
	}

	public String getComment() {
		return comment;
	}

	public Integer getStringLength() {
		return stringLength;
	}

	public void setRelType(String relType) {
		this.relType = relType;
	}

	public String getRelType() {
		return relType;
	}

	public List<KeyShared> getKeys() {
		return keys;
	}

	public void setKeys(List<KeyShared> keys) {
		this.keys = keys;
	}

	public void setPermValues(List<String> permValues) {
		this.permValues = permValues;
	}

	public List<String> getPermValues() {
		return permValues;
	}

}
