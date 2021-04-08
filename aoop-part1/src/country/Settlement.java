package country;

import location.Location;
import location.Size;
import population.Person;

public abstract class Settlement {
	public Settlement(String name, Location location, Person[] people) {
		m_name = name;
		m_location = location;
		m_people = people;
		m_ramzorColor = RamzorColor.GREEN;	// default
	}
	
//	public Settlement(Settlement s) {
//		this()
//	}
	
	@Override
	public String toString() {
		return "settlement name: " + m_name + ", location: " + m_location 
				+ ", num of people: " + m_people.length + "color grade: " + m_ramzorColor;
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Settlement))
			return false;
		Settlement s = (Settlement)o;
		return m_name == s.m_name && m_location == s.m_location;
		}

	/**
	 * 
	 * @return new RamzorColor Grade
	 */
	protected abstract RamzorColor calculateRamzorGrade();
	
	/**
	 * 
	 * @return percentage of sick people in a certain range
	 */
	public double contagiousPercent() {// 0 to 1 max
		//???
	}
	
	/**
	 * 
	 * @return random location in the settlement
	 */
	public Location randomLocation() {
		//???
	}
	
	/**
	 * 
	 * @param p - new person to add
	 * @return true if Person added successfully to settlement
	 */
	public boolean addPerson(Person p) {
		//???
		// use equals no 2 people the same
	}
	
	/**
	 * 
	 * @param p - person to transfer
	 * @param s - new settlement to transfer Person into
	 * @return true if successfully transferred
	 */
	public boolean transferPerson(Person p, Settlement s) {
		return true; // for this part of the project
	}
	
	/**
	 * get method
	 * @return current RamzorColor
	 */
	protected RamzorColor getRamzorColor() {return m_ramzorColor;}
	
	/**
	 * set method
	 * @param r - new RamzorColor of the settlement
	 */
	protected void setRamzorColor(RamzorColor r) {m_ramzorColor = r;}
	
	private final String m_name;
	private final Location m_location;
	private Person[] m_people;
	private RamzorColor m_ramzorColor;
	
}
