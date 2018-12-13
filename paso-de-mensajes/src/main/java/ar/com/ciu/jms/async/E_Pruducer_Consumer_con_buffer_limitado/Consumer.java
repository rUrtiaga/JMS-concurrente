package ar.com.ciu.jms.async.E_Pruducer_Consumer_con_buffer_limitado;

import ar.com.ciu.jms.async.B_Producer_Consumer.Item;
import ar.com.ciu.jms.async.tools.for_runnable.WithConnection;

public class Consumer extends WithConnection implements Runnable{

	public Consumer() {
		super(E_Main.NAMEQ,E_Main.BUFFERN);
		
	}

	@Override
	public void run() {
		try {
			this.conectar();
			
			Item i = (Item) this.getItem();
			System.out.println("Get cant: [" + i.getCant() + "]  desc: [" + i.getDesc() + "]" );
			Thread.sleep(100);
			this.pushTokenBuffer();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.closeSession();
			this.closeConnection();
		}
	}

}
