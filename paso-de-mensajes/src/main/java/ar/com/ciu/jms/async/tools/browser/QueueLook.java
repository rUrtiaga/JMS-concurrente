package ar.com.ciu.jms.async.tools.browser;

import java.util.Enumeration;

import javax.jms.JMSException;
import javax.jms.TextMessage;


public class QueueLook extends AQueueLook{
	
	public QueueLook(String name) {
		super(name);
	}

	
	@SuppressWarnings("rawtypes")
	protected void print(Enumeration e) throws JMSException {
		while (e.hasMoreElements()) {
			TextMessage message = (TextMessage) e.nextElement();
			System.out.println("Get [" + message.getText() + "]");
		}
	}

}
	