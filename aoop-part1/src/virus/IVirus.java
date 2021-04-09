package virus;

import population.Person;
import population.Sick;

public interface IVirus {
	public double contagionProbability(Person p);
	public boolean tryToContagion(Person p1, Person p2);
	public boolean tryToKill(Sick s);
}
