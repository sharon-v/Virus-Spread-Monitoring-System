package country;

import java.util.Scanner;

import io.SimulationFile;

public class Map {
	/**
	 * constructor
	 */
	public Map() {
		m_settlement = new Settlement[0];
	}
	
//	public void test() {
//		System.out.println(m_settlement[5]);
//		System.out.println(m_settlement[5].removePerson(m_settlement[5].getP()[5]));
//		System.out.println("try" + m_settlement[5].getP()[5]+"end");
//		
//		System.out.println(m_settlement[5]);
//		System.out.println(m_settlement[2].getP()[2].equals(m_settlement[2].getP()[2]));
//		System.out.println(m_settlement[2].getP()[2].equals(m_settlement[2].getP()[1]));
//	}
	
	@Override
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
		String str = "=== settlements ===\n";
		for (int i = 0; i < m_settlement.length; ++i)
			str += m_settlement[i].toString() + "\n";
		return str;
	}

	/**
	 * manages input of data from file
	 */
	public void loadInfo() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Please enter the file path: ");
		String filePath = sc.nextLine();
		try {
			SimulationFile loadMap = new SimulationFile();
			loadMap.readFromFile(this, filePath);
		} catch (Exception e) {
			System.out.println(e);	
		} finally {
			sc.close();
		}
	}

	/**
	 * manages simulation sequence
	 */
	public void executeSimulation() throws Exception {
		for (int i = 0; i < 5; ++i) {
			System.out.println("\n		=== simulation num. " + (i + 1) + " ===");
			for (int j = 0; j < m_settlement.length; ++j) // run over settlements
			{
				System.out.println("\n\t-- settlement " + (j + 1) + "--");
				m_settlement[j].simulation();
				System.out.println(m_settlement[j]);
				
			}
		}
		System.out.println("			THE END !! ");
	}



	
	/**
	 * initializes the Settlements with sick people
	 */
	public void intialization() { // 1%
		for (int i = 0; i < m_settlement.length; ++i) {
			m_settlement[i].infectOnePercent();
		}
	}

	private Settlement m_settlement[];// array of Settlements
}
