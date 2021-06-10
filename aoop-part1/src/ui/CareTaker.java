package ui;

public class CareTaker {

	/**
	 * constructor
	 * @param origin - the originator object
	 */
	public CareTaker(Originator origin) {
		m_origin = origin;
	}

	/**
	 * save the previous path
	 */
	public void save() {
		m_memento = m_origin.save();
	}

	/**
	 * restore the previous path
	 */
	public void undo() {
		m_origin.undo(m_memento);
	}

	private Originator m_origin;
	private Memento m_memento;
}
