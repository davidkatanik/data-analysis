package cv06DBSCAN;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author David Katanik
 *
 * @version 1.0 from 21. 3. 2017
 *
 */
public class DBSCAN {
	// Body pro alg
	public List<DataSample> points;
	// vysledne clustery
	private List<Cluster> clusters;

	// Epsilon - maximalni pripustna vzdalenost okolnich bodu
	public double maxDistance;

	// MinPts - minimalni pocet bodu ve vzdalenosti pro oznaceni core bodu
	public int minPts;

	// Navstivene body
	public List<DataSample> visitedPoints = new ArrayList<>();

	/**
	 * Zavedeni algoritmu
	 * 
	 * @param max_distance
	 * @param min_points
	 */
	public DBSCAN(double max_distance, int min_points) {
		this.points = new ArrayList<>();
		this.clusters = new ArrayList<>();
		this.maxDistance = max_distance;
		this.minPts = min_points;
	}

	/**
	 * Hlavni fce clusteringu
	 */
	public void cluster() {
		int cid = 0;
		for (DataSample point : points) {
			if (visitedPoints.contains(point)) {
				continue;
			}
			// Oznac jako navstiveny
			visitedPoints.add(point);

			// Ziskej jeho sousedy do maxDistance
			List<DataSample> neighbors = getNeighbors(point);

			// Pokud ma bod vice sousedu nez minPts vytvor z nich cluster
			if (neighbors.size() < minPts) {
				point.setCluster(-2);
			} else {
				Cluster c = new Cluster(cid);
				expandCluster(point, c, neighbors);
				clusters.add(c);
				cid++;
			}
		}
	}

	/**
	 * Vytvori cluster newCluster s aktualnim bodem actualDataPoint a sousedy
	 * bodu actualDataPointNeighbors
	 * 
	 * @param actualDataPoint
	 * @param newCluster
	 * @param neighbors
	 */
	private void expandCluster(DataSample actualDataPoint, Cluster newCluster, List<DataSample> neighbors) {
		newCluster.addPoint(actualDataPoint);
		int i = 0;
		do {
			DataSample neighbor = neighbors.get(i);
			if (!visitedPoints.contains(neighbor)) {
				visitedPoints.add(neighbor);
				List<DataSample> neighbors2 = getNeighbors(neighbor);

				if (neighbors2.size() >= minPts) {
					neighbors.addAll(neighbors2);
				}
			}
			if (neighbor.getCluster() < 0) {
				newCluster.addPoint(neighbor);
			}
			i++;
		} while (i < neighbors.size());
	}

	private List<DataSample> getNeighbors(DataSample point) {
		List<DataSample> neighbors = new ArrayList<>();

		for (DataSample neighbor : points) {
			if (point != neighbor && point.distance(neighbor) <= maxDistance) {
				neighbors.add(neighbor);
			}
		}

		return neighbors;
	}

	/**
	 * Nastavi body pro spusteni algoritmu
	 * 
	 * @param points
	 */
	public void setPoints(List<DataSample> points) {
		this.points = points;
	}

	public List<Cluster> getResults() {
		return this.clusters;
	}

	public List<DataSample> outliers() {
		return points.stream().filter(p -> p.getCluster() == -2).collect(Collectors.toList());
	}
	
	
}
