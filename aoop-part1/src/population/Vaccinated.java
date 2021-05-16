package population;

import country.Settlement;
import location.Point;
import simulation.Clock;

/**
 * 
 * @author Yarden Hovav, Sharon Vazana
 *
 */
public class Vaccinated extends Person {
	
	/**
	 * Constructor
	 * 
	 * @param age             - age input
	 * @param location        - location input
	 * @param settlement      - settlement input
	 * @param vaccinationTime - vaccination time input
	 */
	public Vaccinated(int age, Point location, Settlement settlement) {
		super(age, new Point(location), settlement);
		m_vaccinationTime = Clock.now();
	}
	
	/**
	 * Copy constructor
	 * 
	 * @param v - Vaccinated object
	 */
	public Vaccinated(Vaccinated v) {
		super(v);
		m_vaccinationTime = v.getvaccinationTime();
	}

	@Override
	public double contagionProbability() {
		long days = Clock.calculateDays(m_vaccinationTime);
		if (days < 21)
			return Math.min(1, (0.56 + 0.15 * Math.sqrt(21 - days)));
		else
			return Math.max(0.05, (1.05 / (days - 14)));
	}
	

	@Override
	public String healthCondition() {
		return "Vaccinated";
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
