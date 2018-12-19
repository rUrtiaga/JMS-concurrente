package ar.com.ciu.jms.async.A_Producer_Consumer.producer;

import javax.jms.JMSException;
import javax.jms.MessageProducer;

import ar.com.ciu.jms.async.tools.for_runnable.WithConnection;

public class Producer extends WithConnection implements Runnable {

	private String body;
	
	public Producer(String nameQueue,String _body){
		super(nameQueue);
		body = _body;
	}
	
	@Override
	public void run() {
		try {
			this.conectar();
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
