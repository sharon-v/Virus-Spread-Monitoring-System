package population;

import country.Settlement;
import location.Point;
import simulation.Clock;
import virus.IVirus;

public class Vaccinated extends Person {
	
	/**
	 * Constructor
	 * @param age - age input 
	 * @param location - location input
	 * @param settlement - settlement input
	 * @param vaccinationTime - vaccination time input
	 */
	public Vaccinated(int age, Point location, Settlement settlement) {
		super(age, new Point(location), settlement);
		m_vaccinationTime = Clock.now();
	}
	
	@Override
	public double contagionProbability() {
		if(m_vaccinationTime < 21)
			return Math.min(1, (0.56 + 0.15 * Math.sqrt(21 - m_vaccinationTime)));
		else
			return Math.max(0.05, (1.05 / (m_vaccinationTime - 14)));
	}
	

	@Override
	public String healthCondition() {
		return "Vaccinated";
	}
	
	@Override
	public IVirus getVirusFromPerson() {
		return null;
	}

	@Override
	public String toString() {
		return super.toString() + "\tstatus: Vaccinated\tvaccination time: " + m_vaccinationTime;
	}
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof Vaccinated))
			return false;
		Vaccinated v = (Vaccinated) o;
		return super.equals(v) && m_vaccinationTime == v.getvaccinationTime();
	}
	
	/**
	 * get method
	 * 
	 * @return vaccination time
	 */
	public long getvaccinationTime() {
		return m_vaccinationTime;
	}
	
	//attributes
	private long m_vaccinationTime;// vaccination time
}
