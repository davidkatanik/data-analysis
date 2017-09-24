package cv7;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
// od cv 2 celkem 17 + 20 prezentace
// Body 2,3	 3	3	3	3	
import java.util.Map;
import java.util.stream.Collectors;

public class Main {

	private static final int K = 3;
	private static final int NUMER_OF_TESTING_DATA = 120;

	public static void main(String[] args) {
		List<DataSample> data = Utils.readDataFromFile();

		Collections.shuffle(data);

		
		List<DataSample> testingData = data.subList(0, NUMER_OF_TESTING_DATA);
		List<DataSample> realdata = data.subList(NUMER_OF_TESTING_DATA, data.size());

		realdata.forEach(System.out::println);
		
		List<DataSample> classificatedData = doKnn(testingData, realdata, K);

		System.out.println("Classificated : ");
		classificatedData.forEach(System.out::println);
	}

	private static List<DataSample> doKnn(List<DataSample> testingData, List<DataSample> realdata, int k) {
		realdata.forEach(rd -> {
			List<DataSample> kNearest = getKNearestNeigborous(testingData, rd, k);
			rd.species = getMostFrequentClass(kNearest);
		});
		return realdata;
	}

	private static List<DataSample> getKNearestNeigborous(List<DataSample> testingData, DataSample data, int k) {
		Map<DataSample, Double> mapOfNearest = new HashMap<>();
		testingData.forEach(td -> {
			mapOfNearest.put(td, calculateSimpleEuclideanDistance(td, data));
		});
		List<DataSample> collect = mapOfNearest.entrySet().stream().sorted(Map.Entry.comparingByValue()).map(m -> m.getKey()).collect(Collectors.toList()).subList(0, k);
		return collect;
	}

	private static String getMostFrequentClass(List<DataSample> kNearest) {
		Map<String, Integer> resultMap = new HashMap<>();
		kNearest.forEach(kn -> {
			if (resultMap.containsKey(kn.species)){
				resultMap.put(kn.species, resultMap.get(kn.species) + 1);
			}
			else {
				resultMap.put(kn.species, 1);
			}
		});
		return resultMap.entrySet().stream().max(Map.Entry.comparingByKey()).map(m -> m.getKey()).get();
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

}
