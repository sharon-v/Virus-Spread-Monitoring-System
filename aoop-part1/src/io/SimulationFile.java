package io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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

public class SimulationFile {

	/**
	 * Read from file
	 */
	public void readFromFile(Map map, String filePath) throws Exception {
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(filePath);// need to receive path
			br = new BufferedReader(fr);
			String settl = br.readLine();
			while (br != null) {
				String[] settlDeteails = settl.split(";");
				String settlementType = settlDeteails[0];
				String settlemntName = settlDeteails[1];
				Point settlementPoint = new Point(Integer.parseInt(settlDeteails[2]),
						Integer.parseInt(settlDeteails[3]));// convert
				Size settlementSize = new Size(Integer.parseInt(settlDeteails[4]), Integer.parseInt(settlDeteails[5]));
				Location settlementLocation = new Location(settlementPoint, settlementSize); // ??
				int settlementPopulationAmount = Integer.parseInt(settlDeteails[6]);

				Settlement mySettlement;
				if (settlementType == "City")
					mySettlement = new City(settlemntName, settlementLocation);
				else if (settlementType == "Moshav")
					mySettlement = new Moshav(settlemntName, settlementLocation);
				else // "Kibbutz"
					mySettlement = new Kibbutz(settlemntName, settlementLocation);
				map.addSettlement(mySettlement);

				for (int i = 0; i < settlementPopulationAmount; ++i)
					mySettlement.addPerson(new Healthy(randomAge(), mySettlement.randomLocation(), mySettlement)); // reference
				settl = br.readLine();
			} // end while
		} catch (FileNotFoundException e) {

		} catch (IOException e) {

		} finally {
			br.close();
			fr.close();
		} // EXCEPTION
	}


	/**
	 * 
	 * @return A random age of person
	 */
	public int randomAge() {
		int standardDeviation = 6;
		int Mean = 9; // Average
		Random ran = new Random();
		// generating integer
		int x = (int) ran.nextGaussian() * standardDeviation + Mean; // random number for x by normal distribution
		int yMin = 0; // ???
		int yMax = 4; // ???
		int y = (int) Math.random() * (yMax - yMin + 1) + yMin; // random number for y
		return (5 * x + y);

	}
}
