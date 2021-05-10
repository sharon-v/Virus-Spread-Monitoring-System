package simulation;



import country.Map;
import ui.MainWindow;
import ui.MapDrawing;

/**
 * 
 * @author Yarden Hovav, Sharon Vazana
 *
 */
public class Main {
	
	public static void main(String[] args) {
		Map map = new Map();// Map instance
		MainWindow theWindow = new MainWindow(map);
		playSimu(map, theWindow.getMapDrawing());
	}

	
	/**
	 * 
	 * @param myMap - Map object 
	 * @param draw - MapDrawing object
	 */
	private static void playSimu(Map myMap, MapDrawing draw) {
		while (true) {
			draw.repaint();
			System.out.print("");
			if (playFlag == true && loadFlag == true) {
				try {
					System.out.println("ticks : " + Clock.now());
					myMap.executeSimulation(); // third stage
					Clock.nextTick();
					Thread.sleep(sleepTime * 1000);
				} catch (Exception ex) {
					System.out.println("an unexpected ERROR has occurred :(");
					ex.printStackTrace();
				}
				draw.updateStatWindow();
			}
		}
	}

	/**
	 * set the play flag
	 * @param val - boolean object
	 */
	public static void setPlayFlag(boolean val) {
		playFlag = val;
	}

	/**
	 * set the load flag
	 * @param val -boolean object
	 */
	public static void setLoadFlag(boolean val) {
		loadFlag = val;
	}

	/**
	 * get the play flag
	 * @return the play flag boolean value
	 */
	public static boolean getPlayFlag() {
		return playFlag;
	}
	
	/**
	 * get the load flag
	 * @return the load flag boolean value
	 */
	public static boolean getLoadFlag() {
		return loadFlag;
	}
	
	/**
	 * set the play flag
	 * @param val - the new sleep time int value
	 */
	public static void setSleepTime(int val) {
		sleepTime = val;
	}


	private static boolean playFlag = true; // flag to know if the simulation is play or pause
	private static boolean loadFlag = false; // flag to know if file has been loaded or not
	private static int sleepTime = 1; // the time between each simulation
//	private final Timer timer ; //????

}
