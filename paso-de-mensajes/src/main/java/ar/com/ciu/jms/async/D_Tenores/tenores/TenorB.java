package ar.com.ciu.jms.async.D_Tenores.tenores;

import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.TextMessage;

import ar.com.ciu.jms.async.D_Tenores.Main;
import ar.com.ciu.jms.async.tools.for_runnable.WithConnection;

public class TenorB extends WithConnection implements Runnable {
	TextMessage recibedMessage;
	String requiredMsg;
	String line;
	String myToken;

	public TenorB(String requiredMsg) {
		super(Main.NAME);
		this.requiredMsg = requiredMsg;

		this.line = "MSJ 2";
		this.myToken = "B";
	}

	@Override
	public void run() {
		try {
			this.conectar();
			
			this.waitTenor();

			MessageProducer producer = session.createProducer(queue);
			System.out.println(line);
			producer.send(session.createTextMessage(myToken));

		} catch (JMSException e) {
			e.printStackTrace();
		} finally {
			this.closeSession();
			this.closeConnection();
		}
	}

	private void waitTenor() throws JMSException {
		this.recibeMessage();
		while (!requiredMsg.equals(recibedMessage.getText())) {
			this.pushRecibedMsg();
			this.recibeMessage();
		}
	}

	private void recibeMessage() throws JMSException {
		MessageConsumer consumer = session.createConsumer(queue);
		recibedMessage = (TextMessage) consumer.receive();

		System.out.println(this.getName() +" Received: " + recibedMessage.getText());
		consumer.close();
	}

	private void pushRecibedMsg() throws JMSException {
		MessageProducer producer = session.createProducer(queue);

		producer.send(recibedMessage);
		producer.close();
	}

}
