package ar.com.ciu.jms.async.A_Producer_Consumer.producer;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

import ar.com.ciu.jms.async.A_Producer_Consumer.Main;
import ar.com.ciu.jms.async.tools.for_runnable.WithConnection;

public class Producer extends WithConnection implements Runnable {

	private String body;
	
	public Producer(String _body){
		super(Main.NAME);
		body = _body;
	}
	
	@Override
	public void run() {
		try {
			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
			connection = connectionFactory.createConnection();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Queue queue = session.createQueue("A_PC");
			MessageProducer producer = session.createProducer(queue);
			System.out.println("Enviando mensaje [" + body + "]");

			producer.send(session.createTextMessage(this.getName() + " "+ body));
			session.close();
		} catch (JMSException e) {
			e.printStackTrace();
		} finally {
			this.closeConnection();
		}
	}

}
