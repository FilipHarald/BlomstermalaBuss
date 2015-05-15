package application;

public class MainApp {
	
	public static void main(String args[]) {
		DatabaseController dbC = new DatabaseController("jdbc:mysql://195.178.235.60/ae8556", "ae8556", "1234");
		SimpleUI ui = new SimpleUI(dbC);
//		System.out.println(dbC.getDogs(1));
	}
	
}
