package application;

import java.sql.*;
import java.util.ArrayList;

import application.models.*;

public class DatabaseController {
	private String url;
	private String userId;
	private String password;
	private Connection con;

	public DatabaseController(String url, String userId, String password,
			String database) {
		this.url = String.format("jdbc:mysql://%s:3306/%s", url, database);
		System.out.println(this.url);
		this.userId = userId;
		this.password = password;
		con = null;

		try {
			String driver = "com.mysql.jdbc.Driver";
			Class.forName(driver).newInstance();
		} catch (Exception e) {
			System.out.println("Failed to load MySQL driver.");
			return;
		}

		try {
			con = DriverManager.getConnection(this.url, this.userId,
					this.password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Tur> getTurer() {
		ArrayList<Tur> turer = new ArrayList<Tur>();

		try {
			Statement select = con.createStatement();
			ResultSet result;
			result = select.executeQuery("SELECT * FROM tur");

			while (result.next()) {

				turer.add(new Tur(result.getInt(1), result.getInt(2), result
						.getString(3), result.getInt(4), result.getTime(5),
						result.getString(6), result.getInt(7), result
								.getTime(8), result.getInt(9)));

			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return turer;

	}

	public ArrayList<Kund> getKunder() {
		ArrayList<Kund> kunder = new ArrayList<Kund>();

		try {
			Statement select = con.createStatement();
			ResultSet result;
			result = select.executeQuery("SELECT * FROM kund");

			while (result.next()) {

				kunder.add(new Kund(result.getString(1), result.getString(2),
						result.getString(3), result.getString(4), result
								.getString(5), result.getString(6)));

			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return kunder;

	}

	public void addKund(Kund kund) {
		throw new RuntimeException("Not implemented yet!");
	}

	public Bokning addBokning(String kund, ArrayList<Integer> turer) {

		// skapa bokning
		// insert into bokning

		// räkna ut datum
		// for turer, select tur
		// mattematik

		// lägg till turid och bokningsid i bokad resa
		// insert into bokad_resa

		return null;
	}

	public ArrayList<String> getUserList() {
		ArrayList<String> users = new ArrayList<String>();
		try {
			con = DriverManager.getConnection(url, userId, password);
			Statement select = con.createStatement();
			ResultSet result = select.executeQuery("SELECT * FROM Medlem");
			while (result.next()) {
				users.add("Namn: " + result.getString(2) + "   ID:"
						+ result.getString(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return users;
	}

	public Paketresa getPaketresaDetails() {
		return null;

	}

	public ArrayList<String> getPaketresorFormatted() {
		ArrayList<String> paketresor = new ArrayList<String>();
		
		try {
			Statement select = con.createStatement();
			ResultSet result;
			result = select.executeQuery("SELECT paketresa.namn,tur.avreseort,tur.avresedag,max(paketresa.dagar_fran_start) "
										+ "FROM paketresa INNER JOIN tur " 
										+ "ON paketresa.tur = tur.id " 
										+ "GROUP BY namn");
			while (result.next()) {
				paketresor.add(result.getString(1) + "," + result.getString(2) + "," + result.getString(3) + "," + result.getInt(4));
				
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		
		return paketresor;
	}
}
