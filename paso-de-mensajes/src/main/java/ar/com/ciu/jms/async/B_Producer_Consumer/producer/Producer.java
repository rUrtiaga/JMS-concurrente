package ar.com.ciu.jms.async.B_Producer_Consumer.producer;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

import ar.com.ciu.jms.async.B_Producer_Consumer.Main;
import ar.com.ciu.jms.async.B_Producer_Consumer.Item;
import ar.com.ciu.jms.async.tools.for_runnable.WithConnection;

public class Producer extends WithConnection implements Runnable {

	private Item item;
	
	public Producer(int _cant, String _desc){
		super(Main.NAME);
		item = new Item(_cant,_desc);
	}
	
	@Override
	public void run() {
		try {
			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
			connection = connectionFactory.createConnection();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Queue queue = session.createQueue("B_PC");
			MessageProducer producer = session.createProducer(queue);
			ObjectMessage om = session.createObjectMessage();
			om.setObject(this.item);
			
			System.out.println("Enviando Objeto ");

			producer.send(om);
			session.close();
		} catch (JMSException e) {
			e.printStackTrace();
		} finally {
			this.closeConnection();
		}
	}

}
