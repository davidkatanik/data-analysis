package cv10a;

import java.util.ArrayList;
import java.util.List;

public class NClique {

	public void calculateNClique(final int[][] data, final int n) {
		List<int[]> validCliques = new ArrayList<>();

		// aktualni kombinace
		// https://en.wikipedia.org/wiki/Clique_problem
		int[] currentCombination = new int[n];

		// oznacuje pozici kdo se ma posunout kdyz dojedeme na konec
		int nn = n - 1;

		// pocatecni kombinace
		for (int i = 0; i < n; i++) {
			currentCombination[i] = i;
		}

		// dokud "prvni" vrchol z kliky neni na posledni mozne pozici - vsechny kombinace
		while (currentCombination[0] < data.length - n - 1) {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < n; i++) {
				sb.append(currentCombination[i]);
			}
			// aktualni kombinace
			System.out.println(sb.toString());
			
			boolean validComb = true;
			
			// overit degree, jinak dalsi kombinace
			for (int i = 0; i < currentCombination.length; i++) {
				if (!degreeValidation(data, n, currentCombination[i])) {
					validComb = false;
					break;
				}
			}

			// overeni kliky
			int sum = 0;
			for (int i = 0; i < currentCombination.length && validComb; i++) {
				for (int j = 0; j < currentCombination.length; j++) {
					sum += data[currentCombination[i]][currentCombination[j]];
				}
			}
			
			if (sum < n) {
				// nejsou propojene
				validComb = false;
			} else {
				validComb = true;
			}
			
			// pokud je kombinace klika, uloz ji
			if (validComb) {
				int[] newCombination = new int[n];
				for (int i = 0; i < currentCombination.length; i++) {
					newCombination[i] = currentCombination[i];
				}
				validCliques.add(newCombination);
			}
			
			if (currentCombination[currentCombination.length - 1] >= data.length - 1) {
				if (nn == 0) {
					currentCombination[nn]++;
					for (int i = 1; i < currentCombination.length; i++) {
						currentCombination[i] = currentCombination[i - 1] + 1; 
					}
					nn = currentCombination.length - 1;
				} else {
					nn--;
				}
				currentCombination[nn]++;
				currentCombination[currentCombination.length - 1] = currentCombination[currentCombination.length - 2] + 1;
			} else {
				currentCombination[currentCombination.length - 1]++;
			}
		}
		
		// vypis
		StringBuilder sb = new StringBuilder();
		for (int[] candidate : validCliques) {
			for (int i = 0; i < candidate.length; i++) {
				sb.append(candidate[i]);
				sb.append(" ");
			}
			System.out.println("Cbn: " + sb.toString());
			sb.setLength(0);
		}
	}
	
	private boolean degreeValidation(final int[][] data, final int n, final int index) {
		int degree = 0;

		for (int i = 0; i < data.length; i++) {
			degree += data[index][i];
		}

		return degree >= n;
	}

}
