package application;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

import application.models.Tur;

public class MainApp {
	
	public static void main(String args[]) {
		Properties prop = new Properties();
		InputStream input;
		try {
			input = new FileInputStream("db.properties");
			prop.load(input);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		DatabaseController dbC = new DatabaseController(
				prop.getProperty("host"), 
				prop.getProperty("username"), 
				prop.getProperty("password"), 
				prop.getProperty("database"));
		
		SimpleUI ui = new SimpleUI(dbC);
//		System.out.println(dbC.getDogs(1));
		
		ArrayList<Tur> turer = dbC.getTurer();
		
		for (Tur tur : turer) {
			System.out.println(tur);
		}
	}
	
}
