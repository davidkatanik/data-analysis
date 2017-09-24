package cviceni5;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {

	static DecimalFormat df = new DecimalFormat("0.0000000");

	public static void main(String[] args) {
		List<DataSample> data = Utils.readDataFromFile();

		Double[][] calculateEuclideanDistance = calculateEuclideanDistance(data);
		DataSample mean = calculateMean(data);
		DataSample median = calculateMedian(data);
		DataSample totalVariace = calculateTotalVariace(data, mean);
		DataSample standardDeviation = calculateStandardDeviation(totalVariace);
		Double[][] calculateCosineSimilarity = calculateCosineSimilarity(data);

		List<DataSampleFrequency> dataSampleFrequency1 = calculateFrequency(data, 1);
		List<DataSampleFrequency> dataSampleFrequency2 = calculateFrequency(data, 2);
		List<DataSampleFrequency> dataSampleFrequency3 = calculateFrequency(data, 3);
		List<DataSampleFrequency> dataSampleFrequency4 = calculateFrequency(data, 4);

		
		StringBuilder csv1 = new StringBuilder();
		dataSampleFrequency1.forEach(dsf -> csv1.append(dsf.toCsv()+"\n"));
		Utils.saveToCsvFile(csv1.toString(), "csv1.csv");
		
		System.out.println(csv1);
		
		// dataSampleFrequency1.forEach(d -> {
		// System.out.println(d);
		// });
		// dataSampleFrequency2.forEach(d -> {
		// System.out.println(d);
		// });
		// dataSampleFrequency3.forEach(d -> {
		// System.out.println(d);
		// });
		// dataSampleFrequency4.forEach(d -> {
		// System.out.println(d);
		// });

//		System.out.println(checkNormalDistribution(dataSampleFrequency1, mean.sepalLength, standardDeviation.sepalLength));
//		System.out.println(checkNormalDistribution(dataSampleFrequency2, mean.sepalWidth, standardDeviation.sepalWidth));
//		System.out.println(checkNormalDistribution(dataSampleFrequency3, mean.petalLength, standardDeviation.petalLength));
//		System.out.println(checkNormalDistribution(dataSampleFrequency4, mean.petalWidth, standardDeviation.petalWidth));

		// print2DArray(calculateEuclideanDistance);
		//
		// System.out.println("Maximum is :" +
		// df.format(findMaximum(calculateEuclideanDistance)));
		// System.out.println("Maximum is :" +
		// df.format(findMinimum(calculateEuclideanDistance)));
		//
		// System.out.println();
		// System.out.println(mean.toString());
		// System.out.println();
		// System.out.println(median.toString());
		// System.out.println();
		// System.out.println(totalVariace.toString());
		// System.out.println();
		// System.out.println(standardDeviation.toString());
		// System.out.println();
		// print2DArray(calculateCosineSimilarity);
		//
		// System.out.println("Maximum is :" +
		// df.format(findMaximum(calculateCosineSimilarity)));
		// System.out.println("Maximum is :" +
		// df.format(findMinimum(calculateCosineSimilarity)));

	}

	private static boolean checkNormalDistribution(List<DataSampleFrequency> dataSampleFrequency, Double mean, Double standardDeviation) {
		// 68
		Double firstLeft = mean - standardDeviation;
		Double firstRight = mean + standardDeviation;
		Double firstResult = 0.D;
		System.out.println("Interval <u-o;u+o> <" + firstLeft + ";" + firstRight + ">");
		// 95
		Double secondLeft = mean - 2 * standardDeviation;
		Double secondRight = mean + 2 * standardDeviation;
		Double secondResult = 0.D;
		System.out.println("Interval <u-o;u+o> <" + secondLeft + ";" + secondRight + ">");
		// 99.7
		Double thirdLeft = mean - 3 * standardDeviation;
		Double thirdRight = mean + 3 * standardDeviation;
		Double thirdResult = 0.D;
		System.out.println("Interval <u-o;u+o> <" + thirdLeft + ";" + thirdRight + ">");

		for (DataSampleFrequency dsf : dataSampleFrequency) {
			if (dsf.referenceValue >= firstLeft && dsf.referenceValue <= firstRight) {
				firstResult += dsf.relativeFrequency;
			}
			if (dsf.referenceValue >= secondLeft && dsf.referenceValue <= secondRight) {
				secondResult += dsf.relativeFrequency;
			}
			if (dsf.referenceValue >= thirdLeft && dsf.referenceValue <= thirdRight) {
				thirdResult += dsf.relativeFrequency;
			}
		}

		firstResult = (Math.round(firstResult * 100)) / 100.0;
		secondResult = (Math.round(secondResult * 100)) / 100.0;
		thirdResult = (Math.round(thirdResult * 100)) / 100.0;

		System.out.println(firstResult);
		System.out.println(secondResult);
		System.out.println(thirdResult);
		return firstResult >= 68 && secondResult >= 95 && thirdResult >= 99.7;
	}

	private static List<DataSampleFrequency> calculateFrequency(List<DataSample> data, int attribute) {
		final List<DataSampleFrequency> freq = new ArrayList<>();
		// Foreach for frequency
		data.forEach(d -> {
			List<DataSampleFrequency> listOfSimilar = freq.stream().filter(dsf -> dsf.referenceValue.equals(d.getAttribute(attribute))).collect(Collectors.toList());
			DataSampleFrequency dataSampleFrequency;
			if (!listOfSimilar.isEmpty() && listOfSimilar.get(0) != null) {
				dataSampleFrequency = listOfSimilar.get(0);
				dataSampleFrequency.absoluteFrequency++;
			} else {
				dataSampleFrequency = new DataSampleFrequency();
				dataSampleFrequency.referenceValue = d.getAttribute(attribute);
				dataSampleFrequency.absoluteFrequency = 1;
				freq.add(dataSampleFrequency);
			}
		});
		// Foreach for relative frequency
		freq.forEach(dsf -> {
			dsf.relativeFrequency = Math.floor((dsf.absoluteFrequency / Double.valueOf(data.size())) * 10000) / 100.0;
		});
		// Foreach for comulative frequency
		freq.forEach(f -> {
			List<DataSample> smallerOrSimilar = data.stream().filter(ds -> ds.getAttribute(attribute) <= f.referenceValue).collect(Collectors.toList());

			f.samples.addAll(smallerOrSimilar);
			f.cumulativeFrequency = smallerOrSimilar.size();

		});

		return freq;
	}

	public static Double[][] calculateEuclideanDistance(List<DataSample> data) {
		Double[][] dataTable = new Double[data.size()][data.size()];
		for (int i = 0; i < data.size(); i++) {
			for (int j = 0; j < data.size(); j++) {
				Double result = calculateSimpleEuclideanDistance(data.get(i), data.get(j));
				dataTable[i][j] = result;
				dataTable[j][i] = result;
			}
		}

		return dataTable;
	}

	private static Double calculateSimpleEuclideanDistance(DataSample first, DataSample second) {
		Double result = 0D;

		result += Math.pow(first.sepalLength - second.sepalLength, 2);
		result += Math.pow(first.sepalWidth - second.sepalWidth, 2);
		result += Math.pow(first.petalLength - second.petalLength, 2);
		result += Math.pow(first.petalWidth - second.petalWidth, 2);

		result = Math.sqrt(result);

		return result;
	}

	private static DataSample calculateMean(List<DataSample> data) {
		DataSample sample = new DataSample();
		data.forEach(d -> {
			sample.petalLength += d.petalLength;
			sample.petalWidth += d.petalWidth;
			sample.sepalLength += d.sepalLength;
			sample.sepalWidth += d.sepalWidth;
		});
		sample.species = "Mean";
		sample.petalLength = sample.petalLength / data.size();
		sample.petalWidth = sample.petalWidth / data.size();
		sample.sepalLength = sample.sepalLength / data.size();
		sample.sepalWidth = sample.sepalWidth / data.size();

		return sample;
	}

	private static DataSample calculateMedian(List<DataSample> data) {
		DataSample sample = new DataSample();

		sample.species = "Median";
		sample.sepalLength = calculateSimpleMedian(data.stream().sorted((d1, d2) -> d1.sepalLength.compareTo(d2.sepalLength)).map(w -> w.sepalLength).collect(Collectors.toList()));
		sample.sepalWidth = calculateSimpleMedian(data.stream().sorted((d1, d2) -> d1.sepalWidth.compareTo(d2.sepalWidth)).map(w -> w.sepalWidth).collect(Collectors.toList()));
		sample.petalLength = calculateSimpleMedian(data.stream().sorted((d1, d2) -> d1.petalLength.compareTo(d2.petalLength)).map(w -> w.petalLength).collect(Collectors.toList()));
		sample.petalWidth = calculateSimpleMedian(data.stream().sorted((d1, d2) -> d1.petalWidth.compareTo(d2.petalWidth)).map(w -> w.petalWidth).collect(Collectors.toList()));

		return sample;
	}

	private static Double calculateSimpleMedian(List<Double> sortedList) {
		Double result;
		int first = sortedList.size() / 2;
		if (sortedList.size() % 2 == 0) {
			result = (sortedList.get(first) + sortedList.get(first + 1)) / 2;
		} else {
			result = sortedList.get(first);
		}
		return result;
	}

	private static DataSample calculateTotalVariace(List<DataSample> data, DataSample mean) {
		List<DataSample> samples = new ArrayList<>();
		data.forEach(d -> {
			DataSample sample = new DataSample();
			sample.petalLength = (d.petalLength - mean.petalLength) * (d.petalLength - mean.petalLength);
			sample.petalWidth = (d.petalWidth - mean.petalWidth) * (d.petalWidth - mean.petalWidth);
			sample.sepalLength = (d.sepalLength - mean.sepalLength) * (d.sepalLength - mean.sepalLength);
			sample.sepalWidth = (d.sepalWidth - mean.sepalWidth) * (d.sepalWidth - mean.sepalWidth);
			samples.add(sample);
		});
		DataSample totalVariace = calculateMean(samples);
		totalVariace.species = "Total variace";
		return totalVariace;
	}

	private static DataSample calculateStandardDeviation(DataSample totalVariace) {
		DataSample sample = new DataSample();
		sample.species = "Standard deviation";
		sample.sepalLength = Math.sqrt(totalVariace.sepalLength);
		sample.sepalWidth = Math.sqrt(totalVariace.sepalWidth);
		sample.petalLength = Math.sqrt(totalVariace.petalLength);
		sample.petalWidth = Math.sqrt(totalVariace.petalWidth);
		return sample;
	}

	private static Double[][] calculateCosineSimilarity(List<DataSample> data) {
		Double[][] dataTable = new Double[data.size()][data.size()];
		for (int i = 0; i < data.size(); i++) {
			for (int j = 0; j < data.size(); j++) {
				Double result = calculateSimpleCosineSimilarity(data.get(i), data.get(j));
				dataTable[i][j] = result;
				dataTable[j][i] = result;
			}
		}

		return dataTable;
	}

	private static Double calculateSimpleCosineSimilarity(DataSample first, DataSample second) {
		Double result = 0D;

		Double dotProduct = first.sepalLength * second.sepalLength + first.sepalWidth * second.sepalWidth + first.petalLength * second.petalLength + first.petalWidth * second.petalWidth;

		Double compA = Math.pow(first.sepalLength, 2) + Math.pow(first.sepalWidth, 2) + Math.pow(first.petalLength, 2) + Math.pow(first.petalWidth, 2);

		Double compB = Math.pow(second.sepalLength, 2) + Math.pow(second.sepalWidth, 2) + Math.pow(second.petalLength, 2) + Math.pow(second.petalWidth, 2);

		result = dotProduct / (Math.sqrt(compA) * Math.sqrt(compB));

		return result;
	}

	// ------------------------- HELP FUNCTIONS ------------------//
	private static void print2DArray(Double[][] array) {
		// numbers of columns
		for (int x = 0; x <= array.length; x++) {
			if (x > 0)
				System.out.print("\t     [" + x + "]");
		}
		System.out.println();
		for (int i = 0; i < array.length; i++) {
			// Numbers of rows
			System.out.print("[" + (i + 1) + "]\t");
			for (int j = 0; j < array.length; j++) {
				System.out.print(df.format(array[i][j]) + "\t");
			}
			System.out.println();
		}
	}

	private static void printMapMatrix(Double[][] array) {
		System.out.println();
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < 4; j++) {
				System.out.print(array[i][j] + "\t");
			}
			System.out.println();
		}
	}

	private static Double findMinimum(Double[][] calculateEuclideanDistance) {
		Double max = calculateEuclideanDistance[0][0];
		for (int i = 0; i < calculateEuclideanDistance.length; i++) {
			for (int j = 0; j < calculateEuclideanDistance.length; j++) {
				if (calculateEuclideanDistance[i][j] > max)
					max = calculateEuclideanDistance[i][j];
			}
		}
		return max;
	}

	private static Double findMaximum(Double[][] calculateEuclideanDistance) {
		Double max = calculateEuclideanDistance[0][0];
		for (int i = 0; i < calculateEuclideanDistance.length; i++) {
			for (int j = 0; j < calculateEuclideanDistance.length; j++) {
				if (calculateEuclideanDistance[i][j] < max)
					max = calculateEuclideanDistance[i][j];
			}
		}
		return max;
	}

}
