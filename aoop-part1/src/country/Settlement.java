package country;

import location.Location;
import location.Point;
import population.Person;
import virus.BritishVariant;
import virus.ChineseVariant;
import virus.IVirus;
import virus.SouthAfricanVariant;

/**
 * 
 * @author Yarden Hovav, Sharon Vazana
 *
 */
public abstract class Settlement {
	/**
	 * 
	 * @param name - name of the Settlement
	 * @param location - Location of the Settlement
	 * @param people - Person array of residents in Settlement
	 */
	public Settlement(String name, Location location, int population) {
		m_name = name;
		m_location = new Location(location);
		m_healthyPeople = new Person[0];
		m_ramzorColor = RamzorColor.GREEN;	// default
		m_maxPopulation = population;
		m_vaccineDoses = 0;
		m_connectedSettlements = new Settlement[0];
		m_sickPeople = new Person[0];
		m_numOfDeceased = 0;
	}
	
	@Override
	public String toString() {
		return "settlement name: " + m_name + "\nlocation: " + m_location + "\ncolor grade: " + m_ramzorColor
				+ "\nnum of people: " + getNumOfPeople() + toStringPeople();
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
		if (getNumOfPeople() == 0)
			return 0;
		return (m_sickPeople.length / (double) getNumOfPeople());
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
		yMin = yMax + m_location.getSize().getHeith();
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
		Person[] arr = null;
		if (p.healthCondition().equals("Sick"))
			arr = m_sickPeople;
		else
			arr = m_healthyPeople;
		Person[] temp = new Person[arr.length + 1];
		for (int i = 0; i < arr.length; ++i) {
			temp[i] = arr[i];
		}
		p.setSettlement(this); // change Settlement
		temp[arr.length] = p;
		if (arr == m_sickPeople)
			m_sickPeople = temp;
		else
			m_healthyPeople = temp;
		return true;
	}
	
	/**
	 * removes a dead Person from Settlement
	 * 
	 * @param p - Person to delete
	 * @return true if Person deleted successfully
	 */
	public boolean removePerson(Person p) {
		Person[] arr = null;
		if (!findPerson(p))
			return false; // person is not in settlement
		if (p.healthCondition().equals("Sick"))
			arr = m_sickPeople;
		else
			arr = m_healthyPeople;
		int j = 0;
		Person[] temp = new Person[arr.length - 1]; // decrease 1 in size
		for (int i = 0; i < arr.length; ++i) {
			if (!(arr[i].equals(p))) {
				temp[j] = arr[i];
				++j;
			}
		}
		if (arr == m_sickPeople)
			m_sickPeople = temp;
		else
			m_healthyPeople = temp;
		return true;
	}

	/**
	 * checks if a certain Person is in Settlement
	 * @param p - a Person to search
	 * @return true if the person already exists in the settlement
	 */
	private boolean findPerson(Person p) {
		for(int i = 0; i < m_healthyPeople.length; ++i) {
			if(m_healthyPeople[i].equals(p))
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
		for (int i = 0; i < m_healthyPeople.length; ++i)
			str += m_healthyPeople[i].toString() + "\n";
		for (int i = 0; i < m_sickPeople.length; ++i)
			str += m_sickPeople[i].toString() + "\n";
		return str;
	}

	/**
	 * move a Person from one Settlement to another
	 * @param p - person to transfer
	 * @param s - new settlement to transfer Person into
	 * @return true if successfully transferred
	 */
	public boolean transferPerson(Person p, Settlement s) {
		if (s.m_maxPopulation == getNumOfPeople())
			return false;
		if (getRamzorColor().getTransferProb() * s.getRamzorColor().getTransferProb() < Math.random()) // [0, 1)
			return false;
		if (removePerson(p)) {
			s.addPerson(p);
			p.setSettlement(s);
			return true; // for this part of the project
		}
		return false;
	}
	
	/**
	 * infects 1 percent of the population in each of the Settlements
	 */
	public void infectOnePercent() {
		// calculate 1%
		int amount = (int) (m_healthyPeople.length * 0.01);
		int randomIndex;
		IVirus virus;
		for(int i = 0; i < amount; ++i) {
			randomIndex = (int)(Math.random() * (m_healthyPeople.length));
			if(!(m_healthyPeople[randomIndex].healthCondition().equals("Sick"))) {
				if(randomIndex % 3 == 0)
					virus = new ChineseVariant();
				else if(randomIndex % 3 == 1)
					virus = new BritishVariant();
				else 
					virus = new SouthAfricanVariant();
				try {
					m_healthyPeople[randomIndex] = m_healthyPeople[randomIndex].contagion(virus);
				}
				catch(Exception e){
					System.out.println(e);
				}
			}
			else
				--i;
		}
	}
	
//	/**
//	 * one simulation operation
//	 */
//	public void simulation() throws Exception {
//		int[] tempIndex = new int[0];
//			for (int j = 0; j < m_healthyPeople.length; ++j) {// run over the population of each settlement
//				if (m_healthyPeople[j].healthCondition().equals("Sick")) {
//					if(!(searchIndex(tempIndex, j))) {
//						tempIndex = randomContagion(m_healthyPeople[j], tempIndex);
//					}
//				}
//			}
//		}

	/**
	 * one simulation operation
	 */
	public void simulation() {
		int tempIndex = m_sickPeople.length;
		for (int j = 0; j < tempIndex; ++j) {// run over the population of each settlement
			randomContagion(m_sickPeople[j]);
		}
	}

	/**
	 * chooses randomly six people to try to infect for each sick Person currently
	 * in the Settlement
	 * 
	 * @param sickPerson - array of sick people
	 */
	private void randomContagion(Person sickPerson) {
		for (int i = 0; i < 6; ++i) {
			int randomIndex = (int) (Math.random() * (m_healthyPeople.length));
			if (sickPerson.getVirusFromPerson().tryToContagion(sickPerson, m_healthyPeople[randomIndex])) {
				m_healthyPeople[randomIndex].contagion(sickPerson.getVirusFromPerson());
			}
		}
	}


	/**
	 * 
	 * @param s - settlement to connect to the current settlement
	 */
	public void addNewConnection(Settlement s) {
		Settlement[] temp = new Settlement[m_connectedSettlements.length + 1];
		for (int i = 0; i < m_connectedSettlements.length; ++i) {
			temp[i] = m_connectedSettlements[i];
		}
		temp[m_connectedSettlements.length] = s;
		m_connectedSettlements = temp;
	}

//	/**
//	 * updates array of new sick Persons indexes
//	 * 
//	 * @param tempIndex
//	 * @param index
//	 */
//	private int[] addTempIndex(int[] tempIndex, int index) {
//		int[] temp = new int[tempIndex.length + 1];
//		for(int i = 0; i < tempIndex.length; ++i)
//			temp[i] = tempIndex[i];
//		temp[tempIndex.length] = index;
//		return temp;
//	}
	
//	/**
//	 * searches for a certain index in array
//	 * 
//	 * @param tempIndex
//	 * @param index
//	 * @return
//	 */
//	private boolean searchIndex(int[] tempIndex, int index) {
//		for(int i = 0; i < tempIndex.length; ++i)
//			if(tempIndex[i] == index)
//				return true;
//		return false;
//	}
	
//	/**
//	 * chooses randomly six people to try to infect for each sick Person currently
//	 * in the Settlement
//	 * 
//	 * @param people     - array of Persons
//	 * @param sickPerson - reference to a Sick Person
//	 * @param tempIndex  - index of a new Sick Person
//	 * @throws Exception - thrown if we try to infect a Sick Person
//	 */
//	private int[] randomContagion( Person sickPerson, int[] tempIndex) throws Exception {
//		for (int i = 0; i < 6; ++i) {
//			int randomIndex = (int)(Math.random() * (m_healthyPeople.length));
//			if (sickPerson.getVirusFromPerson() == null)
//				throw new Exception("this person isn't sick...");
//			if (sickPerson.getVirusFromPerson().tryToContagion(sickPerson, m_healthyPeople[randomIndex])) {
//				try {
//					m_healthyPeople[randomIndex] = m_healthyPeople[randomIndex].contagion(sickPerson.getVirusFromPerson());
//					tempIndex = addTempIndex(tempIndex, randomIndex);
//				}catch(Exception e) {
//					System.out.println(e);
//				}
//			}
//		}
//		return tempIndex;
//	}



	/**
	 * get method
	 * @return current RamzorColor
	 */
	public RamzorColor getRamzorColor() {
		return m_ramzorColor;
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
	 * 
	 * @return total population in settlement
	 */
	public int getNumOfPeople() {
		return m_sickPeople.length + m_healthyPeople.length;
	}

	/**
	 * set method
	 * @param r - new RamzorColor of the settlement
	 */
	protected void setRamzorColor(RamzorColor r) {m_ramzorColor = r;}
	
	/**
	 * 
	 * @return String of Settlement type
	 */
	public abstract String getSettlementType();
	
	public int getVaccineDoses() {
		return m_vaccineDoses;
	}

	public void setNumOfDeceased() {
		++m_numOfDeceased;
	}

	public int getNumOfDeceased() {
		return m_numOfDeceased;
	}

	private final String m_name;// Settlement's name
	private final Location m_location;// Settlement's Location
	private Person[] m_healthyPeople;// Settlement's healthy residents
	private RamzorColor m_ramzorColor;// Settlement's RamzorColor grade
	private int m_maxPopulation;// max residents in settlement
	private int m_vaccineDoses; // num of vaccine doses
	private Settlement[] m_connectedSettlements;// all the connections to current settlement
	private Person[] m_sickPeople;// Settlement's sick residents
	private int m_numOfDeceased;// counts deaths in Settlement
}
