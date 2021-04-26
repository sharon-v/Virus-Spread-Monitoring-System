package country;

import java.awt.Color;

/**
 * 
 * @author Yarden Hovav, Sharon Vazana
 *
 */
public enum RamzorColor {
	GREEN(0.4, Color.GREEN, 1), // up to
	YELLOW(0.6, Color.YELLOW, 0.8), // up to
	ORANGE(0.8, Color.ORANGE, 0.6), // up to
	RED(1.0, Color.RED, 0.4); // up to
	
	/**
	 * constructor
	 * 
	 * @param value - double type input
	 */
	private RamzorColor(double value, Color color, double tProb) {
		this.value = value;
		this.color = color;
		this.transferProb = tProb;
	}
	
	/**
	 * get method
	 * 
	 * @return value of the RamzorColor parameter in type double
	 */
	public double getValue() {
        return this.value;
    }
	
	/**
	 * get method
	 * 
	 * @return Color type object for the corresponding RamzorColor
	 */
	public Color getColor() {
		return this.color;
	}

	/**
	 * get method
	 * 
	 * @return value of the RamzorColor's corresponding transfer probability
	 */
	public double getTransferProb() {
		return this.transferProb;
	}

	/**
	 * converts double to RamzorColor
	 * 
	 * @param d - double input
	 * @return corresponding RamzorColor for the double input
	 */
	public RamzorColor doubleToRamzorColor(double d) {
		if( d <= GREEN.getValue())
			return GREEN;
		if( d <= YELLOW.getValue())
			return YELLOW;
		if( d <= ORANGE.getValue())
			return ORANGE;
		else
			return RED;
	}
	
	private final double value; // value of the parameters
	private Color color;// color of the settlement
	private double transferProb;// probability for successful transfer
}
