package population;

import country.Settlement;
import location.Point;
import virus.IVirus;

public class Sick extends Person {
	public Sick(int age, Point location, Settlement settlement, long contagiousTime, IVirus virus) {
		super(age, location, settlement);
		m_contagiousTime = contagiousTime;
		m_virus = virus;
		}
	
	@Override
	public Person contagion(IVirus virus) {
		throw new UnsupportedOperationException("You can't get sick twice man !!");
	}// where to put catch
	
	public double contagionProbability() {
		return 1;
	}
	
	public String toString() {
		return "The person got infected at " + m_contagiousTime + " in the " + m_virus + "virus.";
	}
	public boolean equals() {
		//??????????????
	}
	
	public Person recover() {
		Convalescent convalescentPerson = new Convalescent()
	}
	
	private long m_contagiousTime;
	private IVirus m_virus;

}
