package com.example.batchprocessing;
import java.math.*;

public class KontoAbrechnung {
	private int idAbrechnung;
	private int idKonto;
	private int lakey;
	private BigDecimal betrag;
	private String name;

	public KontoAbrechnung() {
	}
	
	public KontoAbrechnung(int  idKonto, String name) {
		this.idKonto = idKonto;
		this.name = name;
	}
	
	public void setIdAbrechnung(int idAbrechnung) {
		this.idAbrechnung = idAbrechnung;
	}

	public int getIdAbrechnung() {
		return idAbrechnung;
	}
	
	public void setLakey(int lakey) {
		this.lakey = lakey;
	}

	public int getLakey() {
		return lakey;
	}

	public void setIdKonto(int idKonto) {
		this.idKonto = idKonto;
	}

	public int getIdKonto() {
		return idKonto;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "idKonto: " + idKonto + ", name: " + name;
	}

	public BigDecimal getBetrag() {
		return betrag;
	}

	public void setBetrag(BigDecimal betrag) {
		this.betrag = betrag;
	}

}
