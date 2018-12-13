package ar.com.ciu.jms.async.H_topic_subscribe;

public class H_Main {

	public static String tA = "H_Topic_A";
	public static String tB = "H_Topic_B";
	
	public static void main(String[] args) {

		Thread publi0 = new Thread(new Publisher(tA,"TEXTO PUBLICADO"),"Publi 0");
		Thread publi1 = new Thread(new Publisher(tB,"TEXTO PUBLICADO"),"Publi 1");
		
		Thread sub0 = new Thread(new Subscriber(tA),"Sub 0");
		Thread sub1 = new Thread(new Subscriber(tA),"Sub 1");
		Thread sub2 = new Thread(new Subscriber(tB),"Sub 2");
		Thread sub3 = new Thread(new Subscriber(tB),"Sub 3");
		
		
		publi0.start();publi1.start();
		
		sub0.start();sub1.start();sub2.start();sub3.start();
		
		try {
			publi0.join();publi1.join();
			sub0.join();sub1.join();sub2.join();sub3.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
				
	}
	
}
