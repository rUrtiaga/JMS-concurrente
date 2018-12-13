package ar.com.ciu.jms.async.F_Lectores_Escritores_pLectura;

import javax.jms.JMSException;

import ar.com.ciu.jms.async.tools.for_runnable.WithConnection;

public class Writer extends WithConnection implements Runnable {
	private int num;
	
	public Writer(int _num) {
		super(F_Main.B_READ,F_Main.B_WRITE);
		this.num = _num;
	}

	public void run() {
		try {
			this.conectar();
			
			this.takeTokenBuffer();
			this.writeAccion();
			this.pushTokenBuffer();
			
		} catch (JMSException | InterruptedException e) {
			e.printStackTrace();
		} finally {
			this.closeSession();
			this.closeConnection();
		}
	}
	

	private void writeAccion() throws InterruptedException {
		F_Main.list.add(this.num);
		Thread.sleep(1000);
	}
}
