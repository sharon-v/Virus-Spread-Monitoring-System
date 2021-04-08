package location;

public class Point {
	/**
	 * ctor
	 * @param x - input coordinate
	 * @param y - input coordinate
	 */
	public Point(int x, int y) {
		m_x = x;
		m_y = y;
	}

	public String toString() {
		String point = "(" + m_x + ", " + m_y + ")"; 
		return point;
	}
	
	public boolean equals(Object o) {
		if (!(o instanceof Point))
			return false;
		Point p = (Point)o;
		return m_x == p.m_x && m_y == p.m_y;
		}
	
	private int m_x;
	private int m_y;
	
}
