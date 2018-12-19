package ar.com.ciu.jms.async.G_Producer_Consumer_NoBloq;


public class G_Main_Consumers {
	public static String name_queue = "G_Producer_Consumer_NoBloq";
	
	public static void main(String[] args) {
		Thread[] arrThreadsC = new Thread[4];

		for (int d = 0; d < 3; d++) {
			arrThreadsC[d] = new Thread(new ConsumerNOBloq(), "Consumer " + d);
			arrThreadsC[d].start();
		}

		for (int i = 0; i < arrThreadsC.length; i++) {
			try {
				arrThreadsC[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}
