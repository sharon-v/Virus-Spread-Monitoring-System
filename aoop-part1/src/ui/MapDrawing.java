package ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import country.Map;
import country.Settlement;
import location.Point;

public class MapDrawing extends JPanel implements MouseListener{
	private Settlement[] settl;
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
		Point settlMid ;
		for(int i = 0; i<settl.length;++i) { //run over the settlements and the connections of each and printing the lines
			settlMid = settl[i].middelOfSettlement(); 
			Settlement[] connections = settl[i].getConnections();
			for(int j= 0 ;j < connections.length; ++j) {
				Point connectionMid = connections[j].middelOfSettlement();
				g.drawLine(settlMid.getX(), settlMid.getY(), connectionMid.getX(), connectionMid.getY());
			}
			
		}			
		for(int i = 0; i < settl.length ; ++i) {//run over the settlements and printing them by color
			settlMid = settl[i].middelOfSettlement(); 
			g.setColor(settl[i].getRamzorColor().getColor());
			g.drawRect(settl[i].getLocation().getPoint().getX(), settl[i].getLocation().getPoint().getY(),
					settl[i].getLocation().getSize().getWidth(), settl[i].getLocation().getSize().getHeith());
			g.drawString(settl[i].getSettlementName(), settlMid.getX(), settlMid.getY());
		}
		
		addMouseListener(this);
		
		
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(400, 400);
	}
	
	public void mousePressed(MouseEvent e) {
	       
	    }

	    public void mouseReleased(MouseEvent e) {
	    	int x = e.getX();
		      int y = e.getY();
		      for(int i = 0; i < settl.length ; ++i) {//run over the settlements and printing them by color
		    	  int startX = settl[i].getLocation().getPoint().getX();
		    	  int startY = settl[i].getLocation().getPoint().getY();
		    	  int endX = settl[i].getLocation().getSize().getWidth() + startX;
		    	  int endY = settl[i].getLocation().getSize().getHeith() + startY;//?? minus(-)
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

	    public void mouseEntered(MouseEvent e) {
	       
	    }

	    public void mouseExited(MouseEvent e) {
	      
	    }

	    public void mouseClicked(MouseEvent e) {
	      
	    }


}
