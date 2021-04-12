package population;

import country.Settlement;
import location.Point;
import virus.IVirus;

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
	
	/**
	 * 
	 * @return vaccinated object of the current Person
	 */
	public Person vaccinate() {
		return new Vaccinated(getAge(), getLocation(), getSettelement());
	}
	
	@Override
	public IVirus getVirusFromPerson() {
		return null;
	}

	@Override
	public String toString() {
		return super.toString() + " \nStatus: Healthy."; 
	}
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof Healthy))
			return false;
		Healthy h = (Healthy) o;
		return super.equals(h); //????
	}
	
	@Override
	public String healthCondition() {
		return "Healthy";
	}
}
