package simulation;

import country.Map;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		test();
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
	
	public static void test() {
		Map map = new Map();
		map.loadInfo();
		map.test();
	}
}
