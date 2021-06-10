package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;

import javax.swing.JPanel;

import country.Map;
import country.Settlement;
import location.Location;
import location.Point;

public class MapDrawing extends JPanel{
	/**
	 * constructor
	 * 
	 * @param myMap - MapDrawing Object
	 * @param stat  - Statistics Object
	 */
	public MapDrawing(Map map, Statistics stat) {
		
		m_map = map;
		m_stat = stat;
		
					
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
					int startX = (int) (settlLocations[i].getPoint().getX() * scaleX());
					int startY = (int) (settlLocations[i].getPoint().getY() * scaleY());
					int endX = (int) (settlLocations[i].getSize().getWidth() * scaleX()) + startX;
					int endY = (int) (settlLocations[i].getSize().getHeith() * scaleY()) + startY;
					if(x >= startX && x <= endX && y >= startY && y <= endY) {
						stat.markLine(i);// mark the corresponding line in Statistics window
						stat.showDialog();// open Statistics window
					}
				}
			}
		});

	}

	/**
	 * update statistics window
	 */
	public void updateStatWindow() {
		m_stat.updateTableModel();
	}

	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D gr = (Graphics2D) g; //for better looking
		gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		ColoredLine line = new ColoredLine(this);		
		for (Settlement settlement : m_map) {
			Settlement[] connection = settlement.getConnection();
			for(int i=0; i< connection.length;++i) {
				if(!checkIfConnectionExist(connection[i].getSettlementName(), settlement.getSettlementName()))
					line.createLine(settlement, connection[i], g); // gr??
			}
		}
		
		for (Settlement settlment : m_map) {
			g.setColor(settlment.getRamzorColor().getColor());
			g.fillRect((int) (settlment.getLocation().getPoint().getX() * scaleX()),
					(int) (settlment.getLocation().getPoint().getY() * scaleY()),
					(int) (settlment.getLocation().getSize().getWidth() * scaleX()),
					(int) (settlment.getLocation().getSize().getHeith() * scaleY()));
			g.setColor(Color.BLACK);
			g.drawString(settlment.getSettlementName(), (int) ((settlment.getLocation().getPoint().getX() + 5) * scaleX()),
					(int) ((settlment.middelOfSettlement().getY() + 5) * scaleY()));
			
		}	
	}
	
	private boolean checkIfConnectionExist(String connectionName, String settlementName) {
		Iterator<Settlement> iter = m_map.iterator();
		Settlement settl = iter.next();
		while(!(settl.getSettlementName().equals(settlementName))) {
			if(settl.getSettlementName().equals(connectionName))
				return true;
			settl = (Settlement)iter.next();
		}
		return false;
	}
	
	/**
	 * 
	 * @return the X scale of map
	 */
	public double scaleX() {
		double xx = getMaxXPointAtMap() + 1;
		return (getWidth() / xx);
	}
	
	/**
	 * 
	 * @return the Y scale of map
	 */
	public double scaleY() {
		double yy = getMaxYPointAtMap() + 1;
		return (getHeight() / yy);
	}

	/**
	 * 
	 * @return the maximum x coordinate
	 */
	public int getMaxXPointAtMap() {
		return m_map.getMaxXPointAtMap();
	}
	
	/**
	 * 
	 * @return the maximum y coordinate
	 */
	public int getMaxYPointAtMap() {
		return m_map.getMaxYPointAtMap();
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(400, 400);
	}
	
	private Map m_map;
	private Statistics m_stat;
	

}
