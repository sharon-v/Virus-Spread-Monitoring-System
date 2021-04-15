package country;

import location.Location;

public class City extends Settlement {
	/**
	 * constructor
	 * 
	 * @param name     - name input
	 * @param location - location input
	 * @param people   - Person array for the settlement
	 */
	public City(String name, Location location) {
		super(name, new Location(location));
	}

	// toString-optional

	@Override
	protected RamzorColor calculateRamzorGrade() {
		double p = contagiousPercent();
		double res = 0.2 * Math.pow(4, 1.25 * p);
		return getRamzorColor().doubleToRamzorColor(res); // ???
	}

}
