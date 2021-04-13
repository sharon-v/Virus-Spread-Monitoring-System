package country;

import location.Location;
import location.Point;
import population.Person;
import virus.BritishVariant;
import virus.ChineseVariant;
import virus.IVirus;
import virus.SouthAfricanVariant;

public abstract class Settlement {
	/**
	 * 
	 * @param name - name of the Settlement
	 * @param location - Location of the Settlement
	 * @param people - Person array of residents in Settlement
	 */
	public Settlement(String name, Location location) {
		m_name = name;
		m_location = location;
		m_people = new Person[0];
		m_ramzorColor = RamzorColor.GREEN;	// default
	}
	
	@Override
	public String toString() {
		return "settlement name: " + m_name + "\nlocation: " + m_location + "\ncolor grade: " + m_ramzorColor
				+ "\nnum of people: " + m_people.length + toStringPeople();
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Settlement))
			return false;
		Settlement s = (Settlement)o;
		return m_name.equals(s.m_name) && m_location.equals(s.m_location);
		}

	/**
	 * calculates the Settlements RamzorColor grade
	 * @return new RamzorColor Grade
	 */
	protected abstract RamzorColor calculateRamzorGrade();
	
	/**
	 * calculates percent of Sick Persons in Settlement
	 * @return percentage of sick people in a certain range
	 */
	public double contagiousPercent() {// 0 to 1 max
		int amountSick = 0;
		for(int i = 0; i < m_people.length; ++i) {
			if (m_people[i].healthCondition().equals("Sick"))
				++amountSick;
		}
		return amountSick / m_people.length;
	}
	
	/**
	 * chooses randomly a Point in Settlement
	 * @return random Point in the settlement
	 */
	public Point randomLocation() {
		int xMax, yMax, xMin, yMin;
		xMin = m_location.getPoint().getX();
		yMax = m_location.getPoint().getY();
		xMax = xMin + m_location.getSize().getWidth();
		yMin = yMax - m_location.getSize().getHeith();
		int randX = xMin + (int)(Math.random() * ((xMax - xMin) + 1));
		int randY = yMin + (int)(Math.random() * ((yMax - yMin) + 1));
		return new Point(randX, randY);
	}
	
	/**
	 * adds a new Person to Settlement
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
		p.setSettlement(this); // change Settlement
		temp[m_people.length] = p;
		m_people = temp;
		return true;
	}
	
	/**
	 * removes a dead Person from Settlement
	 * 
	 * @param p - Person to delete
	 * @return true if Person deleted successfully
	 */
	public boolean removePerson(Person p) {
		if (findPerson(p))
			return false; // person is in settlement
		Person[] temp = new Person[m_people.length - 1]; // decrease 1 in size
		for (int i = 0; i < m_people.length; ++i) {
			if (m_people[i].equals(p))
				temp[i] = m_people[++i];
			else
				temp[i] = m_people[i];
		}
		m_people = temp;
		return true;
	}

	/**
	 * checks if a certain Person is in Settlement
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
	 * calls toSting method for all Persons in Settlement
	 * @return toString of all the residents in the Settlement
	 */
	private String toStringPeople() {
		String str = "\n-- residents -- \n";
		for (int i = 0; i < m_people.length; ++i)
			str += m_people[i].toString() + "\n";
		return str;
	}

	/**
	 * move a Person from one Settlement to another
	 * @param p - person to transfer
	 * @param s - new settlement to transfer Person into
	 * @return true if successfully transferred
	 */
	public boolean transferPerson(Person p, Settlement s) {
		return true; // for this part of the project
	}
	
	/**
	 * infects 1 percent of the population in each of the Settlements
	 */
	public void infectOnePercent() {
		// calculate 1%
		int amount = (int)(m_people.length * 0.01);
		int randomIndex;
		IVirus virus;
		for(int i = 0; i < amount; ++i) {
			randomIndex = (int)(Math.random() * (m_people.length));
			if(randomIndex % 3 == 0)
				virus = new ChineseVariant();
			else if(randomIndex % 3 == 1)
				virus = new BritishVariant();
			else 
				virus = new SouthAfricanVariant();
			try {
				m_people[randomIndex] = m_people[randomIndex].contagion(virus);
			}
			catch(Exception e){
				System.out.println(e);
				System.out.println("infactoneprecent...settlement");
			}
		}
	}

	/**
	 * get method
	 * @return current RamzorColor
	 */
	protected RamzorColor getRamzorColor() {return m_ramzorColor;}
	
	/**
	 * get method
	 * 
	 * @return reference to Persons array
	 */
	public Person[] getPeople() {
		return m_people;
	}

	/**
	 * get method
	 * 
	 * @return Settlement name
	 */
	public String getSettlementName() {
		return m_name;
	}

	/**
	 * set method
	 * @param r - new RamzorColor of the settlement
	 */
	protected void setRamzorColor(RamzorColor r) {m_ramzorColor = r;}
	
	// set people???
	
	
	private final String m_name;// Settlement's name
	private final Location m_location;// Settlement's Location
	private Person[] m_people;// Settlement's residents
	private RamzorColor m_ramzorColor;// Settlement's RamzorColor grade
	
}
