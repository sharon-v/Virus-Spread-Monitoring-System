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
public abstract class Person {
	
	/**
	 * Constructor
	 * 
	 * @param age        - age input
	 * @param location   - location input
	 * @param settlement - settlement input
	 */
	public Person(int age, Point location, Settlement settlement) {
		m_age = age; // abs???
		m_location = new Point(location);
		m_settlement = settlement;
		Clock.nextTick();
	}
	
	/**
	 * Copy constructor
	 * @param p - Person input
	 */
	public Person(Person p) {
		this(p.getAge(), p.getLocation(), p.getSettelement());
	}
	
	/**
	 * the probability of a certain Person to get infected
	 * 
	 * @return probability of infection
	 */
	public abstract double contagionProbability(); // ?? need to return 1 as default
	
	/**
	 * current status of Person
	 * 
	 * @return the health condition of the person
	 */
	public abstract String healthCondition();
	
	/**
	 * get method
	 * 
	 * @return the IVirus object of the Person
	 */
	public IVirus getVirusFromPerson() {
		return null;
	}

	/**
	 * infects the Person
	 * 
	 * @param virus - type of virus input
	 * @return Sick object of the current Person
	 */
	public Person contagion(IVirus virus) { 
		return new Sick(m_age, m_location, m_settlement, virus);	
	}
	
	@Override
	public String toString() {
		return "age: " + m_age + "  \tsettlement: " + m_settlement.getSettlementName() + "\tlocation: " + m_location;
	} 
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof Person))
			return false;
		Person p = (Person) o;
		return p.getAge() == m_age && m_location.equals(p.getLocation());
	}
	
	/**
	 * calculates distance
	 * 
	 * @param p - Person object
	 * @return The distance between to persons
	 */
	public double distance(Person p) {
		return Math.sqrt(Math.pow(m_location.getX() - p.m_location.getX(), 2) 
				+ Math.pow(m_location.getY() - p.m_location.getY(), 2));
	}
	
	/**
	 * get method
	 * 
	 * @return The age of the current Person
	 */
	public int getAge() {
		return m_age;
	}
	
	/**
	 * get method
	 * 
	 * @return a copy of the current Person's location (Point type)
	 */
	public Point getLocation() {
		return new Point(m_location);
	}
	
	/**
	 * get method
	 * 
	 * @return a copy of the current Person's Settlement
	 */
	protected Settlement getSettelement() {
		return m_settlement; // ???
	}
	
	/**
	 * set method
	 * 
	 * @param s - new Settlement
	 */
	public void setSettlement(Settlement s) {
		if (m_settlement.removePerson(this))
			m_settlement = s;
	}
	

	//attributes
	private int m_age;// Person's age
	private Point m_location;// Person's location
	private Settlement m_settlement;// Person's settlement
}
