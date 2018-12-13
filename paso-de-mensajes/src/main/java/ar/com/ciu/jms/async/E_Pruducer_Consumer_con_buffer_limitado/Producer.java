package ar.com.ciu.jms.async.E_Pruducer_Consumer_con_buffer_limitado;

import javax.jms.JMSException;

import ar.com.ciu.jms.async.B_Producer_Consumer.Item;
import ar.com.ciu.jms.async.tools.for_runnable.WithConnection;

public class Producer extends WithConnection implements Runnable {
	
	private Item item;
	
	public Producer(int _cant, String _desc){
		super(E_Main.NAMEQ,E_Main.BUFFERN);
		item = new Item(_cant,_desc);
	}
	
	public void run() {
			try {
				this.conectar();
				
				this.takeTokenBuffer();
				this.pushItem(this.item);
				
				
			} catch (JMSException e) {
				e.printStackTrace();
			} finally {
				this.closeSession();
				this.closeConnection();
			}
			
	}
	
}
