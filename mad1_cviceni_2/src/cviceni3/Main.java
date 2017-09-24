package cviceni3;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import domain.DataSample;
import utils.Utils;

public class Main {

	public static void main(String[] args) {
		List<DataSample> gameConditions = Utils.readDataFromFile();

		
		Tree tree = new Tree();
		tree = createTree(tree, gameConditions,createAttributes(gameConditions));

		tree.print(1);
		System.out.println();
	}

	private static Tree createTree(Tree tree, List<DataSample> gameConditions, List<String> attributes) {
		if (gameConditions.stream().filter(gc -> gc.getPlay().equalsIgnoreCase("yes")).count() == gameConditions.size()){
			tree.value = true;
			tree.isLeaf = true;
			tree.numberOfLeafs = gameConditions.size();
			return tree;
		}
		else if (gameConditions.stream().filter(gc -> gc.getPlay().equalsIgnoreCase("no")).count() == gameConditions.size()){
			tree.value = false;
			tree.isLeaf = true;
			tree.numberOfLeafs = gameConditions.size();
			return tree;
		}
		else if (gameConditions.isEmpty()){
			return null;
		}
		if (attributes.isEmpty()) {
			return null;
		}
		RootPredicate root = findRoot(gameConditions, attributes);
		tree.rule.put(root.attribute, root.value);
		tree.others = loadAllRules(root);
		
		List<DataSample> splittedPositiveData = getSplittedData(gameConditions, root);
		List<DataSample> splittedNegativeData = getSplittedNegativeData(gameConditions, root);
		
		tree.nodes.add(createTree(new Tree(),splittedPositiveData,attributes));
		tree.nodes.add(createTree(new Tree(),splittedNegativeData,attributes));
		
		return tree;
	}
	
	
	private static RootPredicate findRootInOneAttribute(List<DataSample> gameConditions, String attribute) {
		Stream<String> positiveMap;
		Stream<String> negativeMap;

		RootPredicate resultRoot = new RootPredicate(attribute);

		if (attribute.equalsIgnoreCase("outlook")) {
			positiveMap = gameConditions.stream().filter(gc -> gc.getPlay().equalsIgnoreCase("yes")).map(gc -> gc.getOutlook());
			negativeMap = gameConditions.stream().filter(gc -> gc.getPlay().equalsIgnoreCase("no")).map(gc -> gc.getOutlook());
		} else if (attribute.equalsIgnoreCase("temperature")) {
			positiveMap = gameConditions.stream().filter(gc -> gc.getPlay().equalsIgnoreCase("yes")).map(gc -> gc.getTemperature());
			negativeMap = gameConditions.stream().filter(gc -> gc.getPlay().equalsIgnoreCase("no")).map(gc -> gc.getTemperature());
		} else if (attribute.equalsIgnoreCase("humidity")) {
			positiveMap = gameConditions.stream().filter(gc -> gc.getPlay().equalsIgnoreCase("yes")).map(gc -> gc.getHumidity());
			negativeMap = gameConditions.stream().filter(gc -> gc.getPlay().equalsIgnoreCase("no")).map(gc -> gc.getHumidity());
		} else {
			// windy
			positiveMap = gameConditions.stream().filter(gc -> gc.getPlay().equalsIgnoreCase("yes")).map(gc -> gc.getWindy());
			negativeMap = gameConditions.stream().filter(gc -> gc.getPlay().equalsIgnoreCase("no")).map(gc -> gc.getWindy());
		}
		Optional<Entry<String, Long>> collectPositives = positiveMap.collect(Collectors.groupingBy(w -> w, Collectors.counting())).entrySet().stream().max((e1, e2) -> e1.getValue() > e2.getValue() ? 1 : -1);
		Optional<Entry<String, Long>> collectNegatives = negativeMap.collect(Collectors.groupingBy(w -> w, Collectors.counting())).entrySet().stream().max((e1, e2) -> e1.getValue() > e2.getValue() ? 1 : -1);

		Entry<String, Long> result = new AbstractMap.SimpleEntry<String, Long>("", 0L);
		result.setValue(0L);
		Boolean positive = false;
		if (collectPositives.isPresent()) {
			result = collectPositives.get();
			resultRoot.positives = collectPositives.get().getValue();
		}
		if (collectNegatives.isPresent()) {
			if (collectNegatives.get().getValue() > result.getValue()) {
				resultRoot.negatives = collectNegatives.get().getValue();
				result = collectNegatives.get();
				positive = true;
			}
		}

		final Boolean isPositive = positive;
		final Entry<String, Long> resultEntry = result;
		Long max = 0L;
		if (attribute.equalsIgnoreCase("outlook")) {
			max = gameConditions.stream()
				.filter(gc -> gc.getPlay().equalsIgnoreCase((isPositive) ? "yes" : "no"))
				.filter(gc -> gc.getOutlook().equalsIgnoreCase(resultEntry.getKey())).map(gc -> gc.getOutlook())
				.collect(Collectors.groupingBy(w -> w, Collectors.counting())).get(resultEntry.getKey());
		} else if (attribute.equalsIgnoreCase("temperature")) {
			max = gameConditions.stream()
				.filter(gc -> gc.getPlay().equalsIgnoreCase((isPositive) ? "yes" : "no"))
				.filter(gc -> gc.getTemperature().equalsIgnoreCase(resultEntry.getKey())).map(gc -> gc.getTemperature())
				.collect(Collectors.groupingBy(w -> w, Collectors.counting())).get(resultEntry.getKey());
		} else if (attribute.equalsIgnoreCase("humidity")) {
			max = gameConditions.stream()
					.filter(gc -> gc.getPlay().equalsIgnoreCase((isPositive) ? "yes" : "no"))
					.filter(gc -> gc.getHumidity().equalsIgnoreCase(resultEntry.getKey())).map(gc -> gc.getHumidity())
					.collect(Collectors.groupingBy(w -> w, Collectors.counting())).get(resultEntry.getKey());
		} else {
			max = gameConditions.stream()
					.filter(gc -> gc.getPlay().equalsIgnoreCase((isPositive) ? "yes" : "no"))
					.filter(gc -> gc.getWindy().equalsIgnoreCase(resultEntry.getKey())).map(gc -> gc.getWindy())
					.collect(Collectors.groupingBy(w -> w, Collectors.counting())).get(resultEntry.getKey());
		}
		if (max == null) 
			max = 0L;
		if (positive)
			resultRoot.positives = max;
		else
			resultRoot.negatives = max;
		resultRoot.value = result.getKey();
		resultRoot.attribute = attribute;

		return resultRoot;
	}
	

	private static List<Entry<String,String>> loadAllRules(RootPredicate root) {
		List<Entry<String,String>> map = new ArrayList<>();
		if (root.attribute.equalsIgnoreCase("outlook")) {
			if (root.value.equalsIgnoreCase("rainy")) {
				new AbstractMap.SimpleEntry<String, String>("","");
				map.add(new AbstractMap.SimpleEntry<String, String>("outlook", "sunny"));
				map.add(new AbstractMap.SimpleEntry<String, String>("outlook", "overcast"));
			} else if (root.value.equalsIgnoreCase("overcast")) {
				map.add(new AbstractMap.SimpleEntry<String, String>("outlook", "sunny"));
				map.add(new AbstractMap.SimpleEntry<String, String>("outlook", "rainy"));
			} else {
				map.add(new AbstractMap.SimpleEntry<String, String>("outlook", "rainy"));
				map.add(new AbstractMap.SimpleEntry<String, String>("outlook", "overcast"));
			}

		} else if (root.attribute.equalsIgnoreCase("temperature")) {
			if (root.value.equalsIgnoreCase("hot")) {
				map.add(new AbstractMap.SimpleEntry<String, String>("temperature", "mild"));
				map.add(new AbstractMap.SimpleEntry<String, String>("temperature", "cool"));
			} else if (root.value.equalsIgnoreCase("mild")) {
				map.add(new AbstractMap.SimpleEntry<String, String>("temperature", "hot"));
				map.add(new AbstractMap.SimpleEntry<String, String>("temperature", "cool"));
			} else {
				map.add(new AbstractMap.SimpleEntry<String, String>("temperature", "hot"));
				map.add(new AbstractMap.SimpleEntry<String, String>("temperature", "mild"));
			}
		} else if (root.attribute.equalsIgnoreCase("humidity")) {
			if (root.value.equalsIgnoreCase("high")) {
				map.add(new AbstractMap.SimpleEntry<String, String>("humidity", "normal"));
			} else {
				map.add(new AbstractMap.SimpleEntry<String, String>("humidity", "high"));
			}
		} else {
			if (root.value.equalsIgnoreCase("false")) {
				map.add(new AbstractMap.SimpleEntry<String, String>("windy", "true"));
			} else {
				map.add(new AbstractMap.SimpleEntry<String, String>("windy", "false"));
			}
		}
		return map;
	}

	private static List<DataSample> getSplittedNegativeData(List<DataSample> gameConditions, RootPredicate root) {
		if (root.attribute.equalsIgnoreCase("outlook")) {
			return gameConditions.stream().filter(gc -> !gc.getOutlook().equalsIgnoreCase(root.value)).collect(Collectors.toList());
		} else if (root.attribute.equalsIgnoreCase("temperature")) {
			return gameConditions.stream().filter(gc -> !gc.getTemperature().equalsIgnoreCase(root.value)).collect(Collectors.toList());
		} else if (root.attribute.equalsIgnoreCase("humidity")) {
			return gameConditions.stream().filter(gc -> !gc.getHumidity().equalsIgnoreCase(root.value)).collect(Collectors.toList());
		} else {
			return gameConditions.stream().filter(gc -> !gc.getWindy().equalsIgnoreCase(root.value)).collect(Collectors.toList());
		}
	}

	private static List<DataSample> getSplittedData(List<DataSample> gameConditions, RootPredicate root) {
		if (root.attribute.equalsIgnoreCase("outlook")) {
			return gameConditions.stream().filter(gc -> gc.getOutlook().equalsIgnoreCase(root.value)).collect(Collectors.toList());
		} else if (root.attribute.equalsIgnoreCase("temperature")) {
			return gameConditions.stream().filter(gc -> gc.getTemperature().equalsIgnoreCase(root.value)).collect(Collectors.toList());
		} else if (root.attribute.equalsIgnoreCase("humidity")) {
			return gameConditions.stream().filter(gc -> gc.getHumidity().equalsIgnoreCase(root.value)).collect(Collectors.toList());
		} else {
			return gameConditions.stream().filter(gc -> gc.getWindy().equalsIgnoreCase(root.value)).collect(Collectors.toList());
		}
	}

	private static List<String> createAttributes(List<DataSample> gameConditions) {
		return Arrays.asList("outlook","temperature","humidity","windy");
	}

	private static RootPredicate findRoot(List<DataSample> gameConditions, List<String> attributes) {
		List<RootPredicate> roots = new ArrayList<>();
		attributes.forEach(f -> {
			roots.add(findRootInOneAttribute(gameConditions, f));
		});
		RootPredicate bestRoot = getBestRoot(roots);
		System.out.println(bestRoot);
		return bestRoot;
	}

	private static RootPredicate getBestRoot(List<RootPredicate> roots) {
		List<RootPredicate> collect = roots.stream().filter(r -> r.negatives == 0 || r.positives == 0).collect(Collectors.toList());
		Optional<RootPredicate> findAny = collect.stream().findAny();
		if (findAny.isPresent()) {
			return findAny.get();
		}
		RootPredicate bestRoot = null;
		double tmp = 0;
		for (RootPredicate r : roots) {
			double x = 0;
			if (r.negatives>r.positives)
				x = r.negatives / r.positives;
			else
				x = r.positives / r.negatives;
			if (x > tmp){
				tmp = x;
				bestRoot = r;
			}
		}
		return bestRoot;
	}



}
