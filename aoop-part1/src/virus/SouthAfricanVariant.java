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
public class SouthAfricanVariant implements IVirus {
	@Override
	public String toString() {
		return "South African Variant";
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof SouthAfricanVariant))
			return false;
		SouthAfricanVariant s = (SouthAfricanVariant) o;
		return toString().equals(s.toString());
	}

	@Override
	public double contagionProbability(Person p) {
		double contagionProbability;
		if(p.getAge() <= 18)
			contagionProbability = contagionProbTo18;
		else
			contagionProbability = contagionProb18Above;
		return contagionProbability * p.contagionProbability();
	}
	
	@Override
	public boolean tryToContagion(Person p1, Person p2){
		double randonNumber = Math.random();
		if (p1.healthCondition().equals("Sick"))
			if (Clock.calculateDays(((Sick) p1).getContagiousTime()) < 5)
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
		else
			p= deathProb18Above;
		long t = Clock.calculateDays(s.getContagiousTime()); // the time that passed since contagion
		if(Math.max(0, p - 0.01 * p * Math.pow(t - 15, 2)) > randonNumber)
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
	private static final double deathProbTo18 = 0.05;// death probability up to 18
	private static final double deathProb18Above = 0.08;// death probability above 18
	private static final double contagionProbTo18 = 0.6;// contagion probability up to 18
	private static final double contagionProb18Above = 0.5;// contagion probability above 18
	private static ArrayList<String> m_possibleVariants = new ArrayList<String>();

}
