package ar.com.ciu.jms.async.tools;

import javax.jms.JMSException;
import javax.jms.MessageProducer;

import ar.com.ciu.jms.async.tools.for_runnable.WithConnection;

public class Puter extends WithConnection implements Runnable {

	private String queueName;
	private String[] listTokens = null;
	private Integer number = null;

	public Puter(String queue_name, String[] list_tokens) {
		super(queue_name);
		this.queueName = queue_name;
		this.listTokens = list_tokens;
	}

	public Puter(String queue_name, Integer i) {
		super(queue_name);
		this.queueName = queue_name;
		this.number = i;
	}

	@Override
	public void run() {
		try {
			this.conectar();

			MessageProducer producer = session.createProducer(queue);

			if (this.listTokens != null) {
				for (int i = 0; i < listTokens.length; i++) {
					producer.send(session.createTextMessage(listTokens[i]));
				}
			}

			if (this.number != null)
				producer.send(session.createObjectMessage(this.number));

			System.out.println("Fin de poner " + this.queueName);
		} catch (JMSException e1) {
			e1.printStackTrace();
		} finally {
			this.closeSession();
			this.closeConnection();
		}
	}
}
