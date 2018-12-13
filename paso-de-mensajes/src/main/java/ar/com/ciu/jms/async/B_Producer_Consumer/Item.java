package ar.com.ciu.jms.async.B_Producer_Consumer;

import java.io.Serializable;

public class Item implements Serializable {

	private static final long serialVersionUID = -3683179901210474071L;
	private String desc;
	private int cant;

	public Item(int _cant, String _desc) {
		cant = _cant;
		desc = _desc;
	}

	public int getCant() {
		return this.cant;
	}
	public String getDesc() {
		return this.desc;
	}
}
