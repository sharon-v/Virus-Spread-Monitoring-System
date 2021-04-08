package country;

import location.Location;
import location.Size;
import population.Person;

public class Settlement {// abstract???
	public Settlement(String name, Location location, Person[] people) {
		m_name = name;
		m_location = location;
		m_people = people;
		m_ramzorColor = RamzorColor.GREEN;	// default
	}
	
	public Settlement(Settlement s) {
		this()
	}

	public String toString() {
		return "settlement name: " + m_name + ", location: " + m_location 
				+ ", num of people: " + m_people.length + "color grade: " + m_ramzorColor;
	}
//	
//	public boolean equals(Object o) {
//		if (!(o instanceof Size))
//			return false;
//		Size s = (Size)o;
//		return m_width == s.m_width && m_height == s.m_height;
//		}

	protected RamzorColor calculateRamzorGrade() {// calculates new color
		// abstract??? protected???
	}
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
	
	private String m_name;
	private Location m_location;
	private Person[] m_people;
	private RamzorColor m_ramzorColor;
	
}
