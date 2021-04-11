package population;
import country.Settlement;
import location.Point;
import simulation.Clock;
import virus.IVirus;

public abstract class Person {
	
	/**
	 * Constructor
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
	 * Copy constructor
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
	 * @return the health condition of the person
	 */
	public abstract String healthCondition();
	
	/**
	 * 
	 * @param virus - type of virus input
	 * @return Sick object of the current Person
	 */
	public Person contagion(IVirus virus) { 
		return new Sick(m_age, m_location, m_settlement, virus);	
	}
	
	
	@Override
	public String toString() {
		return "This person is " + m_age + " he lives in " + m_settlement + ", location is " + m_location; 
	} 
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof Person))
			return false;
		Person p = (Person) o;
		return p.getAge() == m_age && m_location.equals(p.getLocation());
	}
	
	/**
	 * 
	 * @param p - Person object
	 * @return The distance between to persons
	 */
	public double distance(Person p) {
		return Math.sqrt(Math.pow(m_location.getX() - p.m_location.getX(), 2) 
				+ Math.pow(m_location.getY() - p.m_location.getY(), 2));
	}
	
	/**
	 * 
	 * @return The age of the current Person
	 */
	public int getAge() {
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
	
	/**
	 * 
	 * @param s - new Settlement
	 */
	public void setSettlement(Settlement s) {
		m_settlement = s;
	}
	
	//attributes
	private int m_age;
	private Point m_location;
	private Settlement m_settlement;
}
