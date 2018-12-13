package ar.com.ciu.jms.async.H_topic_subscribe;

import javax.jms.JMSException;
import javax.jms.MessageProducer;

import ar.com.ciu.jms.async.tools.for_runnable.WithTopic;

public class Publisher extends WithTopic implements Runnable {

	private String publish_text;
	private MessageProducer messageProducer;
	
	public Publisher(String topic_name, String publish_text) {
		super(topic_name);
		this.publish_text = publish_text;
	}

	@Override
	public void run() {
		try {
			this.conectar();
			this.publish();
		} catch (JMSException e) {
			e.printStackTrace();
		} finally {
			this.closeConnection();
			this.closeSession();
		}
	}

	private void publish() throws JMSException {
		this.messageProducer = session.createProducer(topic);
		this.messageProducer.send(this.session.createTextMessage(this.publish_text));
		System.out.println(this.getName() + " publico el mensaje: " + this.publish_text);
	}

}
