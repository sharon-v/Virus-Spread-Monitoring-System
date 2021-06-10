package virus;

/**
 * 
 * @author Yarden Hovav, Sharon Vazana
 *
 */
public enum VirusEnum {
	BRITISH("British Variant"),
	CHINESE("Chinese Variant"),
	SOUTH_AFRICAN("South African Variant");
	
	private VirusEnum(String type) {
		m_type = type ;
	}
	
	public String getType() {
		return m_type;
	}
	public int stringToIndex(String type) {
		if(type.equals("British Variant"))
			return 0;
		else if (type.equals("Chinese Variant"))
			return 1;
		else 
			return 2;
	}
	
	private String m_type;
}
