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
	
	@Override
	public String toString() {
		return "width: " + m_width + ", height: " + m_height;
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Size))
			return false;
		Size s = (Size)o;
		return m_width == s.m_width && m_height == s.m_height;
	}
	
	/**
	 * get method
	 * 
	 * @return width value of Size
	 */
	public int getWidth() {return m_width;}

	/**
	 * get method
	 * 
	 * @return height value of Size
	 */
	public int getHeith() {return m_height;}
	
	private int m_width;// width value of Size
	private int m_height;// height value of Size
	
}
