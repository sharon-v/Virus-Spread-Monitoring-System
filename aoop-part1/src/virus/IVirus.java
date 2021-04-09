package virus;

import population.Person;
import population.Sick;

public interface IVirus {
	
	/**
	 * 
	 * @param p - Person object
	 * @return The probability that person got sick
	 */
	public double contagionProbability(Person p);
	
	/**
	 * 
	 * @param p1 - Person object
	 * @param p2 - Person object
	 * @return true if the second person will get sick
	 */
	public boolean tryToContagion(Person p1, Person p2);
	
	/**
	 * 
	 * @param s - sick object
	 * @return true if the person will die from the virus
	 */
	public boolean tryToKill(Sick s);
}
