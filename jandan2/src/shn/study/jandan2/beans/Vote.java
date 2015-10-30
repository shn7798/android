package shn.study.jandan2.beans;

public class Vote {

	private String oo;
	private String xx;

	public String getOo() {
		return oo;
	}

	public void setOo(String oo) {
		this.oo = oo;
	}

	public String getXx() {
		return xx;
	}

	public void setXx(String xx) {
		this.xx = xx;
	}

	@Override
	public String toString() {
		return "Vote{" +
				"oo='" + oo + '\'' +
				", xx='" + xx + '\'' +
				'}';
	}
}