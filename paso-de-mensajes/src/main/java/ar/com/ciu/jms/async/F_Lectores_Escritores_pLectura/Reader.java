package ar.com.ciu.jms.async.F_Lectores_Escritores_pLectura;


import ar.com.ciu.jms.async.tools.for_runnable.WithConnection;

public class Reader extends WithConnection implements Runnable {

	public Reader() {
		super(F_Main.B_READ,F_Main.B_WRITE);
	}

	@Override
	public void run() {
		try {
			this.conectar();
			Integer readers = (Integer) this.getItem();
			
			if(readers == 0) {				
				this.takeTokenBuffer();
			}
			this.pushItem(++readers);
			
			
			this.readerAction();
			
			
			readers = (Integer) this.getItem();
			if(readers == 1) {				
				this.pushTokenBuffer();
			}
			this.pushItem(--readers);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.closeSession();
			this.closeConnection();
		}
		
	}

	
	private void readerAction() throws Exception {
			System.out.println( this.getName() +" "+ F_Main.list.size());
			Thread.sleep(100);
	}
}
