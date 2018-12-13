package ar.com.ciu.jms.async.E_Pruducer_Consumer_con_buffer_limitado;

import ar.com.ciu.jms.async.tools.Aux;
import ar.com.ciu.jms.async.tools.browser.QueueLook;
import ar.com.ciu.jms.async.tools.browser.QueueLookObject;

public class E_Main {

	public static final String NAMEQ = "E_Producer_consumer";
	public static final String BUFFERN = "E_Producer_consumer_BUFFER";
	
	public static void main(String[] args) {
		//Limpio y agrego los tokens
		Aux.clean(NAMEQ);
		Aux.clean(BUFFERN);
		String[] pre = {"bufferToken","bufferToken"};
		Aux.putInQueue(BUFFERN,pre);
		
		Thread p1 = new Thread(new Producer(2,"desc1"),"P1");
		Thread p2 = new Thread(new Producer(1,"desc2"),"P2");
		Thread p3 = new Thread(new Producer(10,"desc3"),"P3");
		
		p1.start();
		p2.start();
		p3.start();
		
		Thread p4 = new Thread(new Producer(2,"desc1"),"P4");
		Thread p5 = new Thread(new Producer(1,"desc2"),"P5");
		Thread p6 = new Thread(new Producer(10,"desc3"),"P6");
		
		p4.start();
		p5.start();
		p6.start();
		
		
		Thread c1 = new Thread(new Consumer(),"C1");
		
		c1.start();
		
		
		
		
		try {
			p1.join();p2.join();p3.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}


		Thread b1 = new Thread(new QueueLook(BUFFERN),"buffer");
		Thread b2 = new Thread(new QueueLookObject(NAMEQ),"items");
		b1.start();b2.start();
		try {
			b1.join();b2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
	}
}
