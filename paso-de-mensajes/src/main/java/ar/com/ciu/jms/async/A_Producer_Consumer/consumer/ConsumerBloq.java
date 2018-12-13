package ar.com.ciu.jms.async.A_Producer_Consumer.consumer;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

import ar.com.ciu.jms.async.A_Producer_Consumer.Main;
import ar.com.ciu.jms.async.tools.for_runnable.WithConnection;

/*
 * cunsumer sync queda esperando por mensajes en la cola si no hay
 */
public class ConsumerBloq extends WithConnection implements Runnable {

	public ConsumerBloq() {
		super(Main.NAME);
	}

	@Override
	public void run() {
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
		try {
			connection = connectionFactory.createConnection();

			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Queue queue = session.createQueue("A_PC");
			// Consumer
			MessageConsumer consumer = session.createConsumer(queue);
			connection.start();
			System.out.println("esperando mensaje...");
			TextMessage textMsg = (TextMessage) consumer.receive();
			System.out.println(textMsg);
			System.out.println("Received: " + textMsg.getText());
		} catch (JMSException e) {
			e.printStackTrace();
		} finally {
			this.closeSession();
			this.closeConnection();
		}
	}
}
