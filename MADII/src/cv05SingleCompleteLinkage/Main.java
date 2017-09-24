/**
 * 
 */
package cv05SingleCompleteLinkage;

import java.time.LocalDateTime;
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
 * @version 1.0 from 16. 3. 2017
 *
 */
public class Main {

	/**
	 * 
	 */
	private static final int K = 2;
	private static double[][] distances;
	private static final int SINGLE_LINKAGE = 1;
	private static final int COMPLETE_LINKAGE = 2;
	private static final int AVERAGE_LINKAGE = 3;

	public static void main(String[] args) {
		List<Point> readDataFromFile = Utils.readDataFromFile();
		distances = calculateDistances(readDataFromFile);
//		System.out.println("doing SLC " + LocalDateTime.now());
//		List<Cluster> singleLinkageClustering = linkageClustering(readDataFromFile, K, SINGLE_LINKAGE);
//		System.out.println("done SLC" + LocalDateTime.now());
//
//		System.out.println();

		// saveCluster(singleLinkageClustering, "outputSingleLinkage.csv");
		//
		//
		// List<Cluster> completeLinkageClustering =
		// linkageClustering(readDataFromFile, K, COMPLETE_LINKAGE);
		//
		// saveCluster(completeLinkageClustering, "outputCompleteLinkage.csv");
		//
		// System.out.println();
		//
		// List<Point> classed = createClasses(readDataFromFile, 500);
		//
		// calculateEntropy(singleLinkageClustering, classed);
		// calculateEntropy(completeLinkageClustering, classed);
		//
		// System.out.println("Intra-cluster to Inter-cluster distance ratio for
		// medoids: " +
		// intraInterClusterDistanceRatio(collectToOneList(singleLinkageClustering)));
		// System.out.println("Intra-cluster to Inter-cluster distance ratio for
		// medoids: " +
		// intraInterClusterDistanceRatio(collectToOneList(completeLinkageClustering)));

		System.out.println("Average linkage clustering start" + LocalDateTime.now());
		List<Cluster> averageLinkageClustering = linkageClustering(readDataFromFile, K, AVERAGE_LINKAGE);
		System.out.println("Average linkage clustering stop" + LocalDateTime.now());

	}

	/**
	 * @param results
	 * @return
	 */
	private static List<Point> collectToOneList(List<Cluster> results) {
		// TODO Auto-generated method stub
		List<Point> result = new LinkedList<>();
		for (Cluster cl : results) {
			result.addAll(cl.getIncludedPoints());
		}
		return result;
	}

	private static double intraInterClusterDistanceRatio(List<Point> clusters) {
		double intra = 0d;
		int count = 0;

		List<Entry<Point, Point>> existingCombination = new LinkedList<>();
		for (Point dataSample : clusters) {
			if (existingCombination.size() > 5000)
				continue;
			for (Point dataSample2 : clusters) {
				if (dataSample.cluster != dataSample2.cluster || dataSample.equals(dataSample2)) {
					continue;
				} else if (checkIfExistsCombination(existingCombination, dataSample, dataSample2)) {
					continue;
				}

				existingCombination.add(new AbstractMap.SimpleEntry<>(dataSample, dataSample2));

				intra += calculateEuclideanDistance(dataSample, dataSample2);
				count++;
			}
		}
		intra /= count;

		double inter = 0d;
		existingCombination = new LinkedList<>();
		count = 0;

		for (Point dataSample : clusters) {
			if (existingCombination.size() > 5000)
				continue;
			for (Point dataSample2 : clusters) {
				if (dataSample.cluster == dataSample2.cluster || dataSample.equals(dataSample2)) {
					continue;
				}
				if (checkIfExistsCombination(existingCombination, dataSample, dataSample2)) {
					continue;
				}

				existingCombination.add(new AbstractMap.SimpleEntry<>(dataSample, dataSample2));

				inter += calculateEuclideanDistance(dataSample, dataSample2);
				count++;

			}
		}

		inter /= count;

		return intra / inter;
	}

	private static boolean checkIfExistsCombination(List<Entry<Point, Point>> existingCombination, Point dataSample, Point dataSample2) {
		return existingCombination.parallelStream().filter(entry -> (entry.getKey().equals(dataSample) && entry.getValue().equals(dataSample2)) || (entry.getKey().equals(dataSample2) && entry.getValue().equals(dataSample))).findAny().isPresent();
	}

	private static List<Point> createClasses(List<Point> readDataFromFile, int j) {
		Comparator<Point> comparator = Comparator.comparing(Point::getX).thenComparing(Point::getY);
		List<Point> result = new ArrayList<>();
		result.addAll(readDataFromFile);

		result.sort(comparator);

		int i = 0;
		int cl = 0;
		for (Point dataSample : result) {
			if (i % j == 0) {
				cl++;
			}
			dataSample.cluster = cl;
			i++;
		}

		return result;

	}

	private static void calculateEntropy(List<Cluster> singleLinkageClustering, List<Point> classed) {
		Map<Integer, Integer> clusters = new HashMap<>();

		for (Point sample : classed) {
			if (clusters.containsKey(sample.cluster)) {
				clusters.put(sample.cluster, clusters.get(sample.cluster) + 1);
			} else {
				clusters.put(sample.cluster, 1);
			}
		}

		double entropy = 0d;

		int n = clusters.values().stream().mapToInt(i -> i.intValue()).sum();

		for (Entry<Integer, Integer> clusterInfo : clusters.entrySet()) {
			List<Point> collect = classed.stream().filter(x -> x.cluster == clusterInfo.getKey()).collect(Collectors.toList());
			entropy += ((clusterInfo.getValue() / (double) n) * entropy(singleLinkageClustering, collect));
		}
		System.out.println("Entropy :" + entropy);
	}

	/**
	 * @param kMedian
	 * @param collect
	 * @return
	 */
	private static double entropy(List<Cluster> singleLinkageClustering, List<Point> p) {
		Map<Integer, List<Point>> clusters = new HashMap<>();

		int i = 1;
		for (Cluster cl : singleLinkageClustering) {
			for (Point sample : cl.getIncludedPoints()) {
				if (clusters.containsKey(i)) {
					List<Point> list = clusters.get(i);
					list.add(sample);
					clusters.put(i, list);
				} else {
					List<Point> list = new LinkedList<>();
					list.add(sample);
					clusters.put(i, list);
				}
			}
			i++;
		}

		double result = 0d;

		for (List<Point> samples : clusters.values()) {
			List<Point> nij = intersection(samples, p);

			double x = nij.size() / (double) p.size();
			if (x == 0)
				continue;
			result += (x * (Math.log(x) / Math.log(2)));
		}

		return -result;
	}

	private static List<Point> intersection(List<Point> samples, List<Point> p) {
		List<Point> result = new LinkedList<>();
		for (Point dataSample : samples) {
			for (Point predicted : p) {
				if (dataSample.x == predicted.x && dataSample.y == predicted.y) {
					result.add(dataSample);
					break;
				}
			}
		}
		return result;
	}

	private static void saveCluster(List<Cluster> singleLinkageClustering, String fileName) {
		int clusterNum = 1;
		StringBuilder csv = new StringBuilder();
		for (Cluster cluster : singleLinkageClustering) {
			for (Point point : cluster.getIncludedPoints()) {
				point.cluster = clusterNum;
				csv.append(point.x + ";" + point.y + ";" + point.cluster + "\n");
			}
			clusterNum++;
		}

		Utils.saveToCsvFile(csv.toString(), fileName);
	}

	private static List<Cluster> linkageClustering(List<Point> data, int k, int metric) {
		List<Cluster> clusters = initializeClusters(data);

		while (clusters.size() != k) {
			List<Cluster> pair = findPairOfClosestClusers(clusters, metric);

			clusters.add(new Cluster(pair.get(0), pair.get(1)));

			clusters.removeAll(pair);
		}
		return clusters;
	}

	private static List<Cluster> findPairOfClosestClusers(List<Cluster> clusters, int metric) {
		List<Cluster> resultPair = new ArrayList<>(2);
		Cluster one = null;
		Cluster two = null;
		double clusterDistance = Double.MAX_VALUE;

		for (Cluster cluster : clusters) {

			Cluster closest = findClosestCluster(clusters, cluster, metric);
			if (closest.getdistanceBeteweenSubClusters() < clusterDistance) {
				one = cluster;
				two = closest;
				clusterDistance = closest.getdistanceBeteweenSubClusters();
			}
		}

		resultPair.add(one);
		resultPair.add(two);

		return resultPair;
	}

	private static Cluster findClosestCluster(List<Cluster> clusters, Cluster cluster, int metric) {
		Cluster closest = null;
		double distance = Double.MAX_VALUE;
		for (Cluster cluster2 : clusters) {
			if (cluster.equals(cluster2))
				continue;
			double dist = calculateDistance(cluster, cluster2, metric);
			if (dist < distance) {
				distance = dist;
				closest = cluster2;
				closest.setdistanceBeteweenSubClusters(distance);
			}
		}
		return closest;
	}

	private static double calculateDistance(Cluster cluster, Cluster cluster2, int metric) {
		double result = Double.MAX_VALUE;
		if (metric == SINGLE_LINKAGE) {
			for (Point p1 : cluster.getIncludedPoints()) {
				for (Point p2 : cluster2.getIncludedPoints()) {
					double distance = distances[p1.position][p2.position];
					if (distance < result) {
						result = distance;
					}
				}
			}
		} else if (metric == COMPLETE_LINKAGE) {
			result = Double.MIN_VALUE;
			for (Point p1 : cluster.getIncludedPoints()) {
				for (Point p2 : cluster2.getIncludedPoints()) {
					double distance = distances[p1.position][p2.position];
					if (distance > result) {
						result = distance;
					}
				}
			}
		} else if (metric == AVERAGE_LINKAGE) {
			result = Double.MIN_VALUE;
			for (Point p1 : cluster.getIncludedPoints()) {
				for (Point p2 : cluster2.getIncludedPoints()) {
					result += distances[p1.position][p2.position];
				}
			}

			result = (1.0 / (cluster.getIncludedPoints().size() * cluster2.getIncludedPoints().size())) * result;

		}
		return result;
	}

	private static double[][] calculateDistances(List<Point> readDataFromFile) {
		double[][] matrix = new double[readDataFromFile.size()][readDataFromFile.size()];
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix.length; j++) {
				if (i == j)
					matrix[i][j] = Double.MAX_VALUE;
				else
					matrix[i][j] = calculateEuclideanDistance(readDataFromFile.get(i), readDataFromFile.get(j));
			}
		}
		return matrix;
	}

	private static double calculateEuclideanDistance(Point first, Point second) {
		double result = 0D;

		result += Math.pow(first.x - second.x, 2);
		result += Math.pow(first.y - second.y, 2);

		result = Math.sqrt(result);

		return result;
	}

	private static List<Cluster> initializeClusters(List<Point> readDataFromFile) {
		List<Cluster> result = new ArrayList<>(readDataFromFile.size());

		for (Point point : readDataFromFile) {
			result.add(new Cluster(point));
		}

		return result;
	}

}
