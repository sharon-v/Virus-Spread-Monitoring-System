package location;

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
	
	public String toString() {
		return "point: " + m_point + ", size: " + m_size;
	}

	public boolean equals(Object o) {
		if (!(o instanceof Location))
			return false;
		Location l = (Location)o;
		return m_point.equals(l.m_point) && m_size.equals(l.m_size);
		}

	public Point getPoint() {return new Point(m_point);}
	public Size getSize() {return new Size(m_size);}
	// get location ???
	
	private Point m_point;
	private Size m_size;
}
