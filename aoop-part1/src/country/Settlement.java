package country;

import location.Location;
import location.Point;
import population.Person;

public abstract class Settlement {
	public Settlement(String name, Location location, Person[] people) {
		m_name = name;
		m_location = location;
		m_people = people;
		m_ramzorColor = RamzorColor.GREEN;	// default
	}
	
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
		int amountSick = 0;
		for(int i = 0; i < m_people.length; ++i) {
			if(m_people[i].healthCondition() == "Sick")
				++amountSick;
		}
		return amountSick / m_people.length;
	}
	
	/**
	 * 
	 * @return random Point in the settlement
	 */
	public Point randomLocation() {
		int xMax, yMax, xMin, yMin;
		xMax = xMin = m_people[0].getLocation().getX();
		yMax = yMin = m_people[0].getLocation().getY();
		for(int i = 1; i < m_people.length; ++i) {
			int temp = m_people[i].getLocation().getX();
			if(temp > xMax)
				xMax = temp;
			else if(temp < xMin)
				xMin = temp;
			temp = m_people[i].getLocation().getY();
			if(temp > yMax)
				yMax = temp;
			else if(temp < yMin)
				yMin = temp;
		}
		int randX = xMin + (int)(Math.random() * ((xMax - xMin) + 1));
		int randY = yMin + (int)(Math.random() * ((yMax - yMin) + 1));
		return new Point(randX, randY);
	}
	
	/**
	 * 
	 * @param p - new person to add
	 * @return true if Person added successfully to settlement
	 */
	public boolean addPerson(Person p) {
		// use equals no 2 people the same
		if(findPerson(p))
			return false;	// person is already in settlement
		Person[] temp = new Person[m_people.length + 1];
		for(int i = 0; i < m_people.length; ++i) {
			temp[i] = m_people[i];
		}
		temp[m_people.length] = p;
		m_people = temp;
		return true;
	}
	
	/**
	 * 
	 * @param p - a Person to search
	 * @return true if the person already exists in the settlement
	 */
	private boolean findPerson(Person p) {
		for(int i = 0; i < m_people.length; ++i) {
			if(m_people[i].equals(p))
				return true;
		}
		return false;
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
