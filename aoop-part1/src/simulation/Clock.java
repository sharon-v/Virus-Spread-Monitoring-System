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
		ticks_per_day = 1;
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
	
	/**
	 * 
	 * @param x - num of ticks in a single day
	 */
	public static void setTicksPerDay(int x) {
		ticks_per_day = x;
	}

	public static long calculateDays(long startTime) {
		return (long) (Math.ceil((now() - startTime) / ticks_per_day));
	}

	//attributes
	private static long m_currentTime; // current time
	private static int ticks_per_day;
}
