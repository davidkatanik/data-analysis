/**
 * 
 */
package projekt;

import java.util.LinkedList;
import java.util.List;

/**
 * @author David Katanik
 *
 * @version 1.0 from 16. 3. 2017
 *
 */
public class Cluster {

	private List<Point> includedPoints = new LinkedList<>();
	private double distanceBeteweenSubClusters = Double.MAX_VALUE;

	private Cluster l = null;
	private Cluster r = null;

	public Cluster(Point point) {
		includedPoints.add(point);
	}

	public Cluster(Cluster l, Cluster r) {
		super();
		this.l = l;
		this.r = r;

		if (l != null) {
			includedPoints.addAll(l.includedPoints);
		}
		if (r != null) {
			includedPoints.addAll(r.includedPoints);
		}
	}

	/**
	 * @return the includedPoints
	 */
	public List<Point> getIncludedPoints() {
		return includedPoints;
	}

	/**
	 * @return the l
	 */
	public Cluster getL() {
		return l;
	}

	/**
	 * @return the r
	 */
	public Cluster getR() {
		return r;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((includedPoints == null) ? 0 : includedPoints.hashCode());
		result = prime * result + ((l == null) ? 0 : l.hashCode());
		result = prime * result + ((r == null) ? 0 : r.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cluster other = (Cluster) obj;
		if (includedPoints == null) {
			if (other.includedPoints != null)
				return false;
		} else if (!includedPoints.equals(other.includedPoints))
			return false;
		if (l == null) {
			if (other.l != null)
				return false;
		} else if (!l.equals(other.l))
			return false;
		if (r == null) {
			if (other.r != null)
				return false;
		} else if (!r.equals(other.r))
			return false;
		return true;
	}

	/**
	 * @return the distanceBeteweenLand
	 */
	public double getdistanceBeteweenSubClusters() {
		return distanceBeteweenSubClusters;
	}

	/**
	 * @param distanceBeteweenLand the distanceBeteweenLand to set
	 */
	public void setdistanceBeteweenSubClusters(double distanceBeteweenSubClusters) {
		this.distanceBeteweenSubClusters = distanceBeteweenSubClusters;
	}

}
