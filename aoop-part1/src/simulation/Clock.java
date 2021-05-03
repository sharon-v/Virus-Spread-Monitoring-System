package simulation;

/**
 * 
 * @author Yarden Hovav, Sharon Vazana
 *
 */
public class Clock {
	
	/**
	 * get method
	 * 
	 * @return current time
	 */
	public static long now() {
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
		if(x == 0)
			x = 1;
		ticks_per_day = x;
	}

	/**
	 * 
	 * @return value of ticks per day
	 */
	public static int getTicksPerDay() {
		return ticks_per_day;
	}

	/**
	 * 
	 * @param startTime - the initial time to compare to
	 * @return how many days passed since
	 */
	public static long calculateDays(long startTime) {
		return (long) (Math.ceil((now() - startTime) / ticks_per_day));
	}

	//attributes
	private static long m_currentTime = 0; // current time
	private static int ticks_per_day = 1;
}
