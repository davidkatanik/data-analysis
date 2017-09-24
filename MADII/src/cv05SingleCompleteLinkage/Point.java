package cv05SingleCompleteLinkage;

public class Point {
	double x;
	double y;
	int position;
	Integer cluster;

	public Point(String line, String csvFileSplitter) {
		String[] data = line.split(";");
		x = Double.valueOf(data[0]);
		y = Double.valueOf(data[1]);
	}

	public Point(String line, String csvFileSplitter, int i) {
		String[] data = line.split(";");
		x = Double.valueOf(data[0]);
		y = Double.valueOf(data[1]);
		position = i;
	}

	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public String toString() {
		return "Point [x=" + x + ", y=" + y + ", position=" + position + ", cluster=" + cluster + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cluster == null) ? 0 : cluster.hashCode());
		result = prime * result + position;
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		Point other = (Point) obj;
		if (cluster == null) {
			if (other.cluster != null)
				return false;
		} else if (!cluster.equals(other.cluster))
			return false;
		if (position != other.position)
			return false;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
			return false;
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
			return false;
		return true;
	}

	/**
	 * @return the x
	 */
	public double getX() {
		return x;
	}
	
	/**
	 * @return the y
	 */
	public double getY() {
		return y;
	}
}
