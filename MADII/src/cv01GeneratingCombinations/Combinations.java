package cv01GeneratingCombinations;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author David Katanik
 * 
 * @version 1.0
 *
 */
public class Combinations {
	private static final int CSV_COLUMNS_LENGTH = 8;
	private static final int POSSIBLE_VALUES[] = { 1, 2, 3, 4, 5, 6, 7, 8 };
	private static final String CSV_FILE = "dataset.csv";
	private static final String CSV_FILE_SPLITTER = ",";

	public static void main(String[] args) {
		List<String[]> readDataFromFile = readDataFromFile();
		int frequencies[] = new int[CSV_COLUMNS_LENGTH];

		for (String[] strings : readDataFromFile) {
			int position = 0;
			for (String string : strings) {
				if (string.trim().equalsIgnoreCase("1")) {
					frequencies[position]++;
				}
				position++;
			}
		}

		System.out.println("Frequencies");
		for (int j = 0; j < frequencies.length; j++) {
			int i = frequencies[j];
			System.out.print(j + ":" + i + "\t");
		}

		System.out.println();

		int tmpValues[] = { 1, 2, 3, 4, 5, 6, 7, 8 };
		int size = 5;
		combine(size, 0, tmpValues, 0);

	}

	static void combine(int size, int indexInData, int tmpArray[], int i) {
		// vypsat kombinaci
		if (indexInData == size) {
			printCombination(size, tmpArray);
			return;
		}

		// preteceni?
		if (i >= CSV_COLUMNS_LENGTH)
			return;

		// novy zmen index a pretec
		tmpArray[indexInData] = POSSIBLE_VALUES[i];
		combine(size, indexInData + 1, tmpArray, i + 1);

		// zahrnuty nemen index
		combine(size, indexInData, tmpArray, i + 1);
	}

	private static void printCombination(int size, int[] data) {
		for (int j = 0; j < size; j++)
			System.out.print(data[j] + " ");
		System.out.println("");
		return;
	}

	public static List<String[]> readDataFromFile() {
		String line = "";
		List<String[]> data = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE))) {
			while ((line = br.readLine()) != null) {
				data.add(line.split(CSV_FILE_SPLITTER));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}

}
