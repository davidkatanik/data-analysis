package cv06DBSCAN;

/**
 * @author David Katanik
 *
 * @version 1.0 from 21. 3. 2017
 *
 */
public class DataSample {

	int cluster = -1;
	Double x;
	Double y;

	public DataSample(String line, String csvFileSplitter) {
		String[] data = line.split(";");
		x = Double.valueOf(data[0]);
		y = Double.valueOf(data[1]);
	}

	public Double distance(DataSample datapoint) {
		Double result = 0D;

		result += Math.pow(this.x - datapoint.getX(), 2);
		result += Math.pow(this.y - datapoint.getY(), 2);

		result = Math.sqrt(result);

		return result;
	}

	public void setCluster(int id) {
		this.cluster = id;

	}

	public int getCluster() {
		return this.cluster;
	}

	public Double getX() {
		return this.x;
	}

	public Double getY() {
		return this.y;
	}

}
