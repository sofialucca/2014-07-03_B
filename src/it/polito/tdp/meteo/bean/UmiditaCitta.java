package it.polito.tdp.meteo.bean;

public class UmiditaCitta {
	
	private String localita ;
	private double umidita ; // media
	
	public UmiditaCitta(String localita, double umidita) {
		super();
		this.localita = localita;
		this.umidita = umidita;
	}
	public String getLocalita() {
		return localita;
	}
	public void setLocalita(String localita) {
		this.localita = localita;
	}
	public double getUmidita() {
		return umidita;
	}
	public void setUmidita(double umidita) {
		this.umidita = umidita;
	}
}
