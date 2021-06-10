package virus;

import java.util.Random;
import java.util.Vector;

import javax.swing.JTable;

public class VirusManager {
	//static ???????????????
	public static VirusManager SingeltonVirusManager(JTable table) {
		if (m_singeltonVirusManager == null)
			m_singeltonVirusManager = new VirusManager(table);
		return m_singeltonVirusManager;
	}
	private VirusManager(JTable table) {
		m_table = table;
	}
	/**
	 * 
	 * @param currentVirus - virus from which we want to infect from
	 * @param table        - contains info of the possible variants for contagion
	 * @return IVirus to contagion, else null
	 */
	public static IVirus createVirus(IVirus currentVirus) {
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

	private static JTable m_table;// ?????? is allowed
	private static VirusManager m_singeltonVirusManager;
}

