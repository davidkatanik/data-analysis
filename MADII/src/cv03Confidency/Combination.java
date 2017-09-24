/**
 * 
 */
package cv03Confidency;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @author David Katanik
 *
 * @version 1.0 from 28. 2. 2017
 *
 */
public class Combination {
	Set<Integer> combination = new LinkedHashSet<>();
	
	/**
	 * 
	 */
	public Combination(Set<Integer> combination) {
		this.combination = combination;
	}

	/**
	 * @param list
	 */
	public Combination(List<Integer> list) {
		combination = new LinkedHashSet<>(list);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return combination.toString();
	}
}
