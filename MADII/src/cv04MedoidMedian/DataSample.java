package cv04MedoidMedian;

public class DataSample implements Cloneable {
	Double x;
	Double y;
	Integer cluster;
	Boolean centroid = false;

	public DataSample(String line, String csvFileSplitter) {
		String[] data = line.split(";");
		x = Double.valueOf(data[0]);
		y = Double.valueOf(data[1]);
	}

	public DataSample() {
		x = 0D;
		y = 0D;
	}

	public DataSample(Double x, Double y) {
		this.x = x;
		this.y = y;
	}

	public Double getAttribute(int attribute) {
		switch (attribute) {
		case 1:
			return x;
		case 2:
			return y;
		case 3:
			return Double.valueOf(cluster);
		}
		return null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((centroid == null) ? 0 : centroid.hashCode());
		result = prime * result + ((cluster == null) ? 0 : cluster.hashCode());
		result = prime * result + ((x == null) ? 0 : x.hashCode());
		result = prime * result + ((y == null) ? 0 : y.hashCode());
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
		DataSample other = (DataSample) obj;
		if (centroid == null) {
			if (other.centroid != null)
				return false;
		} else if (!centroid.equals(other.centroid))
			return false;
		if (cluster == null) {
			if (other.cluster != null)
				return false;
		} else if (!cluster.equals(other.cluster))
			return false;
		if (x == null) {
			if (other.x != null)
				return false;
		} else if (!x.equals(other.x))
			return false;
		if (y == null) {
			if (other.y != null)
				return false;
		} else if (!y.equals(other.y))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DataSample [x=" + x + ", y=" + y + ", cluster=" + cluster + ", centroid=" + centroid + "]";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	/**
	 * @return the x
	 */
	public Double getX() {
		return x;
	}
	
	/**
	 * @return the y
	 */
	public Double getY() {
		return y;
	}
}
