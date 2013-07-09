package org.icatproject.editor.shared;

@SuppressWarnings("serial")
public class EditorException extends Exception {

	public EditorException(String msg) {
		super(msg);
	}
	
	//	Needed for GWT RPC
	public EditorException(){};

}
