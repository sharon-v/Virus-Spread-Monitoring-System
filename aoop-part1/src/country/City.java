package country;

import location.Location;

public class City extends Settlement {
	/**
	 * 
	 * @param name - name input
	 * @param location - location input
	 * @param people - Person array for the settlement
	 */
	public City(String name, Location location) {
		super(name, location);
	}
	
	@Override
	protected RamzorColor calculateRamzorGrade() {
		double p = contagiousPercent();
		double res = 0.2 * Math.pow(4, 1.25 * p);
		return getRamzorColor().doubleToRamzorColor(res); // ???
	}

}
