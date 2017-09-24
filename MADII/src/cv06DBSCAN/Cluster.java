/**
 * 
 */
package cv06DBSCAN;

import java.util.ArrayList;
import java.util.List;

/**
 * @author David Katanik
 *
 * @version 1.0 from 21. 3. 2017
 *
 */
public class Cluster {
	public List<DataSample> points;
	public DataSample centroid;
	public int id;

	public Cluster(int id) {
		this.id = id;
		this.points = new ArrayList<>();
		this.centroid = null;
	}

	public List<DataSample> getPoints() {
		return points;
	}

	public void addPoint(DataSample point) {
		points.add(point);
		point.setCluster(id);
	}

	public void setPoints(List<DataSample> points) {
		this.points = points;
	}

	public DataSample getCentroid() {
		return centroid;
	}

	public void setCentroid(DataSample centroid) {
		this.centroid = centroid;
	}

	public int getId() {
		return id;
	}

	public void clear() {
		points.clear();
	}

	public void plotCluster() {
		System.out.println("[Cluster: " + id + "]");
		System.out.println("[Centroid: " + centroid + "]");
		System.out.println("[Points: \n");
		for (DataSample p : points) {
			System.out.println(p);
		}
		System.out.println("]");
	}
}
