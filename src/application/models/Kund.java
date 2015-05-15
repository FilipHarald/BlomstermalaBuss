package application.models;

public class Kund {

	private String personnr;
	private String namn;
	private String telefonnr;
	private String mail;
	private String losenord;
	private String adress;
	
	public Kund(String personnr, String namn, String telefonnr, String mail,
			String losenord, String adress) {
		super();
		this.personnr = personnr;
		this.namn = namn;
		this.telefonnr = telefonnr;
		this.mail = mail;
		this.losenord = losenord;
		this.adress = adress;
	}

	public String getPersonnr() {
		return personnr;
	}

	public String getNamn() {
		return namn;
	}

	public String getTelefonnr() {
		return telefonnr;
	}

	public String getMail() {
		return mail;
	}

	public String getLosenord() {
		return losenord;
	}

	public String getAdress() {
		return adress;
	}

	@Override
	public String toString() {
		return String.format("%s, %s, %s, %s, %s, %s", personnr, namn,
				telefonnr, mail, losenord, adress);
	}
	
}
