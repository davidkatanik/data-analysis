package cviceni5;

public class DataSample {
	Double sepalLength;
	Double sepalWidth;
	Double petalLength;
	Double petalWidth;
	String species;

	public DataSample(String line, String csvFileSplitter) {
		String[] data = line.split(";");
		sepalLength = Double.valueOf(data[0]);
		sepalWidth = Double.valueOf(data[1]);
		petalLength = Double.valueOf(data[2]);
		petalWidth = Double.valueOf(data[3]);
		species = data[4];
	}

	public DataSample() {
		sepalLength = 0D;
		sepalWidth = 0D;
		petalLength = 0D;
		petalWidth = 0D;
	}

	@Override
	public String toString() {
		return "DataSample [sepalLength=" + sepalLength + ", sepalWidth=" + sepalWidth + ", petalLength=" + petalLength
				+ ", petalWidth=" + petalWidth + ", species=" + species + "]";
	}

	public Double getAttribute(int attribute) {
		switch (attribute) {
		case 1: return sepalLength;
		case 2: return sepalWidth;
		case 3: return petalLength;
		case 4: return petalWidth;
		}
		return null;
	}

}
