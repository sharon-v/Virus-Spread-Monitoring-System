package ui;

public class Memento {

	/**
	 * Constructor
	 * @param state - the previous path that need to save
	 */
	public Memento(String state){ 
		m_previousPath = state; 
	} 

	/**
	 * 
	 * @return the path
	 */
	public String getPath() { return m_previousPath; } 

	private String m_previousPath; 
}