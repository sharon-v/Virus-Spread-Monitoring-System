package country;

import java.util.Scanner;

import io.SimulationFile;

/**
 * 
 * @author Yarden Hovav, Sharon Vazana
 *
 */
public class Map {
	/**
	 * constructor
	 */
	public Map() {
		m_settlement = new Settlement[0];
	}

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
		String[] connections = null;
		try {
			SimulationFile loadMap = new SimulationFile();
			connections = loadMap.readFromFile(this, filePath);
			makeConnections(connections);
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

	public String[][] getTableData() {
		String[][] temp = new String[m_settlement.length][];
		for (int i = 0; i < m_settlement.length; ++i) {
			temp[i] = new String[7];//????? datamember
			for(int j = 0; j < temp[i].length; ++j) {
				if (j % temp[i].length == 0)
					temp[i][j] = m_settlement[i].getSettlementName();
				else if (j % temp[i].length == 1)
					temp[i][j] = m_settlement[i].getSettlementType();
				else if (j % temp[i].length == 2)
					temp[i][j] = m_settlement[i].getRamzorColor().toString();
				else if (j % temp[i].length == 3)
					temp[i][j] = String.valueOf(m_settlement[i].contagiousPercent());
				else if (j % temp[i].length == 4)
					temp[i][j] = m_settlement[i].getVaccineDoses() + "";
				else if (j % temp[i].length == 5)
					temp[i][j] = m_settlement[i].getNumOfDeceased() + "";
				else if (j % temp[i].length == 6)
					temp[i][j] = m_settlement[i].getNumOfPeople() + "";
			}
		}
		return temp;
	}

	/**
	 * 
	 * @param arr
	 */
	private void makeConnections(String[] connections) {
		String[] temp = null;
		for (int i = 0; i < connections.length; ++i) {
			temp = connections[i].split(";");
			connectSettlements(temp[1], temp[2]);
		}
	}

	public static String[][] filtering(String[][] data, String type) {
		if (type.equals("filter by"))
			return data;

		String[][] temp = new String[0][];
		for (int i = 0; i < data.length; ++i) {
			for (int j = 0; j < data[i].length; ++j) {
				if (data[i][j].equals(type))
					temp = addData(temp, data[i]);
			}
		}
		return temp;
	}

	private static String[][] addData(String[][] arr, String[] newStr) {
		String[][] temp = new String[arr.length + 1][];
		for (int i = 0; i < arr.length; ++i) {
			temp[i] = arr[i];
		}
		temp[arr.length] = newStr;
		return temp;
	}

	/**
	 * 
	 * @param s1
	 * @param s2
	 */
	private void connectSettlements(String s1, String s2) {
		Settlement settl1 = null, settl2 = null;
		for (int i = 0; i < m_settlement.length; ++i) {
			if (m_settlement[i].getSettlementName().equals(s1))
				settl1 = m_settlement[i];
			else if (m_settlement[i].getSettlementName().equals(s2))
				settl2 = m_settlement[i];
		}
		if (settl1 != null && settl2 != null) {
			settl1.addNewConnection(settl2);
			settl2.addNewConnection(settl1);
		}

	}

	private Settlement m_settlement[];// array of Settlements
}
