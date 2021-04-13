package simulation;

import country.Map;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Map myMap = new Map();
		myMap.loadInfo(); // first stage
		myMap.intialization();// second stage

		try {
			myMap.executeSimulation(); // third stage
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("an unexpected ERROR has occurred :(");
		}
		System.out.println(myMap);
	}
}
