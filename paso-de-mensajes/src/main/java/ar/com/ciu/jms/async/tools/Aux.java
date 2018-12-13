package ar.com.ciu.jms.async.tools;


public class Aux {


	public static void clean(String name) {
		Thread clean = new Thread(new Cleaner(name));
		clean.start();
		try {
			clean.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void putInQueue(String queue_name, String[] list_tokens) {
		Thread put = new Thread( new Puter(queue_name,list_tokens));
		put.start();
		try {
			put.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void putNumberInQueue(String queue_name, Integer i) {
		Thread put = new Thread( new Puter(queue_name,i));
		put.start();
		try {
			put.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
}
