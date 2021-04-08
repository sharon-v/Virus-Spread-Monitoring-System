package population;
import country.Settlement;
import location.Point;
import simulation.Clock;
import virus.IVirus;

public abstract class Person {
	
	public Person(int age, Point location, Settlement settlement) {
		m_age = age;
		m_location = location;
		m_settlement = settlement;
	}
	
	public abstract double contagionProbability(); // ?? need to return 1 as default
	public Person contagion(IVirus virus) { //??? to complete
		Sick sickPerson = new Sick(m_age, m_location, m_settlement, Clock.now(), virus);
		return sickPerson;
		
	}
	public String toString() {
		return "This person is " + m_age + " he lives in " + m_settlement + ", location is " + m_location+ ".";
	} ///????
	public boolean equals(Object o) {
		if(!(o instanceof Person))
			return false;
		Person p = (Person) o;
		return true; //????
	}
	
	private int m_age;
	private Point m_location;
	private Settlement m_settlement;
}
