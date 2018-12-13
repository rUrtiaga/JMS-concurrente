package ar.com.ciu.jms.async.C_Contador;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

import ar.com.ciu.jms.async.tools.for_runnable.WithConnection;

public class Sumador extends WithConnection implements Runnable {
	
	private int cant;
	
	public Sumador(int _cant) {
		super(Main.NAME);
		cant = _cant;
	}
	
	@Override
	public void run() {
		for (int i = 0; i < this.cant; i++) {
			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
			try {
				connection = connectionFactory.createConnection();

				session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
				Queue queue = session.createQueue(Main.NAME);
			
				MessageConsumer consumer = session.createConsumer(queue);
				connection.start();
				System.out.println("esperando TOKEN...");
				
				TextMessage textMsg = (TextMessage) consumer.receive();
							
				System.out.println("Recibed: "+ textMsg.getText() );

				this.MUTEXOPERATION();
				
				MessageProducer producer = session.createProducer(queue);
				System.out.println("Enviando TOKEN");

				producer.send(session.createTextMessage("TOKEN"));
				
			} catch (JMSException e) {
				e.printStackTrace();
			} finally {
				this.closeSession();
				this.closeConnection();
			}

		}
		
	}

	private void MUTEXOPERATION() {
		Main.contador++;
	}
	
}
