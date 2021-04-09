/**
 * 
 */
package virus;

import population.Person;

public class BritishVariant implements IVirus {
	public static final double deathProbTo18 = 0.01;
	public static final double deathProb18Above = 0.1;
	public static final double contagionProb = 0.7;


	public double contagionProbability(Person p) {
		return contagionProb * p.contagionProbability();
	}
	
	public boolean tryToContagion(Person p1, Person p2){
		if(p2.healthCondition() != "Sick") {
			double d = p1.distance(p2);
			return contagionProbability(p2) * Math.min(1, 0);
			
		}
		// d - distance between 2 people
					return min(1, 0.14 * exp(2 - 0.25 * d));
		}
}
