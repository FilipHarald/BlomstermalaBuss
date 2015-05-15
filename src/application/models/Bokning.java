package application.models;

import java.sql.Date;

public class Bokning {

	private int id;
	private String kund;
	private Date datum;
	
	public Bokning(int id, String kund, Date datum) {
		super();
		this.id = id;
		this.kund = kund;
		this.datum = datum;
	}

	public int getId() {
		return id;
	}

	public String getKund() {
		return kund;
	}

	public Date getDatum() {
		return datum;
	}

	@Override
	public String toString() {
		return String.format("%s, %s, %s", id, kund, datum);
	}
	
	
}
