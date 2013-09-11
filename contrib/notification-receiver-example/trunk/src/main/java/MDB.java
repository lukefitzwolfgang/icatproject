import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

/*
 * The MessageDriven annotation defines a message selector with an SQL like syntax to select only datafile creates.
 */
@MessageDriven(mappedName = "jms/ICAT/Topic", activationConfig = { @ActivationConfigProperty(propertyName = "messageSelector", propertyValue = "entity = 'Datafile' AND operation='C'") })
public class MDB implements MessageListener {

	/**
	 * This is a singleton as the container may create many instances of a message driven bean and
	 * we choose to have one instance dealing with all messages. In this case it is not necessary
	 * however if we wish to deal with create and delete of data files then having one instance with
	 * an overview is preferable.
	 */
	static private final DelayedAction delayedAction = DelayedAction.INSTANCE;

	@Override
	public void onMessage(Message message) {

		ObjectMessage om = (ObjectMessage) message;
		try {
			/* The values of entity and operation are constrained by the message selector */
			String operation = om.getStringProperty("operation");
			String entity = om.getStringProperty("entity");
			Long id = (Long) om.getObject();
			delayedAction.note(operation, entity, id);

		} catch (JMSException e) {
			e.printStackTrace();
		}

	}

}