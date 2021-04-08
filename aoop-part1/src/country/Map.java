package country;

public class Map {
	public Map() {
		// ???
	}
	
	public String toString() {
//		return "point: " + m_point + ", size: " + m_size;
	}

//	public boolean equals(Object o) {
//		if (!(o instanceof Map))
//			return false;
//		Map l = (Map)o;
//		return m_point.equals(l.m_point) && m_size.equals(l.m_size);
//		}

	public void addSettlement(Settlement s) {
		Settlement[] temp = new Settlement[m_settlement.length + 1];
		
		for(int i = 0; i < m_settlement.length; ++i) {
			temp[i] = m_settlement[i];
		}
		temp[m_settlement.length - 1] = s;
	}
	
	private Settlement m_settlement[];
}
