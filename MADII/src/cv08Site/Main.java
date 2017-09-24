package cv08Site;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Main {

	public static void main(String[] args) {
		List<String> readDataFromFile = Utils.readDataFromFile();
		Matrix matrix = new Matrix(readDataFromFile, false);
		Matrix floydWarshall = new Matrix(readDataFromFile, true);
		floydWarshall(floydWarshall);

//		matrix.printMatrix();

		System.out.println("Average degree: " + getAvrage(matrix));
		System.out.println("Average degree: " + getMinimum(matrix));
		System.out.println("Maximum degree: " + getMaximum(matrix));

		List<Double> clusteringCoefficient = clusteringCoefficient(matrix);
		System.out.println("Average clustering coefficient " + clusteringCoefficient.stream().mapToDouble(v -> v).average().getAsDouble()/2);
		
		System.out.println("Average distance floyd: " + caluclateAverageDistance(floydWarshall));

		//System.out.println("Average: " + calculateAverage(matrix));
		System.out.println("Average floyd: " + calculateAverage(floydWarshall));
		
		
		//System.out.println("Closeness centrality");
		Map<Integer, Double> closenest = calculateClosenessCentrality(floydWarshall);
		//closenest.entrySet().forEach(System.out::println);
		System.out.println("Average closeness centrality :" + closenest.values().stream().mapToDouble(x->x.doubleValue()).average().getAsDouble());
		
	}
	
	public static int getMaximum(Matrix matrix) {
		int maximum = 0;
		for (int i = 0; i < matrix.getSize(); i++) {
			int tmpMax = 0;
			for (int j : matrix.getRow(i)) {
				tmpMax += j;
			}
			if (maximum < tmpMax) {
				maximum = tmpMax;
			}
		}
		return maximum;
	}
	
	public static int getMinimum(Matrix matrix) {
		int minimum = matrix.getSize();
		for (int i = 0; i < matrix.getSize(); i++) {
			int tmpMin = 0;
			for (int j : matrix.getRow(i)) {
				tmpMin += j;
			}
			if (minimum > tmpMin) {
				minimum = tmpMin;
			}
		}
		return minimum;
	}
	
	/**
	 * Floyd-Warshall algorithm. Finds all shortest paths among all pairs of nodes
	 * @param d matrix of distances (Integer.MAX_VALUE represents positive infinity)
	 * @return matrix of predecessors
	 */
	public static Matrix floydWarshall(Matrix d) {
	    Matrix p = constructInitialMatixOfPredecessors(d);
	    for (int k = 0; k < d.getSize(); k++) {
	        for (int i = 0; i < d.getSize(); i++) {
	            for (int j = 0; j < d.getSize(); j++) {
	                if (d.getValue(i, k) == Integer.MAX_VALUE || d.getValue(k, j) == Integer.MAX_VALUE) {
	                    continue;                  
	                }
	                
	                if (d.getValue(i, j) > (d.getValue(i, k) + d.getValue(k, j))) {
	                	d.setValue(i, j, d.getValue(i, k) + d.getValue(k, j));
	                    p.setValue(i, j, p.getValue(k, j));
	                }

	            }
	        }
	    }
	    return d;
	}

	/**
	 * Constructs matrix P0
	 * @param d matrix of lengths
	 * @return P0
	 */
	private static Matrix constructInitialMatixOfPredecessors(Matrix d) {
	    Matrix p = new Matrix(d.getSize());
	    
	    for (int i = 0; i < d.getSize(); i++) {
	        for (int j = 0; j < d.getSize(); j++) {
	        	int value = d.getValue(i, j);
	            if (value != 0 && value != Integer.MAX_VALUE) {
	                p.setValue(i, j, i);
	            } else {
	            	p.setValue(i, j, -1);
	            }
	        }
	    }
	    return p;
	}
	
	public static double getAvrage(Matrix matrix) {
		double sum = 0;
		for (int i = 0; i < matrix.getSize(); i++) {
			for (int j : matrix.getRow(i)) {
				sum += j;
			}
		}
		return sum / (double) matrix.getSize();
	}

	private static double caluclateAverageDistance(Matrix matrix) {
		int sum = 0;
		for (int i = 0; i < matrix.getSize(); i++) {
	        for (int j = i+1; j < matrix.getSize(); j++) {
	        	sum += matrix.getValue(i, j);
	        }
	    }
		
		return (2.0 / (matrix.getSize()*(matrix.getSize()-1)))*sum;
	}
	
	private static int calculateAverage(Matrix matrix) {
		int average = 0;
		for (int i = 0; i < matrix.getSize(); i++) {
	        for (int j = 0; j < matrix.getSize(); j++) {
	        	int value = matrix.getValue(i, j);
	            if (value > average) {
	            	average = value;
	            }
	        }
	    }
		return average;
	}

	public static int getDegree(Matrix matrix, int x) {
		int degree = 0;
		for (int j : matrix.getRow(x)) {
			degree += j;
		}
		return degree;
	}
	
	private static List<Double> clusteringCoefficient(Matrix matrix) {
		List<Double> result = new ArrayList<>();
		for (int i = 0; i < matrix.getSize(); i++) {
			result.add(calculateSimpleCoeficient(i, matrix));
		}
		return result;
	}

	private static double calculateSimpleCoeficient(int i, Matrix matrix) {
		int triangles = 0;
		Set<Integer> set = new HashSet<>();
		int[] row = matrix.getRow(i);
		for (int k = 0; k < row.length; k++) {
			if (row[k] == 1) {
				set.add(k);
			}
		}
		for (Integer neigh : set) {
			int[] neighRow = matrix.getRow(neigh);
			for (int k = 0; k < neighRow.length; k++) {
				if (neighRow[k] == 1 && k > neigh && i != neigh && k != neigh && set.contains(k)) {
					triangles += 1;
				}
			}
		}
		if (set.isEmpty() || triangles == 0)
			return 0;
		return (2 * triangles) / (double) (set.size() * (set.size() - 1));
	}
	
	private static Map<Integer, Double > calculateClosenessCentrality(Matrix matrix) {
		Map<Integer, Double> map = new HashMap<>();
		for (int i = 0; i < matrix.getSize(); i++) {
			map.put(i+1, calculateClosenessCentrality(matrix, i));
		}
		return map;
	}
	
	private static double calculateClosenessCentrality(Matrix matrix, int i) {
		int mean = 0;
		for (int j = 0; j < matrix.getSize(); j++) {
			mean += matrix.getValue(i, j);
		}
		return (matrix.getSize()/(double)mean);
	}

}
