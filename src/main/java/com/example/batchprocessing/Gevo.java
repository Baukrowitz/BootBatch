package com.example.batchprocessing;
import java.math.*;

public class Gevo {
	private int idgevo;
	private int idAbrechnung;
	private int idKonto;
	private int lakey;
	private int pem;
	private String gevoart;
	private String stornoart;
	private BigDecimal betrag;
	private int tilgbezug;
	
	
	public int getIdgevo() {
		return idgevo;
	}
	public void setIdgevo(int idgevo) {
		this.idgevo = idgevo;
	}
	public int getIdAbrechnung() {
		return idAbrechnung;
	}
	public void setIdAbrechnung(int idAbrechnung) {
		this.idAbrechnung = idAbrechnung;
	}
	public int getIdKonto() {
		return idKonto;
	}
	public void setIdKonto(int idKonto) {
		this.idKonto = idKonto;
	}
	public int getLakey() {
		return lakey;
	}
	public void setLakey(int lakey) {
		this.lakey = lakey;
	}
	public int getPem() {
		return pem;
	}
	public void setPem(int pem) {
		this.pem = pem;
	}
	public String getGevoart() {
		return gevoart;
	}
	public void setGevoart(String gevoart) {
		this.gevoart = gevoart;
	}
	public String getStornoart() {
		return stornoart;
	}
	public void setStornoart(String stornoart) {
		this.stornoart = stornoart;
	}
	public BigDecimal getBetrag() {
		return betrag;
	}
	public void setBetrag(BigDecimal betrag) {
		this.betrag = betrag;
	}
	public int getTilgbezug() {
		return tilgbezug;
	}
	public void setTilgbezug(int tilgbezug) {
		this.tilgbezug = tilgbezug;
	}
	

}
