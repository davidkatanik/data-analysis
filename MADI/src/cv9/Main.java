package cv9;

import java.util.HashMap;
import java.util.Map;

import cv8.Utils;

//2,3,3,3,3,3,3,3,3  
public class Main {

	public static void main(String[] args) {
		Matrix matrix = new Matrix(Utils.readDataFromFile(), true);

//		matrix.printMatrix();
		
//		matrix = new Matrix(Utils.readDataFromFile(), true);
		
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
		
		StringBuilder sb = new StringBuilder();
		calculateFrequency.entrySet().forEach(f -> {
			sb.append(f.getKey().toString()+";"+f.getValue().toString()+"\n");
		});
		Utils.saveToCsvFile(sb.toString(), "cv9Freq.csv");
		StringBuilder sbb = new StringBuilder();
		calculateRelativeFrequency.entrySet().forEach(f -> {
			sbb.append(f.getKey().toString()+";"+f.getValue().toString()+"\n");
		});
		Utils.saveToCsvFile(sbb.toString(), "cv9RelFreq.csv");
		
		System.out.println("Closeness centrality");
		Map<Integer, Double> closenest = calculateClosenessCentrality(matrix);
		closenest.entrySet().forEach(System.out::println);
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
	
	private static Map<Integer, Integer> calculateFrequency(Matrix matrix) {
		Map<Integer, Integer> frequency = new HashMap<>();
		for (int i = 0; i < Matrix.SIZE; i++) {
			for (int j = i + 1; j < Matrix.SIZE; j++) {
				int value = matrix.getValue(i, j);
				if (frequency.containsKey(value)) {
					frequency.put(value, frequency.get(value) + 1);
				} else {
					frequency.put(value, 1);
				}
			}
		}
		return frequency;
	}

	private static Map<Integer, Double> calculateRelativeFrequency(Matrix matrix) {
		Map<Integer, Double> frequency = new HashMap<>();
		calculateFrequency(matrix).entrySet().forEach(e -> {
			frequency.put(e.getKey(), (e.getValue() / (double) Matrix.SIZE));
		});
		return frequency;
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
