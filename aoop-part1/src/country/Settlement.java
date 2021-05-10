package country;

import java.util.Random;

import location.Location;
import location.Point;
import population.Healthy;
import population.Person;
import population.Sick;
import simulation.Clock;
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
		m_sickPeople = new Sick[0];
		m_numOfDeceased = 0;
	}

	@Override
	public String toString() {
		return "settlement name: " + m_name + "\nlocation: " + m_location + "\ncolor grade: " + m_ramzorColor
				+ "\nnum of people: " + getNumOfPeople() + "\nnum of sick: " + m_sickPeople.length + toStringPeople();
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
		Random ran = new Random();
		xMin = m_location.getPoint().getX();
		yMin = m_location.getPoint().getY();
		xMax = xMin + m_location.getSize().getWidth();
		yMax = yMin + m_location.getSize().getHeith();
//		int randX = xMin + (int)(Math.random() * ((xMax - xMin) + 1));
//		int randY = yMin + (int)(Math.random() * ((yMax - yMin) + 1));
		int randX = ran.nextInt(xMax - xMin +1) + xMin;
		int randY = ran.nextInt(yMax - yMin +1) + yMin;
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
		if (p.healthCondition().equals("Sick")) {
			Sick[] temp = new Sick[m_sickPeople.length + 1];
			for (int i = 0; i < m_sickPeople.length; ++i) {
				temp[i] = m_sickPeople[i];
			}
			temp[m_sickPeople.length] = (Sick) p;
			m_sickPeople = temp;
		}
		else {
			Person[] temp = new Person[m_healthyPeople.length + 1];
			for (int i = 0; i < m_healthyPeople.length; ++i) {
				temp[i] = m_healthyPeople[i];
			}
			temp[m_healthyPeople.length] = p;
			m_healthyPeople = temp;
		}
		p.setSettlement(this); // change Settlement
		setRamzorColor(calculateRamzorGrade());
		return true;
	}

	/**
	 * removes a dead Person from Settlement
	 * 
	 * @param p - Person to delete
	 * @return true if Person deleted successfully
	 */
	public boolean removePerson(Person p) {
		if (!findPerson(p))
			return false; // person is not in settlement
		if (p.healthCondition().equals("Sick")) {
			Sick[] temp = new Sick[m_sickPeople.length - 1]; // decrease 1 in size
			int j = 0;
			for (int i = 0; i < m_sickPeople.length; ++i) {
				if (!(m_sickPeople[i].equals(p))) {
					temp[j] = m_sickPeople[i];
					++j;
				}
			}
			m_sickPeople = temp;
		}
		else
		{
			int j = 0;
			Person[] temp = new Person[m_healthyPeople.length - 1]; // decrease 1 in size
			for (int i = 0; i < m_healthyPeople.length; ++i) {
				if (!(m_healthyPeople[i].equals(p))) {
					temp[j] = m_healthyPeople[i];
					++j;
				}
			}
			m_healthyPeople = temp;
		}
		setRamzorColor(calculateRamzorGrade());
		return true;
	}

	/**
	 * checks if a certain Person is in Settlement
	 * @param p - a Person to search
	 * @return true if the person already exists in the settlement
	 */
	private boolean findPerson(Person p) {
		if(m_sickPeople.length != 0) {
			if(p.healthCondition().equals("Sick")) {
				for (int i = 0; i < m_sickPeople.length; ++i) {
					if (m_sickPeople[i].equals(p))
						return true;
				}
			}
		}
		if(m_healthyPeople.length != 0) {
			for (int i = 0; i < m_healthyPeople.length; ++i) {
				if (m_healthyPeople[i].equals(p))
					return true;
			}
		}
		return false;
	}
	
	/**
	 * run over the sick people and try to kill them
	 */
	public void attemptedMurder() {
		for(int i = 0; i<m_sickPeople.length;++i) {
			if(m_sickPeople[i].getVirusFromPerson().tryToKill(m_sickPeople[i])) {
				removePerson(m_sickPeople[i]);
				++m_numOfDeceased;
			}			
		}
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
	private boolean transferPerson(Person p, Settlement s) {
		if(this.equals(s))
			return false;
		if (s.m_maxPopulation <= getNumOfPeople())
			return false;
		Random ran = new Random();
		if ((getRamzorColor().getTransferProb() * s.getRamzorColor().getTransferProb()) >= ran.nextDouble()) // [0, 1) 
			return false;
		if (removePerson(p)) {
			s.addPerson(p);
			p.setSettlement(s);
			return true; // for this part of the project
		}
		return false;
	}

	/**
	 * tries to transfer random 3% of the Settlement to a random Settlement
	 * 
	 * @param randomSettlement - random Settlement to transfer to
	 */
	public void randomTransfer(Settlement randomSettlement) {
		// try transfer 3% from settlement
		int size = getNumOfPeople();
		Person[] temp = new Person[size];
		for (int i = 0; i < m_healthyPeople.length; ++i) {
			temp[i] = m_healthyPeople[i];
		}
		for (int i = 0; i < m_sickPeople.length; ++i) {
			temp[m_healthyPeople.length + i] = m_sickPeople[i];
		}
		Random ran = new Random();
		int random;
		int threePercent = (int) (size * 0.03);
		for (int i = 0; i < threePercent; ++i) {
			random = ran.nextInt(size);
			transferPerson(temp[random], randomSettlement);
		}
	}
	
	
	/**
	 * vaccinate the population
	 */
	public void vaccineTime() {
		int index = 0 ;
		while(getVaccineDoses() > 0 && index != m_healthyPeople.length) {
			if (m_healthyPeople[index].healthCondition().equals("Healthy")) {
				((Healthy) m_healthyPeople[index]).vaccinate();
				--m_vaccineDoses;
			}
			++index;
		}
	}
	
	/**
	 * infects 1 percent of the population in each of the Settlements
	 */
	public void infectPercent(double num) {
		int amountToInfect = (int) (m_healthyPeople.length * num);
		int randomIndex;
		Random ran = new Random();
		IVirus virus;
		for(int i = 0; i < amountToInfect; ++i) {
			randomIndex = ran.nextInt(m_healthyPeople.length);
			if (randomIndex % 3 == 0)
				virus = new ChineseVariant();
			else if (randomIndex % 3 == 1)
				virus = new BritishVariant();
			else
				virus = new SouthAfricanVariant();
			try {
				m_healthyPeople[randomIndex] = m_healthyPeople[randomIndex].contagion(virus);
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	/**
	 * 
	 * @return a random Settlement from the Connections array
	 */
	public Settlement randomConnection() {
		if (m_connectedSettlements.length == 0)
			return null;
		Random ran = new Random();
		return m_connectedSettlements[ran.nextInt(m_connectedSettlements.length)];

	}
	/**
	 * one simulation operation
	 */
	public void simulation() {
		int tempIndex = (int) (m_sickPeople.length * 0.2);
		Random ran = new Random();
		for (int j = 0; j < tempIndex; ++j) {// run over the population of each settlement
			randomContagion(m_sickPeople[ran.nextInt(tempIndex)]);
		}
	}

	/**
	 * chooses randomly six people to try to infect for each sick Person currently
	 * in the Settlement
	 * 
	 * @param sickPerson - array of sick people
	 */
	public void randomContagion(Person sickPerson) {
		IVirus virus = null;
		for (int i = 0; i < 3; ++i) {
			if (m_healthyPeople.length == 0)
				return;
			Random ran = new Random();
			int randomIndex = ran.nextInt(m_healthyPeople.length);
			virus = sickPerson.getVirusFromPerson();
			if (virus.getVars().size() != 0) {
				virus = sickPerson.contagionVariants(virus.getVars());
				if (virus.tryToContagion(sickPerson, m_healthyPeople[randomIndex])) {
					m_healthyPeople[randomIndex].contagion(virus);
//					System.out.println("contagion");
				}
			}
		}
	}

	/**
	 * 
	 * @return point of the middel of the settlements
	 */
	public Point middelOfSettlement() {
		int x = (int)(m_location.getPoint().getX() + (m_location.getSize().getWidth()/ 2)) ;
		int y = (int)(m_location.getPoint().getY() + (m_location.getSize().getHeith()/2)); 
		return new Point(x, y);
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

	/**
	 *  if past 25 days since the sick person got infected - recovery him
	 */
	public void sickToConvalescent() {
		for (int i = 0; i < m_sickPeople.length; ++i) {
			if (Clock.calculateDays(m_sickPeople[i].getContagiousTime()) > m_recoveryTime) {
				m_sickPeople[i].recover();
			}

		}
	}

	/**
	 * 
	 * @return middle Points connections of a settlement array 
	 */
	public Point[] conectionsPoints() {
		Point[] settlPoints = new Point[0];
		for(int i=0 ; i< m_connectedSettlements.length; ++i) {
			Point middel = m_connectedSettlements[i].middelOfSettlement();
			settlPoints = addToConectionsPoints(settlPoints, middel);
		}
		return settlPoints;
	}

	/**
	 * 
	 * @param arr - array of Point
	 * @param p - new Point
	 * @return new Point array with the new Point
	 */
	private Point[] addToConectionsPoints(Point[] arr, Point p) {
		Point[] temp = new Point[arr.length + 1];
		for(int i=0 ; i<arr.length;++i) 
			temp[i] = arr[i];
		temp[arr.length] = p;
		return temp;
	}



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
	protected void setRamzorColor(RamzorColor r) {
		m_ramzorColor = r;
	}

	/**
	 * 
	 * @return String of Settlement type
	 */
	public abstract String getSettlementType();
	
	/**
	 * 
	 * @return the number of the vaccines in the settlement
	 */
	public int getVaccineDoses() {
		return m_vaccineDoses;
	}

	/**
	 * set the number of the deceased
	 */
	public void setNumOfDeceased() {
		++m_numOfDeceased;
	}
	
	/**
	 * 
	 * @return the number of the deceased
	 */
	public int getNumOfDeceased() {
		return m_numOfDeceased;
	}
	
	/**
	 * set the number of the vaccine doses
	 * @param amount - the new number of the vaccine doses
	 */
	public void setVaccineDoses(int amount) {
		if (amount >= 0)
			m_vaccineDoses += amount;
	}
	
	/**
	 * 
	 * @return the location of the settlement
	 */
	public Location getLocation() {
		return new Location(m_location);
	}

	private static final int m_recoveryTime = 25; //the number of days that after this the sick person recovery
	private final String m_name;// Settlement's name
	private final Location m_location;// Settlement's Location
	private Person[] m_healthyPeople;// Settlement's healthy residents
	private RamzorColor m_ramzorColor;// Settlement's RamzorColor grade
	private int m_maxPopulation;// max residents in settlement
	private int m_vaccineDoses; // num of vaccine doses
	private Settlement[] m_connectedSettlements;// all the connections to current settlement
	private Sick[] m_sickPeople;// Settlement's sick residents
	private int m_numOfDeceased;// counts deaths in Settlement
}
