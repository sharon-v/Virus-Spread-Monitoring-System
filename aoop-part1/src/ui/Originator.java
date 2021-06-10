package ui;

public class Originator {

	/**
	 * Constructor
	 * @param path - the current path
	 */
	public Originator() {
		m_currentPath = null;
	}
	
	/**
	 * 
	 * @param path - the new path to the log file
	 */
	public void setOriginator(String path) {
		m_currentPath = path;
	}
	
	/**
	 * 
	 * @return the current path
	 */
	public String getpath() {
		return m_currentPath ;
	}

	/**
	 * 
	 * @return new memento
	 */
	public Memento save() {
		return new Memento(m_currentPath);
	}
	
	/**
	 * 
	 * @param memento - the old memento
	 */
	public void undo(Memento memento) {
		String path = memento.getPath();
		if(path != null)
			m_currentPath = path;

	}

	private String m_currentPath;

}
