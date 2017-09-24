package cv7;

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

	public DataSample(Double sepalLenth, Double sepalWidth, Double petalLength, Double petalWidth) {
		this.sepalLength = sepalLenth;
		this.sepalWidth = sepalWidth;
		this.petalLength = petalLength;
		this.petalWidth = petalWidth;
	}

	@Override
	public String toString() {
		return "DataSample [sepalLength=" + sepalLength + ", sepalWidth=" + sepalWidth + ", petalLength=" + petalLength + ", petalWidth=" + petalWidth + ", species=" + species + "]";
	}

	public Double getAttribute(int attribute) {
		switch (attribute) {
		case 1:
			return sepalLength;
		case 2:
			return sepalWidth;
		case 3:
			return petalLength;
		case 4:
			return petalWidth;
		}
		return null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((petalLength == null) ? 0 : petalLength.hashCode());
		result = prime * result + ((petalWidth == null) ? 0 : petalWidth.hashCode());
		result = prime * result + ((sepalLength == null) ? 0 : sepalLength.hashCode());
		result = prime * result + ((sepalWidth == null) ? 0 : sepalWidth.hashCode());
		result = prime * result + ((species == null) ? 0 : species.hashCode());
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
		if (petalLength == null) {
			if (other.petalLength != null)
				return false;
		} else if (!petalLength.equals(other.petalLength))
			return false;
		if (petalWidth == null) {
			if (other.petalWidth != null)
				return false;
		} else if (!petalWidth.equals(other.petalWidth))
			return false;
		if (sepalLength == null) {
			if (other.sepalLength != null)
				return false;
		} else if (!sepalLength.equals(other.sepalLength))
			return false;
		if (sepalWidth == null) {
			if (other.sepalWidth != null)
				return false;
		} else if (!sepalWidth.equals(other.sepalWidth))
			return false;
		if (species == null) {
			if (other.species != null)
				return false;
		} else if (!species.equals(other.species))
			return false;
		return true;
	}

}
