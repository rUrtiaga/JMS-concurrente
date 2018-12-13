package ar.com.ciu.jms.async.F_Lectores_Escritores_pLectura;

import java.util.ArrayList;
import java.util.List;

import ar.com.ciu.jms.async.tools.Aux;

public class F_Main {
	public static final String B_WRITE = "F_Write";
	public static final String B_READ = "F_Read";
	public static List<Integer> list = new ArrayList<>();
	
	public static void main(String[] args) throws InterruptedException {
		
		F_Main.list.add(0);F_Main.list.add(1);F_Main.list.add(2);F_Main.list.add(3);
		
		Aux.clean(B_WRITE);
		Aux.clean(B_READ);
	
		String[] inizialice = {"Token"};
		Aux.putNumberInQueue(B_READ,0);
		Aux.putInQueue(B_WRITE,inizialice);
		
		
		Thread e0 = new Thread(new Writer(10),"Writer 0");
		Thread e1 = new Thread(new Writer(10),"Writer 1");
		Thread e2 = new Thread(new Writer(10),"Writer 2");
		Thread e3 = new Thread(new Writer(10),"Writer 3");
		Thread e4 = new Thread(new Writer(10),"Writer 4");
		
		Thread l0 = new Thread(new Reader(),"Reader 0");
		Thread l1 = new Thread(new Reader(),"Reader 1");
		Thread l2 = new Thread(new Reader(),"Reader 2");
		Thread l3 = new Thread(new Reader(),"Reader 3");
		Thread l4 = new Thread(new Reader(),"Reader 4");
		Thread l5 = new Thread(new Reader(),"Reader 5");
		Thread l6 = new Thread(new Reader(),"Reader 6");
		Thread l7 = new Thread(new Reader(),"Reader 7");
		Thread l8 = new Thread(new Reader(),"Reader 8");
		Thread l9 = new Thread(new Reader(),"Reader 9");
		Thread l10 = new Thread(new Reader(),"Reader 10");
		
		
		l0.start();
		
		l1.start();l2.start();l3.start();l4.start();l5.start();l6.start();l7.start();l8.start();l9.start();l10.start();
		e0.start();
		e1.start();e2.start();e3.start();e4.start();
		
		try {
			e0.join();
			e1.join();e2.join();e3.join();e4.join();
			l0.join();l1.join();l2.join();l3.join();l4.join();l5.join();l6.join();l7.join();l8.join();l9.join();l10.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
