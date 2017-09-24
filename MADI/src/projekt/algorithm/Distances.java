package projekt.algorithm;

import projekt.domain.DataSample;

public class Distances {
	public static Double calculateSimpleEuclideanDistance(DataSample first, DataSample second) {
		Double result = 0D;

		if (first.age != second.age)
			result += 1;
		if (first.capitalGain != second.capitalGain)
			result += 1;
		if (first.capitalLoss != second.capitalLoss)
			result += 1;
		if (first.hoursPerWeek != second.hoursPerWeek)
			result += 1;
		if (first.education != second.education)
			result += 1;
		if (first.maritalStatus != second.maritalStatus)
			result += 1;
		if (first.occupation != second.occupation)
			result += 1;
		if (first.race != second.race)
			result += 1;
		if (first.relationship != second.relationship)
			result += 1;
		if (first.workClass != second.workClass)
			result += 1;
		if (first.sex != second.sex) {
			result += 1;
		}
		if (!first.nativeCountry.nativeCountry.equalsIgnoreCase(second.nativeCountry.nativeCountry)) {
			result += 1;
		}
		result = Math.sqrt(result);
		return result;
	}
	
//	private static DataSample calculateTotalVariace(List<DataSample> samples) {
//		double ageMean = MedianAndMean.meanForNumericalAttribute(samples, 0);
//		double capitalGainMean = MedianAndMean.meanForNumericalAttribute(samples, 10);		 
//		double capitalLossMean = MedianAndMean.meanForNumericalAttribute(samples, 11);		
//		double horusPerWeekMean = MedianAndMean.meanForNumericalAttribute(samples, 12);		
//				
//		List<DataSample> result = new ArrayList<>();
//		samples.forEach(d -> {
//			DataSample sample = new DataSample();
//			sample.petalLength = (d.petalLength - mean.petalLength) * (d.petalLength - mean.petalLength);
//			sample.petalWidth = (d.petalWidth - mean.petalWidth) * (d.petalWidth - mean.petalWidth);
//			sample.sepalLength = (d.sepalLength - mean.sepalLength) * (d.sepalLength - mean.sepalLength);
//			sample.sepalWidth = (d.sepalWidth - mean.sepalWidth) * (d.sepalWidth - mean.sepalWidth);
//			samples.add(sample);
//		});
//		DataSample totalVariace = calculateMean(samples);
//		totalVariace.species = "Total variace";
//		return totalVariace;
//	}
//	
//	
//	
//	private static DataSample calculateStandardDeviation(DataSample totalVariace) {
//		DataSample sample = new DataSample();
//		sample.species = "Standard deviation";
//		sample.sepalLength = Math.sqrt(totalVariace.sepalLength);
//		sample.sepalWidth = Math.sqrt(totalVariace.sepalWidth);
//		sample.petalLength = Math.sqrt(totalVariace.petalLength);
//		sample.petalWidth = Math.sqrt(totalVariace.petalWidth);
//		return sample;
//	}
//	
//	private static Double calculateSimpleCosineSimilarity(DataSample first, DataSample second) {
//		Double result = 0D;
//
//		Double dotProduct = first.sepalLength * second.sepalLength + first.sepalWidth * second.sepalWidth + first.petalLength * second.petalLength + first.petalWidth * second.petalWidth;
//
//		Double compA = Math.pow(first.sepalLength, 2) + Math.pow(first.sepalWidth, 2) + Math.pow(first.petalLength, 2) + Math.pow(first.petalWidth, 2);
//
//		Double compB = Math.pow(second.sepalLength, 2) + Math.pow(second.sepalWidth, 2) + Math.pow(second.petalLength, 2) + Math.pow(second.petalWidth, 2);
//
//		result = dotProduct / (Math.sqrt(compA) * Math.sqrt(compB));
//
//		return result;
//	}
}
