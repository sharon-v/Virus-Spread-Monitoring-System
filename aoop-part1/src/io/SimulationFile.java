package io;

import java.io.*;
import java.util.Random;

import country.*;
import location.*;
import population.Healthy;
import population.Person;

public class SimulationFile {
	/**
	 * Constractor
	 */
	public SimulationFile(Map map, String filePath) {
		// need to implement
	}

	/**
	 * Read from the file
	 */
	public void readFromFile(Map map, String filePath) {
		FileReader fr;
		BufferedReader br;
		try {
			fr = new FileReader(filePath);// need to receive path
			br = new BufferedReader(fr);
			String settl = br.readLine();
			while(br != null) {
				String[] settlDeteails = settl.split(";");
				String settlementType = settlDeteails[0];
				String settlemntName = settlDeteails[1];
				Point settlementPoint = new Point(Integer.parseInt(settlDeteails[2]), Integer.parseInt(settlDeteails[3]));//convert from string to integer
				Size settlementSize = new Size(Integer.parseInt(settlDeteails[4]), Integer.parseInt(settlDeteails[5]));
				Location settlementLocation = new Location(settlementPoint, settlementSize); //??
				int settlementPopulationAmount = Integer.parseInt(settlDeteails[6]);

				Person [] population = new Person[settlementPopulationAmount]; // create the population array
				for(int i = 0; i < settlementPopulationAmount; ++i) 
					population[0] = new Healthy(randomAge(), settlementLocation, ??? ); // reference to settlement
				//	Map newMap = new Map();
				if(settlementType == "City")
					map.addSettlement(new City(settlemntName, settlementLocation, population));
				else if(settlementType == "Moshav")
					map.addSettlement(new Moshav(settlemntName, settlementLocation, population));
				else if(settlementType == "Kibbutz")
					map.addSettlement(new Kibbutz(settlemntName, settlementLocation, population));
				settl = br.readLine();
			}
		}
		catch (FileNotFoundException e) {
			throw e; 
		}
		catch (IOException e) {
			System.err.println(e.getMessage()); 
			throw e;
		}
		finally {
			br.close();
			fr.close();
		}
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

	} // Class NormalDistribution
} // NormalDistribution(double mean, double sd)
