package application.models;

public class Paketresa {

	private String namn;
	private int tur;
	private int dagar_fran_start;
	
	public Paketresa(String namn, int tur, int dagar_fran_start) {
		super();
		this.namn = namn;
		this.tur = tur;
		this.dagar_fran_start = dagar_fran_start;
	}
	
	public String getNamn() {
		return namn;
	}
	public int getTur() {
		return tur;
	}
	public int getDagar_fran_start() {
		return dagar_fran_start;
	}
	
	@Override
	public String toString() {
		return String.format("%s, %s, %s", namn, tur, dagar_fran_start);
	}

	public static String[] getColumnNames() {
		return new String[] { "Namn", "Avreseort", "Avresedag", "Resans längd (antal dagar)" };
	}
	
	
}
