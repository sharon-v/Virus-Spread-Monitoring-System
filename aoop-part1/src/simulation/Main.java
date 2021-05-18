package simulation;



import country.Map;
import ui.MainWindow;

/**
 * 
 * @author Yarden Hovav, Sharon Vazana
 *
 */
public class Main {
	
	public static void main(String[] args) {
		Map map = new Map();// Map instance
		MainWindow theWindow = new MainWindow(map);
	}
}
