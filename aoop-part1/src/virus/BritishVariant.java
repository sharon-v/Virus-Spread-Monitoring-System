/**
 * 
 */
package virus;

import population.Person;
import population.Sick;
import simulation.Clock;

public class BritishVariant implements IVirus {
	
	//attributes
	public static final double deathProbTo18 = 0.01;
	public static final double deathProb18Above = 0.1;
	public static final double contagionProb = 0.7;

	@Override
	public double contagionProbability(Person p) {
		return contagionProb * p.contagionProbability();
	}
	
	@Override
	public boolean tryToContagion(Person p1, Person p2){
		if(p2.healthCondition() != "Sick") {
			double d = p1.distance(p2); // distance between 2 people
			if(contagionProbability(p2) * Math.min(1, 0.14 * Math.exp(2 - 0.25 * d)) < 1)
				return false; //??
			else 
				return true;
		}
		return false;
	}
	
	@Override
	public boolean tryToKill(Sick s) {
		double p ; //the probability to die according to age
		if(s.getAge() <= 18)
			p = deathProbTo18;
		else
			p= deathProb18Above;
		long t = Clock.now() - s.getContagiousTime(); //the time that passed since contagion 
		if(Math.max(0, p-0.01*p*Math.pow(t-15, 2)) != 1)
			return false; //???
		return true;
	}
}
