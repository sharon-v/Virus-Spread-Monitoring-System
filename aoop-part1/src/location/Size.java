package location;

public class Size {
	/**
	 * constructor
	 * @param w - width input
	 * @param h - height input
	 */
	public Size(int w, int h) {
		if (w > 0 && h > 0) { 
			m_width = w;
			m_height = h;
		}
	}
	/**
	 * copy constructor
	 * @param s - other Size type object
	 */
	public Size(Size s) {
		this(s.getWidth(), s.getHeith());
	}
	
	public String toString() {
		return "width: " + m_width + ", height: " + m_height;
	}
	
	public boolean equals(Object o) {
		if (!(o instanceof Size))
			return false;
		Size s = (Size)o;
		return m_width == s.m_width && m_height == s.m_height;
		}
	
	public int getWidth() {return m_width;}
	public int getHeith() {return m_height;}
	
	private int m_width;
	private int m_height;
	
}
