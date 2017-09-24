package projekt.algorithm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import projekt.domain.Age;
import projekt.domain.Attribute;
import projekt.domain.Capital;
import projekt.domain.DataSample;
import projekt.domain.Education;
import projekt.domain.HoursPerWeek;
import projekt.domain.MaritalStatus;
import projekt.domain.NativeCountry;
import projekt.domain.Occupation;
import projekt.domain.Race;
import projekt.domain.Relationship;
import projekt.domain.Sex;
import projekt.domain.WorkClass;

public class Frequencies {

	/**
	 * Absolute & relative frequencies for {@link DataSample#age}
	 */
	public static Map<Attribute, Integer> calculateAgeAbsoluteFrequencies(List<DataSample> samples) {
		Map<Attribute, Integer> frequency = new HashMap<>();
		samples.forEach(s -> {
			if (frequency.containsKey(s.age)) {
				frequency.put(s.age, frequency.get(s.age) + 1);
			} else {
				frequency.put(s.age, 1);
			}
		});
		return frequency;
	}

	public static Map<Attribute, Double> calculateAgeRelativeFrequencies(List<DataSample> samples) {
		Map<Attribute, Double> frequency = new HashMap<>();
		calculateAgeAbsoluteFrequencies(samples).entrySet().forEach(e -> {
			frequency.put((Age) e.getKey(), (e.getValue() / (double) samples.size()));
		});
		return frequency;
	}

	public static Age mostFrequentAge(List<DataSample> samples) {
		return (Age) calculateAgeAbsoluteFrequencies(samples).entrySet().stream().max(Map.Entry.comparingByValue()).map(m -> m.getKey()).get();
	}

	/**
	 * Absolute & relative frequencies for {@link DataSample#workClass}
	 */
	public static Map<Attribute, Integer> calculateWorkClassAbsoluteFrequencies(List<DataSample> samples) {
		Map<Attribute, Integer> frequency = new HashMap<>();
		samples.forEach(s -> {
			if (frequency.containsKey(s.workClass)) {
				frequency.put(s.workClass, frequency.get(s.workClass) + 1);
			} else {
				frequency.put(s.workClass, 1);
			}
		});
		return frequency;
	}

	public static Map<Attribute, Double> calculateWorkClassFrequencies(List<DataSample> samples) {
		Map<Attribute, Double> frequency = new HashMap<>();
		calculateWorkClassAbsoluteFrequencies(samples).entrySet().forEach(e -> {
			frequency.put((WorkClass) e.getKey(), (e.getValue() / (double) samples.size()));
		});
		return frequency;
	}

	public static WorkClass mostFrequentWorkClass(List<DataSample> samples) {
		return (WorkClass) calculateWorkClassAbsoluteFrequencies(samples).entrySet().stream().max(Map.Entry.comparingByValue()).map(m -> m.getKey()).get();
	}

	/**
	 * Absolute & relative frequencies for {@link DataSample#education}
	 */
	public static Map<Attribute, Integer> calculateEducationAbsoluteFrequencies(List<DataSample> samples) {
		Map<Attribute, Integer> frequency = new HashMap<>();
		samples.forEach(s -> {
			if (frequency.containsKey(s.education)) {
				frequency.put(s.education, frequency.get(s.education) + 1);
			} else {
				frequency.put(s.education, 1);
			}
		});
		return frequency;
	}

	public static Map<Attribute, Double> calculateEducationFrequencies(List<DataSample> samples) {
		Map<Attribute, Double> frequency = new HashMap<>();
		calculateEducationAbsoluteFrequencies(samples).entrySet().forEach(e -> {
			frequency.put((Education) e.getKey(), (e.getValue() / (double) samples.size()));
		});
		return frequency;
	}

	public static Education mostFrequentEducation(List<DataSample> samples) {
		return (Education) calculateEducationAbsoluteFrequencies(samples).entrySet().stream().max(Map.Entry.comparingByValue()).map(m -> m.getKey()).get();
	}

	/**
	 * Absolute & relative frequencies for {@link DataSample#maritalStatus}
	 */
	public static Map<Attribute, Integer> calculateMaritalStatusAbsoluteFrequencies(List<DataSample> samples) {
		Map<Attribute, Integer> frequency = new HashMap<>();
		samples.forEach(s -> {
			if (frequency.containsKey(s.maritalStatus)) {
				frequency.put(s.maritalStatus, frequency.get(s.maritalStatus) + 1);
			} else {
				frequency.put(s.maritalStatus, 1);
			}
		});
		return frequency;
	}

	public static Map<Attribute, Double> calculateMaritalStatusFrequencies(List<DataSample> samples) {
		Map<Attribute, Double> frequency = new HashMap<>();
		calculateMaritalStatusAbsoluteFrequencies(samples).entrySet().forEach(e -> {
			frequency.put((MaritalStatus) e.getKey(), (e.getValue() / (double) samples.size()));
		});
		return frequency;
	}

	public static MaritalStatus mostFrequentMaritalStatus(List<DataSample> samples) {
		return (MaritalStatus) calculateMaritalStatusAbsoluteFrequencies(samples).entrySet().stream().max(Map.Entry.comparingByValue()).map(m -> m.getKey()).get();
	}

	/**
	 * Absolute & relative frequencies for {@link DataSample#occupation}
	 */
	public static Map<Attribute, Integer> calculateOccupationAbsoluteFrequencies(List<DataSample> samples) {
		Map<Attribute, Integer> frequency = new HashMap<>();
		samples.forEach(s -> {
			if (frequency.containsKey(s.occupation)) {
				frequency.put(s.occupation, frequency.get(s.occupation) + 1);
			} else {
				frequency.put(s.occupation, 1);
			}
		});
		return frequency;
	}

	public static Map<Attribute, Double> calculateOccupationFrequencies(List<DataSample> samples) {
		Map<Attribute, Double> frequency = new HashMap<>();
		calculateOccupationAbsoluteFrequencies(samples).entrySet().forEach(e -> {
			frequency.put((Occupation) e.getKey(), (e.getValue() / (double) samples.size()));
		});
		return frequency;
	}

	public static Occupation mostFrequentOccupation(List<DataSample> samples) {
		return (Occupation) calculateOccupationAbsoluteFrequencies(samples).entrySet().stream().filter(e -> e.getKey() != null).max(Map.Entry.comparingByValue()).map(m -> m.getKey()).get();
	}

	/**
	 * Absolute & relative frequencies for {@link DataSample#relationship}
	 */
	public static Map<Attribute, Integer> calculateRelationshipAbsoluteFrequencies(List<DataSample> samples) {
		Map<Attribute, Integer> frequency = new HashMap<>();
		samples.forEach(s -> {
			if (frequency.containsKey(s.relationship)) {
				frequency.put(s.relationship, frequency.get(s.relationship) + 1);
			} else {
				frequency.put(s.relationship, 1);
			}
		});
		return frequency;
	}

	public static Map<Attribute, Double> calculateRelationshipFrequencies(List<DataSample> samples) {
		Map<Attribute, Double> frequency = new HashMap<>();
		calculateRelationshipAbsoluteFrequencies(samples).entrySet().forEach(e -> {
			frequency.put((Relationship) e.getKey(), (e.getValue() / (double) samples.size()));
		});
		return frequency;
	}

	public static Relationship mostFrequentRelationship(List<DataSample> samples) {
		return (Relationship) calculateRelationshipAbsoluteFrequencies(samples).entrySet().stream().max(Map.Entry.comparingByValue()).map(m -> m.getKey()).get();
	}

	/**
	 * Absolute & relative frequencies for {@link DataSample#race}
	 */
	public static Map<Attribute, Integer> calculateRaceAbsoluteFrequencies(List<DataSample> samples) {
		Map<Attribute, Integer> frequency = new HashMap<>();
		samples.forEach(s -> {
			if (frequency.containsKey(s.race)) {
				frequency.put(s.race, frequency.get(s.race) + 1);
			} else {
				frequency.put(s.race, 1);
			}
		});
		return frequency;
	}

	public static Map<Attribute, Double> calculateRaceFrequencies(List<DataSample> samples) {
		Map<Attribute, Double> frequency = new HashMap<>();
		calculateRaceAbsoluteFrequencies(samples).entrySet().forEach(e -> {
			frequency.put((Race) e.getKey(), (e.getValue() / (double) samples.size()));
		});
		return frequency;
	}

	public static Race mostFrequentRace(List<DataSample> samples) {
		return (Race) calculateRaceAbsoluteFrequencies(samples).entrySet().stream().max(Map.Entry.comparingByValue()).map(m -> m.getKey()).get();
	}

	/**
	 * Absolute & relative frequencies for {@link DataSample#sex}
	 */
	public static Map<Attribute, Integer> calculateSexAbsoluteFrequencies(List<DataSample> samples) {
		Map<Attribute, Integer> frequency = new HashMap<>();
		samples.forEach(s -> {
			if (frequency.containsKey(s.sex)) {
				frequency.put(s.sex, frequency.get(s.sex) + 1);
			} else {
				frequency.put(s.sex, 1);
			}
		});
		return frequency;
	}

	public static Map<Attribute, Double> calculateSexFrequencies(List<DataSample> samples) {
		Map<Attribute, Double> frequency = new HashMap<>();
		calculateSexAbsoluteFrequencies(samples).entrySet().forEach(e -> {
			frequency.put((Sex) e.getKey(), (e.getValue() / (double) samples.size()));
		});
		return frequency;
	}

	public static Sex mostFrequentSex(List<DataSample> samples) {
		return (Sex) calculateSexAbsoluteFrequencies(samples).entrySet().stream().max(Map.Entry.comparingByValue()).map(m -> m.getKey()).get();
	}

	/**
	 * Absolute & relative frequencies for {@link DataSample#capitalGain}
	 */
	public static Map<Attribute, Integer> calculateCapitalGainAbsoluteFrequencies(List<DataSample> samples) {
		Map<Attribute, Integer> frequency = new HashMap<>();
		samples.forEach(s -> {
			if (frequency.containsKey(s.capitalGain)) {
				frequency.put(s.capitalGain, frequency.get(s.capitalGain) + 1);
			} else {
				frequency.put(s.capitalGain, 1);
			}
		});
		return frequency;
	}

	public static Map<Attribute, Double> calculateCapitalGainFrequencies(List<DataSample> samples) {
		Map<Attribute, Double> frequency = new HashMap<>();
		calculateCapitalGainAbsoluteFrequencies(samples).entrySet().forEach(e -> {
			frequency.put((Capital) e.getKey(), (e.getValue() / (double) samples.size()));
		});
		return frequency;
	}

	public static Capital mostFrequentCapitalGain(List<DataSample> samples) {
		return (Capital) calculateCapitalGainAbsoluteFrequencies(samples).entrySet().stream().max(Map.Entry.comparingByValue()).map(m -> m.getKey()).get();
	}

	/**
	 * Absolute & relative frequencies for {@link DataSample#capitalLoss}
	 */
	public static Map<Attribute, Integer> calculateCapitalLossAbsoluteFrequencies(List<DataSample> samples) {
		Map<Attribute, Integer> frequency = new HashMap<>();
		samples.forEach(s -> {
			if (frequency.containsKey(s.capitalLoss)) {
				frequency.put(s.capitalLoss, frequency.get(s.capitalLoss) + 1);
			} else {
				frequency.put(s.capitalLoss, 1);
			}
		});
		return frequency;
	}

	public static Map<Attribute, Double> calculateCapitalLossFrequencies(List<DataSample> samples) {
		Map<Attribute, Double> frequency = new HashMap<>();
		calculateCapitalLossAbsoluteFrequencies(samples).entrySet().forEach(e -> {
			frequency.put((Capital) e.getKey(), (e.getValue() / (double) samples.size()));
		});
		return frequency;
	}

	public static Capital mostFrequentCapitalLoss(List<DataSample> samples) {
		return (Capital) calculateCapitalLossAbsoluteFrequencies(samples).entrySet().stream().max(Map.Entry.comparingByValue()).map(m -> m.getKey()).get();
	}

	/**
	 * Absolute & relative frequencies for {@link DataSample#hoursPerWeek}
	 */
	public static Map<Attribute, Integer> calculateHoursPerWeekAbsoluteFrequencies(List<DataSample> samples) {
		Map<Attribute, Integer> frequency = new HashMap<>();
		samples.forEach(s -> {
			if (frequency.containsKey(s.hoursPerWeek)) {
				frequency.put(s.hoursPerWeek, frequency.get(s.hoursPerWeek) + 1);
			} else {
				frequency.put(s.hoursPerWeek, 1);
			}
		});
		return frequency;
	}

	public static Map<Attribute, Double> calculateHoursPerWeekFrequencies(List<DataSample> samples) {
		Map<Attribute, Double> frequency = new HashMap<>();
		calculateHoursPerWeekAbsoluteFrequencies(samples).entrySet().forEach(e -> {
			frequency.put((HoursPerWeek) e.getKey(), (e.getValue() / (double) samples.size()));
		});
		return frequency;
	}

	public static HoursPerWeek mostFrequentHoursPerWeek(List<DataSample> samples) {
		return (HoursPerWeek) calculateHoursPerWeekAbsoluteFrequencies(samples).entrySet().stream().max(Map.Entry.comparingByValue()).map(m -> m.getKey()).get();
	}

	/**
	 * Absolute & relative frequencies for {@link DataSample#hoursPerWeek}
	 */
	public static Map<Attribute, Integer> calculateNativeCountryAbsoluteFrequencies(List<DataSample> samples) {
		Map<Attribute, Integer> frequency = new HashMap<>();
		samples.forEach(s -> {
			if (frequency.containsKey(s.nativeCountry)) {
				frequency.put(s.nativeCountry, frequency.get(s.nativeCountry) + 1);
			} else {
				frequency.put(s.nativeCountry, 1);
			}
		});
		return frequency;
	}

	public static Map<Attribute, Double> calculateNativeCountryFrequencies(List<DataSample> samples) {
		Map<Attribute, Double> frequency = new HashMap<>();
		calculateNativeCountryAbsoluteFrequencies(samples).entrySet().forEach(e -> {
			frequency.put((NativeCountry) e.getKey(), (e.getValue() / (double) samples.size()));
		});
		return frequency;
	}

	public static NativeCountry mostFrequentNativeCountry(List<DataSample> samples) {
		return (NativeCountry) calculateNativeCountryAbsoluteFrequencies(samples).entrySet().stream().max(Map.Entry.comparingByValue()).map(m -> m.getKey()).get();
	}

	/**
	 * Absolute & relative frequencies for {@link DataSample#hoursPerWeek}
	 */

	public static Map<Boolean, Integer> calculateIncomeAbsoluteFrequencies(List<DataSample> samples) {
		Map<Boolean, Integer> frequency = new HashMap<>();
		samples.forEach(s -> {
			if (frequency.containsKey(s.income)) {
				frequency.put(s.income, frequency.get(s.income) + 1);
			} else {
				frequency.put(s.income, 1);
			}
		});
		return frequency;
	}

	public static Map<Boolean, Double> calculateIncomeFrequencies(List<DataSample> samples) {
		Map<Boolean, Double> frequency = new HashMap<>();
		calculateIncomeAbsoluteFrequencies(samples).entrySet().forEach(e -> {
			frequency.put(e.getKey(), (e.getValue() / (double) samples.size()));
		});
		return frequency;
	}

	public static Boolean mostFrequentIncome(List<DataSample> samples) {
		return calculateIncomeAbsoluteFrequencies(samples).entrySet().stream().max(Map.Entry.comparingByValue()).map(m -> m.getKey()).get();
	}
}
