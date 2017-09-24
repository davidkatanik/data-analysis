package projekt.domain;

import java.util.ArrayList;
import java.util.List;

import projekt.algorithm.Frequencies;

public class DataSample {

	/**
	 * Age
	 */
	public Age age;
	/**
	 * WorkClass: <br>
	 * <i> Private, Self-emp-not-inc, Self-emp-inc, Federal-gov, Local-gov,
	 * State-gov, Without-pay, Never-worked. fnlwgt: continuous. </i>
	 */
	public WorkClass workClass;
	/**
	 * Education: <br>
	 * <i>Bachelors, Some-college, 11th, HS-grad, Prof-school, Assoc-acdm,
	 * Assoc-voc, 9th, 7th-8th, 12th, Masters, 1st-4th, 10th, Doctorate,
	 * 5th-6th, Preschool. </i>
	 */
	public Education education;
	/**
	 * Education number : <br>
	 * <b>Will be ignored!!!</b>
	 */
	public MaritalStatus maritalStatus;
	/**
	 * Occupation (work area): <br>
	 * <i>Tech-support, Craft-repair, Other-service, Sales, Exec-managerial,
	 * Prof-specialty, Handlers-cleaners, Machine-op-inspct, Adm-clerical,
	 * Farming-fishing, Transport-moving, Priv-house-serv, Protective-serv,
	 * Armed-Forces. </i>
	 */
	public Occupation occupation;
	/**
	 * Relationship: <br>
	 * <i>Wife, Own-child, Husband, Not-in-family, Other-relative, Unmarried.
	 * </i>
	 */
	public Relationship relationship;
	/**
	 * Race: <br>
	 * <i>White, Asian-Pac-Islander, Amer-Indian-Eskimo, Other, Black. </i>
	 */
	public Race race;

	/**
	 * Sex: <br>
	 * <i> 0 represents male and 1 represents female </i>
	 */
	public Sex sex;
	/**
	 * Capital Gain
	 */
	public Capital capitalGain;
	/**
	 * Capital Loss
	 */
	public Capital capitalLoss;
	/**
	 * Work hours per week
	 */
	public HoursPerWeek hoursPerWeek;
	/**
	 * Native country: <br>
	 * <i>United-States, Cambodia, England, Puerto-Rico, Canada, Germany,
	 * Outlying-US(Guam-USVI-etc), India, Japan, Greece, South, China, Cuba,
	 * Iran, Honduras, Philippines, Italy, Poland, Jamaica, Vietnam, Mexico,
	 * Portugal, Ireland, France, Dominican-Republic, Laos, Ecuador, Taiwan,
	 * Haiti, Columbia, Hungary, Guatemala, Nicaragua, Scotland, Thailand,
	 * Yugoslavia, El-Salvador, Trinadad&Tobago, Peru, Hong,
	 * Holand-Netherlands.</i>
	 */
	public NativeCountry nativeCountry;
	/**
	 * Result income: >50K and <=50K
	 */
	public Boolean income;
	
	private static Age mostFrequentAge;
	private static WorkClass mostFrequentWorkClass;
	private static Education mostFrequentEducation;
	private static MaritalStatus mostFrequentMaritalStatus;
	private static Occupation mostFrequentOccupation;
	private static Relationship mostFrequentRelationship;
	private static Race mostFrequentRace;
	private static Sex mostFrequentSex;
	private static Capital mostFrequentCapitalLoss;
	private static Capital mostFrequentCapitalGain;
	private static HoursPerWeek mostFrequentHoursPerWeek;
	private static NativeCountry mostFrequentNativeCountry;
	private static Boolean mostFrequentIncome;

	public DataSample() {
		// TODO Auto-generated constructor stub
	}

	public static List<DataSample> createDataSamples(List<PreprocessDataSample> dataSamples) {
		List<DataSample> samples = new ArrayList<>();
		Integer medianForPreprocessing = PreprocessDataSample.medianForPreprocessing(dataSamples, 10);
		Integer medianForPreprocessing2 = PreprocessDataSample.medianForPreprocessing(dataSamples, 11);
		dataSamples.forEach(dataSample -> {
			DataSample ds = new DataSample();
			ds.age = assignAge(dataSample.age);
			ds.workClass = assignWorkClass(dataSample.workClass);
			ds.education = assignEducation(dataSample.education);
			ds.maritalStatus = assignMarialStatus(dataSample.maritalStatus);
			ds.occupation = assignOccupation(dataSample.occupation);
			ds.relationship = assignRelationsip(dataSample.relationship);
			ds.race = assignRace(dataSample.race);
			ds.sex = assignSex(dataSample.sex);
			ds.capitalGain = assignCapital(dataSample.capitalGain, medianForPreprocessing);
			ds.capitalLoss = assignCapital(dataSample.capitalLoss, medianForPreprocessing2);
			ds.hoursPerWeek = assignHoursPerWeek(dataSample.hoursPerWeek);
			ds.nativeCountry = new NativeCountry(dataSample.nativeCountry);
			ds.income = assignIncome(dataSample.income);
			samples.add(ds);
		});

		initializeMostFrequentSamples(samples);

		samples.forEach(r -> {
			if (r.age == null)
				r.age = mostFrequentAge;
			if (r.workClass == null)
				r.workClass = mostFrequentWorkClass;
			if (r.education == null)
				r.education = mostFrequentEducation;
			if (r.maritalStatus == null)
				r.maritalStatus = mostFrequentMaritalStatus;
			if (r.occupation == null)
				r.occupation = mostFrequentOccupation;
			if (r.relationship == null)
				r.relationship = mostFrequentRelationship;
			if (r.race == null)
				r.race = mostFrequentRace;
			if (r.sex == null)
				r.sex = mostFrequentSex;
			if (r.capitalGain == null)
				r.capitalGain = mostFrequentCapitalGain;
			if (r.capitalLoss == null)
				r.capitalLoss = mostFrequentCapitalLoss;
			if (r.hoursPerWeek == null)
				r.hoursPerWeek = mostFrequentHoursPerWeek;
			if (r.nativeCountry == null || r.nativeCountry.nativeCountry == null)
				r.nativeCountry = mostFrequentNativeCountry;
			if (r.income == null)
				r.income = mostFrequentIncome;
		});

		return samples;
	}

	private static void initializeMostFrequentSamples(List<DataSample> samples) {
		mostFrequentAge = Frequencies.mostFrequentAge(samples);
		mostFrequentWorkClass = Frequencies.mostFrequentWorkClass(samples);
		mostFrequentEducation = Frequencies.mostFrequentEducation(samples);
		mostFrequentMaritalStatus = Frequencies.mostFrequentMaritalStatus(samples);
		mostFrequentOccupation = Frequencies.mostFrequentOccupation(samples);
		mostFrequentRelationship = Frequencies.mostFrequentRelationship(samples);
		mostFrequentRace = Frequencies.mostFrequentRace(samples);
		mostFrequentSex = Frequencies.mostFrequentSex(samples);
		mostFrequentCapitalLoss = Frequencies.mostFrequentCapitalLoss(samples);
		mostFrequentCapitalGain = Frequencies.mostFrequentCapitalGain(samples);
		mostFrequentHoursPerWeek = Frequencies.mostFrequentHoursPerWeek(samples);
		mostFrequentNativeCountry = Frequencies.mostFrequentNativeCountry(samples);
		mostFrequentIncome = Frequencies.mostFrequentIncome(samples);
	}

	private static Boolean assignIncome(String income) {
		if (income.equalsIgnoreCase(">50K"))
			return true;
		return false;
	}

	private static HoursPerWeek assignHoursPerWeek(Integer hoursPerWeek) {
		if (hoursPerWeek == null || hoursPerWeek <= 25)
			return HoursPerWeek.PART_TIME;
		else if (hoursPerWeek <= 40)
			return HoursPerWeek.FULL_TIME;
		else if (hoursPerWeek <= 60)
			return HoursPerWeek.OVER_TIME;
		return HoursPerWeek.WORKAHOLIC;
	}

	private static Capital assignCapital(Integer capital, Integer median) {
		if (capital == null || capital <= 0)
			return Capital.NONE;
		else if (capital <= median)
			return Capital.LOW;
		return Capital.HIGH;
	}

	private static Sex assignSex(String sex) {
		if (sex == null || sex.equalsIgnoreCase("male"))
			return Sex.MALE;
		return Sex.FEMALE;
	}

	private static Age assignAge(Integer age) {
		if (age == null || age <= 25)
			return Age.YOUNG;
		else if (age <= 45)
			return Age.MIDDLE_AGE;
		else if (age <= 65)
			return Age.SENIOR;
		return Age.OLD;
	}

	private static Race assignRace(String string) {
		switch (string) {
		case "White":
			return Race.WHITE;
		case "Asian-Pac-Islander":
			return Race.ASIAN_PAC_ISLANDER;
		case "Amer-Indian-Eskimo":
			return Race.AMER_INDIAN_ESKIMO;
		case "Other":
			return Race.OTHER;
		case "Black":
			return Race.BLACK;
		default:
			return null;
		}
	}

	private static Relationship assignRelationsip(String string) {
		switch (string) {
		case "Wife":
			return Relationship.WIFE;
		case "Own-child":
			return Relationship.OWN_CHILD;
		case "Husband":
			return Relationship.HUSBAND;
		case "Not-in-family":
			return Relationship.NOT_IN_FAMILY;
		case "Other-relative":
			return Relationship.OTHER_RELATIVE;
		case "Unmarried":
			return Relationship.UNMARRIED;
		default:
			return null;
		}
	}

	private static Occupation assignOccupation(String string) {
		switch (string) {
		case "Tech-support":
			return Occupation.TECH_SUPPORT;
		case "Craft-repair":
			return Occupation.CRAFT_REPAIR;
		case "Other-service":
			return Occupation.OTHER_SERVICE;
		case "Sales":
			return Occupation.SALES;
		case "Exec-managerial":
			return Occupation.EXEC_MANAGERIAL;
		case "Prof-specialty":
			return Occupation.PROF_SPECIALTY;
		case "Handlers-cleaners":
			return Occupation.HANDLERS_CLEANERS;
		case "Machine-op-inspct":
			return Occupation.MACHINE_OP_INSPCT;
		case "Adm-clerical":
			return Occupation.ADM_CLERICAL;
		case "Farming-fishing":
			return Occupation.FARMING_FISHING;
		case "Transport-moving":
			return Occupation.TRANSPORT_MOVING;
		case "Priv-house-serv":
			return Occupation.PRIV_HOUSE_SERV;
		case "Protective-serv":
			return Occupation.PROTECTIVE_SERV;
		case "Armed-Forces":
			return Occupation.ARMED_FORCES;
		default:
			return null;
		}
	}

	private static MaritalStatus assignMarialStatus(String string) {
		switch (string) {
		case "Married-civ-spouse":
			return MaritalStatus.MARRIED_CIV_SPOUSE;
		case "Divorced":
			return MaritalStatus.DIVORCED;
		case "Never-married":
			return MaritalStatus.NEVER_MARRIED;
		case "Separated":
			return MaritalStatus.SEPARATED;
		case "Widowed":
			return MaritalStatus.WIDOWED;
		case "Married-spouse-absent":
			return MaritalStatus.MARRIED_SPOUSE_ABSENT;
		case "Married-AF-spouse":
			return MaritalStatus.MARRIED_AF_SPOUSE;
		default:
			return null;
		}
	}

	private static Education assignEducation(String string) {
		switch (string) {
		case "Bachelors":
			return Education.BACHELORS;
		case "Some-college":
			return Education.SOME_COLLEGE;
		case "11th":
			return Education._11TH;
		case "HS-grad":
			return Education.HS_GRAD;
		case "Prof-school":
			return Education.PROF_SCHOOL;
		case "Assoc-acdm":
			return Education.ASSOC_ACDM;
		case "Assoc-voc":
			return Education.ASSOC_VOC;
		case "9th":
			return Education._9TH;
		case "7th-8th":
			return Education._7TH_8TH;
		case "12th":
			return Education._12TH;
		case "Masters":
			return Education.MASTERS;
		case "1st-4th":
			return Education._1ST_4TH;
		case "10th":
			return Education._10TH;
		case "Doctorate":
			return Education.DOCTORATE;
		case "5th-6th":
			return Education._5TH_6TH;
		case "Preschool":
			return Education.PRESCHOOL;
		default:
			return null;
		}
	}

	private static WorkClass assignWorkClass(String string) {
		switch (string) {
		case "Private":
			return WorkClass.PRIVATE;
		case "Self-emp-not-inc":
			return WorkClass.SELF_EMP_NOT_INC;
		case "Self-emp-inc":
			return WorkClass.SELF_EMP_INC;
		case "Federal-gov":
			return WorkClass.FEDERAL_GOV;
		case "Local-gov":
			return WorkClass.LOCAL_GOV;
		case "State-gov":
			return WorkClass.STATE_GOV;
		case "Without-pay":
			return WorkClass.WITHOUT_PAY;
		case "Never-worked":
			return WorkClass.NEVER_WORKED;
		default:
			return null;
		}
	}

	@Override
	public String toString() {
		return "DataSample [age=" + age + ", workClass=" + workClass + ", education=" + education + ", maritalStatus=" + maritalStatus + ", occupation=" + occupation + ", relationship=" + relationship + ", race=" + race + ", sex=" + sex
				+ ", capitalGain=" + capitalGain + ", capitalLoss=" + capitalLoss + ", hoursPerWeek=" + hoursPerWeek + ", nativeCountry=" + nativeCountry + ", income=" + income + "]";
	}

}
