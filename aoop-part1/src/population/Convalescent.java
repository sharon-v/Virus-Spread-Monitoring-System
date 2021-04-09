package population;

import country.Settlement;
import location.Point;
import virus.IVirus;

public class Convalescent extends Person {
	public Convalescent(int age, Point location, Settlement settlement, IVirus virus) {
		super(age, location, settlement);
		m_virus = virus;
	}
	
	public double contagionProbability(){
		return 0.2; //????
	}
	
	public  String healthCondition() {
		return "Convalescent";
		
	}
	
	public String toString() {
		return super.toString() + "\nStatus: Convalescent.";
		
	}
	
	private IVirus m_virus;
	// think about adding another variable of contagion Probability
}
