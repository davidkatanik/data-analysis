/**
 * 
 */
package cv04MedoidMedian;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

/**
 * @author David Katanik
 *
 * @version 1.0 from 8. 3. 2017
 *
 */
public class Main {

	/**
	 * 
	 */
	private static final int N = 100;
	/**
	 * 
	 */
	private static final int K = 3;
	private static final boolean GENERATE = true;

	public static void main(String[] args) {
		List<DataSample> readDataFromFile = Utils.readDataFromFile();

		Double[][] matrix = new Double[readDataFromFile.size()][readDataFromFile.size()];

		calculateDistances(matrix, readDataFromFile);

		List<DataSample> kMedoids = doKMedoids(readDataFromFile, matrix, K);

		StringBuilder csv1 = new StringBuilder();
		for (DataSample dataSample : kMedoids) {
			csv1.append(dataSample.x + ";" + dataSample.y + ";" + dataSample.cluster + "\n");
		}

		Utils.saveToCsvFile(csv1.toString(), "outputMedoid.csv");
		System.out.println("kmedoid done");
		List<DataSample> kMedian = doKMedian(readDataFromFile, matrix, K);

		StringBuilder csv2 = new StringBuilder();
		for (DataSample dataSample : kMedian) {
			csv2.append(dataSample.x + ";" + dataSample.y + ";" + dataSample.cluster + "\n");
		}

		Utils.saveToCsvFile(csv2.toString(), "outputMedian.csv");

		System.out.println("kmedian done");
		// kMedian.stream().filter(x -> x.centroid ==
		// true).forEach(System.out::println);
		// System.out.println();
		// kMedoids.stream().filter(x -> x.centroid ==
		// true).forEach(System.out::println);

//		System.out.println("SSQ for kmedoids :" + sse(kMedoids));
//		System.out.println("SSQ for kmedians :" + sse(kMedian));

		System.out.println("Intra-cluster to Inter-cluster distance ratio for medoids: " + intraInterClusterDistanceRatio(kMedoids));
		System.out.println("Intra-cluster to Inter-cluster distance ratio for median: " + intraInterClusterDistanceRatio(kMedian));
		System.out.println();

		List<DataSample> classed = createClasses(readDataFromFile, N);

		calculateEntropy(kMedoids, classed);
		calculateEntropy(kMedian, classed);

	}

	private static void calculateEntropy(List<DataSample> kMedian, List<DataSample> classed) {
		Map<Integer, Integer> clusters = new HashMap<>();
		//kMedian = kMedian.stream().filter(x -> x.centroid == false).collect(Collectors.toList());

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
			entropy += ((clusterInfo.getValue() / (double) n) * entropy(kMedian, collect));
			System.out.println();
		}
		System.out.println("Entropy :" + entropy);
	}

	/**
	 * @param kMedian
	 * @param collect
	 * @return
	 */
	private static Double entropy(List<DataSample> n, List<DataSample> p) {
		Map<Integer, List<DataSample>> clusters = new HashMap<>();

		for (DataSample sample : n) {
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

		Double result = 0d;

		for (List<DataSample> samples : clusters.values()) {
			List<DataSample> nij = intersection(samples, p);

			double x = nij.size() / (double) p.size();
			if (x == 0)
				continue;
			result += (x * (Math.log(x) / (double)Math.log(2)));
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

	/**
	 * @param kMedoids
	 * @return
	 */
	private static Double sse(List<DataSample> kMedoids) {
		Map<Integer, DataSample> centroids = new HashMap<>();

		for (DataSample dataSample : kMedoids) {
			if (dataSample.centroid) {
				centroids.put(dataSample.cluster, dataSample);
			}
		}

		Double result = 0d;

		for (DataSample dataSample : kMedoids) {
			if (dataSample.centroid) {
				continue;
			}
			result += Math.pow(calculateEuclideanDistance(dataSample, centroids.get(dataSample.cluster)), 2);
		}

		return result;
	}

	private static Double intraInterClusterDistanceRatio(List<DataSample> clusters) {
		Double intra = 0d;
		Double inter = 0d;
		int count = 0;
		int count2 = 0;

		List<Entry<DataSample, DataSample>> existingCombination = new LinkedList<>();
		List<Entry<DataSample, DataSample>> existingCombination2 = new LinkedList<>();
		
		for (DataSample dataSample : clusters) {
			if (existingCombination.size() > 5000 && existingCombination2.size() > 5000)
				break;
			for (DataSample dataSample2 : clusters) {

				if (dataSample.equals(dataSample2))
					continue;
				
				
				if (dataSample.cluster != dataSample2.cluster) {
					if (!checkIfExistsCombination(existingCombination2, dataSample, dataSample2)) {
						existingCombination2.add(new AbstractMap.SimpleEntry<>(dataSample, dataSample2));

						inter += calculateEuclideanDistance(dataSample, dataSample2);
						count2++;
					}
				} else if (dataSample.cluster == dataSample2.cluster) {
					if (!checkIfExistsCombination(existingCombination, dataSample, dataSample2)) {
						existingCombination.add(new AbstractMap.SimpleEntry<>(dataSample, dataSample2));

						intra += calculateEuclideanDistance(dataSample, dataSample2);
						count++;
					}
				}

			}
		}
		intra /= count;		inter /= count2;


		return intra / inter;
	}

	private static boolean checkIfExistsCombination(List<Entry<DataSample, DataSample>> existingCombination, DataSample dataSample, DataSample dataSample2) {
		return existingCombination.parallelStream().filter(entry -> (entry.getKey().equals(dataSample) && entry.getValue().equals(dataSample2)) || (entry.getKey().equals(dataSample2) && entry.getValue().equals(dataSample))).findAny().isPresent();
	}

	/**
	 * @param readDataFromFile
	 * @param matrix
	 * @param k2
	 * @return
	 */
	private static List<DataSample> doKMedian(List<DataSample> data, Double[][] matrix, int k) {
		Map<Integer, Integer> randomCentroids = generateRandomCentroids(matrix, k);
		Map<Integer, DataSample> medians = new HashMap<>(k);

		List<DataSample> dataX = new ArrayList<>();
		dataX.addAll(data);

		for (Entry<Integer, Integer> e : randomCentroids.entrySet()) {
			DataSample dataSample = data.get(e.getValue());
			dataSample.cluster = e.getKey();
			medians.put(e.getKey(), dataSample);
		}

		List<DataSample> result = doKMedianLogic(dataX, matrix, medians);

		return result;
	}

	/**
	 * Do K medoids
	 * 
	 * @param matrix2
	 * @param k2
	 * @return
	 */
	private static List<DataSample> doKMedoids(List<DataSample> data, Double[][] matrix, int k) {
		Map<Integer, Integer> medoidsPosition = generateRandomCentroids(matrix, k);
		List<DataSample> result = null;
		try {
			result = doKMedoidsLogic(data, matrix, medoidsPosition);
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * @param data
	 * @param matrix
	 * @param matrix
	 * @param medians
	 * @return
	 * @throws CloneNotSupportedException
	 */
	private static List<DataSample> doKMedianLogic(List<DataSample> data, Double[][] matrix, Map<Integer, DataSample> medians) {
		boolean somethingChanged = true;
		int i = 0;
		while (somethingChanged && i < 5000) {
			somethingChanged = false;
			for (DataSample dataSample : data) {
				Integer cluster = findCluster(medians, dataSample);
				if (cluster != dataSample.cluster) {
					dataSample.cluster = cluster;
					somethingChanged = true;
				}
			}
			medians = regenerateMedians(data, K);
			i++;
		}
		assignMedians(data, medians);
		return data;
	}

	/**
	 * @param medians
	 * @param dataSample
	 * @return
	 */
	private static Integer findCluster(Map<Integer, DataSample> medians, DataSample dataSample) {
		int cluster = medians.keySet().stream().min(Comparator.naturalOrder()).get();
		Double minimumDistance = Double.MAX_VALUE;

		for (Entry<Integer, DataSample> iterable_element : medians.entrySet()) {
			Double calculateEuclideanDistance = calculateEuclideanDistance(dataSample, iterable_element.getValue());
			if (calculateEuclideanDistance < minimumDistance) {
				minimumDistance = calculateEuclideanDistance;
				cluster = iterable_element.getKey();
			}
		}

		return cluster;
	}

	/**
	 * Do k medoids bl logic
	 * 
	 * @param data
	 * @param matrix
	 * @param medoidsPosition
	 * @throws CloneNotSupportedException
	 */
	private static List<DataSample> doKMedoidsLogic(List<DataSample> data, Double[][] matrix, Map<Integer, Integer> medoidsPosition) throws CloneNotSupportedException {
		List<DataSample> clusteredData = new ArrayList<>(data.size());

		boolean somethingChanged = false;
		int dataSamplePosition = 0;
		for (DataSample dataSample : data) {
			DataSample clonedDS = (DataSample) dataSample.clone();

			clonedDS.cluster = findCluster(medoidsPosition, dataSample, dataSamplePosition, matrix);

			if (clonedDS.cluster.equals(dataSample.cluster)) {
				clusteredData.add(dataSample);
			} else {
				clusteredData.add(clonedDS);
				somethingChanged = true;
			}
			dataSamplePosition++;
		}

		if (!somethingChanged) {
			assignMedoids(data, medoidsPosition);
			return clusteredData;
		}

		medoidsPosition = regenerateMedoids(clusteredData, matrix);

		return doKMedoidsLogic(clusteredData, matrix, medoidsPosition);
	}

	/**
	 * @param data
	 * @param medoidsPosition
	 */
	private static void assignMedoids(List<DataSample> data, Map<Integer, Integer> medoidsPosition) {
		for (Integer dataSample : medoidsPosition.values()) {
			data.get(dataSample).centroid = true;
		}
		// TODO Auto-generated method stub

	}

	/**
	 * @param clusteredData
	 * @param medians
	 */
	private static void assignMedians(List<DataSample> clusteredData, Map<Integer, DataSample> medians) {
		int i = 1;
		for (Entry<Integer, DataSample> median : medians.entrySet()) {
			DataSample value = median.getValue();
			value.cluster = i++;
			value.centroid = true;
			clusteredData.add(value);
		}
	}

	/**
	 * @param clusteredData
	 * @param matrix
	 * @param k
	 * @return
	 */
	private static Map<Integer, DataSample> regenerateMedians(List<DataSample> clusteredData, int k) {
		Map<Integer, DataSample> result = new LinkedHashMap<>();

		Map<Integer, List<Entry<DataSample, Double>>> map = new LinkedHashMap<>();
		for (int i = 0; i < k; i++) {
			map.put(i + 1, new LinkedList<>());
		}
		for (DataSample sample : clusteredData) {
			Double sum = 0d;
			for (DataSample s2 : clusteredData) {
				if (sample.cluster == s2.cluster && !sample.equals(s2)) {
					continue;
				}
				sum += calculateEuclideanDistance(sample, s2);
			}
			List<Entry<DataSample, Double>> list = map.get(sample.cluster);
			list.add(new AbstractMap.SimpleEntry<DataSample, Double>(sample, sum));
			map.put(sample.cluster, list);
		}

		for (Entry<Integer, List<Entry<DataSample, Double>>> entry : map.entrySet()) {
			List<Entry<DataSample, Double>> entryList = entry.getValue();
			Collections.sort(entryList, (o1, o2) -> o1.getValue().compareTo(o2.getValue()));
			DataSample median = getMedian(entry.getValue());
			result.put(entry.getKey(), median);
		}

		return result;
	}

	/**
	 * @param list
	 * @return
	 */
	private static DataSample getMedian(List<Entry<DataSample, Double>> list) {
		int middle = list.size() / 2;
		if (list.size() % 2 == 1) {
			DataSample key = list.get(middle).getKey();
			// key.centroid=true;
			return key;
		} else {
			DataSample a = list.get(middle - 1).getKey();
			DataSample b = list.get(middle).getKey();

			DataSample result = new DataSample();
			result.x = (a.x + b.x) / 2.0;
			result.y = (a.y + b.y) / 2.0;

			// result.centroid = true;
			return result;
		}
	}

	/**
	 * regenrate medoids
	 * 
	 * @param clusteredData
	 * @param matrix
	 * @return
	 */
	private static Map<Integer, Integer> regenerateMedoids(List<DataSample> clusteredData, Double[][] matrix) {
		Map<Integer, Integer> result = new LinkedHashMap<>();
		Map<Integer, Double> tmp = new LinkedHashMap<>();
		int dsPosition = 0;
		for (DataSample dataSample : clusteredData) {
			Double temp = 0d;
			int ds2Position = 0;
			for (DataSample dataSample2 : clusteredData) {
				ds2Position++;
				if (!dataSample.cluster.equals(dataSample2.cluster))
					continue;
				temp += matrix[dsPosition][ds2Position - 1];
			}
			if (tmp.containsKey(dataSample.cluster)) {
				Double stored = tmp.get(dataSample.cluster);
				if (stored > temp) {
					result.put(dataSample.cluster, dsPosition);
					tmp.put(dataSample.cluster, temp);
				}
			} else {
				result.put(dataSample.cluster, dsPosition);
				tmp.put(dataSample.cluster, temp);
			}
			dsPosition++;
		}
		return result;
	}

	/**
	 * @param medoidsPosition
	 * @param dataSample
	 * @param dataSamplePosition
	 * @param matrix
	 * @return
	 */
	private static Integer findCluster(Map<Integer, Integer> medoidsPosition, DataSample dataSample, int dataSamplePosition, Double[][] matrix) {
		Entry<Integer, Integer> tmpMin = medoidsPosition.entrySet().stream().findFirst().get();
		double min = matrix[dataSamplePosition][tmpMin.getValue()];
		for (Entry<Integer, Integer> iterable_element : medoidsPosition.entrySet()) {
			Double tmp = matrix[dataSamplePosition][iterable_element.getValue()];
			if (tmp < min) {
				min = tmp;
				tmpMin = iterable_element;
			}
		}
		return tmpMin.getKey();
	}

	/**
	 * Returns K number of medoids
	 * 
	 * @param matrix
	 * @param k2
	 * @return
	 */
	private static Map<Integer, Integer> generateRandomCentroids(Double[][] matrix, int k) {
		Map<Integer, Integer> medoids = new LinkedHashMap<>();
		if (!GENERATE) {
			int position = 1;
			for (int i = 0; i < k * 10; i += 10) {
				medoids.put(position, i);
				position++;
			}
		} else {
			for (int j = 0; j < k;) {
				int first = 0 + (int) (Math.random() * matrix.length);
				if (!medoids.containsValue(first)) {
					medoids.put(j + 1, first);
					j++;
				}
			}
		}
		return medoids;

	}

	/**
	 * Calculate distances in matrix
	 * 
	 * @param matrix
	 * @param readDataFromFile
	 */
	private static void calculateDistances(Double[][] matrix, List<DataSample> readDataFromFile) {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix.length; j++) {
				matrix[i][j] = calculateEuclideanDistance(readDataFromFile.get(i), readDataFromFile.get(j));
			}
		}
	}

	/**
	 * Calculate eucleidian distance
	 * 
	 * @param first
	 * @param second
	 * @return
	 */
	private static Double calculateEuclideanDistance(DataSample first, DataSample second) {
		Double result = 0D;

		result += Math.pow(first.x - second.x, 2);
		result += Math.pow(first.y - second.y, 2);

		result = Math.sqrt(result);

		return result;
	}

}
