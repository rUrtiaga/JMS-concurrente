package ar.com.ciu.jms.async.C_Contador;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

import ar.com.ciu.jms.async.tools.Cleaner;
import ar.com.ciu.jms.async.tools.browser.QueueLook;

public class Main {
	static String NAME = "C_Contador";
	static int contador = 0;
	
	public static void main(String[] args) {

		Main.clean();
		
		Thread s1 = new Thread(new Sumador(2000));
		Thread s2 = new Thread(new Sumador(3000));
		
		Main.inizialice();
		
		s1.start();
		s2.start();
		
		
		try {
			s1.join();
			s2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		Main.clean();
		
		System.out.println(Main.contador);
	}
	
	private static void inizialice() {
		Session session = null;
		Connection connection = null;
		try {
			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
			connection = connectionFactory.createConnection();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Queue queue = session.createQueue(Main.NAME);
			MessageProducer producer = session.createProducer(queue);

			producer.send(session.createTextMessage("First TOKEN"));

		} catch (JMSException e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				try {
					session.close();
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private static void clean() {
		Thread clean = new Thread(new Cleaner(Main.NAME));
		clean.start();
		try {
			clean.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static void printListQueue() {
		Thread look = new Thread(new QueueLook(Main.NAME));
		look.start();
		try {
			look.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
