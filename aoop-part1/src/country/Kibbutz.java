package country;

import location.Location;
import population.Person;

public class Kibbutz extends Settlement {
	/**
	 * 
	 * @param name - name input
	 * @param location - location input
	 * @param people - Person array for the settlement
	 */
	public Kibbutz(String name, Location location, Person[] people) {
		super(name, location, people);
	}
	
	@Override
	protected RamzorColor calculateRamzorGrade() {
		double p = contagiousPercent();
		double c = getRamzorColor().getValue(); // ???
		double res = 0.45 + Math.pow(Math.pow(1.5, c) * (p - 0.4), 3);
		setRamzorColor(getRamzorColor().doubleToRamzorColor(res));
		return getRamzorColor().doubleToRamzorColor(res); // ???
	}
}

