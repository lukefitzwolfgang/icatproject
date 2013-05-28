package org.icatproject.core.manager;

import java.util.Map;

import javax.persistence.EntityManager;

import org.icatproject.core.IcatException;
import org.icatproject.core.PropertyHandler;
import org.icatproject.core.PropertyHandler.Operation;
import org.icatproject.core.entity.EntityBaseBean;

public class NotificationMessage {

	public class Message {
		private Operation operation;
		private String entityName;
		private Long entityId;

		public Message(Operation operation, String entityName, Long entityId) {
			this.operation = operation;
			this.entityName = entityName;
			this.entityId = entityId;
		}

		public String getEntityName() {
			return entityName;
		}

		public long getEntityId() {
			return entityId;
		}

		public String getOperation() {
			return operation.name();
		}
	}

	private final static EntityInfoHandler entityInfoHandler = EntityInfoHandler.getInstance();

	public static EntityInfoHandler getEntityinfohandler() {
		return entityInfoHandler;
	}

	private Message message;

	public NotificationMessage(Operation operation, EntityBaseBean bean, EntityManager manager)
			throws IcatException {
		Map<String, NotificationRequest> notificationRequests = PropertyHandler.getInstance()
				.getNotificationRequests();
		String entity = bean.getClass().getSimpleName();
		String key = entity + ":" + operation.name();
		NotificationRequest nr = notificationRequests.get(key);
		if (nr != null) {
			message = new Message(operation, entity, bean.getId());
		}
	}

	public Message getMessage() {
		return this.message;
	}

}
