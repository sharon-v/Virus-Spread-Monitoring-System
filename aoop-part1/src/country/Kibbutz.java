package country;

import location.Location;

/**
 * 
 * @author Yarden Hovav, Sharon Vazana
 *
 */
public class Kibbutz extends Settlement {
	/**
	 * constructor
	 * 
	 * @param name     - name input
	 * @param location - location input
	 * @param people   - Person array for the settlement
	 */
	public Kibbutz(String name, Location location, int population, Map map) {
		super(name, new Location(location), population, map);
	}
	
	/**
	 * Return the settlement type
	 */
	public String getSettlementType() {
		return "Kibbutz";
	}

	/**
	 * Return the new Color of the settlement
	 */
	protected RamzorColor calculateRamzorGrade() { 
		double p = contagiousPercent();
		double c = getRamzorColor().getValue(); 
		double res = 0.45 + Math.pow(Math.pow(1.5, c) * (p - 0.4), 3);
		return getRamzorColor().doubleToRamzorColor(res); 
	}
}

