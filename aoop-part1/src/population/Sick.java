package population;

import country.Settlement;
import location.Point;
import simulation.Clock;
import virus.IVirus;

public class Sick extends Person {
	/**
	 * Constructor
	 * @param age - age input
	 * @param location - location input
	 * @param settlement - settlement input
	 * @param contagiousTime - contagious Time input  
	 * @param virus - virus type input
	 */
	public Sick(int age, Point location, Settlement settlement, IVirus virus) {
		super(age, location, settlement);
		m_contagiousTime = Clock.now();
		m_virus = virus;
	}
	
//	/**
//	 * copy constructor
//	 * 
//	 * @param s - Sick person
//	 */
//	public Sick(Sick s) {
//		super(s.getAge(), s.getLocation(), s.getSettelement());
//		m_contagiousTime = s.getContagiousTime();
//		m_virus = s.getVirus();
//	}

	@Override
	public Person contagion(IVirus virus) {
		throw new UnsupportedOperationException("You can't get sick twice man !!");
	}// where to put catch
	
	@Override
	public double contagionProbability() {
		return 1; 
	}
	
	@Override
	public IVirus getVirusFromPerson() {
		return m_virus;
	}

	@Override
	public String toString() {
		return super.toString() + "Status: Sick.\nThe person got infected at " + m_contagiousTime 
				+ " with the " + m_virus + "virus.";
	}
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof Sick))
			return false;
		Sick s = (Sick) o;
		return super.equals(s) && m_contagiousTime == s.getContagiousTime() 
				&& m_virus.equals(s.getVirus());
	}
	
	/**
	 * 
	 * @return Convalescent object of the current Person
	 */
	public Person recover() {
		return new Convalescent(getAge(), getLocation(), getSettelement(), m_virus);
	}
	
	/**
	 * 
	 * @return if the person will die or not
	 */
	public boolean tryToDie() {
		return m_virus.tryToKill(this); 
	}
	
	/**
	 * 
	 * @return Contagious time
	 */
	public long getContagiousTime() {
		return m_contagiousTime;
	}
	
	/**
	 * 
	 * @return Virus type
	 */
	public IVirus getVirus() {
		return m_virus; 
	}
	
	@Override
	public String healthCondition() {
		return "Sick";
	}
	
	//attributes
	private long m_contagiousTime;
	private IVirus m_virus;

}
