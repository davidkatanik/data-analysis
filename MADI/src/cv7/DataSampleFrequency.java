package cv7;

import java.util.ArrayList;
import java.util.List;

public class DataSampleFrequency {

	private static final String SPLIT = ",";

	Double referenceValue;
	Integer absoluteFrequency;
	Double relativeFrequency;
	Integer cumulativeFrequency;

	List<DataSample> samples = new ArrayList<>();

	@Override
	public String toString() {
		return "DataSampleFrequency [referenceValue=" + referenceValue + ", absoluteFrequency=" + absoluteFrequency + ", relativeFrequency=" + relativeFrequency + ", cumulativeFrequency=" + cumulativeFrequency + ", samples=" + samples + "]";
	}

	public String toCsv() {
		return referenceValue + SPLIT + absoluteFrequency + SPLIT + relativeFrequency + SPLIT + cumulativeFrequency;
	}

}
