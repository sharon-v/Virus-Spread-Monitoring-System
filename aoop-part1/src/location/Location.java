package location;

/**
 * 
 * @author Yarden Hovav, Sharon Vazana
 *
 */
public class Location {
	/**
	 * constructor
	 * @param p - point input
	 * @param s - size input
	 */
	public Location(Point p, Size s) {
		m_point = new Point(p);
		m_size = new Size(s);
	}
	
	/**
	 * copy constructor
	 * 
	 * @param location - Location input
	 */
	public Location(Location location) {
		this(new Point(location.getPoint()), new Size(location.getSize()));
	}

	@Override
	public String toString() {
		return "point: " + m_point + ", size: " + m_size;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Location))
			return false;
		Location l = (Location)o;
		return m_point.equals(l.m_point) && m_size.equals(l.m_size);
		}

	/**
	* get method
	* 
	* @return copy of Point
	*/
	public Point getPoint() {return new Point(m_point);}

	/**
	* get method
	* 
	* @return copy of Size
	*/
	public Size getSize() {return new Size(m_size);}
	
		private Point m_point;// Location's top left corner
		private Size m_size;// Location's size
}
