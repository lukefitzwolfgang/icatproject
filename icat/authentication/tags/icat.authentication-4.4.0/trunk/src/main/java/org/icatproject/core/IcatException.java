package org.icatproject.core;

import javax.ejb.ApplicationException;
import javax.ws.rs.core.Response.Status;

@SuppressWarnings("serial")
@ApplicationException(rollback = true)
public class IcatException extends Exception {

	public enum IcatExceptionType {
		BAD_PARAMETER(Status.BAD_REQUEST), INTERNAL(Status.INTERNAL_SERVER_ERROR), INSUFFICIENT_PRIVILEGES(
				Status.FORBIDDEN), NO_SUCH_OBJECT_FOUND(Status.NOT_FOUND), 
				OBJECT_ALREADY_EXISTS(Status.PRECONDITION_FAILED), SESSION(Status.FORBIDDEN), 
				VALIDATION(Status.PRECONDITION_FAILED);

		private Status status;

		private IcatExceptionType(Status status) {
			this.status = status;
		}

		public Status getStatus() {
			return status;
		}
	
	}

	private IcatExceptionType type;
	private int offset = -1;

	@Override
	public String toString() {
		return type + " " + super.toString();
	}

	public IcatException(IcatExceptionType type, String msg) {
		super(msg);
		this.type = type;
	}

	public IcatException(IcatExceptionType type, String msg, int offset) {
		this(type, msg);
		this.offset = offset;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public IcatExceptionType getType() {
		return type;
	}

	public void setType(IcatExceptionType type) {
		this.type = type;
	}
}
