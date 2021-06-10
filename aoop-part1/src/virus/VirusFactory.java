package virus;

/**
 * 
 * @author Yarden Hovav, Sharon Vazana
 *
 */
public class VirusFactory {
	
	/**
	 * 
	 * @param type - String of the requested object to create
	 * @return new implementation of IVirus 
	 */
	public IVirus createVirus(String type) {
		if(type == null)
			return null;
		if (type.equalsIgnoreCase("British Variant"))
			 return new BritishVariant();
		else if (type.equalsIgnoreCase("Chinese Variant"))
			 return new ChineseVariant();
		else if(type.equalsIgnoreCase("South African Variant"))
			return new SouthAfricanVariant();
		return null;
	}
}
