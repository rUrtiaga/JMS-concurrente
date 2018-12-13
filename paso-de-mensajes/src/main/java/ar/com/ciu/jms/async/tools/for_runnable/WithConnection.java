package ar.com.ciu.jms.async.tools.for_runnable;

import java.io.Serializable;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;


public class WithConnection {
	protected Queue queue;
	protected Queue buffer;
	protected Connection connection;
	protected Session session;
	protected String nameQ;
	protected String bufferN;

	public WithConnection(String nameQ) {
		this.nameQ = nameQ;
		this.queue = null;
		this.buffer = null;
		this.connection = null;
		this.session = null;
	}


	public WithConnection(String nameQ,String bufferN) {
		this(nameQ);
		this.bufferN = bufferN;
	}
	
	protected void conectar() throws JMSException {
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
		connection = connectionFactory.createConnection();

		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		if(this.nameQ != null) {
			queue = session.createQueue(this.nameQ);
		}
		if(this.bufferN != null) {
			buffer = session.createQueue(this.bufferN);
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
	
	protected void takeTokenBuffer() throws JMSException {
		MessageConsumer consumer = session.createConsumer(buffer);
		TextMessage recibedMessage = (TextMessage) consumer.receive();
		
		System.out.println(this.getName() +" Received: " + recibedMessage.getText());
		consumer.close();
	}

	protected Serializable getItem() throws JMSException {
		MessageConsumer consumer = session.createConsumer(queue);
		ObjectMessage recibedMessage = (ObjectMessage) consumer.receive();
		consumer.close();
		return (Serializable) recibedMessage.getObject();
	}

	protected void pushItem(Serializable obj) throws JMSException {
		MessageProducer producer = session.createProducer(queue);
		ObjectMessage om = session.createObjectMessage();
		om.setObject(obj);
		
		producer.send(om);
	}

	
	protected void pushTokenBuffer() throws JMSException {
		MessageProducer producer = session.createProducer(buffer);
		producer.send(session.createTextMessage("bufferToken"));
	}

	public String getName() {
		return Thread.currentThread().getName();
	}

}
