package cv08Site;

import java.util.List;

public class Matrix {

	private static final String CSV_FILE_SPLITTER = ";";

	int[][] matrix;

	private int size = 0;

	public Matrix(int size) {
		this.size = size;
		matrix = new int[size][size];
	}

	public Matrix(List<String> readDataFromFile, boolean b) {
		this.size  = findMax(readDataFromFile) + 1;

		matrix = new int[size][size];
		
		for (int i = 0; i < matrix.length; i++)
			for (int j = 0; j < matrix[i].length; j++) {
				if (i == j)
					matrix[i][j] = 0;
				else
					if (b)
						matrix[i][j] = Integer.MAX_VALUE;
					else
						matrix[i][j] = 0;
			}

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

	int getValue(int x, int y) {
		return matrix[x][y];
	}

	void setValue(int x, int y, int value) {
		matrix[x][y] = value;
	}

	int[] getRow(int x) {
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
