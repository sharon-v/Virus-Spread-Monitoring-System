package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import country.Map;
import location.Location;
import location.Point;

/**
 * 
 * @author Yarden Hovav, Sharon Vazana
 *
 */
public class MapDrawing extends JPanel{
	/**
	 * constructor
	 * 
	 * @param myMap - MapDrawing Object
	 * @param stat  - Statistics Object
	 */
	public MapDrawing(Map myMap, Statistics stat) {
		map = myMap;
		st = stat;

		/**
		 * actions for MapDrawing
		 */
		this.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int x = e.getX();
			    int y = e.getY();
				Location[] settlLocations = map.settlementsLocation();
				// run over the settlements and printing them by color
				for (int i = 0; i < settlLocations.length; ++i) {
			    	 int startX = settlLocations[i].getPoint().getX();
			    	 int startY = settlLocations[i].getPoint().getY();
			    	 int endX = settlLocations[i].getSize().getWidth() + startX;
			    	 int endY = settlLocations[i].getSize().getHeith() + startY;
			    	 if(x >= startX && x <= endX && y >= startY && y <= endY) {
						st.markLine(i);// mark the corresponding line in Statistics window
						st.showDialog();// open Statistics window
			    	 }
			    }
			}
		});
		
	}
	
	/**
	 * update statistics window
	 */
	public void updateStatWindow() {
		st.updateTableModel();
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
		
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(400, 400);
	}

	// fields
	private Map map;
	private final Statistics st;
}

