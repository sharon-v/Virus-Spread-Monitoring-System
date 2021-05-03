package virus;

import java.util.ArrayList;

import population.Person;
import population.Sick;
import simulation.Clock;

/**
 * 
 * @author Yarden Hovav, Sharon Vazana
 *
 */
public class ChineseVariant implements IVirus {

	@Override
	public String toString() {
		return "Chinese Variant";
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof ChineseVariant))
			return false;
		ChineseVariant c = (ChineseVariant) o;
		return toString().equals(c.toString());
	}

	@Override
	public double contagionProbability(Person p) {
		double contagionProbability;
		if(p.getAge() <= 18)
			contagionProbability = contagionProbTo18;
		else if( p.getAge() <= 55)
			contagionProbability = contagionProb18To55;
		else
			contagionProbability = contagionProb55Above;
		return contagionProbability * p.contagionProbability();
	}
	
	@Override
	public boolean tryToContagion(Person p1, Person p2){
		double randonNumber = Math.random();
		if (p1.healthCondition().equals("Sick"))
			if (((Sick) p1).getContagiousTime() < 5)
				return false;
		if (!(p2.healthCondition().equals("Sick"))) {
			double d = p1.distance(p2); // distance between 2 people
			if(contagionProbability(p2) * Math.min(1, 0.14 * Math.exp(2 - 0.25 * d)) > randonNumber)
				return true; 
			else 
				return false;
		}
		return false;
	}
	
	@Override
	public boolean tryToKill(Sick s) {
		double randonNumber = Math.random();
		double p ; //the probability to die according to age
		if(s.getAge() <= 18)
			p = deathProbTo18;
		else if(s.getAge() <= 55)
			p = deathProb18To55;
		else 
			p = deathProb55Above;
		long t = Clock.calculateDays(s.getContagiousTime()); //the time that passed since contagion 
		if(Math.max(0, p - 0.01 * p * Math.pow(t  -15, 2)) > randonNumber)
			return true; 
		return false;
	}

	/**
	 * 
	 * @return ArrayList containing the possible variants from this Variant
	 */
	public static ArrayList<String> getPossibleVariants() {
		return new ArrayList<String>(m_possibleVariants);
	}

	@Override
	public ArrayList<String> getVars() {
		return getPossibleVariants();
	}

	/**
	 * sets the variant ArrayList
	 * 
	 * @param newVariant - new variant to add
	 */
	public static void setPossibleVariants(ArrayList<String> newVariants) {
		m_possibleVariants = new ArrayList<String>(newVariants);
	}


	// attributes
	private static final double deathProbTo18 = 0.0001;// death probability up to 18
	private static final double deathProb18To55 = 0.05;// death probability 18 to 55
	private static final double deathProb55Above = 0.1;// death probability above 55
	private static final double contagionProbTo18 = 0.2;// contagion probability up to 18
	private static final double contagionProb18To55 = 0.5;// contagion probability 18 to 55
	private static final double contagionProb55Above = 0.7;// contagion probability above 55
	private static ArrayList<String> m_possibleVariants = new ArrayList<String>();

}
