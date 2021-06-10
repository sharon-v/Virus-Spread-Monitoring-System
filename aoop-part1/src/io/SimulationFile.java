package io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Random;

import country.Map;
import country.Settlement;
import country.SettlementFactory;
import location.Location;
import location.Point;
import location.Size;
import population.Healthy;

/**
 * 
 * @author Yarden Hovav, Sharon Vazana
 *
 */
public class SimulationFile {

	/**
	 * constractor
	 */
	public SimulationFile() {
		m_connections = new String[0];
	}

	/**
	 * read from file
	 * @param map - Map object
	 * @param filePath - String object thet contain the path to the file
	 * @return String array that contains the connections in the map
	 * @throws Exception any type
	 */
	public String[] readFromFile(Map map, String filePath) throws Exception {
		FileReader fr = null;
		BufferedReader br = null;
		map.resetMap();
		try {
			fr = new FileReader(filePath);// need to receive path
			br = new BufferedReader(fr);
			String settl = br.readLine();
			while (settl != null) {
				if (settl.contains("#"))// if its a connection
					addConnection(settl.replaceAll("\\s+", ""));
				else {
					String[] settlDeteails = settl.replaceAll("\\s+", "").split(";");
					String settlementType = settlDeteails[0];
					String settlemntName = settlDeteails[1];
					Point settlementPoint = new Point(Integer.parseInt(settlDeteails[2]),
							Integer.parseInt(settlDeteails[3]));// convert
					Size settlementSize = new Size(Integer.parseInt(settlDeteails[4]), Integer.parseInt(settlDeteails[5]));
					Location settlementLocation = new Location(settlementPoint, settlementSize);
					int settlementPopulationAmount = Integer.parseInt(settlDeteails[6]);

					SettlementFactory factory = new SettlementFactory();
					Settlement mySettlement = factory.createSettlement(settlementType, settlemntName, settlementLocation,
							calculateMaxCapacity(settlementPopulationAmount), map);
					if(mySettlement != null)
						map.addSettlement(mySettlement);

					for (int i = 0; i < settlementPopulationAmount; ++i)
						if(!mySettlement.addPerson(new Healthy(randomAge(), mySettlement.randomLocation(), mySettlement)))
							--i;
				}// end else
				settl = br.readLine();
			} // end while
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			br.close();
			fr.close();
		} // EXCEPTION
		return m_connections;
	}

	/**
	 * chooses a random age
	 * 
	 * @return A random age of person
	 */
	public int randomAge() {
		int standardDeviation = 6;
		int Mean = 9; // Average
		Random ran = new Random();
		// generating integer
		double x;
		double rng;
		do {
			rng = ran.nextGaussian();
		} while (rng < -1 || rng > 1);
		x = (rng * standardDeviation + Mean); // random number for x by normal distribution
		int y = ran.nextInt(5);
		return (int) ((5 * x) + y);
	}

	/**
	 * 
	 * @param amountOfPeople - amount of people in settlement
	 * @return max capacity of the settlement
	 */
	private int calculateMaxCapacity(int amountOfPeople) {
		return (int) (CAPACITY * amountOfPeople);
	}

	/**
	 * 
	 * @param s - new String type connection
	 */
	private void addConnection(String s) {
		String[] temp = new String[m_connections.length + 1];
		for (int i = 0; i < m_connections.length; ++i) {
			temp[i] = m_connections[i];
		}
		temp[m_connections.length] = s;
		m_connections = temp;
	}

	private final double CAPACITY = 1.3; 
	private String[] m_connections; // the connections in the map
}
