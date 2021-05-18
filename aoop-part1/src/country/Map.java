package country;

import java.awt.Color;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicBoolean;

import io.SimulationFile;
import location.Location;
import location.Point;

/**
 * 
 * @author Yarden Hovav, Sharon Vazana
 *
 */
public class Map {
	/**
	 * constructor
	 */
	public Map() {
		resetMap();
	}

	/**
	 * reset the map - delete the old map
	 */
	public void resetMap() {
		m_settlement = new Settlement[0];
	}

	@Override
	public String toString() {
		return "amount of settlements: " + m_settlement.length + "\n" + toStringSettlements();
	}


	/**
	 * 
	 * @param s new settlement to add to map
	 */
	public void addSettlement(Settlement s) {
		Settlement[] temp = new Settlement[m_settlement.length + 1];

		for(int i = 0; i < m_settlement.length; ++i) {
			temp[i] = m_settlement[i];
		}
		temp[m_settlement.length] = s;
		m_settlement = temp;
	}

	/**
	 * 
	 * @return toString of all the Settlement in the Map
	 */
	private String toStringSettlements() {
		String str = "=== settlements ===\n";
		for (int i = 0; i < m_settlement.length; ++i)
			str += m_settlement[i].toString() + "\n";
		return str;
	}

	/**
	 * manages input of data from file
	 */
	public void loadInfo(String filePath) {
		String[] connections = null;
		try {
			SimulationFile loadMap = new SimulationFile();
			connections = loadMap.readFromFile(this, filePath);
			makeConnections(connections);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

//	/**
//	 * manages simulation sequence
//	 */
//	public void executeSimulation() throws Exception {
//		sampleTwentyPercent();
//		massRecovery();
//		tryToTransfer();
//		massVaccination();
//		massMurder();
//	}

//	/**
//	 * Run over the settlements and try to kill 
//	 */
//	public void massMurder(){
//		for (int i = 0; i < m_settlement.length; ++i) {
//			m_settlement[i].attemptedMurder();
//		}
//	}

//	/**
//	 * Run over the settlements and trying to contagion 3 people for 20% from the sick persons
//	 */
//	public void sampleTwentyPercent() {
//		for (int i = 0; i < m_settlement.length; ++i) {
//			m_settlement[i].simulation();
//		}
//	}

//	/**
//	 * Run over the settlements and for each sick person that past 25 days since he got sick , turn his to convalescent
//	 */
//	public void massRecovery() {
//		for (int i = 0; i < m_settlement.length; ++i) {
//			m_settlement[i].sickToConvalescent();
//		}
//	}


	/**
	 * initializes the Settlements with sick people
	 */
	public void intialization() { // 1%
		for (int i = 0; i < m_settlement.length; ++i) {
			m_settlement[i].infectPercent(0.01);
		}
	}

	/**
	 * infect 1% people
	 */
	public synchronized void activateOnePercent(Object settName) {
		findSettlementByName(settName.toString()).infectPercent(0.001);
	}

	/**
	 * find the settlement by its name and return 
	 */
	private Settlement findSettlementByName(String name) {
		for (int i = 0; i < m_settlement.length; ++i) {
			if (m_settlement[i].getSettlementName().equals(name))
				return m_settlement[i];
		}
		return null;
	}

	/**
	 * adding vaccines to the settlement
	 */
	public synchronized void addVaccines(Object settName, int amount) {
		findSettlementByName(settName.toString()).setVaccineDoses((int) amount);
	}

//	/**
//	 * vaccinates Healthy People if there are enough vaccines foe each Settlement
//	 */
//	public void massVaccination() {
//		for (int i = 0; i < m_settlement.length; ++i) {
//			m_settlement[i].vaccineTime();
//		}
//	}

//	/**
//	 * calls a method that tries to transfer random 3% from each Settlement
//	 */
//	public void tryToTransfer() {
//		if (m_settlement.length == 1)
//			return;
//		for (int i = 0; i < m_settlement.length; ++i) {
//			Settlement s = m_settlement[i].randomConnection();
//			if (s == null)
//				return;
//			m_settlement[i].randomTransfer(s);
//		}
//	}

	/**
	 * create the connections in the map foe each settlement
	 * @param arr - list of string that describe the connections in the map
	 */
	private void makeConnections(String[] connections) {
		String[] temp = null;
		for (int i = 0; i < connections.length; ++i) {
			temp = connections[i].split(";");
			connectSettlements(temp[1], temp[2]);
		}
	}

	/**
	 * 
	 * @return Color array of all the settlements ramzor color
	 */
	public Color[] settlementsColors() {
		Color[] settlColor = new Color[m_settlement.length];
		for(int i= 0 ;i< m_settlement.length;++i) 
			settlColor[i] = m_settlement[i].getRamzorColor().getColor();
		return settlColor;
	}

	/**
	 * 
	 * @return Location array of all the settlements location
	 */
	public Location[] settlementsLocation() {
		Location[] settlPoints = new Location[m_settlement.length];
		for(int i= 0 ;i< m_settlement.length;++i) 
			settlPoints[i] = new Location(m_settlement[i].getLocation());
		return settlPoints;
	}

	/**
	 * 
	 * @return String array of all the settlements name
	 */
	public String[] settlementsNames() {
		String[] settlNames = new String[m_settlement.length];
		for(int i= 0 ;i< m_settlement.length;++i) 
			settlNames[i] = m_settlement[i].getSettlementName();
		return settlNames;
	}

	/**
	 * 
	 * @return Point array of all the settlements middle point
	 */
	public Point[] settlementPoints() {
		Point[] settlPoints = new Point[m_settlement.length];
		for(int i= 0 ;i< m_settlement.length;++i) 
			settlPoints[i] = new Point(m_settlement[i].middelOfSettlement());
		return settlPoints;
	}

	/**
	 * 
	 * @return all the connections in the map
	 */
	public Point[] middelPoints() {
		Point[] Connections = new Point[0];
		for(int i=0 ; i< m_settlement.length;++i){
			Point[] connectionsMid = m_settlement[i].conectionsPoints();
			Point settleMiddle = m_settlement[i].middelOfSettlement();
			Connections = addConection(Connections, connectionsMid, settleMiddle);
		}
		return Connections;
	}

	/**
	 * 
	 * @param arr - middle point of the connections settlement array 
	 * @param newPoints - new points connection of settlement
	 * @param settlPoint - middle point of settlement
	 * @return - the new connection array after adding a new connection
	 */
	private Point[] addConection(Point[] arr, Point[] newPoints, Point settlPoint) {
		Point[] temp = arr;
		for(int j = 0; j< newPoints.length ; ++j) {
			if(!checkIfConnectionExist(arr, newPoints[j], settlPoint)) {
				temp = new Point[arr.length + 2];
				for(int i=0 ; i< arr.length;++i) 
					temp[i] = arr[i];
				temp[arr.length] = settlPoint;
				temp[arr.length + 1] = newPoints[j];
			}
		}
		return temp;
	}

	/**
	 * 
	 * @param PointsConnection - middle point of the connections settlement array
	 * @param settl1 - middle point of settlement
	 * @param settl2 - - middle point of settlement
	 * @return - true if the connection exist in the array, if not return false
	 */
	private boolean checkIfConnectionExist(Point[] PointsConnection, Point settl1, Point settl2) {
		for (int i = 0; i < PointsConnection.length - 1; i += 2) {
			if(PointsConnection[i].equals(settl1))
				if(PointsConnection[i+1].equals(settl2))                  
					return true;
			if(PointsConnection[i].equals(settl2))
				if(PointsConnection[i+1].equals(settl1))
					return true;	
		}
		return false;
	}


	/**
	 * 
	 * @param s1 - the name of the first settlement in the connection
	 * @param s2 - the name of the second settlement in the connection
	 */
	private void connectSettlements(String s1, String s2) {
		Settlement settl1 = null, settl2 = null;
		for (int i = 0; i < m_settlement.length; ++i) {
			if (m_settlement[i].getSettlementName().equals(s1))
				settl1 = m_settlement[i];
			else if (m_settlement[i].getSettlementName().equals(s2))
				settl2 = m_settlement[i];
		}
		if (settl1 != null && settl2 != null) {
			settl1.addNewConnection(settl2);
			settl2.addNewConnection(settl1);
		}

	}

	/**
	 * set the cyclic barrier
	 */
	public void setCyclic(CyclicBarrier myCyclic) {
		m_cyclic = myCyclic;

	}

	/**
	 * create and run all the threads
	 */
	public void createThreads() {
		for (Settlement s : m_settlement) {
			new Thread(s).start();
		}
	}

	/**
	 * 
	 * @return - the number of the settlement in the map
	 */
	public int getMapSize() {
		return m_settlement.length;
	}

	/**
	 * 
	 * @param rowIndex - the row index 
	 * @return - the settlement in the index that sent
	 */
	public Settlement at(int rowIndex) {
		if (rowIndex < getMapSize())
			return m_settlement[rowIndex];
		return null;
	}

	/**
	 * 
	 * @param newPath - the path for the log file
	 */
	public void setLogPath(String newPath) {
		if(m_path == null) {
			m_path = newPath ;
			setLogFlag(true);
		}
	}

	/**
	 * 
	 * @return the path of the log file
	 */
	public String getLogPath() {
		return m_path;
	}

	/**
	 * applies wait to cyclic barrier
	 */
	public void cyclicAwait() {
		try {
			m_cyclic.await();
		}catch(InterruptedException ex) {ex.printStackTrace();}
		catch(BrokenBarrierException e) {e.printStackTrace();}
	}


	/**
	 * set the play flag
	 * @param val - boolean object
	 */
	public void setPlayFlag(boolean val) {
		playFlag.set(val);
	}

	/**
	 * set the load flag
	 * @param val -boolean object
	 */
	public void setLoadFlag(boolean val) {
		loadFlag.set(val);
	}

	/**
	 * get the play flag
	 * @return the play flag boolean value
	 */
	public boolean getPlayFlag() {
		return playFlag.get();
	}

	/**
	 * get the load flag
	 * @return the load flag boolean value
	 */
	public boolean getLoadFlag() {
		return loadFlag.get();
	}

	/**
	 * get method
	 * 
	 * @return logFlag boolean value
	 */
	public boolean getLogFlag() {
		return logFlag.get();
	}

	/**
	 * set method
	 * 
	 * @param val - new logFlag value
	 */
	public void setLogFlag(boolean val) {
		logFlag.set(val);
	}


	private AtomicBoolean playFlag = new AtomicBoolean(true); // flag to know if the simulation is play or pause
	private AtomicBoolean loadFlag = new AtomicBoolean(); // initialized false, flag to know if file has been loaded or not
	private AtomicBoolean logFlag = new AtomicBoolean();// initialized false, flag to indicate if a logFile location was selected
	private CyclicBarrier m_cyclic ;
	private String m_path;
	private Settlement[] m_settlement;// array of Settlements

}
