package country;

import java.util.Scanner;

import io.SimulationFile;
import population.Person;

public class Map {
	public Map() {
		m_settlement = new Settlement[0];
	}
	
	public String toString() {
		return "amount of settlements: " + m_settlement.length + "\n" + toStringSettlements();
	}

	// no need for equals()

	/**
	 * 
	 * @param s new settlement to add to map
	 */
	public void addSettlement(Settlement s) {
		Settlement[] temp = new Settlement[m_settlement.length + 1];
		
		for(int i = 0; i < m_settlement.length; ++i) {
			temp[i] = m_settlement[i];
		}
		temp[m_settlement.length] = s;
		m_settlement = temp;
	}
	
	/**
	 * 
	 * @return toString of all the Settlement in the Map
	 */
	private String toStringSettlements() {
		String str = "settlements: \n";
		for (int i = 0; i < m_settlement.length; ++i)
			str += m_settlement[i].toString() + "\n";
		return str;
	}

	public void loadInfo() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Please enter the file path: ");
		String filePath = sc.nextLine();
		try {
			SimulationFile loadMap = new SimulationFile();
			loadMap.readFromFile(this, filePath);
		} catch (Exception e) {
			System.out.println(e);	
			System.out.println("loadInfo...Map");
		} finally {
			sc.close();
		}
	}

	/**
	 * 
	 */
	public void executeSimulation() throws Exception {
		for (int i = 0; i < 5; ++i) {
			System.out.println("=== simulation num. " + i + " ===");
			simulation();
		}
	}

	/**
	 * 
	 */
	private void simulation() throws Exception {
		Person[] people;
		for (int i = 0; i < m_settlement.length; ++i) // run over settlements
		{
			people = m_settlement[i].getPeople();
			for (int j = 0; j < people.length; ++j) {// run over the population of each settlement
				if (people[j].healthCondition().equals("Sick"))
					randomContagion(people, people[j]);

			}
		}
	}

	public void intialization() { // 1%
		for (int i = 0; i < m_settlement.length; ++i) {
			m_settlement[i].infectOnePercent();
		}
	}

	private void randomContagion(Person[] people, Person sickPerson) throws Exception {
		for (int i = 0; i < 6; ++i) {
			int randomIndex = (int)(Math.random() * (people.length));
			if (sickPerson.getVirusFromPerson() == null)
				throw new Exception("this person isn't sick...");
			if (!(sickPerson.getVirusFromPerson().tryToContagion(sickPerson, people[randomIndex])))
				System.out.println("Low contagion probability --> Contagion failed !! :)");
			else {
				System.out.println("High contagion probability --> Contagion succeeded !! :(");
				people[randomIndex] = people[randomIndex].contagion(sickPerson.getVirusFromPerson());
			}
		}
	}

	private Settlement m_settlement[];
}
