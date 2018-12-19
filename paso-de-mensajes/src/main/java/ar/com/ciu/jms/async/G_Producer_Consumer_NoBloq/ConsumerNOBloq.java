package ar.com.ciu.jms.async.G_Producer_Consumer_NoBloq;

import java.util.concurrent.CountDownLatch;

import javax.jms.JMSException;
import javax.jms.MessageConsumer;

import ar.com.ciu.jms.async.tools.for_runnable.WithConnection;

public class ConsumerNOBloq extends WithConnection implements Runnable {

	 private CountDownLatch latch = new CountDownLatch(1);
	
	public ConsumerNOBloq() {
		super(G_Main_Consumers.name_queue);
	}

	@Override
	public void run() {
        try {
        	this.conectar();
            MessageConsumer consumer = session.createConsumer(queue);
            ConsumerMessageListener consumerListener = new ConsumerMessageListener("Customer");
            consumer.setMessageListener(consumerListener);
            consumerListener.setJmsConsumerAsyncQueueClient(this);
            connection.start();
            latch.await();
        } catch (JMSException | InterruptedException e) {
			e.printStackTrace();
		} finally {
            this.closeSession();
            this.closeConnection();
        }
	}

    public void latchCountDown() {
        latch.countDown();
    }
	
}
