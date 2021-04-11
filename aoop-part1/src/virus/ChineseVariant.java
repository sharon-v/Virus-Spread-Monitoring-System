package virus;

import population.Person;
import population.Sick;
import simulation.Clock;

public class ChineseVariant implements IVirus {
	
	//attributes
	public static final double deathProbTo18 = 0.0001;
	public static final double deathProb18To55	 = 0.05;
	public static final double deathProb55Above = 0.1;
	public static final double contagionProbTo18 = 0.2;
	public static final double contagionProb18To55 = 0.5;
	public static final double contagionProb55Above = 0.7;

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
		double randonNumber = Math.random()*2;
		if(p2.healthCondition() != "Sick") {
			double d = p1.distance(p2); // distance between 2 people
			if(contagionProbability(p2) * Math.min(1, 0.14 * Math.exp(2 - 0.25 * d)) > randonNumber)
				return true; 
			else 
				return false;
		}
//		throw new UnsupportedOperationException("You can't get sick twice man !!"); //where to catch this
		return false;
	}
	
	@Override
	public boolean tryToKill(Sick s) {
		double randonNumber = Math.random()*2;
		double p ; //the probability to die according to age
		if(s.getAge() <= 18)
			p = deathProbTo18;
		else if(s.getAge() <= 55)
			p = deathProb18To55;
		else 
			p = deathProb55Above; 
		long t = Clock.now() - s.getContagiousTime(); //the time that passed since contagion 
		if(Math.max(0, p - 0.01 * p * Math.pow(t  -15, 2)) > randonNumber)
			return true; 
		return false;
	}
}
