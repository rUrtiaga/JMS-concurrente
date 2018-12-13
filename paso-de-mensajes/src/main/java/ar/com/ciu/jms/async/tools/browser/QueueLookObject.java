package ar.com.ciu.jms.async.tools.browser;

import java.util.Enumeration;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;

import ar.com.ciu.jms.async.B_Producer_Consumer.Item;

public class QueueLookObject extends QueueLook {

	public QueueLookObject(String name) {
		super(name);
	}

	@SuppressWarnings("rawtypes")
	protected void print(Enumeration e) throws JMSException {
		while (e.hasMoreElements()) {
			ObjectMessage objMsg = (ObjectMessage) e.nextElement();
			Item i = (Item) objMsg.getObject();
			System.out.println("Get cant: [" + i.getCant() + "]  desc: [" + i.getDesc() + "]" );
		}
	}
}
