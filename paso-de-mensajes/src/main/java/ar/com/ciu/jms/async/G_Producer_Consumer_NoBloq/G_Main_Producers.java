package ar.com.ciu.jms.async.G_Producer_Consumer_NoBloq;

import ar.com.ciu.jms.async.A_Producer_Consumer.producer.Producer;

public class G_Main_Producers {
	public static String name_queue = "G_Producer_Consumer_NoBloq";
	
	public static void main(String[] args) {
		Thread[] arrThreads = new Thread[5];

		for (int j = 0; j < 3; j++) {
			arrThreads[j] = new Thread(new Producer(name_queue,"Mensaje"), "Producer " + j);
			arrThreads[j].start();
		}
		

		for (int i = 0; i < arrThreads.length; i++) {
			try {
				arrThreads[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		

	}

}
