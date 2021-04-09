package population;
import country.Settlement;
import location.Point;
import simulation.Clock;
import virus.IVirus;

public abstract class Person {
	
	/**
	 * Constractor
	 * @param age - age input 
	 * @param location - location input
	 * @param settlement - input settlement
	 */
	public Person(int age, Point location, Settlement settlement) {
		m_age = age;
		m_location = location;
		m_settlement = settlement;
	}
	
	/**
	 * Copy constractor
	 * @param p - Person input
	 */
	public Person(Person p) {
		this(p.getAge(), p.getLocation(), p.getSettelement());
	}
	
	/**
	 * 
	 * @return probability of infection
	 */
	public abstract double contagionProbability(); // ?? need to return 1 as default
	
	/**
	 * 
	 * @param virus - type of virus input
	 * @return Sick object of the current Person
	 */
	public Person contagion(IVirus virus) { 
		return new Sick(m_age, m_location, m_settlement, Clock.now(), virus);
		
		
	}
	
	public String toString() {
		return "This person is " + m_age + " he lives in " + m_settlement + ", location is " + m_location+ ".";
	} 
	
	public boolean equals(Object o) {
		if(!(o instanceof Person))
			return false;
		Person p = (Person) o;
		return p.getAge() == m_age && m_location.equals(p.getLocation());
	}
	
	protected abstract String healthCondition();
	
	/**
	 * 
	 * @return The age of the current Person
	 */
	protected int getAge() {
		return m_age;
	}
	
	/**
	 * 
	 * @return The location of the current Person
	 */
	public Point getLocation() {
		return new Point(m_location); //??
	}
	
	/**
	 * 
	 * @return The settlement of the current Person
	 */
	protected Settlement getSettelement() {
		return m_settlement; //???
	}
	
	private int m_age;
	private Point m_location;
	private Settlement m_settlement;
}
