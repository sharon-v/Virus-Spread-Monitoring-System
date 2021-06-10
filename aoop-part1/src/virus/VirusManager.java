package virus;

public class VirusManager {
	//static ???????????????
	public static IVirus createVirus(IVirus currentVirus) {
		VirusEnum[] viruses = VirusEnum.values();
		int currentIndex;
		for (int i=0; i<viruses.length;++i) {
			if(viruses[i].getType().equals(currentVirus.toString()))
				currentIndex = i;
		}
		return currentVirus;//delete
	}
}
