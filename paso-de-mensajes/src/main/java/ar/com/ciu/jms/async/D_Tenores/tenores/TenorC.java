package ar.com.ciu.jms.async.D_Tenores.tenores;


public class TenorC extends TenorB implements Runnable {

	public TenorC(String requiredMsg) {
		super(requiredMsg);

		this.line = "MSJ 3";
		this.myToken = "C";
	}


}
