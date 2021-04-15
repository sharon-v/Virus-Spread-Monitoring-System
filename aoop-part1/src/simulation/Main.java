package simulation;

import country.Map;

/**
 * 
 * @author Yarden Hovav, Sharon Vazana
 *
 */
public class Main {

	public static void main(String[] args) {
		Map myMap = new Map();
		myMap.loadInfo(); // first stage
		myMap.intialization();// second stage
		try {
			myMap.executeSimulation(); // third stage
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("an unexpected ERROR has occurred :(");
		}
	}
}
