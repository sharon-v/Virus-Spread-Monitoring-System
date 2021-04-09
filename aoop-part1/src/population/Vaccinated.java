package population;

import country.Settlement;
import location.Point;

public class Vaccinated extends Person {
	
	/**
	 * Constractor
	 * @param age - age input 
	 * @param location - location input
	 * @param settlement - settlement input
	 * @param vaccinationTime - vaccination time input
	 */
	public Vaccinated(int age, Point location, Settlement settlement, long vaccinationTime) {
		super(age, location, settlement);
		m_vaccinationTime = vaccinationTime;
	}
	
	@Override
	public double contagionProbability() {
		if(m_vaccinationTime < 21)
			return Math.min(1, (0.56+0.15*Math.sqrt(21-m_vaccinationTime)));
		else
			return Math.max(0.05, (1.05/(m_vaccinationTime-14)));
	}
	
	@Override
	public String toString() {
		return super.toString() + "\nStatus: Vaccinated.\nThe person got vaccinated at " + m_vaccinationTime + " .";
	}
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof Vaccinated))
			return false;
		Vaccinated v = (Vaccinated) o;
		return super.equals(v) && m_vaccinationTime == v.getvaccinationTime();
	}
	
	/**
	 * 
	 * @return vaccination time
	 */
	public long getvaccinationTime() {
		return m_vaccinationTime;
	}
	
	@Override
	public String healthCondition() {
		return "Vaccinated";
	}
	
	private long m_vaccinationTime;

}
