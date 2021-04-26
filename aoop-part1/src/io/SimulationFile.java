package io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Random;

import country.City;
import country.Kibbutz;
import country.Map;
import country.Moshav;
import country.Settlement;
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
	public SimulationFile() {
		m_connections = new String[0];
	}

	/**
	 * reads from file
	 */
	public String[] readFromFile(Map map, String filePath) throws Exception {
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(filePath);// need to receive path
			br = new BufferedReader(fr);
			String settl = br.readLine();
			while (settl != null) {
				if (settl.contains("#"))// if its a connection
					addConnection(settl.replaceAll("\\s+", ""));
				String[] settlDeteails = settl.replaceAll("\\s+", "").split(";");
				String settlementType = settlDeteails[0];
				String settlemntName = settlDeteails[1];
				Point settlementPoint = new Point(Integer.parseInt(settlDeteails[2]),
						Integer.parseInt(settlDeteails[3]));// convert
				Size settlementSize = new Size(Integer.parseInt(settlDeteails[4]), Integer.parseInt(settlDeteails[5]));
				Location settlementLocation = new Location(settlementPoint, settlementSize); // ??
				int settlementPopulationAmount = Integer.parseInt(settlDeteails[6]);

				Settlement mySettlement;
				if (settlementType.equals("City"))
					mySettlement = new City(settlemntName, settlementLocation,
							calculateMaxCapacity(settlementPopulationAmount));
				else if (settlementType.equals("Moshav"))
					mySettlement = new Moshav(settlemntName, settlementLocation,
							calculateMaxCapacity(settlementPopulationAmount));
				else if (settlementType.equals("Kibbutz")) 
					mySettlement = new Kibbutz(settlemntName, settlementLocation,
							calculateMaxCapacity(settlementPopulationAmount));
				else
					throw new Exception("No such settlement !");
				map.addSettlement(mySettlement);

				for (int i = 0; i < settlementPopulationAmount; ++i)
					if(!mySettlement.addPerson(new Healthy(randomAge(), mySettlement.randomLocation(), mySettlement)))
							--i;
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
		int yMin = 0;
		int yMax = 4;
		double y = Math.random() * (yMax - yMin + 1) + yMin; // random number for y
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

	private static final double CAPACITY = 1.3;
	private String[] m_connections;
}
