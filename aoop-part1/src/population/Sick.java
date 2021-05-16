package population;

import country.Settlement;
import location.Point;
import simulation.Clock;
import virus.IVirus;

/**
 * 
 * @author Yarden Hovav, Sharon Vazana
 *
 */
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
	
	/**
	 * Copy constructor
	 * 
	 * @param s - Sick object
	 */
	public Sick(Sick s) {
		super(s);
		m_contagiousTime = s.m_contagiousTime;
		m_virus = s.m_virus;
	}

	@Override
	public Person contagion(IVirus virus) {
		throw new UnsupportedOperationException("You can't get sick twice !!");
	}
	
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
		return super.toString() + "\tstatus: Sick\t\tcontagion time: " + m_contagiousTime + "\tvirus: " + m_virus;
	}
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof Sick))
			return false;
		Sick s = (Sick) o;
		return super.equals(s) && m_contagiousTime == s.getContagiousTime() 
				&& m_virus.equals(s.getVirusFromPerson());
	}

	@Override
	public String healthCondition() {
		return "Sick";
	}

	/**
	 * creates a copy of the Person as a Convalescent object
	 * 
	 * @return Convalescent object of the current Person
	 */
	public Person recover() {
		Convalescent newConv = new Convalescent(getAge(), getLocation(), getSettelement(), m_virus);
		getSettelement().removePerson(this);
		getSettelement().addPerson(newConv);
		return newConv;
	}
	
	/**
	 * tries to kill a Person
	 * 
	 * @return true if the person will die
	 */
	public boolean tryToDie() {
		return m_virus.tryToKill(this); 
	}
	
	/**
	 * get method
	 * 
	 * @return Contagious time
	 */
	public long getContagiousTime() {
		return m_contagiousTime;
	}
	
	
	//attributes
	private long m_contagiousTime;// time of infection
	private IVirus m_virus;// IVirus object of this Person

}
