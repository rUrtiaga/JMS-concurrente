package ar.com.ciu.jms.async.tools.browser;

import java.util.Enumeration;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

public abstract class AQueueLook implements Runnable {

		private String queueName;
		
		public AQueueLook(String name) {
			this.queueName = name;
		}

		@Override
		public void run() {
			Connection connection = null;
			try {
				ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
				connection = connectionFactory.createConnection();
				Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
				Queue queue = session.createQueue(this.queueName);
				connection.start();
				System.out.println("Mensajes en la cola:");
				QueueBrowser browser = session.createBrowser(queue);
				@SuppressWarnings("rawtypes")
				Enumeration e = browser.getEnumeration();
				this.print(e);
				System.out.println("Fin");
				browser.close();
				session.close();
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

		protected abstract void print(@SuppressWarnings("rawtypes") Enumeration e) throws JMSException;
}
