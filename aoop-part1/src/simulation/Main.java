package simulation;

import javax.swing.JFrame;

import country.Map;
import ui.Statistics;

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
		//???????????????????
		
		JFrame frame = new JFrame("Table Example"); 		
		Statistics st = new Statistics(myMap, frame);		
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

			  
		//???????????????????
	}
}
