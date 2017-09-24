package projekt;

public class Point implements Cloneable {
	double area;
	double perimeter;
	double compactness;
	double lengthOfKernel;
	double widthOfKernel;
	double asymmetryCoefficient;
	double lengthOfKernelGroove;

	int position;
	int cluster;

	public Point(String line, String csvFileSplitter) {
		String[] data = line.split(";");
		area = Double.valueOf(data[0]);
		perimeter = Double.valueOf(data[1]);
	}

	public Point(String line, String csvFileSplitter, int i) {
		String[] data = line.split(";");

		int j = 0;

		area = Double.valueOf(data[j++]);
		perimeter = Double.valueOf(data[j++]);
		compactness = Double.valueOf(data[j++]);
		lengthOfKernel = Double.valueOf(data[j++]);
		widthOfKernel = Double.valueOf(data[j++]);
		asymmetryCoefficient = Double.valueOf(data[j++]);
		lengthOfKernelGroove = Double.valueOf(data[j]);
		position = i;
	}

	@Override
	public String toString() {
		return "Point [area=" + area + ", perimeter=" + perimeter + ", compactness=" + compactness + ", lengthOfKernel=" + lengthOfKernel + ", widthOfKernel=" + widthOfKernel + ", asymmetryCoefficient=" + asymmetryCoefficient
				+ ", lengthOfKernelGroove=" + lengthOfKernelGroove + ", position=" + position + ", cluster=" + cluster + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(area);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(asymmetryCoefficient);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + cluster;
		temp = Double.doubleToLongBits(compactness);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(lengthOfKernel);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(lengthOfKernelGroove);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(perimeter);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + position;
		temp = Double.doubleToLongBits(widthOfKernel);
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
		if (Double.doubleToLongBits(area) != Double.doubleToLongBits(other.area))
			return false;
		if (Double.doubleToLongBits(asymmetryCoefficient) != Double.doubleToLongBits(other.asymmetryCoefficient))
			return false;
		if (cluster != other.cluster)
			return false;
		if (Double.doubleToLongBits(compactness) != Double.doubleToLongBits(other.compactness))
			return false;
		if (Double.doubleToLongBits(lengthOfKernel) != Double.doubleToLongBits(other.lengthOfKernel))
			return false;
		if (Double.doubleToLongBits(lengthOfKernelGroove) != Double.doubleToLongBits(other.lengthOfKernelGroove))
			return false;
		if (Double.doubleToLongBits(perimeter) != Double.doubleToLongBits(other.perimeter))
			return false;
		if (position != other.position)
			return false;
		if (Double.doubleToLongBits(widthOfKernel) != Double.doubleToLongBits(other.widthOfKernel))
			return false;
		return true;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	/**
	 * 
	 */
	public String customToString() {
		return area + ";" + perimeter + ";" + cluster + "\n";
	}

	public static double calculateEuclideanDistance(Point first, Point second) {
		double result = 0D;

		result += Math.pow(first.area - second.area, 2);
		result += Math.pow(first.perimeter - second.perimeter, 2);
		result += Math.pow(first.compactness - second.compactness, 2);
		result += Math.pow(first.lengthOfKernel - second.lengthOfKernel, 2);
		result += Math.pow(first.widthOfKernel - second.widthOfKernel, 2);
		result += Math.pow(first.asymmetryCoefficient - second.asymmetryCoefficient, 2);
		result += Math.pow(first.lengthOfKernelGroove - second.lengthOfKernelGroove, 2);

		result = Math.sqrt(result);

		return result;
	}
}
