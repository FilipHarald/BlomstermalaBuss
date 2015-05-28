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

    public int getId() {
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
	
	public static String[] getColumnNames() {
		return new String[] {"ID",
				"Pris",
				"Avreseort",
				"Avresedag",
				"Avresetid",
				"Ankomstort",
				"Ankomstdag",
				"Ankomsttid",
				"Kapacitet"};
	}
	
	@Override
	public String toString() {
		return id + "," + kostnad + ","
				+ avreseort + "," + intToDay(avresedag) + ","
				+ avresetid + "," + ankomstort + ","
				+ intToDay(ankomstdag) + "," + ankomsttid + ","
				+ kapacitet;
	}
	public String intToDay(int i){
		String day = "";
		switch(i){
			case 1: 
				day = "Måndag";
				break;
			case 2: 
				day = "Tisdag";
				break;
			case 3: 
				day = "Onsdag";
				break;
			case 4: 
				day = "Torsdag";
				break;
			case 5: 
				day = "Fredag";
				break;
			case 6: 
				day = "Lördag";
				break;
			case 7: 
				day = "Söndag";
				break;
		}
		return day;
	}
	
}
