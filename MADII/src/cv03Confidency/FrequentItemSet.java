package cv03Confidency;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 
 * @author David Katanik
 *
 * @version 1.0 from 28. 2. 2017
 * 
 * @see <a href=
 *      "http://www.code2learn.com/2015/02/frequent-itemsets-apriori-algorithm-and.html">Idea
 *      of algorithm</a>
 *
 */
public class FrequentItemSet {
	/**
	 * 
	 */
	private static final String DATASET_TRUE = "1";
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

		Map<Entry<Combination, Set<Integer>>, Double> calculateConfidency = calculateConfidency(supportedItems, readDataFromFile);
		
		System.out.println("Confidence map");
		System.out.println(calculateConfidency);
	}

	private static Map<Entry<Combination, Set<Integer>>, Double> calculateConfidency(Map<Set<Integer>, Double> supportedItems, List<String[]> readDataFromFile) {
		Map<Entry<Combination, Set<Integer>>, Double> map = new LinkedHashMap<>();

		for (Entry<Set<Integer>, Double> supportedItem : supportedItems.entrySet()) {
			Map<Combination, Set<Integer>> generateRullesWithOneRightSide = generateRullesWithOneRightSide(supportedItem.getKey());

			for (Entry<Combination, Set<Integer>> rule : generateRullesWithOneRightSide.entrySet()) {
				Integer supplyOfWholeRule = calculateSupply(rule.getKey().combination, readDataFromFile);
				Integer supplyOfReducedRule = calculateSupply(rule.getValue(), readDataFromFile);

				map.put(rule, supplyOfWholeRule / (double) supplyOfReducedRule);
			}

		}
		return map;
	}

	public static int calculateSupply(Set<Integer> suppliers, List<String[]> transactionTable) {
		int supplyCount = 0;

		for (String[] transactionRow : transactionTable) {
			boolean transactionFits = true;
			for (Integer supply : suppliers) {
				String string = transactionRow[supply - 1];
				if (!string.trim().equals(DATASET_TRUE)) {
					transactionFits = false;
					break;
				}
			}
			if (transactionFits) {
				supplyCount++;
			}

		}

		return supplyCount;
	}
	
	public static Map<Combination, Set<Integer>> generateRullesWithOneRightSide(Set<Integer> itemsSet) {
		Map<Combination, Set<Integer>> setCombination = new LinkedHashMap<>();
		if (itemsSet.size() < 2) {
			setCombination.put(new Combination(itemsSet), itemsSet);
			return setCombination;
		}
		
		Set<List<Integer>> result = new LinkedHashSet<>();
		permutingArray(itemsSet.stream().collect(Collectors.toList()), 0, result);

		
		for (List<Integer> list : result) {
			
			setCombination.put(new Combination(list), new LinkedHashSet<>(list.subList(0, list.size()-1)));
		}
		
		return setCombination;
	}

	public static void permutingArray(List<Integer> arrayList, int element, Set<List<Integer>> result) {
        for (int i = element; i < arrayList.size(); i++) {
            Collections.swap(arrayList, i, element);
            permutingArray(arrayList, element + 1, result);
            Collections.swap(arrayList, element, i);
        }
        if (element == arrayList.size() - 1) {
//            System.out.println(Arrays.toString(arrayList.toArray()));
        	List<Integer> clone = new ArrayList<Integer>(arrayList.size());
            for (Integer item : arrayList) clone.add(item);
        	result.add(clone);
        }
    }

	public static void printFrequentItemSet(Map<Set<Integer>, Double> supportedItems) {
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
				if (string.trim().equals(DATASET_TRUE)) {
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
