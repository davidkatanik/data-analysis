package cv11;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

public class Main {

	public static void main(String[] args) {
		List<Entry<Integer, Integer>> initEdges = initEdges();

		List<Entry<Integer, Integer>> graph = initGraph(initEdges);
		List<Integer> degrees = initDegrees(initEdges);

		Integer numberOfNodes = 20;
		Integer numberOfGeneratedEdges = 3;
		generateBAModel(graph, degrees, numberOfNodes, numberOfGeneratedEdges);

		saveToFile("cv11.csv", graph);

		return;
	}

	private static void saveToFile(String string, List<Entry<Integer, Integer>> graph) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(new File(string)))) {
			for (Entry<Integer, Integer> entry : graph) {
				bw.write(entry.getKey() + ";" + entry.getValue());
				bw.newLine();
			}
			bw.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void generateBAModel(List<Entry<Integer, Integer>> graph, List<Integer> degrees, Integer numberOfNodes, Integer numberOfGeneratedNodes) {
		Random random = new Random();

		for (int j = degrees.get(degrees.size() - 1) + 1; j <= numberOfNodes; j++) {
			Set<Integer> randomNodes = new HashSet<>();
			for (int i = numberOfGeneratedNodes; i > 0;) {
				int indexOfNewNode = random.nextInt(degrees.size()) + 1;
				if (!randomNodes.contains(degrees.get(indexOfNewNode - 1)) && degrees.get(indexOfNewNode - 1) != j) {
					randomNodes.add(degrees.get(indexOfNewNode - 1));
					i--;
					int old = degrees.get(indexOfNewNode - 1);
					degrees.add(old);
					degrees.add(j);
					degrees.sort(Comparator.naturalOrder());
					graph.add(new AbstractMap.SimpleEntry<Integer, Integer>(old, j));

				}
			}
		}

	}

	private static List<Integer> initDegrees(List<Entry<Integer, Integer>> initEdges) {
		List<Integer> result = new LinkedList<>();
		for (Entry<Integer, Integer> entry : initEdges) {
			result.add(entry.getValue());
			result.add(entry.getKey());
		}
		result.sort(Comparator.naturalOrder());
		return result;
	}

	private static List<Entry<Integer, Integer>> initEdges() {
		List<Entry<Integer, Integer>> result = new LinkedList<>();
		// 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 5, 5, 5, 5, 5, 6, 6, 7, 7,
		// 7, 7, 7, 7, 8, 9, 10, 10, 10
		result.add(new AbstractMap.SimpleEntry<Integer, Integer>(1, 2));
		result.add(new AbstractMap.SimpleEntry<Integer, Integer>(1, 3));
		result.add(new AbstractMap.SimpleEntry<Integer, Integer>(1, 4));
		result.add(new AbstractMap.SimpleEntry<Integer, Integer>(1, 6));
		result.add(new AbstractMap.SimpleEntry<Integer, Integer>(1, 10));
		result.add(new AbstractMap.SimpleEntry<Integer, Integer>(2, 3));
		result.add(new AbstractMap.SimpleEntry<Integer, Integer>(2, 7));
		result.add(new AbstractMap.SimpleEntry<Integer, Integer>(3, 5));
		result.add(new AbstractMap.SimpleEntry<Integer, Integer>(5, 6));
		result.add(new AbstractMap.SimpleEntry<Integer, Integer>(5, 7));
		result.add(new AbstractMap.SimpleEntry<Integer, Integer>(5, 10));
		result.add(new AbstractMap.SimpleEntry<Integer, Integer>(7, 8));
		result.add(new AbstractMap.SimpleEntry<Integer, Integer>(7, 9));
		result.add(new AbstractMap.SimpleEntry<Integer, Integer>(7, 10));
		return result;
	}

	private static List<Entry<Integer, Integer>> initGraph(List<Entry<Integer, Integer>> initEdges) {
		List<Entry<Integer, Integer>> result = new LinkedList<>();
		result.addAll(initEdges);
		return result;
	}

}
