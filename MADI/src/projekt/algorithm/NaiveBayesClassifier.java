package projekt.algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import projekt.domain.Age;
import projekt.domain.Attribute;
import projekt.domain.Capital;
import projekt.domain.DataSample;
import projekt.domain.Education;
import projekt.domain.HoursPerWeek;
import projekt.domain.MaritalStatus;
import projekt.domain.Occupation;
import projekt.domain.Race;
import projekt.domain.Relationship;
import projekt.domain.Sex;
import projekt.domain.WorkClass;

public class NaiveBayesClassifier {

	public static final String WORK_CLASS = "WORK_CLASS";
	public static final String SEX = "SEX";
	public static final String RELATIONSHIP = "RELATIONSHIP";
	public static final String RACE = "RACE";
	public static final String OCCUPATION = "OCCUPATION";
	public static final String MARITAL_STATUS = "MARITAL_STATUS";
	public static final String HOURS_PER_WEEK = "HOURS_PER_WEEK";
	public static final String EDUCATION = "EDUCATION";
	public static final String CAPITAL_LOSS = "CAPITAL_LOSS";
	public static final String CAPITAL_GAIN = "CAPITAL_GAIN";
	public static final String AGE = "AGE";

	public static final String NATIVE_COUNTRY = "NATIVE_COUNTRY";

	private Map<String, Map<Attribute, Map<Boolean, Integer>>> probability;

	public NaiveBayesClassifier(List<DataSample> samples) {
		probability = calculateProbability(samples);
	}

	public Map<String, Map<Attribute, Map<Boolean, Integer>>> calculateProbability(List<DataSample> samples) {
		List<String> strListInit = strListInit();

		Map<String, Map<Attribute, Map<Boolean, Integer>>> map = inicializeMap();

		for (DataSample dataSample : samples) {
			strListInit.forEach(str -> handleAttributeIncrease(map, str, dataSample));
		}
		return map;
	}

	private void handleAttributeIncrease(Map<String, Map<Attribute, Map<Boolean, Integer>>> map, String attrName, DataSample dataSample) {
		Map<Attribute, Map<Boolean, Integer>> attrMap = map.get(attrName);
		Map<Boolean, Integer> dsAttr = null;
		switch (attrName) {
		case WORK_CLASS:
			dsAttr = attrMap.get(dataSample.workClass);
			dsAttr.put(dataSample.income, dsAttr.get(dataSample.income) + 1);
			attrMap.put(dataSample.workClass, dsAttr);
			break;
		case SEX:
			dsAttr = attrMap.get(dataSample.sex);
			dsAttr.put(dataSample.income, dsAttr.get(dataSample.income) + 1);
			attrMap.put(dataSample.sex, dsAttr);
			break;
		case RELATIONSHIP:
			dsAttr = attrMap.get(dataSample.relationship);
			dsAttr.put(dataSample.income, dsAttr.get(dataSample.income) + 1);
			attrMap.put(dataSample.relationship, dsAttr);
			break;
		case RACE:
			dsAttr = attrMap.get(dataSample.race);
			dsAttr.put(dataSample.income, dsAttr.get(dataSample.income) + 1);
			attrMap.put(dataSample.race, dsAttr);
			break;
		case OCCUPATION:
			dsAttr = attrMap.get(dataSample.occupation);
			dsAttr.put(dataSample.income, dsAttr.get(dataSample.income) + 1);
			attrMap.put(dataSample.occupation, dsAttr);
			break;
		case MARITAL_STATUS:
			dsAttr = attrMap.get(dataSample.maritalStatus);
			dsAttr.put(dataSample.income, dsAttr.get(dataSample.income) + 1);
			attrMap.put(dataSample.maritalStatus, dsAttr);
			break;
		case HOURS_PER_WEEK:
			dsAttr = attrMap.get(dataSample.hoursPerWeek);
			dsAttr.put(dataSample.income, dsAttr.get(dataSample.income) + 1);
			attrMap.put(dataSample.hoursPerWeek, dsAttr);
			break;
		case EDUCATION:
			dsAttr = attrMap.get(dataSample.education);
			dsAttr.put(dataSample.income, dsAttr.get(dataSample.income) + 1);
			attrMap.put(dataSample.education, dsAttr);
			break;
		case CAPITAL_LOSS:
			dsAttr = attrMap.get(dataSample.capitalLoss);
			dsAttr.put(dataSample.income, dsAttr.get(dataSample.income) + 1);
			attrMap.put(dataSample.capitalLoss, dsAttr);
			break;
		case CAPITAL_GAIN:
			dsAttr = attrMap.get(dataSample.capitalGain);
			dsAttr.put(dataSample.income, dsAttr.get(dataSample.income) + 1);
			attrMap.put(dataSample.capitalGain, dsAttr);
			break;
		case AGE:
			dsAttr = attrMap.get(dataSample.age);
			dsAttr.put(dataSample.income, dsAttr.get(dataSample.income) + 1);
			attrMap.put(dataSample.age, dsAttr);
			break;
		case NATIVE_COUNTRY:
			if (attrMap.containsKey(dataSample.nativeCountry)) {
				dsAttr = attrMap.get(dataSample.nativeCountry);
				if (dsAttr.containsKey(dataSample.income))
					dsAttr.put(dataSample.income, dsAttr.get(dataSample.income) + 1);
				else
					dsAttr.put(dataSample.income, 1);
			} else {
				dsAttr = new HashMap<>();
				dsAttr.put(dataSample.income, 1);
			}
			attrMap.put(dataSample.nativeCountry, dsAttr);
			break;
		}
		map.put(attrName, attrMap);
	}

	private Map<String, Map<Attribute, Map<Boolean, Integer>>> inicializeMap() {
		Map<String, Map<Attribute, Map<Boolean, Integer>>> map = new HashMap<>();

		map.put(AGE, handleAttribute(Age.class));
		map.put(CAPITAL_GAIN, handleAttribute(Capital.class));
		map.put(CAPITAL_LOSS, handleAttribute(Capital.class));
		map.put(EDUCATION, handleAttribute(Education.class));
		map.put(HOURS_PER_WEEK, handleAttribute(HoursPerWeek.class));
		map.put(MARITAL_STATUS, handleAttribute(MaritalStatus.class));
		map.put(OCCUPATION, handleAttribute(Occupation.class));
		map.put(RACE, handleAttribute(Race.class));
		map.put(RELATIONSHIP, handleAttribute(Relationship.class));
		map.put(SEX, handleAttribute(Sex.class));
		map.put(WORK_CLASS, handleAttribute(WorkClass.class));
		map.put(NATIVE_COUNTRY, new HashMap<>());
		return map;
	}

	private Map<Attribute, Map<Boolean, Integer>> handleAttribute(Class<?> c) {
		Map<Attribute, Map<Boolean, Integer>> result = new HashMap<>();
		for (int i = 0; i < c.getEnumConstants().length; i++) {
			result.put((Attribute) c.getEnumConstants()[i], createTrueFalseMap());
		}
		return result;
	}

	private Map<Boolean, Integer> createTrueFalseMap() {
		Map<Boolean, Integer> result = new HashMap<>();
		result.put(true, 0);
		result.put(false, 0);
		return result;
	}

	private List<String> strListInit() {
		List<String> strList = new ArrayList<>();
		strList.add(AGE);
		strList.add(EDUCATION);
		strList.add(HOURS_PER_WEEK);
		strList.add(RACE);
		strList.add(RELATIONSHIP);
		strList.add(SEX);
		strList.add(CAPITAL_GAIN);
		strList.add(CAPITAL_LOSS);
		strList.add(MARITAL_STATUS);
		strList.add(OCCUPATION);
		strList.add(WORK_CLASS);
		strList.add(NATIVE_COUNTRY);
		return strList;
	}

	public boolean classify(List<DataSample> samples, DataSample ds) {
		double incomeTrue = 1D;
		double incomeFalse = 1D;
		double incomeTrue2 = 0D;
		double incomeFalse2 = 0D;
		Map<Boolean, Integer> map = probability.get(AGE).get(ds.age);
		incomeTrue = map.get(true) / (double) sumOfIncomeForAttribute(AGE, true);
		incomeFalse = map.get(false) / (double) sumOfIncomeForAttribute(AGE, false);
		incomeTrue2 = map.get(true) / (double) sumOfIncomeForAttribute(AGE, true);
		incomeFalse2 = map.get(false) / (double) sumOfIncomeForAttribute(AGE, false);

		map = probability.get(CAPITAL_GAIN).get(ds.capitalGain);
		incomeTrue *= (map.get(true) / (double) sumOfIncomeForAttribute(CAPITAL_GAIN, true));
		incomeFalse *= (map.get(false) / (double) sumOfIncomeForAttribute(CAPITAL_GAIN, false));
		incomeTrue2 += (map.get(true) / (double) sumOfIncomeForAttribute(CAPITAL_GAIN, true));
		incomeFalse2 += (map.get(false) / (double) sumOfIncomeForAttribute(CAPITAL_GAIN, false));
		
		map = probability.get(CAPITAL_LOSS).get(ds.capitalLoss);
		incomeTrue *= (map.get(true) / (double) sumOfIncomeForAttribute(CAPITAL_LOSS, true));
		incomeFalse *= (map.get(false) / (double) sumOfIncomeForAttribute(CAPITAL_LOSS, false));
		incomeTrue2 += (map.get(true) / (double) sumOfIncomeForAttribute(CAPITAL_LOSS, true));
		incomeFalse2 += (map.get(false) / (double) sumOfIncomeForAttribute(CAPITAL_LOSS, false));
		
		map = probability.get(EDUCATION).get(ds.education);
		incomeTrue *= (map.get(true) / (double) sumOfIncomeForAttribute(EDUCATION, true));
		incomeFalse *= (map.get(false) / (double) sumOfIncomeForAttribute(EDUCATION, false));
		incomeTrue2 += (map.get(true) / (double) sumOfIncomeForAttribute(EDUCATION, true));
		incomeFalse2 += (map.get(false) / (double) sumOfIncomeForAttribute(EDUCATION, false));
		
		map = probability.get(HOURS_PER_WEEK).get(ds.hoursPerWeek);
		incomeTrue *= (map.get(true) / (double) sumOfIncomeForAttribute(HOURS_PER_WEEK, true));
		incomeFalse *= (map.get(false) / (double) sumOfIncomeForAttribute(HOURS_PER_WEEK, false));
		incomeTrue2 += (map.get(true) / (double) sumOfIncomeForAttribute(HOURS_PER_WEEK, true));
		incomeFalse2 += (map.get(false) / (double) sumOfIncomeForAttribute(HOURS_PER_WEEK, false));
		
		map = probability.get(MARITAL_STATUS).get(ds.maritalStatus);
		incomeTrue *= (map.get(true) / (double) sumOfIncomeForAttribute(MARITAL_STATUS, true));
		incomeFalse *= (map.get(false) / (double) sumOfIncomeForAttribute(MARITAL_STATUS, false));
		incomeTrue2 += (map.get(true) / (double) sumOfIncomeForAttribute(MARITAL_STATUS, true));
		incomeFalse2 += (map.get(false) / (double) sumOfIncomeForAttribute(MARITAL_STATUS, false));

		map = probability.get(OCCUPATION).get(ds.occupation);
		incomeTrue *= (map.get(true) / (double) sumOfIncomeForAttribute(OCCUPATION, true));
		incomeFalse *= (map.get(false) / (double) sumOfIncomeForAttribute(OCCUPATION, false));
		incomeTrue2 += (map.get(true) / (double) sumOfIncomeForAttribute(OCCUPATION, true));
		incomeFalse2 += (map.get(false) / (double) sumOfIncomeForAttribute(OCCUPATION, false));

		map = probability.get(RACE).get(ds.race);
		incomeTrue *= (map.get(true) / (double) sumOfIncomeForAttribute(RACE, true));
		incomeFalse *= (map.get(false) / (double) sumOfIncomeForAttribute(RACE, false));
		incomeTrue2 += (map.get(true) / (double) sumOfIncomeForAttribute(RACE, true));
		incomeFalse2 += (map.get(false) / (double) sumOfIncomeForAttribute(RACE, false));

		map = probability.get(RELATIONSHIP).get(ds.relationship);
		incomeTrue *= (map.get(true) / (double) sumOfIncomeForAttribute(RELATIONSHIP, true));
		incomeFalse *= (map.get(false) / (double) sumOfIncomeForAttribute(RELATIONSHIP, false));
		incomeTrue2 += (map.get(true) / (double) sumOfIncomeForAttribute(RELATIONSHIP, true));
		incomeFalse2 += (map.get(false) / (double) sumOfIncomeForAttribute(RELATIONSHIP, false));

		map = probability.get(SEX).get(ds.sex);
		incomeTrue *= (map.get(true) / (double) sumOfIncomeForAttribute(SEX, true));
		incomeFalse *= (map.get(false) / (double) sumOfIncomeForAttribute(SEX, false));
		incomeTrue2 += (map.get(true) / (double) sumOfIncomeForAttribute(SEX, true));
		incomeFalse2 += (map.get(false) / (double) sumOfIncomeForAttribute(SEX, false));

		map = probability.get(WORK_CLASS).get(ds.workClass);
		incomeTrue *= (map.get(true) / (double) sumOfIncomeForAttribute(WORK_CLASS, true));
		incomeFalse *= (map.get(false) / (double) sumOfIncomeForAttribute(WORK_CLASS, false));
		incomeTrue2 += (map.get(true) / (double) sumOfIncomeForAttribute(WORK_CLASS, true));
		incomeFalse2 += (map.get(false) / (double) sumOfIncomeForAttribute(WORK_CLASS, false));

		map = probability.get(NATIVE_COUNTRY).get(ds.nativeCountry);
		int missingTrueAndFalse = 0;
		if (map.containsKey(true))
			missingTrueAndFalse = map.get(true);
		incomeTrue *= (missingTrueAndFalse / (double) sumOfIncomeForAttribute(NATIVE_COUNTRY, true));
		incomeTrue2 += (missingTrueAndFalse / (double) sumOfIncomeForAttribute(NATIVE_COUNTRY, true));
		missingTrueAndFalse = 0;
		if (map.containsKey(false))
			missingTrueAndFalse = map.get(false);
		incomeFalse *= (missingTrueAndFalse / (double) sumOfIncomeForAttribute(NATIVE_COUNTRY, false));
		incomeFalse2 += (missingTrueAndFalse / (double) sumOfIncomeForAttribute(NATIVE_COUNTRY, false));
		
		Map<Boolean, Integer> calculateIncomeAbsoluteFrequencies = Frequencies.calculateIncomeAbsoluteFrequencies(samples);
		
		incomeTrue *= (calculateIncomeAbsoluteFrequencies.get(true) / (double) samples.size());
		incomeFalse *= (calculateIncomeAbsoluteFrequencies.get(false) / (double) samples.size());
		incomeTrue2 += (calculateIncomeAbsoluteFrequencies.get(true) / (double) samples.size());
		incomeFalse2 += (calculateIncomeAbsoluteFrequencies.get(false) / (double) samples.size());
		
//		System.out.println("t = " + incomeTrue + " f = " + incomeFalse);
//		System.out.println("t = " + incomeTrue/incomeTrue2 + " f = " + incomeFalse/incomeFalse2);
//		System.out.println("t = " + incomeTrue2 + " f = " + incomeFalse2);
//		if (incomeFalse > incomeTrue)
//			System.out.println("false");
//		else 
//			System.out.println("true");
//		
		
		return incomeTrue > incomeFalse;
	}

	private Integer sumOfIncomeForAttribute(String attribute, boolean classifier) {
		Integer sum = 0;
		for (Entry<Attribute, Map<Boolean, Integer>> entry : probability.get(attribute).entrySet()) {
			Map<Boolean, Integer> value = entry.getValue();
			if (value.containsKey(classifier))
				sum += value.get(classifier);
			else
				sum += 0;
		}
		return sum;
	}

}
