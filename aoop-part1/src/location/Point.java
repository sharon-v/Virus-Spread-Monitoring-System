package location;


public class Point {
	/**
	 * constructor
	 * @param x - input coordinate
	 * @param y - input coordinate
	 */
	public Point(int x, int y) {
		m_x = x;
		m_y = y;
	}
	/**
	 * copy constructor
	 * @param p - other Point type object
	 */
	public Point(Point p) {
		this(p.getX(), p.getY());
//		this.m_x = p.getX();
//		this.m_y = p.getY();
	}

	public String toString() {
		return "(" + m_x + ", " + m_y + ")";
	}
	
	public boolean equals(Object o) {
		if (!(o instanceof Point))
			return false;
		Point p = (Point)o;
		return m_x == p.m_x && m_y == p.m_y;
		}
	
	public int getX() {return m_x;}
	public int getY() {return m_y;}
	
	private int m_x;
	private int m_y;
	
}
