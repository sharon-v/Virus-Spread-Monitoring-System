package virus;

import population.Person;

public interface IVirus {
	public double contagionProbability(Person p);
	public boolean tryToContagion(Person p1, Person p2);
	public boolean tryToKill(Person p);
}
