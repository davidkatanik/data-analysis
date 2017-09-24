package cv6;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class Main {

	private static final int K = 2;
	static DecimalFormat df = new DecimalFormat("0.0000000");
	private static final boolean GENERATE = true;

	public static void main(String[] args) {
		
		System.out.println(calculateSimpleEuclideanDistance(new DataSample(4.9, 2.4, 3.3, 1.0),new DataSample(5.005999999999999, 3.4180000000000006, 1.464, 0.2439999999999999)));
		System.out.println(calculateSimpleEuclideanDistance(new DataSample(4.9, 2.4, 3.3, 1.0),new DataSample(5.88360655737705, 2.740983606557377, 4.388524590163935, 1.4344262295081966)));
		
		List<DataSample> data = Utils.readDataFromFile();

		List<List<DataSample>> clusters = kmeansClustering(data, K);
		Double sse = 0D;

		int i = 1;
		for (List<DataSample> cluster : clusters) {
			DataSample centroid = calculateMean(cluster);
			sse += calculateSSE(cluster, centroid);

			System.out.println("Cluster number: [" + i++ + "]");
			System.out.println("Centroid: [" + centroid + "]");
			System.out.println("Size of cluster: [" + cluster.size() + "]");
			cluster.forEach(ds -> System.out.println("\t" + ds));
		}

		System.out.println("SSE for clusters with k="+K+": [" + sse + "]");
	}

	private static Double calculateSSE(List<DataSample> cluster, DataSample centroid) {
		Double sse = 0D;
		for (DataSample ds : cluster) {
			sse += calculateSimpleEuclideanDistance(ds, centroid);
		}
		return sse;
	}

	private static List<List<DataSample>> kmeansClustering(List<DataSample> data, int k) {
		return doKmeansClustering(data, generateCentroids(data, k));
	}

	private static List<List<DataSample>> doKmeansClustering(List<DataSample> data, List<DataSample> centroids) {
		List<List<DataSample>> result = new ArrayList<>();
		// Adds numbers of list similar as length of centroids.
		centroids.forEach(c -> result.add(new ArrayList<>()));

		data.forEach(d -> {
			int positionOfMinimumDistanceCentroid = 0;
			Double distanceOfMinimumCentroid = null;
			for (DataSample c : centroids) {
				if (distanceOfMinimumCentroid == null || distanceOfMinimumCentroid > calculateSimpleEuclideanDistance(d, c)) {
					distanceOfMinimumCentroid = calculateSimpleEuclideanDistance(d, c);
					positionOfMinimumDistanceCentroid = centroids.indexOf(c);
				}
			}
			result.get(positionOfMinimumDistanceCentroid).add(d);
		});

		List<DataSample> recalculatesCentroids = calculateMeans(result);

		if (checkEqualityOfCentroids(centroids, recalculatesCentroids)) {
			System.out.println("K-means has ended");
			return result;
		}
		return doKmeansClustering(data, recalculatesCentroids);
	}

	private static boolean checkEqualityOfCentroids(List<DataSample> centroids, List<DataSample> recalculatesCentroids) {
		for (int i = 0; i < centroids.size(); i++) {
			if (!centroids.get(i).equals(recalculatesCentroids.get(i)))
				return false;
		}
		return true;
	}

	private static List<DataSample> calculateMeans(List<List<DataSample>> result) {
		List<DataSample> means = new ArrayList<>(result.size());
		result.forEach(d -> means.add(calculateMean(d)));
		return means;
	}

	private static Double calculateSimpleEuclideanDistance(DataSample first, DataSample second) {
		Double result = 0D;

		result += Math.pow(first.sepalLength - second.sepalLength, 2);
		result += Math.pow(first.sepalWidth - second.sepalWidth, 2);
		result += Math.pow(first.petalLength - second.petalLength, 2);
		result += Math.pow(first.petalWidth - second.petalWidth, 2);

		result = Math.sqrt(result);

		return result;
	}

	private static DataSample calculateMean(List<DataSample> data) {
		DataSample sample = new DataSample();
		data.forEach(d -> {
			sample.petalLength += d.petalLength;
			sample.petalWidth += d.petalWidth;
			sample.sepalLength += d.sepalLength;
			sample.sepalWidth += d.sepalWidth;
		});
		sample.species = "Mean";
		sample.petalLength = sample.petalLength / data.size();
		sample.petalWidth = sample.petalWidth / data.size();
		sample.sepalLength = sample.sepalLength / data.size();
		sample.sepalWidth = sample.sepalWidth / data.size();

		return sample;
	}

	private static Double findMinimumForAttribute(List<DataSample> data, int i) {
		return data.stream().map(d -> d.getAttribute(i)).min(Comparator.naturalOrder()).get();
	}

	private static Double findMaximumForAttribute(List<DataSample> data, int i) {
		return data.stream().map(d -> d.getAttribute(i)).max(Comparator.naturalOrder()).get();
	}

	private static List<DataSample> generateCentroids(List<DataSample> data, int i) {
		if (!GENERATE) {
			List<DataSample> result = new ArrayList<>();
			result.add(new DataSample(5.348633686499111D, 4.200809794005082D, 1.4758243110962599D, 1.4758243110962599D));
			result.add(new DataSample(7.630231225875927D, 3.135529053194769D, 2.7446097209712628D, 0.4434295075835145D));
			result.add(new DataSample(7.291733018131223D, 3.007535458813488D, 6.452795282898441D, 2.424396096730986D));
			return result;
		}
		List<DataSample> result = new ArrayList<>();
		Random r = new Random();
		DataSample ds;
		for (int j = 0; j < i; j++) {
			ds = new DataSample();
			double nextDouble = r.nextDouble();
			ds.sepalLength = findMinimumForAttribute(data, 1) + (findMaximumForAttribute(data, 1) - findMinimumForAttribute(data, 1)) * nextDouble;
			ds.sepalWidth = findMinimumForAttribute(data, 2) + (findMaximumForAttribute(data, 2) - findMinimumForAttribute(data, 2)) * nextDouble;
			ds.petalLength = findMinimumForAttribute(data, 3) + (findMaximumForAttribute(data, 3) - findMinimumForAttribute(data, 3)) * nextDouble;
			ds.petalWidth = findMinimumForAttribute(data, 4) + (findMaximumForAttribute(data, 4) - findMinimumForAttribute(data, 4)) * nextDouble;
			result.add(ds);
		}
		return result;
	}

}
