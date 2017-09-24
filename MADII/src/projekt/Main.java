/**
 * 
 */
package projekt;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author David Katanik
 *
 * @version 2.0 from 24. 4. 2017
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

		// System.out.println("Single linkage clustering start" +
		// LocalDateTime.now());
		// List<Cluster> singleLinkageClustering =
		// linkageClustering(readDataFromFile, K, SINGLE_LINKAGE);
		// System.out.println("Single linkage clustering stop" +
		// LocalDateTime.now());
		//
		// System.out.println();
		//
		// saveCluster(singleLinkageClustering, "outputSingleLinkage.csv");
		//
		// System.out.println("Complete linkage clustering start");
		// List<Cluster> completeLinkageClustering =
		// linkageClustering(readDataFromFile, K, COMPLETE_LINKAGE);
		// System.out.println("Complete linkage clustering stop");
		//
		// saveCluster(completeLinkageClustering, "outputCompleteLinkage.csv");
		// System.out.println();
		System.out.println("Average linkage clustering start" + LocalDateTime.now());
		List<Cluster> averageLinkageClustering = linkageClustering(readDataFromFile, K, AVERAGE_LINKAGE);
		System.out.println("Average linkage clustering stop" + LocalDateTime.now());

		saveCluster(averageLinkageClustering, "outputAverageLinkage.csv");

		System.out.println();

	}

	private static void saveCluster(List<Cluster> singleLinkageClustering, String fileName) {
		int clusterNum = 1;
		StringBuilder csv = new StringBuilder();
		for (Cluster cluster : singleLinkageClustering) {
			for (Point point : cluster.getIncludedPoints()) {
				point.cluster = clusterNum;
				csv.append(point.customToString());
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

		Cluster closest;
		for (Cluster cluster : clusters) {

			closest = findClosestCluster(clusters, cluster, metric);
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
					matrix[i][j] = Point.calculateEuclideanDistance(readDataFromFile.get(i), readDataFromFile.get(j));
			}
		}
		return matrix;
	}

	private static List<Cluster> initializeClusters(List<Point> readDataFromFile) {
		List<Cluster> result = new ArrayList<>();

		for (Point point : readDataFromFile) {
			result.add(new Cluster(point));
		}

		return result;
	}

}
