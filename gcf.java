import java.util.ArrayList;
import java.util.HashMap;
import java.lang.Math;

class Gcf {

	boolean isPrime(int integer) {
		for (int i = 2; i < integer; ++i) {
			if ((integer % i) == 0) {
				return false;
			}
		}
		return true;
	}

	int getNextPrime(int integer) {
		if (integer < 3)
			return ++integer;

		while (isPrime(++integer) != true) {
			// do nothing here
		}
		return integer;
	}

	HashMap<Integer, Integer> findFactors(int integer) {
		// The result is histogram of primes. For example, the 20 is factored to primes 2 * 2 * 5.
		// This will make a histogram like that:
		// ____________
		// key | value
		// ------------
		//  2  | 2      because there are two 2s
		//  5  | 1      because there is one 5
		HashMap<Integer, Integer> result = new HashMap<Integer, Integer>();

		if (integer == 0)
			return result;

		// All integers have 1 also as a factor
		result.put(1, 1);
		int prime = 2;
		while (integer != 1) {
			if ((integer % prime) == 0) {
				integer /= prime;
				// Insert if not present, otherwise increment the histogram value
				int count = result.containsKey(prime) ? result.get(prime) : 0;
				result.put(prime, count + 1);
			}
			else {
				prime = getNextPrime(prime);
			}
		}
		return result;
	}

	int getGreatestCommonFactor(ArrayList<Integer> integers) {
		// First factor all the numbers to primes
		ArrayList<HashMap<Integer, Integer>> factoredIntegers = new ArrayList<HashMap<Integer, Integer>>();
		for (Integer i : integers) {
			factoredIntegers.add(findFactors(i));
		}

		// Find all the common factors
		HashMap<Integer,Integer> commonFactors = new HashMap<Integer,Integer>();
		boolean firstPatch = true;
		for (HashMap<Integer,Integer> map : factoredIntegers) {
			// FIRSTLY, iterate over factored integer (the 'map') and update commonFactors
			for (HashMap.Entry<Integer, Integer> pair : map.entrySet()) {
				Integer key = pair.getKey();
				Integer value = pair.getValue();
				// If it is first iteration, then add all items
				if (firstPatch) {
					commonFactors.put(key, value);
				}
				// One every next iteration, only update the commonFactors only if the key is already present
				else {
					int count = commonFactors.containsKey(key) ? commonFactors.get(key) : 0;
					// If the factor is nor present already, it cannot be common for all integers, so skip it right away
					if (count == 0)
						continue;

					// If the new one has less occurences of the factor, use the new value (according to GCF algorithm)
					if (count > value) {
						commonFactors.put(key, value);
					}
				}
			}
			// SECONDLT, make a reverse search and remove all those keys from commonFactors that are not present in 'map'
			for (HashMap.Entry<Integer, Integer> pair : commonFactors.entrySet()) {
				Integer key = pair.getKey();
				if (map.containsKey(key) == false)
					commonFactors.keySet().removeIf(k -> k == key);
			}
			firstPatch = false;
		}

		// Multiply all the common factors to get the Greatest Common Factor
		double result = 1;
		for (HashMap.Entry<Integer, Integer> pair : commonFactors.entrySet()) {
			Integer key = pair.getKey();
			Integer value = pair.getValue();
			result *= Math.pow(key.doubleValue(), value.doubleValue());
		}

		return (int)result;
	}

	public static void main(String args[]) {
		Gcf gcf = new Gcf();

		ArrayList<Integer> integers = new ArrayList<Integer>();
		integers.add(2);
		integers.add(36);
		integers.add(72);
		System.out.print("\nIntegers are: ");
		for (Integer i : integers) {
			System.out.print(i + " ");
		}
		System.out.println("\nGreatest common factor is: " + gcf.getGreatestCommonFactor(integers));
	}
}
