package virus;

import population.Person;
import population.Sick;

/**
 * 
 * @author Yarden Hovav, Sharon Vazana
 *
 */
public interface IVirus {
	
	/**
	 * calculates probability of the Person getting infected
	 * 
	 * @param p - Person object
	 * @return the probability that person got sick
	 */
	public double contagionProbability(Person p);
	
	/**
	 * tries to infect the Person
	 * 
	 * @param p1 - Person object
	 * @param p2 - Person object
	 * @return true if the second person will get sick
	 */
	public boolean tryToContagion(Person p1, Person p2);
	
	/**
	 * calculates if the Person will die
	 * 
	 * @param s - sick object
	 * @return true if the person will die from the virus
	 */
	public boolean tryToKill(Sick s);

}
