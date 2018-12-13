package ar.com.ciu.jms.async.tools;

import java.util.Enumeration;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Cleaner implements Runnable {

	private String queueName;
	
	public Cleaner(String name) {
		this.queueName = name;
	}

	@Override
	public void run() {
		Connection connection = null;
		try {
			int count = 0;
			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
			connection = connectionFactory.createConnection();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Queue queue = session.createQueue(this.queueName);
			connection.start();
			System.out.println("Empezando a limpiar " + this.queueName);

			QueueBrowser browser = session.createBrowser(queue);
			@SuppressWarnings("rawtypes")
			Enumeration e = browser.getEnumeration();
			while (e.hasMoreElements()) {
				e.nextElement();
				count++;
			}
			browser.close();

			MessageConsumer consumer = session.createConsumer(queue);
			for (int i = 0; i < count; i++) {
				consumer.receive();
			}

			session.close();
			System.out.println("Fin de limpieza"+ this.queueName);
		} catch (JMSException e1) {
			e1.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
