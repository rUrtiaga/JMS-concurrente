package ar.com.ciu.jms.async.A_Producer_Consumer;

import ar.com.ciu.jms.async.A_Producer_Consumer.consumer.ConsumerBloq;
//import ar.com.ciu.jms.async.A_Producer_Consumer.consumer.ConsumerBloq;
import ar.com.ciu.jms.async.A_Producer_Consumer.producer.Producer;
import ar.com.ciu.jms.async.tools.Cleaner;
import ar.com.ciu.jms.async.tools.browser.QueueLook;

public class Main {
	static public String NAME = "A_PC";
	
	public static void main(String[] args) {
		Thread[] arrThreads = new Thread[5];
		Thread[] arrThreadsC = new Thread[4];

		for (int j = 0; j < 5; j++) {
			arrThreads[j] = new Thread(new Producer(NAME,"Mensaje"), "Producer " + j);
			arrThreads[j].start();
		}
		for (int d = 0; d < 4; d++) {
			arrThreadsC[d] = new Thread(new ConsumerBloq(), "Consumer " + d);
			arrThreadsC[d].start();
		}

		
		for (int i = 0; i < arrThreads.length; i++) {
			try {
				arrThreads[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		for (int i = 0; i < arrThreadsC.length; i++) {
			try {
				arrThreadsC[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		Main.printListQueue();
		Main.clean();
		Main.printListQueue();
	}

	private static void clean() {
		Thread clean = new Thread(new Cleaner(Main.NAME));
		clean.start();
		try {
			clean.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static void printListQueue() {
		Thread look = new Thread(new QueueLook(Main.NAME));
		look.start();
		try {
			look.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
