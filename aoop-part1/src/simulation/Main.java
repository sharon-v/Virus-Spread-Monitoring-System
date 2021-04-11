package simulation;

import java.util.Scanner;

import country.Map;
import country.Settlement;
import io.SimulationFile;
import population.Person;
import population.Sick;


public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Map myMap = loadInfo();
		
	}
	
	public static Map loadInfo() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Please enter the file path: ");
		String filePath = sc.nextLine();
		Map myMap = new Map();
		SimulationFile loadMap = new SimulationFile(myMap, filePath);
		sc.close();
		return myMap;
		
	}
	
	public static void intialization() {
		
	}
	
	public static void simulation(Map myMap) {
		Settlement[] settlements = myMap.getSettlementArr();
		Person[] people;
		for(int i = 0 ; i < settlements.length ; ++i) //run over settlements
		{
			 people = settlements[i].getPeople();
			for(int j = 0 ; j < people.length ; ++j) {//run over the population of each settlement
				if(people[j].healthCondition() == "Sick")
					randomContagion(people, people[j]);
			}
		}
	}
	
	private static void randomContagion(Person[] people , Sick sickPerson) {
		for(int i = 0 ; i < 6 ; ++i)
		{
			int randomIndex = (int)Math.random()*(people.length +1);
			if(!(sickPerson.getVirus().tryToContagion(sickPerson, people[randomIndex]))
					System.out.println("Contagion failed !! :)");
			else
				System.out.println("Contagion succeeded !! :(");
		}
	}

}
