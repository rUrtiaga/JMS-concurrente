package ar.com.ciu.jms.async.tools.for_runnable;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;

public class WithTopic {
	protected Topic topic;
	protected Connection connection;
	protected Session session;
	protected String t_name;

	public WithTopic(String name) {
		this.t_name = name;
		this.connection = null;
		this.session = null;
	}

	protected void conectar() throws JMSException {
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
		connection = connectionFactory.createConnection();

		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		if(this.t_name != null) {
			topic = session.createTopic(this.t_name);
		}
		connection.start();
	}

	public void closeConnection() {
		if (connection != null) {
			try {
				connection.close();
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
	}

	public void closeSession() {
		if (session != null) {
			try {
				session.close();
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
	}

	protected String getName() {
		return Thread.currentThread().getName();
	}
	

}
