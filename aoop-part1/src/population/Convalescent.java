package population;

import country.Settlement;
import location.Point;
import virus.IVirus;

/**
 * 
 * @author Yarden Hovav, Sharon Vazana
 *
 */
public class Convalescent extends Person {
	
	/**
	 * Constructor
	 * 
	 * @param age        - age input
	 * @param location   - location input
	 * @param settlement - settlement input
	 * @param virus      - virus input
	 */
	public Convalescent(int age, Point location, Settlement settlement, IVirus virus) {
		super(age, location, settlement);
		m_virus = virus;
	}
	
	/**
	 * Copy constructor
	 * 
	 * @param c - Convalescent Object
	 */
	public Convalescent(Convalescent c) {
		super(c);
		m_virus = c.m_virus;
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
	public IVirus getVirusFromPerson() {
		return m_virus;
	}

	@Override
	public String toString() {
		return super.toString() + "\tstatus: Convalescent\tvirus: " + m_virus;
	}
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof Convalescent))
			return false;
		Convalescent s = (Convalescent) o;
		return super.equals(s) && m_virus.equals(s.getVirus());
	}
	
	/**
	 * get method
	 * 
	 * @return IVirus object of this Person
	 */
	protected IVirus getVirus() {
		return m_virus;
	}
	
	//Attributes
	private IVirus m_virus;// IVirus object of this Person
}
