package projekt.domain;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PreprocessDataSample {
	public Integer age = null;
	public String workClass = null;
	public String education = null;
	public String maritalStatus = null;
	public String occupation = null;
	public String relationship = null;
	public String race = null;
	public String sex = null;
	public Integer capitalGain = null;
	public Integer capitalLoss = null;
	public Integer hoursPerWeek = null;
	public String nativeCountry = null;
	public String income = null;

	public PreprocessDataSample(String line, String csvFileSplitter) {
		String[] split = line.split(csvFileSplitter);
		age = Integer.valueOf(split[0].trim());
		workClass = split[1].trim();
		education = split[3].trim();
		maritalStatus = split[5].trim();
		occupation = split[6].trim();
		relationship = split[7].trim();
		race = split[8].trim();
		sex = split[9].trim();
		if (!split[10].trim().equalsIgnoreCase("?"))
			capitalGain = Integer.valueOf(split[10].trim());
		if (!split[11].trim().equalsIgnoreCase("?"))
			capitalLoss = Integer.valueOf(split[11].trim());
		if (!split[12].trim().equalsIgnoreCase("?"))
			hoursPerWeek = Integer.valueOf(split[12].trim());
		if (!split[13].trim().equalsIgnoreCase("?"))
			nativeCountry = split[13].trim();
		income = split[14].trim();
	}

	public Integer getNumericAttribute(int attribute) {
		switch (attribute) {
		case 10:
			return this.capitalGain;
		case 11:
			return this.capitalLoss;
		default:
			return 0;
		}
	}

	public static Integer medianForPreprocessing(List<PreprocessDataSample> dataSamples, int attribute) {
		List<Integer> sortedList = new ArrayList<>();
		for (PreprocessDataSample dataSample : dataSamples) {
			if (dataSample.getNumericAttribute(attribute) > 0)
				sortedList.add(dataSample.getNumericAttribute(attribute));
		}
		sortedList.sort(Comparator.naturalOrder());

		Integer result;
		int first = sortedList.size() / 2;
		if (sortedList.size() % 2 == 0) {
			result = (sortedList.get(first) + sortedList.get(first + 1)) / 2;
		} else {
			result = sortedList.get(first);
		}
		return result;
	}

	@Override
	public String toString() {
		return "PreprocessDataSample [age=" + age + ", workClass=" + workClass + ", education=" + education + ", maritalStatus=" + maritalStatus + ", occupation=" + occupation + ", relationship=" + relationship + ", race=" + race + ", sex=" + sex
				+ ", capitalGain=" + capitalGain + ", capitalLoss=" + capitalLoss + ", hoursPerWeek=" + hoursPerWeek + ", nativeCountry=" + nativeCountry + ", income=" + income + "]";
	}
	
	

}
