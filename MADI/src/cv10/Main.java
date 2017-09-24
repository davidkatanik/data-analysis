package cv10;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

// 2,3,3,3,3,3,3,3 
public class Main {

	private static final int SIZE = Matrix.SIZE;

	public static void main(String[] args) {
		Matrix matrix = new Matrix(Utils.readDataFromFile(), false, Matrix.SIZE);

		List<Double> clusteringCoefficient = clusteringCoefficient(matrix);
		System.out.println("Clustering coefficient for all nodes");
		clusteringCoefficient.forEach(System.out::println);
		System.out.println("Average clustering coefficient " + clusteringCoefficient.stream().mapToDouble(v -> v).average().getAsDouble());
		
		
		System.out.println("Ukol 2: ----------------");
		Matrix first = new Matrix(SIZE);
		Matrix second = new Matrix(SIZE);
		Matrix third = new Matrix(SIZE);
		fillMatrixWithProbability(first, 0.2d);
		fillMatrixWithProbability(second, 0.5d);
		fillMatrixWithProbability(third, 0.8d);
		
		makeAllKnownOprations(first);
		makeAllKnownOprations(second);
		makeAllKnownOprations(third);
		
		saveToFile(first, "first");
		saveToFile(second, "second");
		saveToFile(third, "third");
		
		makeAllKnowOperationsForWharshall(new Matrix(Utils.readDataFromFile("first.csv"), true, SIZE));
		makeAllKnowOperationsForWharshall(new Matrix(Utils.readDataFromFile("second.csv"), true, SIZE));
		makeAllKnowOperationsForWharshall(new Matrix(Utils.readDataFromFile("third.csv"), true, SIZE));
	}

	private static void makeAllKnowOperationsForWharshall(Matrix matrix) {
		Matrix floydWarshall = floydWarshall(matrix);
		floydWarshall.printMatrix();
		
		System.out.println("Average");
		System.out.println(calculateAverage(matrix));
		System.out.println("Average distance");
		System.out.println(caluclateAverageDistance(matrix));
		
		System.out.println("Absolute frequency");
		Map<Integer, Integer> calculateFrequency = calculateFrequency(matrix);
		calculateFrequency.entrySet().forEach(System.out::println);

		System.out.println("Relative frequency");
		Map<Integer, Double> calculateRelativeFrequency = calculateRelativeFrequency(matrix);
		calculateRelativeFrequency.entrySet().forEach(System.out::println);
		
		System.out.println("Closeness centrality");
		Map<Integer, Double> closenest = calculateClosenessCentrality(matrix);
		closenest.entrySet().forEach(System.out::println);
	}

	private static void saveToFile(Matrix first, String string) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(new File(string+".csv")))) {
			for (int i = 0; i < SIZE; i++) {
				for (int j = i; j < SIZE; j++) {
					int value = first.getValue(i, j);
					if (value == 1) {
						bw.write((i+1)+";"+(j+1));
						if (i != 39)
							bw.newLine();
						
					}
				}
			}
			bw.flush();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	private static void makeAllKnownOprations(Matrix matrix) {
		// CV 8 
		matrix.printMatrix();

		System.out.println("Maximum: " + getMaximum(matrix));
		System.out.println("Minumum: " + getMinimum(matrix));
		System.out.println("Avrage: " + getAvrage(matrix));

		System.out.println("Absolute frequency");
		Map<Integer, Integer> calculateFrequency = calculateFrequency(matrix);
		calculateFrequency.entrySet().forEach(System.out::println);

		System.out.println("Relative frequency");
		Map<Integer, Double> calculateRelativeFrequency = calculateRelativeFrequency(matrix);
		calculateRelativeFrequency.entrySet().forEach(System.out::println);
	}

	private static void fillMatrixWithProbability(Matrix first, double d) {
		Random r = new Random();
		for (int i = 0; i < SIZE; i++) {
	        for (int j = 0; j < SIZE; j++) {
	        	if (i == j)
	        		continue;
	        	if (r.nextDouble() < d){
	        		first.setValue(i, j, 1);
	        		first.setValue(j, i, 1);
	        	}
	        }
		}
	}

	private static List<Double> clusteringCoefficient(Matrix matrix) {
		List<Double> result = new ArrayList<>();
		for (int i = 0; i < Matrix.SIZE; i++) {
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
		// i = 0, 1,3 -

		// 0 1 0 1
		// 1 0 1 1
		// 0 1 0 0
		// 1 1 0 0

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
	
	
	
	
	
	
	public static int getMaximum(Matrix matrix) {
		int maximum = 0;
		for (int i = 0; i < Matrix.SIZE; i++) {
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
		int minimum = Matrix.SIZE;
		for (int i = 0; i < Matrix.SIZE; i++) {
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

	public static double getAvrage(Matrix matrix) {
		double sum = 0;
		for (int i = 0; i < Matrix.SIZE; i++) {
			for (int j : matrix.getRow(i)) {
				sum += j;
			}
		}
		return sum / (double) Matrix.SIZE;
	}

	public static int getDegree(Matrix matrix, int x) {
		int degree = 0;
		for (int j : matrix.getRow(x)) {
			degree += j;
		}
		return degree;
	}

	public static Map<Integer, Integer> calculateFrequency(Matrix matrix) {
		Map<Integer, Integer> frequency = new HashMap<>();
		for (int i = 0; i < Matrix.SIZE; i++) {
			int degree = getDegree(matrix, i);
			if (frequency.containsKey(degree)) {
				frequency.put(degree, frequency.get(degree) + 1);
			} else {
				frequency.put(degree, 1);
			}
		}
		return frequency;
	}

	public static Map<Integer, Double> calculateRelativeFrequency(Matrix matrix) {
		Map<Integer, Double> frequency = new HashMap<>();
		calculateFrequency(matrix).entrySet().forEach(e -> {
			frequency.put(e.getKey(), (e.getValue() / (double) Matrix.SIZE));
		});
		return frequency;
	}

	
	/**
	 * Floyd-Warshall algorithm. Finds all shortest paths among all pairs of nodes
	 * @param d matrix of distances (Integer.MAX_VALUE represents positive infinity)
	 * @return matrix of predecessors
	 */
	public static Matrix floydWarshall(Matrix d) {
	    Matrix p = constructInitialMatixOfPredecessors(d);
	    for (int k = 0; k < Matrix.SIZE; k++) {
	        for (int i = 0; i < Matrix.SIZE; i++) {
	            for (int j = 0; j < Matrix.SIZE; j++) {
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
	    Matrix p = new Matrix(Matrix.SIZE);
	    
	    for (int i = 0; i < Matrix.SIZE; i++) {
	        for (int j = 0; j < Matrix.SIZE; j++) {
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
	
	private static double caluclateAverageDistance(Matrix matrix) {
		int sum = 0;
		for (int i = 0; i < Matrix.SIZE; i++) {
	        for (int j = i+1; j < Matrix.SIZE; j++) {
	        	sum += matrix.getValue(i, j);
	        }
	    }
		
		return (2.0 / (Matrix.SIZE*(Matrix.SIZE-1)))*sum;
	}
	
	private static int calculateAverage(Matrix matrix) {
		int average = 0;
		for (int i = 0; i < Matrix.SIZE; i++) {
	        for (int j = 0; j < Matrix.SIZE; j++) {
	        	int value = matrix.getValue(i, j);
	            if (value > average) {
	            	average = value;
	            }
	        }
	    }
		return average;
	}

	private static double calculateClosenessCentrality(Matrix matrix, int i) {
		int mean = 0;
		for (int j = 0; j < Matrix.SIZE; j++) {
			mean += matrix.getValue(i, j);
		}
		return (Matrix.SIZE/(double)mean);
	}
	
	private static Map<Integer, Double > calculateClosenessCentrality(Matrix matrix) {
		Map<Integer, Double> map = new HashMap<>();
		for (int i = 0; i < Matrix.SIZE; i++) {
			map.put(i+1, calculateClosenessCentrality(matrix, i));
		}
		return map;
	}
	
}
