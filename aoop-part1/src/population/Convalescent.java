package population;

import country.Settlement;
import location.Point;
import virus.IVirus;

public class Convalescent extends Person {
	public Convalescent(int age, Point location, Settlement settlement, IVirus virus) {
		super(age, location, settlement);
		m_virus = virus;
	}
	
	private IVirus m_virus;

}
