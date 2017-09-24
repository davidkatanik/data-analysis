package cv02Apriori;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * 
 * @author David Katanik
 *
 * @version 1.0 from 15. 2. 2017
 * 
 * @see <a href=
 *      "http://www.code2learn.com/2015/02/frequent-itemsets-apriori-algorithm-and.html">Idea
 *      of algorithm</a>
 *
 */
public class FrequentItemSet {
	private static int possibleValues[];
	private static int attributesInCsv;

	private static final String CSV_FILE = "dataset22.csv";
	private static final String CSV_FILE_SPLITTER = ",";

	private static List<Set<Integer>> combinations;
	private static List<Set<Integer>> skipCombinationList = new ArrayList<>();

	public static void main(String[] args) {
		List<String[]> readDataFromFile = readDataFromFile();
		Map<Integer, Set<String>> transactionTable = loadTransactionTable(readDataFromFile);
		int desiredSupport = 40;

		Map<Set<Integer>, Double> supportedItems = generateSupportedItemSet(transactionTable, desiredSupport);

		printFrequentItemSet(supportedItems);
	}

	private static void printFrequentItemSet(Map<Set<Integer>, Double> supportedItems) {
		System.out.println("Supported item set");
		for (Entry<Set<Integer>, Double> supportedItem : supportedItems.entrySet()) {
			int numberOfComma = supportedItem.getKey().size() - 1;

			System.out.print("[");
			for (Integer key : supportedItem.getKey()) {
				System.out.print(key);
				if (numberOfComma > 0) {
					System.out.print(",");
					numberOfComma--;
				}
			}
			System.out.print("]");
			System.out.println(" : " + supportedItem.getValue());
		}
	}

	public static Map<Set<Integer>, Double> generateSupportedItemSet(Map<Integer, Set<String>> transactionTable, int support) {
		Map<Set<Integer>, Double> result = new LinkedHashMap<>();

		for (int i = 1; i <= attributesInCsv; i++) {
			int tmpValues[] = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
			combinations = new ArrayList<>();

			prepareCombinations(i, 0, tmpValues, 0, skipCombinationList);

			Map<Set<Integer>, Integer> itemCount = countItemsInTransactionTable(transactionTable);

			for (Entry<Set<Integer>, Integer> countedItems : itemCount.entrySet()) {
				double percentageSupport = (countedItems.getValue() / (double) transactionTable.size() * 100);
				if (percentageSupport > support) {
					result.put(countedItems.getKey(), percentageSupport);
				} else {
					skipCombinationList.add(countedItems.getKey());
				}
			}
		}

		return result;
	}

	public static Map<Set<Integer>, Integer> countItemsInTransactionTable(Map<Integer, Set<String>> transactionTable) {
		Map<Set<Integer>, Integer> result = new HashMap<>();

		for (Entry<Integer, Set<String>> transaction : transactionTable.entrySet()) {
			Set<String> transactionRow = transaction.getValue();

			for (Set<Integer> combination : combinations) {
				boolean combinationFits = true;

				for (Integer combineItem : combination) {
					if (!transactionRow.contains(combineItem.toString())) {
						combinationFits = false;
						break;
					}
				}

				if (combinationFits) {
					if (result.containsKey(combination)) {
						result.put(combination, result.get(combination) + 1);
					} else {
						result.put(combination, 1);
					}
				}
			}
		}

		return result;
	}

	public static Map<Integer, Set<String>> loadTransactionTable(List<String[]> readDataFromFile) {
		Map<Integer, Set<String>> transactionTable = new HashMap<>();
		int position = 1;

		for (String[] strings : readDataFromFile) {
			Set<String> tmpSet = new HashSet<>();
			int itemId = 1;

			for (String string : strings) {
				if (string.trim().equals("1")) {
					tmpSet.add(String.valueOf(itemId));
				}
				itemId++;
			}

			transactionTable.put(position, tmpSet);
			position++;
		}

		return transactionTable;
	}

	public static List<String[]> readDataFromFile() {
		String line = "";
		List<String[]> data = new ArrayList<>();

		try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE))) {
			while ((line = br.readLine()) != null) {
				String[] split = line.split(CSV_FILE_SPLITTER);

				attributesInCsv = split.length;
				data.add(split);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		possibleValues = new int[attributesInCsv];

		for (int i = 0; i < attributesInCsv; i++) {
			possibleValues[i] = i + 1;
		}

		return data;
	}

	public static void prepareCombinations(int size, int indexInData, int tmpArray[], int i, List<Set<Integer>> skip) {
		if (indexInData == size) {
			storeCombination(size, tmpArray);
			return;
		}

		if (i >= attributesInCsv) {
			return;
		}
		tmpArray[indexInData] = possibleValues[i];

		prepareCombinations(size, indexInData + 1, tmpArray, i + 1, skip);
		prepareCombinations(size, indexInData, tmpArray, i + 1, skip);
	}

	public static void storeCombination(int size, int[] data) {
		Set<Integer> combination = new HashSet<>();

		for (int j = 0; j < size; j++) {
			if (!skipCombination(data, size)) {
				combination.add(data[j]);
			}
		}

		combinations.add(combination);
	}

	public static boolean skipCombination(int[] data, int sizeOfData) {
		Set<Integer> dataSet = new HashSet<>();

		for (int i = 0; i < sizeOfData; i++) {
			dataSet.add(data[i]);
		}
		for (Set<Integer> combineItem : skipCombinationList) {
			if (combineItem.containsAll(dataSet)) {
				return true;
			}
		}

		return false;
	}
}
