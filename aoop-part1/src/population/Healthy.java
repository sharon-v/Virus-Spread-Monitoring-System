package population;

import country.Settlement;
import location.Point;

/**
 * 
 * @author Yarden Hovav, Sharon Vazana
 *
 */
public class Healthy extends Person{
	
	/**
	 * Constructor
	 * 
	 * @param age        - age input
	 * @param location   - Point input
	 * @param settlement - settlement input
	 */
	public Healthy(int age, Point location, Settlement settlement) {
		super(age, location, settlement);
	}
	
	@Override
	public double contagionProbability() {
		return 1; 
	}
	
	
	@Override
	public String toString() {
		return super.toString() + "\tstatus: Healthy";
	}
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof Healthy))
			return false;
		Healthy h = (Healthy) o;
		return super.equals(h);
	}
	
	@Override
	public String healthCondition() {
		return "Healthy";
	}

	/**
	 * creates a copy of the Person as a Vaccinated object
	 * 
	 * @return vaccinated object of the current Person
	 */
	public Person vaccinate() {
		Vaccinated newVaccinated = new Vaccinated(getAge(), getLocation(), getSettelement());
		removeFromSettl();
		newVaccinated.addToSettl();
		return newVaccinated;
	}
}
