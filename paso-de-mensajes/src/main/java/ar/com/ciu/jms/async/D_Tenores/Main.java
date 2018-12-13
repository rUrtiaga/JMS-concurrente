package ar.com.ciu.jms.async.D_Tenores;

import ar.com.ciu.jms.async.D_Tenores.tenores.TenorA;
import ar.com.ciu.jms.async.D_Tenores.tenores.TenorB;
import ar.com.ciu.jms.async.D_Tenores.tenores.TenorC;
import ar.com.ciu.jms.async.tools.Cleaner;

public class Main {
		public static String NAME = "D_Tenores";
		
		public static void main(String[] args) throws InterruptedException {

			Main.clean();
			
			Thread ca = new Thread(new TenorA(), "Tenor A");
			Thread cb = new Thread(new TenorB("A"), "Tenor B");
			Thread cc = new Thread(new TenorC("B"), "Tenor C");
			
			System.out.println("Inicio Himno");
			cc.start();
			cb.start();
			ca.start();
			try {
				ca.join();
				cb.join();
				cc.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Fin Himno");
						
			Main.clean();
			
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

}
