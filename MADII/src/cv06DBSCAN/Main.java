/**
 * 
 */
package cv06DBSCAN;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

/**
 * @author David Katanik
 *
 * @version 1.0 from 21. 3. 2017
 *
 */
public class Main {
	private static final Double MAX_DISTANCE = 5D;
	private static final int MIN_POINTS = 30;

	public static void main(String[] args) {
		DBSCAN scan = new DBSCAN(MAX_DISTANCE, MIN_POINTS);
		List<DataSample> points = Utils.readDataFromFile();

		scan.setPoints(points);

		scan.cluster();

		List<Cluster> results = scan.getResults();
		List<DataSample> outliers = scan.outliers();
		System.out.println("Clusters " + results.size());
		saveClusters(results, outliers, "C:\\Users\\david\\Desktop\\ClusterViz\\outputDBSCAN.csv");

		System.out.println();

		List<DataSample> classed = createClasses(points, 500);

		calculateEntropy(results, classed);

		System.out.println("Intra-cluster to Inter-cluster distance ratio for medoids: " + intraInterClusterDistanceRatio(collectToOneList(results)));
	}

	/**
	 * @param results
	 * @return
	 */
	private static List<DataSample> collectToOneList(List<Cluster> results) {
		// TODO Auto-generated method stub
		List<DataSample> result = new LinkedList<>();
		for (Cluster cl : results) {
			result.addAll(cl.points);
		}
		return result;
	}

	private static Double intraInterClusterDistanceRatio(List<DataSample> clusters) {
		Double intra = 0d;
		int count = 0;

		List<Entry<DataSample, DataSample>> existingCombination = new LinkedList<>();
		for (DataSample dataSample : clusters) {
//			if (existingCombination.size() > 5000)
//				continue;
			for (DataSample dataSample2 : clusters) {
				if (dataSample.cluster != dataSample2.cluster || dataSample.equals(dataSample2)) {
					continue;
				} else if (checkIfExistsCombination(existingCombination, dataSample, dataSample2)) {
					continue;
				}

				existingCombination.add(new AbstractMap.SimpleEntry<>(dataSample, dataSample2));

				intra += dataSample.distance(dataSample2);
				count++;
			}
		}
		intra /= count;

		Double inter = 0d;
		existingCombination = new LinkedList<>();
		count = 0;

		for (DataSample dataSample : clusters) {
//			if (existingCombination.size() > 5000)
//				continue;
			for (DataSample dataSample2 : clusters) {
				if (dataSample.cluster == dataSample2.cluster || dataSample.equals(dataSample2)) {
					continue;
				}
				if (checkIfExistsCombination(existingCombination, dataSample, dataSample2)) {
					continue;
				}

				existingCombination.add(new AbstractMap.SimpleEntry<>(dataSample, dataSample2));

				inter += dataSample.distance(dataSample2);
				count++;

			}
		}

		inter /= count;

		return intra / inter;
	}

	private static boolean checkIfExistsCombination(List<Entry<DataSample, DataSample>> existingCombination, DataSample dataSample, DataSample dataSample2) {
		for (Entry<DataSample, DataSample> entry : existingCombination) {
			if (entry.getKey().equals(dataSample) && entry.getValue().equals(dataSample2)) {
				return true;
			}
			if (entry.getKey().equals(dataSample2) && entry.getValue().equals(dataSample)) {
				return true;
			}
		}
		return false;
	}

	private static List<DataSample> createClasses(List<DataSample> readDataFromFile, int j) {
		Comparator<DataSample> comparator = Comparator.comparing(DataSample::getX).thenComparing(DataSample::getY);
		List<DataSample> result = new ArrayList<>();
		result.addAll(readDataFromFile);

		result.sort(comparator);

		int i = 0;
		int cl = 0;
		for (DataSample dataSample : result) {
			if (i % j == 0) {
				cl++;
			}
			dataSample.cluster = cl;
			i++;
		}

		return result;

	}

	private static void calculateEntropy(List<Cluster> results, List<DataSample> classed) {
		Map<Integer, Integer> clusters = new HashMap<>();

		for (DataSample sample : classed) {
			if (clusters.containsKey(sample.cluster)) {
				clusters.put(sample.cluster, clusters.get(sample.cluster) + 1);
			} else {
				clusters.put(sample.cluster, 1);
			}
		}

		Double entropy = 0d;

		int n = clusters.values().stream().mapToInt(i -> i.intValue()).sum();

		for (Entry<Integer, Integer> clusterInfo : clusters.entrySet()) {
			List<DataSample> collect = classed.stream().filter(x -> x.cluster == clusterInfo.getKey()).collect(Collectors.toList());
			entropy += ((clusterInfo.getValue() / (double) n) * entropy(results, collect));
			System.out.println();
		}
		System.out.println("Entropy :" + entropy);
	}

	/**
	 * @param kMedian
	 * @param collect
	 * @return
	 */
	private static Double entropy(List<Cluster> results, List<DataSample> p) {
		Map<Integer, List<DataSample>> clusters = new HashMap<>();

		for (Cluster cl : results) {
			for (DataSample sample : cl.points) {
				if (clusters.containsKey(sample.cluster)) {
					List<DataSample> list = clusters.get(sample.cluster);
					list.add(sample);
					clusters.put(sample.cluster, list);
				} else {
					List<DataSample> list = new LinkedList<>();
					list.add(sample);
					clusters.put(sample.cluster, list);
				}
			}
		}

		Double result = 0d;

		for (List<DataSample> samples : clusters.values()) {
			List<DataSample> nij = intersection(samples, p);

			double x = nij.size() / (double) p.size();
			if (x == 0)
				continue;
			result += (x * (Math.log(x) / Math.log(2)));
		}

		return -result;
	}

	private static List<DataSample> intersection(List<DataSample> samples, List<DataSample> p) {
		List<DataSample> result = new LinkedList<>();
		for (DataSample dataSample : samples) {
			for (DataSample predicted : p) {
				if (dataSample.x == predicted.x && dataSample.y == predicted.y) {
					result.add(dataSample);
					break;
				}
			}
		}
		return result;
	}

	/**
	 * @param results
	 * @param outliers
	 * @param string
	 */
	private static void saveClusters(List<Cluster> results, List<DataSample> outliers, String string) {
		StringBuilder csv = new StringBuilder();
		for (Cluster cluster : results) {
			for (DataSample point : cluster.getPoints()) {
				csv.append(point.getX() + ";" + point.getY() + ";" + point.getCluster() + "\n");
			}
		}

		for (DataSample point : outliers) {
			csv.append(point.getX() + ";" + point.getY() + ";" + point.getCluster() + "\n");
		}

		Utils.saveToCsvFile(csv.toString(), string);
	}

}
