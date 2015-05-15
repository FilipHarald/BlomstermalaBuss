package application;
import java.sql.*;
import java.util.ArrayList;

public class DatabaseController {
	private String url;
	private String userId;
	private String password;
	private Connection con;

	public DatabaseController(String url, String userId, String password) {
		this.url = url;
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
	}

	public ArrayList<String> getDogs(int OwnerId) {
		ArrayList<String> dogs = new ArrayList<String>();
		try {
			con = DriverManager.getConnection(url, userId, password);
			Statement select = con.createStatement();
			ResultSet result;
			if (OwnerId != 0) {
				result = select.executeQuery(String.format(
						"SELECT * FROM Hund WHERE Agare=%s", OwnerId));
			} else {
				result = select.executeQuery("SELECT * FROM Hund");
			}
			while (result.next()) {
				dogs.add(result.getString(2) + "," + result.getString(3) + ","
						+ result.getInt(4));
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
		return dogs;
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

	public boolean addDog(String regnr, String namn, String ras, String fyear,
			Integer agare, String farRegnr, String morRegnr) {
		try {
			con = DriverManager.getConnection(url, userId, password);
			PreparedStatement statement = con
					.prepareStatement("INSERT INTO Hund (Regnr, Namn, Ras, Fyear, Agare, FarRegnr, MorRegnr) values (?,?,?,?,?,?,?)");
			statement.setString(1, regnr);
			statement.setString(2, namn);
			statement.setString(3, ras);
			statement.setString(4, fyear);
			statement.setInt(5, agare);
			statement.setString(6, farRegnr);
			statement.setString(7, morRegnr);
			
			statement.executeUpdate();
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
		return false;
	}
}
