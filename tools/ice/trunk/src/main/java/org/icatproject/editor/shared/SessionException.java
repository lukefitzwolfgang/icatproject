package org.icatproject.editor.shared;

@SuppressWarnings("serial")
public class SessionException extends Exception {

	public SessionException(String msg) {
		super(msg);
	}
	
//	Needed for GWT RPC
	public SessionException(){};

}
