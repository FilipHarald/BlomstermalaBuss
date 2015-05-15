package application.models;

import java.sql.Time;

public class Tur {
	
	private int id;
	private int kostnad;
	private String avreseort;
	private int avresedag;
	private Time avresetid;
	private String ankomstort;
	private int ankomstdag;
	private Time ankomsttid;
	private int kapacitet;
	
	public Tur(int id, int kostnad, String avreseort, int avresedag,
			Time avresetid, String ankomstort, int ankomstdag,
			Time ankomsttid, int kapacitet) {
		super();
		this.id = id;
		this.kostnad = kostnad;
		this.avreseort = avreseort;
		this.avresedag = avresedag;
		this.avresetid = avresetid;
		this.ankomstort = ankomstort;
		this.ankomstdag = ankomstdag;
		this.ankomsttid = ankomsttid;
		this.kapacitet = kapacitet;
	}

	int getId() {
		return id;
	}
	public int getKostnad() {
		return kostnad;
	}
	public String getAvreseort() {
		return avreseort;
	}
	public int getAvresedag() {
		return avresedag;
	}
	public Time getAvresetid() {
		return avresetid;
	}
	public String getAnkomstort() {
		return ankomstort;
	}
	public int getAnkomstdag() {
		return ankomstdag;
	}
	public Time getAnkomsttid() {
		return ankomsttid;
	}
	public int getKapacitet() {
		return kapacitet;
	}
	
	@Override
	public String toString() {
		return id + "," + kostnad + ","
				+ avreseort + "," + avresedag + ","
				+ avresetid + "," + ankomstort + ","
				+ ankomstdag + "," + ankomsttid + ","
				+ kapacitet;
	}

}
