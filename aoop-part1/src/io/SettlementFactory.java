package io;

import country.City;
import country.Kibbutz;
import country.Map;
import country.Moshav;
import country.Settlement;
import location.Location;

public class SettlementFactory {
	
	/**
	 * 
	 * @param type - String of the requested object to create
	 * @param name - settlement name
	 * @param location - settlement location
	 * @param population - settlement amount of population
	 * @param map - map that contains the settlement
	 * @return new descendant of settlement   
	 */
	public Settlement createSettlement(String type, String name, Location location, int population, Map map) {
		if(type == null)
			return null;
		if(type.equalsIgnoreCase("City"))
			return new City(name, location, population, map);
		else if(type.equalsIgnoreCase("Moshav"))
			return new Moshav(name, location, population, map);
		else if(type.equalsIgnoreCase("Kibbutz"))
			return new Kibbutz(name, location, population, map);
		return null;
	}
}
