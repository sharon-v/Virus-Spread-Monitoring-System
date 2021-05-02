package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;

import country.Map;
import country.Settlement;
import location.Location;
import location.Point;

public class MapDrawing extends JPanel implements MouseListener{
	private Map map;
	private final Statistics st;
	
	public MapDrawing(Map myMap, Statistics stat) {
		//create border
		Border raisedbevel = BorderFactory.createRaisedBevelBorder();
		Border loweredbevel = BorderFactory.createLoweredBevelBorder();
		Border compound = BorderFactory.createCompoundBorder(raisedbevel, loweredbevel);
		this.setBorder(compound);

		map = myMap;
		st = stat;
		this.add(new JScrollPane());
		
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D gr = (Graphics2D) g; //for better looking
		gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		Point[] theConnections = map.middelPoints();
		for(int i = 0; i<theConnections.length - 1 ; i+=2) {
			g.drawLine(theConnections[i].getX(), theConnections[i].getY(), theConnections[i+1].getX(), theConnections[i+1].getY());
		}

		Color[] settlColors = map.settlementsColors();
		Location[] settlLocations = map.settlementsLocation();
		Point[] settlementsMiddlePoints = map.settlementPoints();
		String[] settleNames = map.settlementsNames();
		for(int i=0; i< settlColors.length ; ++i) {
			g.setColor(settlColors[i]);
			g.fillRect(settlLocations[i].getPoint().getX(), settlLocations[i].getPoint().getY(),
					settlLocations[i].getSize().getWidth(), settlLocations[i].getSize().getHeith());
			g.setColor(Color.BLACK);
			g.drawString(settleNames[i], settlLocations[i].getPoint().getX()+5, settlementsMiddlePoints[i].getY()+5);
		}
				
		addMouseListener(this);
	}
	
//	public void setMapDraw(Map map) {
//		this.map = map;
//		paintComponent(getGraphics());
//	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(600, 400);
	}
	
	public void mousePressed(MouseEvent e) {
	       
	    }

	    public void mouseReleased(MouseEvent e) {
	    }

	    public void mouseEntered(MouseEvent e) {
	       
	    }

	    public void mouseExited(MouseEvent e) {
	      
	    }

	    public void mouseClicked(MouseEvent e) {
	    	int x = e.getX();
		    int y = e.getY();
			Location[] settlLocations = map.settlementsLocation();
		    for(int i = 0; i < settlLocations.length ; ++i) {//run over the settlements and printing them by color
		    	 int startX = settlLocations[i].getPoint().getX();
		    	 int startY = settlLocations[i].getPoint().getY();
		    	 int endX = settlLocations[i].getSize().getWidth() + startX;
		    	 int endY = settlLocations[i].getSize().getHeith() + startY;
		    	 if(x >= startX && x <= endX && y >= startY && y <= endY) {
		    		 st.markLine(i);
		    		 st.showDialog();
		    	 }
		    }
	    }


}
