package ar.com.ciu.jms.async.D_Tenores.tenores;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

import ar.com.ciu.jms.async.D_Tenores.Main;
import ar.com.ciu.jms.async.tools.for_runnable.WithConnection;

public class TenorA  extends WithConnection implements Runnable  {

	public TenorA(){
		super(Main.NAME);
	}
	
	@Override
	public void run() {
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
		try {
			connection = connectionFactory.createConnection();

			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Queue queue = session.createQueue(Main.NAME);
			
			
			MessageProducer producer = session.createProducer(queue);
			System.out.println("MSJ 1");
			producer.send(session.createTextMessage("A"));
			producer.close();
		} catch (JMSException e) {
			e.printStackTrace();
		} finally {
			this.closeSession();
			this.closeConnection();
		}
	}

}
