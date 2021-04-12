package simulation;

public class Clock {
	
	/**
	 * Constructor
	 */
	public Clock() {
		m_currentTime = 0;
	}
	
	/**
	 * 
	 * @return The current time
	 */
	public static long now() {
		nextTick();
		return m_currentTime;
	}
	
	/**
	 * Increase the time in 1
	 */
	public static void nextTick() {
		++m_currentTime;
	}
	
	//attributes
	private static long m_currentTime; // when to advance
}
