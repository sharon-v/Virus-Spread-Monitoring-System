package simulation;

public class Clock {
	
	/**
	 * Constractor
	 */
	public Clock() {
		m_currentTime = 0;
	}
	
	/**
	 * 
	 * @return The current time
	 */
	public static long now() {
		return m_currentTime;
	}
	
	/**
	 * Increase the time in 1
	 */
	public static void nextTick() {
		++m_currentTime;
	}
	
	//attributes
	private static long  m_currentTime;
}
