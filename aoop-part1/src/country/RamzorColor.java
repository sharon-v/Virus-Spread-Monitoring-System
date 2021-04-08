package country;

public enum RamzorColor {
	GREEN(0.4),		// up to
	YELLOW(0.6),	// up to
	ORANGE(0.8),	// up to
	RED(1.0);		// up to
	
	private RamzorColor(double value) {
		this.value = value;
	}
	
	public double getValue() {
        return this.value;
    }
		
	private final double value;
}
