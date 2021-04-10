package population;

import country.Settlement;
import location.Point;
import virus.IVirus;

public class Convalescent extends Person {
	
	/**
	 * Constractor
	 * @param age - age input
	 * @param location - location input
	 * @param settlement - settlement input
	 * @param virus - virus input
	 */
	public Convalescent(int age, Point location, Settlement settlement, IVirus virus) {
		super(age, location, settlement);
		m_virus = virus;
	}
	
	@Override
	public double contagionProbability(){
		return 0.2; 
	}
	
	@Override
	public  String healthCondition() {
		return "Convalescent";
		
	}
	
	@Override
	public String toString() {
		return super.toString() + "\nStatus: Convalescent."; 
	}
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof Convalescent))
			return false;
		Convalescent s = (Convalescent) o;
		return super.equals(s) && m_virus == s.getVirus();
	}
	
	protected IVirus getVirus() {
		return m_virus;
	}
	
	//Attributes
	private IVirus m_virus;
}
