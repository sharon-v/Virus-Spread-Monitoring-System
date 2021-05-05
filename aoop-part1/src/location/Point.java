package location;

/**
 * 
 * @author Yarden Hovav, Sharon Vazana
 *
 */
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
	}

	@Override
	public String toString() {
		return "(" + m_x + ", " + m_y + ")";
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Point))
			return false;
		Point p = (Point)o;
		return m_x == p.m_x && m_y == p.m_y;
	}

	/**
	 * get method
	 * 
	 * @return x value of Point
	 */
	public int getX() {return m_x;}

	/**
	 * get method
	 * 
	 * @return y value of Point
	 */
	public int getY() {return m_y;}

	private int m_x;// x value of Point
	private int m_y;// y value of Point

}
