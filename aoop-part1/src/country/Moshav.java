package country;

import location.Location;

/**
 * 
 * @author Yarden Hovav, Sharon Vazana
 *
 */
public class Moshav extends Settlement {
	/**
	 * constructor
	 * 
	 * @param name     - name input
	 * @param location - location input
	 * @param people   - Person array for the settlement
	 */
	public Moshav(String name, Location location, int population) {
		super(name, new Location(location), population);
	}
	
	@Override
	protected RamzorColor calculateRamzorGrade() {
		// p - sick percentage , c - current color
		double p = contagiousPercent();
		double c = getRamzorColor().getValue(); // ???
		double res = 0.3 + 3 * (Math.pow(1.2, c) * Math.pow((p - 0.35), 5));
		return getRamzorColor().doubleToRamzorColor(res);
	}
}
