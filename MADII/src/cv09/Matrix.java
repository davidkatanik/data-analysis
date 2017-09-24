package cv09;

import java.util.List;

public class Matrix {

	private static final String CSV_FILE_SPLITTER = ";";

	double[][] matrix;

	private int size = 0;

	public Matrix(int size) {
		this.size = size;
		matrix = new double[size][size];
	}

	public Matrix(List<String> readDataFromFile) {
		this.size  = findMax(readDataFromFile) + 1;

		matrix = new double[size][size];
		
		readDataFromFile.forEach(rd -> {
			String[] split = rd.split(CSV_FILE_SPLITTER);
			int first = Integer.valueOf(split[0]);
			int second = Integer.valueOf(split[1]);

			setValue(first, second, 1);
			setValue(second, first, 1);

		});

	}

	/**
	 * @param readDataFromFile
	 */
	private int findMax(List<String> readDataFromFile) {
		int max = Integer.MIN_VALUE;
		for (String string : readDataFromFile) {
			String[] split = string.split(CSV_FILE_SPLITTER);
			int first = Integer.valueOf(split[0]);
			int second = Integer.valueOf(split[1]);
			if (first > max)
				max = first;
			if (second > max)
				max = first;
		}
		return max;
	}

	double getValue(int x, int y) {
		return matrix[x][y];
	}

	void setValue(int x, int y, double value) {
		matrix[x][y] = value;
	}

	double[] getRow(int x) {
		return matrix[x];
	}

	void printMatrix() {
		for (int i = 0; i < matrix.length; i++) { 
			for (int j = 0; j < matrix[i].length; j++) {
				System.out.print(matrix[i][j] + " "); 
			}
			System.out.println(""); 
		}
	}

	/**
	 * @return
	 */
	public int getSize() {
		return this.size;
	}
}
