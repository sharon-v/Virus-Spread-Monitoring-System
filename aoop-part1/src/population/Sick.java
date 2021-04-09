package population;

import country.Settlement;
import location.Point;
import virus.IVirus;

public class Sick extends Person {
	/**
	 * Constractor
	 * @param age - age input
	 * @param location - location input
	 * @param settlement - settlement input
	 * @param contagiousTime - contagious Time input  
	 * @param virus - virus type input
	 */
	public Sick(int age, Point location, Settlement settlement, long contagiousTime, IVirus virus) {
		super(age, location, settlement);
		m_contagiousTime = contagiousTime;
		m_virus = virus;
		}
	
	@Override
	public Person contagion(IVirus virus) {
		throw new UnsupportedOperationException("You can't get sick twice man !!");
	}// where to put catch
	
	@Override
	public double contagionProbability() {
		return 1; //??
	}
	
	@Override
	public String toString() {
		return super.toString() + "Status: Sick.\nThe person got infected at " + m_contagiousTime + " in the " + m_virus + "virus.";
	}
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof Sick))
			return false;
		Sick s = (Sick) o;
		return super.equals(s) && m_contagiousTime == s.getContagiousTime() && m_virus == s.getVirus();
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
		return m_virus.tryToKill(this); //??
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
	protected IVirus getVirus() {
		return m_virus;
	}
	
	@Override
	public String healthCondition() {
		return "Sick";
	}
	
	private long m_contagiousTime;
	private IVirus m_virus;

}
