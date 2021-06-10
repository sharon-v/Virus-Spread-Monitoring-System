package virus;

import java.util.Random;
import java.util.Vector;

import javax.swing.JTable;

public class VirusManager {
	
	public static VirusManager SingeltonVirusManager() {
		if (m_singeltonVirusManager == null)
			m_singeltonVirusManager = new VirusManager();
		return m_singeltonVirusManager;
	}
	
	private VirusManager() {
		m_table = null;
	}
	
	/**
	 * 
	 * @param table - contains info of the possible variants for contagion
	 */
	public void setTable(JTable table) {
		m_table = table;
	}
	
	/**
	 * 
	 * @param currentVirus - virus from which we want to infect from
	 * @return IVirus to contagion, else null
	 */
	public IVirus createVirus(IVirus currentVirus) {
		VirusEnum[] viruses = VirusEnum.values();
		int currentIndex = -1;

		// finds virus index in table
		for (int i=0; i<viruses.length;++i) {
			if(viruses[i].getType().equals(currentVirus.toString()))
				currentIndex = i;
		}
		if (currentIndex == -1)
			return null;// if index not found

		// create list of possible variants
		Vector<String> possibleViruses = new Vector<String>(0);
		for (int i = 0; i < m_table.getColumnCount(); ++i) {
			if (m_table.getValueAt(currentIndex, i).equals(true)) {
				possibleViruses.add(VirusEnum.values()[i].getType());
			}
		}
		if (possibleViruses.size() == 0)
			return null;// no possible variants

		// choose random virus from possibilities
		Random ran = new Random();
		int varIndex = ran.nextInt(possibleViruses.size());
		VirusFactory vf = new VirusFactory();
		return vf.createVirus(possibleViruses.get(varIndex));// return random virus
	}

	private JTable m_table;
	private static VirusManager m_singeltonVirusManager;
}

