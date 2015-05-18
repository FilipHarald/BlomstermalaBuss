package application;

import java.sql.*;
import java.util.Calendar;
import java.util.Date;
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

	public Tur getTur(int id) {
		try {
			PreparedStatement select = con
					.prepareStatement("SELECT * FROM tur WHERE id=?");
			select.setInt(1, id);
			ResultSet result = select.executeQuery();

			if (result.next()) {
				return new Tur(result.getInt(1), result.getInt(2),
						result.getString(3), result.getInt(4),
						result.getTime(5), result.getString(6),
						result.getInt(7), result.getTime(8), result.getInt(9));
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return null;
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

	public Bokning addPaketBokning(String kund, Date datum,
			ArrayList<Integer> turer) {

		int bokadResaId = -1;

		String insertBokadResa = "INSERT INTO bokad_resa (bokning, tur, datum) VALUES (?, ?, ?)";

		java.sql.Date bokningsDatum = new java.sql.Date(new Date().getTime());
		java.sql.Date startDatum = new java.sql.Date(datum.getTime());

		try {
			int bokningId = addBokning(kund, bokningsDatum);

			Calendar calendar = Calendar.getInstance();

			int dagarSedanStart = 0;

			for (int i = 0; i < turer.size(); i++) {
				Tur tur = getTur(turer.get(i));

				PreparedStatement insert = con.prepareStatement(
						insertBokadResa, Statement.RETURN_GENERATED_KEYS);
				insert.setInt(1, bokningId);
				insert.setInt(2, tur.getId());

				if (i == 0) {
					// If it's the first tur, we don't have to calculate
					// anything
					insert.setDate(3, startDatum);
				} else {
					// Here we have to calculate the days from previous
					// ankomstdag to current avresedag

				}

				dagarSedanStart += tur.getAnkomstdag() - tur.getAvresedag();
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return null;
	}

	private int addBokning(String kund, Date datum) {
		int bokningId = -1;

		String insertBokning = "INSERT INTO bokning (kund, datum) VALUES (?, ?)";

		java.sql.Date bokningsDatum = new java.sql.Date(new Date().getTime());

		try {
			PreparedStatement insert = con.prepareStatement(insertBokning,
					Statement.RETURN_GENERATED_KEYS);
			insert.setString(1, kund);
			insert.setDate(2, bokningsDatum);

			int affectedRows = insert.executeUpdate();

			if (affectedRows == 0) {
				throw new SQLException("No rows affected!");
			}

			try (ResultSet keys = insert.getGeneratedKeys()) {
				if (keys.next()) {
					bokningId = keys.getInt(1);
				} else {
					throw new SQLException("Insert failed!");
				}
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return bokningId;
	}

	public int addTurBokning(String kund, Date datum, int tur) {

		int bokningId = -1;
		int bokadResaId = -1;

		String insertBokning = "INSERT INTO bokning (kund, datum) VALUES (?, ?)";
		String insertBokadResa = "INSERT INTO bokad_resa (bokning, tur, datum) VALUES (?, ?, ?)";

		java.sql.Date bokningsDatum = new java.sql.Date(new Date().getTime());
		java.sql.Date startDatum = new java.sql.Date(datum.getTime());

		try {
			bokningId = addBokning(kund, datum);

			PreparedStatement insert = con.prepareStatement(insertBokadResa);
			insert.setInt(1, bokningId);
			insert.setInt(2, tur);
			insert.setDate(3, startDatum);

			int affectedRows = insert.executeUpdate();

			if (affectedRows == 0) {
				throw new SQLException("No rows affected!");
			}

			System.out.println(affectedRows);

		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return bokningId;
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

	public String getPaketresaDetails(String paketresenamn) {
		String s = "";
		try {
			Statement stmt = con.createStatement();
			ResultSet rs;
			rs = stmt.executeQuery(String.format(
					"SELECT sum(tur.kostnad) "
							+ "FROM tur INNER JOIN paketresa "
							+ "ON tur.id = paketresa.tur "
							+ "WHERE paketresa.namn = %s", paketresenamn));
			s = "Pris totalt:" + rs.getInt(1);
			Statement statement = con.createStatement();
			ResultSet result;
			result = statement
					.executeQuery(String
							.format("SELECT tur.avreseort,tur.avresedag,tur.avresetid,tur.ankomsstort,tur.ankomstdag,tur.ankomsttid "
									+ "FROM tur INNER JOIN paketresa "
									+ "ON tur.id = paketresa.tur "
									+ "WHERE paketresa.namn = '%s'",
									paketresenamn));
			s = s + "," + result.getString(1) + "," + result.getString(2) + ","
					+ result.getString(3) + "," + result.getString(4) + ","
					+ result.getString(5) + "," + result.getString(6);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return s;

	}

	public ArrayList<String> getPaketresorFormatted() {
		ArrayList<String> paketresor = new ArrayList<String>();

		try {
			Statement select = con.createStatement();
			ResultSet result;
			result = select
					.executeQuery("SELECT paketresa.namn,tur.avreseort,tur.avresedag,max(paketresa.dagar_fran_start) "
							+ "FROM paketresa INNER JOIN tur "
							+ "ON paketresa.tur = tur.id " + "GROUP BY namn");
			while (result.next()) {
				paketresor.add(result.getString(1) + "," + result.getString(2)
						+ "," + result.getString(3) + "," + result.getInt(4));

			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return paketresor;
	}
}
