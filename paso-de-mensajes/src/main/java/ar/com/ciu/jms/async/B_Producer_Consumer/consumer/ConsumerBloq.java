package ar.com.ciu.jms.async.B_Producer_Consumer.consumer;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

import ar.com.ciu.jms.async.A_Producer_Consumer.Main;
import ar.com.ciu.jms.async.B_Producer_Consumer.Item;
import ar.com.ciu.jms.async.tools.for_runnable.WithConnection;

/*
 * cunsumer sync queda esperando por mensajes en la cola si no hay
 */
public class ConsumerBloq extends WithConnection implements Runnable {

	public ConsumerBloq(){
		super(Main.NAME);
	}
	
	@Override
	public void run() {
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
		try {
			connection = connectionFactory.createConnection();

			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Queue queue = session.createQueue("B_PC");
		
			// Consumer
			MessageConsumer consumer = session.createConsumer(queue);
			connection.start();
			System.out.println("esperando objeto...");
			
			ObjectMessage objMsg = (ObjectMessage) consumer.receive();
			Item item = (Item) objMsg.getObject();
			
			System.out.println("Received: cant:" + item.getCant()+ " desc:" + item.getDesc());
		} catch (JMSException e) {
			e.printStackTrace();
		} finally {
			this.closeSession();
			this.closeConnection();
		}
	}
}
