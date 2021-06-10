package ui;

import java.util.Stack;

public class CareTaker {

	/**
	 * constructor
	 * @param origin - the originator object
	 */
	public CareTaker(Originator origin) {
		m_origin = origin;
		m_history = new History();
	}

	// ===============================================
	// private class History
	private class History {
		private Stack<Memento> m_undo = new Stack<>();

		/**
		 * save the current path in stack
		 */
		public void save() {
			m_undo.push(m_origin.save());
		}

		/**
		 * restores the previous path
		 */
		public void undo() {
			m_origin.undo(m_undo.pop());
		}
	}// end private class History
		// ===============================================

	public void save() {
		m_history.save();
	}

	/**
	 * restores the previous path
	 */
	public void undo() {
		m_history.undo();
	}

	private Originator m_origin;
	private History m_history;
}
