package simulation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import country.Map;
import ui.MapDrawing;
import ui.Mutations;
import ui.Statistics;

/**
 * 
 * @author Yarden Hovav, Sharon Vazana
 *
 */
public class Main {

	public static void main(String[] args) {
		Map myMap = new Map();
		myMap.loadInfo(); // first stage
		myMap.intialization();// second stage
		try {
			myMap.executeSimulation(); // third stage
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("an unexpected ERROR has occurred :(");
		}
		//???????????????????
		
		JFrame frame = new JFrame("Table Example"); 		
//		frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.PAGE_AXIS));
		Statistics st = new Statistics(myMap, frame);
		
		JButton b = new JButton("bbbb");
		b.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Mutations m = new Mutations(frame);
				m.showDialog();
			}
		});
		
		 
		frame.add(new MapDrawing(myMap)); // the map drawing model
		
//		frame.add(b);
//		frame.add(st);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

			  
		//???????????????????
	}
}
