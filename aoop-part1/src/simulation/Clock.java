package simulation;

public static class Clock {
	public static long now() {
		return m_currentTime;
	}
	public static void nextTick() {
		++m_currentTime;
	}
	private static long  m_currentTime;
}
