package country;

import location.Location;

/**
 * 
 * @author Yarden Hovav, Sharon Vazana
 *
 */
public class City extends Settlement {
	/**
	 * constructor
	 * 
	 * @param name     - name input
	 * @param location - location input
	 * @param people   - Person array for the settlement
	 */
	public City(String name, Location location, int population, Map map) {
		super(name, new Location(location), population, map);
	}


	/**
	 * return the settlement type
	 */
	public String getSettlementType() {
		return "City";
	}

	/**
	 * return the new color of the settlement
	 */
	protected RamzorColor calculateRamzorGrade() { 
		double p = contagiousPercent();
		double res = 0.2 * Math.pow(4, 1.25 * p);
		return getRamzorColor().doubleToRamzorColor(res);
	}

}
