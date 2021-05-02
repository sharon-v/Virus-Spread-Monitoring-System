package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import country.Map;
import country.Settlement;
import location.Location;
import location.Point;

public class MapDrawing extends JPanel implements MouseListener{
	private Settlement[] settl;///??????????????????????????????????????
	private Map map;
	
	public MapDrawing(Map myMap) {
//		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		settl = myMap.getSettlement();
		map = myMap;
		
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
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(1000, 800);
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
		      for(int i = 0; i < settl.length ; ++i) {//run over the settlements and printing them by color
		    	  int startX = settl[i].getLocation().getPoint().getX();
		    	  int startY = settl[i].getLocation().getPoint().getY();
		    	  int endX = settl[i].getLocation().getSize().getWidth() + startX;
		    	  int endY = settl[i].getLocation().getSize().getHeith() + startY;
		    	  if(x >= startX && x <= endX && y >= startY && y <= endY) {
		    			Statistics st = new Statistics(map, this.getParent());
		    			//to think how to open the statistic from here and mark the selected settlement
						this.getParent().add(st);
//						this.add(st);

		    			st.markLine(settl[i].getSettlementName());
//		    			n.pack();
//		    			n.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		    			n.setVisible(true);

		    			
//		    			this.getParent().removeAll();
//		    			this.getParent().add(st);
		     	  }
		      }

	    }


}
