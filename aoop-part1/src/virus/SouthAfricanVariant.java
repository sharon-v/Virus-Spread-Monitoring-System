package virus;

import population.Person;
import population.Sick;
import simulation.Clock;

public class SouthAfricanVariant implements IVirus {
	public static final double deathProbTo18 = 0.05;
	public static final double deathProb18Above = 0.08;
	public static final double contagionProbTo18 = 0.6;
	public static final double contagionProb18Above = 0.5;

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
//		if(p2.healthCondition() != "Sick") {
//			double d = p1.distance(p2); // distance between 2 people
//			if(contagionProbability(p2) * Math.min(1, 0.14 * Math.exp(2 - 0.25 * d)) != 1)
//				return false; //??
//			else 
//				return true;
//		}
//		return false;
	}
	
	@Override
	public boolean tryToKill(Sick s) {
//		double p ; //the probability to die according to age
//		if(s.getAge() <= 18)
//			p = deathProbTo18;
//		else
//			p= deathProb18Above;
//		long t = Clock.now() - s.getContagiousTime(); //the time that passed since contagion 
//		if(Math.max(0, p-0.01*p*Math.pow(t-15, 2)) != 1)
//			return false; //???
//		return true;
	}
}
