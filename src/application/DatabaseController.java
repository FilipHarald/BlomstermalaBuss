package application;

import java.sql.*;
import java.util.Calendar;
import java.util.Date;
import java.util.ArrayList;

import application.models.*;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

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

	public ArrayList<Bokning> getBokningar() {
		ArrayList<Bokning> bokningar= new ArrayList<Bokning>();
		try {
			Statement select = con.createStatement();
			ResultSet result;
			result = select.executeQuery("SELECT * FROM bokning");

			while (result.next()) {
				bokningar.add(new Bokning(result.getInt(1), result.getString(2), result.getDate(3)));
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return bokningar;

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

	public void addKund(String socialNumber, String name, String tel, String mail,
			String password, String address) {
		String insertKund = "insert into kund (personnr, namn, telefonnr, mail, losenord, adress) values (?, ?, ?, ?, ?, ?)";
		
		try {
			PreparedStatement insert = con.prepareStatement(
					insertKund, Statement.RETURN_GENERATED_KEYS);
			insert.setString(1, socialNumber);
			insert.setString(2, name);
			insert.setString(3, tel);
			insert.setString(4, mail);
			insert.setString(5, password);
			insert.setString(6, address);
			insert.executeUpdate();
		} catch (SQLException e){
			e.printStackTrace();
		}
		
	}

	public String addPaketBokning(String kund, Date datum,
			String paketresaId) {
		
		Calendar calendar = Calendar.getInstance();
		java.sql.Date startDatum = new java.sql.Date(datum.getTime());
		
		ArrayList<Paketresa> paketresaTurer = getPaketresaTurer(paketresaId);
		
		for (Paketresa paketTur : paketresaTurer) {
			calendar.setTime(datum);
            calendar.add(Calendar.DATE, paketTur.getDagarFranStart());

            java.sql.Date avreseDatum = new java.sql.Date(calendar.getTime().getTime());
            
			if (turIsFull(paketTur.getTur(), avreseDatum)) {
				return "TUR HAR INGA LEDIGA PLATSER";
			}
		}

        int bokningId = -1;
		int bokadResaId = -1;

		String insertBokadResa = "INSERT INTO bokad_resa (bokning, tur, datum) VALUES (?, ?, ?)";

		java.sql.Date bokningsDatum = new java.sql.Date(new Date().getTime());
		

		try {
            bokningId = addBokning(kund);

			int dagarSedanStart = 0;

			for (int i = 0; i < paketresaTurer.size(); i++) {
				Paketresa tur = paketresaTurer.get(i);

				PreparedStatement insert = con.prepareStatement(
						insertBokadResa, Statement.RETURN_GENERATED_KEYS);
				insert.setInt(1, bokningId);
				insert.setInt(2, tur.getTur());

				if (i == 0) {
					// If it's the first tur, we don't have to calculate
					// anything
					insert.setDate(3, startDatum);
				} else {
					// Here we have to calculate the days from previous
					// avresedag, to current avresedag

                    calendar.setTime(datum);
                    calendar.add(Calendar.DATE, tur.getDagarFranStart());

                    java.sql.Date avreseDatum = new java.sql.Date(calendar.getTime().getTime());

                    insert.setDate(3, avreseDatum);

				}

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

                System.out.println(affectedRows);

			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return "BOKNING GENOMFÖRD";
	}

	public int addBokning(String kund) {
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

	public String addTurBokning(String kund, Date datum, int turId) {
		java.sql.Date startDatum = new java.sql.Date(datum.getTime());
		
		if (turIsFull(turId, startDatum)) {
			return "TUR HAR INGA LEDIGA PLATSER";
		}

        int bokningId = -1;
		int bokadResaId = -1;

		String insertBokning = "INSERT INTO bokning (kund, datum) VALUES (?, ?)";
		String insertBokadResa = "INSERT INTO bokad_resa (bokning, tur, datum) VALUES (?, ?, ?)";

		java.sql.Date bokningsDatum = new java.sql.Date(new Date().getTime());
		

		try {
            bokningId = addBokning(kund);

			PreparedStatement insert = con.prepareStatement(insertBokadResa);
			insert.setInt(1, bokningId);
			insert.setInt(2, turId);
			insert.setDate(3, startDatum);

			int affectedRows = insert.executeUpdate();

			if (affectedRows == 0) {
				throw new SQLException("No rows affected!");
			}

			System.out.println(affectedRows);

		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return "BOKNING OK";
	}

	private boolean turIsFull(int turId, java.sql.Date datum) {
		Tur tur = getTur(turId);
		
		try {
			PreparedStatement select = con.prepareStatement("SELECT COUNT(*) FROM bokad_resa WHERE tur = ? AND datum = ?");
			select.setInt(1, turId);
			select.setDate(2, datum);
			
			ResultSet result = select.executeQuery();
			
			if (result.next()) {
				System.out.println("ASD");
				System.out.println(tur.getKapacitet());
				
				if (result.getInt(1) >= tur.getKapacitet()) {
					return true;
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		
		System.out.println("ZXCZXC");
		
		return false;
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
		String s = paketresenamn + "\n";
		try {
			Statement stmt = con.createStatement();
			ResultSet rs;
			rs = stmt.executeQuery(String.format(
					"SELECT sum(tur.kostnad) "
							+ "FROM tur INNER JOIN paketresa "
							+ "ON tur.id = paketresa.tur "
							+ "WHERE paketresa.namn = '%s'", paketresenamn));
			rs.next();
			s = s + "Pris totalt: " + rs.getInt(1) + "\n";
			Statement statement = con.createStatement();
			ResultSet result;
			result = statement
					.executeQuery(String
							.format("SELECT tur.avreseort,tur.avresedag,tur.avresetid,tur.ankomstort,tur.ankomstdag,tur.ankomsttid "
									+ "FROM tur INNER JOIN paketresa "
									+ "ON tur.id = paketresa.tur "
									+ "WHERE paketresa.namn = '%s'",
									paketresenamn));
			while (result.next()) {
				s = s + result.getString(1) + "," + result.getString(2)
						+ "," + result.getString(3) + "," + result.getString(4)
						+ "," + result.getString(5) + "," + result.getString(6) + "\n";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return s;

	}

    public String getBokningDetails(int id) {
        String details = "";
        try {
            PreparedStatement select = con.prepareStatement("SELECT bokning.id, bokning.datum, bokad_resa.datum, kund.namn, tur.avreseort, tur.ankomstort " +
                    "FROM bokning " +
                    "INNER JOIN kund ON bokning.kund=kund.personnr " +
                    "INNER JOIN bokad_resa ON bokning.id=bokad_resa.bokning " +
                    "INNER JOIN tur ON bokad_resa.tur=tur.id " +
                    "WHERE bokning.id = ?");
            select.setInt(1, id);
            ResultSet result;
            result = select.executeQuery();

            result.next();

            details = String.format("ID: %d\nDATUM: %s\nAVGÅNG: %s\nKUND: %s\nAVRESEORT: %s\nANKOMSTORT: %s", result.getInt(1), result.getString(2), result.getString(3),result.getString(4), result.getString(5), result.getString(6));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return details;
    }

    public ArrayList<Paketresa> getPaketresaTurer(String id) {
        ArrayList<Paketresa> turer = new ArrayList<Paketresa>();

        try {
            PreparedStatement select = con.prepareStatement("SELECT paketresa.* "
                    + "FROM paketresa "
                    + "WHERE paketresa.namn = ? "
                    + "ORDER BY paketresa.dagar_fran_start ASC");
            select.setString(1, id);
            ResultSet result;
            result = select.executeQuery();
            while (result.next()) {
                turer.add(new Paketresa(result.getString(1), result.getInt(2),
                        result.getInt(3)));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return turer;
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


    public String removeTur(int id) {
        try {
            PreparedStatement delete = con.prepareStatement("DELETE FROM tur WHERE id = ?");
            delete.setInt(1, id);

            int affectedRows = delete.executeUpdate();

            if (affectedRows > 0) {
                return "OK";
            }

        } catch (MySQLIntegrityConstraintViolationException ex) {
            return "TUR ANVÄNDS I EN PAKETRESA ELLER BOKNING";
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return "ERROR";
    }

    public String removeBokning(int id) {
        try {
            PreparedStatement delete = con.prepareStatement("DELETE FROM bokning WHERE id = ?");
            delete.setInt(1, id);

            int affectedRows = delete.executeUpdate();

            if (affectedRows > 0) {
                return "OK";
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return "ERROR";
    }

    public String removePaketresa(String id) {
        try {
            PreparedStatement delete = con.prepareStatement("DELETE FROM paketresa WHERE namn = ?");
            delete.setString(1, id);

            int affectedRows = delete.executeUpdate();

            if (affectedRows > 0) {
                return "OK";
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return "ERROR";
    }

    public String removeKund(String id) {
        try {
            PreparedStatement delete = con.prepareStatement("DELETE FROM kund WHERE personnr = ?");
            delete.setString(1, id);

            int affectedRows = delete.executeUpdate();

            if (affectedRows > 0) {
                return "OK";
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return "ERROR";
    }
}
