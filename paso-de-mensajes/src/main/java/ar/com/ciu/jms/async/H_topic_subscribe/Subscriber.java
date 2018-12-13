package ar.com.ciu.jms.async.H_topic_subscribe;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.TextMessage;

import ar.com.ciu.jms.async.tools.for_runnable.WithTopic;

public class Subscriber extends WithTopic implements Runnable {

	MessageConsumer messageConsumer;
	String texto;
	
	public Subscriber(String topic_name) {
		super(topic_name);
	}
	
	@Override
	public void run() {
		try {
			this.conectar();
			this.subscribe();
			this.getMessage();
		} catch (JMSException e) {
			e.printStackTrace();
		} finally {
			this.closeSession();
			this.closeConnection();
		}
	}

	private void subscribe() throws JMSException {
		this.messageConsumer = session.createConsumer(topic);
	}

	public String getMessage() throws JMSException {
		Message message = this.messageConsumer.receive(600);

		if (message != null) {
			TextMessage textMessage = (TextMessage) message;
			texto = textMessage.getText();
		}
		String forPrintMessage =this.texto != null ? this.texto:" no hay mensajes nuevos"; 
		System.out.println(this.getName() + " " + forPrintMessage);

		return texto;
	}
	
}
