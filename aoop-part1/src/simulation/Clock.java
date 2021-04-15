package simulation;

/**
 * 
 * @author Yarden Hovav, Sharon Vazana
 *
 */
public class Clock {
	
	/**
	 * Constructor
	 */
	public Clock() {
		m_currentTime = 0;
	}
	
	/**
	 * get method
	 * 
	 * @return current time
	 */
	public static long now() {
		nextTick();
		return m_currentTime;
	}
	
	/**
	 * increase current time in 1
	 */
	public static void nextTick() {
		++m_currentTime;
	}
	
	//attributes
	private static long m_currentTime; // current time
}
