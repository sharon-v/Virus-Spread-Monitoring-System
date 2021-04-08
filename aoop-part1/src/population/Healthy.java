package population;

import country.Settlement;
import location.Point;
import simulation.Clock;

public class Healthy extends Person{
	
	public Healthy(int age, Point location, Settlement settlement) {
		super(age, location, settlement);
	}
	
	public double contagionProbability() {
		return 1; //???
	}
	
	public Person vaccinate() {
		return new Vaccinated(getAge(), getLocation(), getSettelement(), Clock.now());
	}
	
	@Override
	public String toString() {
		return super.toString() + "The person is healthy."; //???
	}
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof Healthy))
			return false;
		Healthy h = (Healthy) o;
		return super.equals(h); //????
	}

}
