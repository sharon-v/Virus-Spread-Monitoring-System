package ui;

import java.awt.Graphics;

import country.RamzorColor;
import country.Settlement;
import location.Point;

public abstract class LineDecorator {
	
	public LineDecorator(MapDrawing draw) {
		m_draw = draw;
	}
	public abstract void createLine(Settlement s1, Settlement s2, Graphics g) ;
	
	protected MapDrawing getMapDrawing() {
		return m_draw;  
	}
	
	private MapDrawing m_draw; //delegator
	
}
