package ui;

import java.awt.Graphics;

import country.RamzorColor;
import country.Settlement;
import location.Point;

public class LineDecorator {
	
	
	public void createLine(Settlement s1, Settlement s2, Graphics g) {
		
		Point s1Middel = s1.middelOfSettlement();
		Point s2Middel = s2.middelOfSettlement();
		
		//calculate color
		double color = (s1.getRamzorColor().getValue() + s2.getRamzorColor().getValue()) / 2 ; 
		
		RamzorColor n;
		g.setColor(n.doubleToRamzorColor(color).getColor());
		
		g.drawLine((int) (s1Middel.getX() * scaleX()), (int) (s1Middel.getY() * scaleY()),
				(int) (s2Middel.getX() * scaleX()),
				(int) (s2Middel.getY() * scaleY()));
	}
	
	

	
	
	
	
	
}
