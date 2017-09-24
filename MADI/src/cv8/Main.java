package cv8;

import java.util.HashMap;
import java.util.Map;
// 2,3,3,3,3,3,3,3 
public class Main {

	public static void main(String[] args) {
		Matrix matrix = new Matrix(Utils.readDataFromFile());

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

		StringBuilder sb = new StringBuilder();
		calculateFrequency.entrySet().forEach(f -> {
			sb.append(f.getKey().toString()+";"+f.getValue().toString()+"\n");
		});
		//Utils.saveToCsvFile(sb.toString(), "cv8Freq.csv");
		StringBuilder sbb = new StringBuilder();
		calculateRelativeFrequency.entrySet().forEach(f -> {
			sbb.append(f.getKey().toString()+";"+f.getValue().toString()+"\n");
		});
		Utils.saveToCsvFile(sbb.toString(), "cv8RelFreq.csv");


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

}
