package projekt;

import java.util.List;
import java.util.Map;

import projekt.algorithm.Frequencies;
import projekt.algorithm.NaiveBayesClassifier;
import projekt.domain.Age;
import projekt.domain.Attribute;
import projekt.domain.DataSample;
import projekt.domain.PreprocessDataSample;
import projekt.domain.WorkClass;

public class main {

	public static void main(String[] args) {
		List<PreprocessDataSample> readDataFromFile = Utils.readDataFromFile();
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("Soubor se zpracovanymi vysledky implemenetace Davida Katanika(KAT0013) pro predmet MAD I rok 2016\n\n"
				+ "Udaje o datech:\n"
				+ "Odkaz na data <a href='http://archive.ics.uci.edu/ml/datasets/Adult'>Data<a>\n"
				+ "Pocet instanci problemu:\t" + readDataFromFile.size() 
				+ "\nPrepracovani dat:"
				+ "\n\tNahrazeni chybejicich hodnot za nejcastejsi"
				+ "\n\tPrevod ordinalnich atributu na kategorialni reprezentovane intervalem"
				+ "\n\tOdlazeni kodu pro velke mnozsti dat"
				+ "\nProvadene akce:"
				+ "\n\tKlasifikace podle vsech atributu zpracovane Naive Bayes algoritmem"
				+ "\n\tAbsolutni a relativni cetnosti"
				+ "\n\tEukleidovska vzdalenost mezi prvnimi 10 instancemi"
				+ "\n\tNejcasteji vyskytujici se hodnoty atributu"
				+ "\n\nCelkovy cas na projektu:\t17 hodin\n\n"
				+ "\nPocet atributu:\t\t\t14"
				+ "\nVypis atributu:"
				+ "\n\tAge � V�k"
				+ "\n\tWorkclass � Pracovn� sv�fra (ve�ejn�, priv�tn� apod.)"
				+ "\n\tfnlwgt: identifika�n� ��slo � bude zanedb�no"
				+ "\n\teducation � Vzd�l�n� (vysoko�kolsk�, z�kladn� apod.)"
				+ "\n\teducation-num - Vzd��ln� v ��seln� podob�"
				+ "\n\tmarital-status � Osobn� stav(�enat�, svodobn� apod.)"
				+ "\n\toccupation - Zam�stn�n�"
				+ "\n\trelationship � Ozna�ov�n jako rodinn� stav (man�el, nem� rodinu apod.)"
				+ "\n\trace - Rasa"
				+ "\n\tSex � Pohlav�"
				+ "\n\tcapital-gain - Prijem"
				+ "\n\tcapital-loss - Vydej"
				+ "\n\thours-per-week � Po�et pracovn�ch hodin t�dn�"
				+ "\n\tnative-country � Zem� p�vodu (United-States, Cambodia, England,�)"
				+ "\n\tIncome �Boolovsk� hodnota ur�uj�c� zda osoba vyd�lala v�ce jak 50 tis�c dolaru za rok"
				+ "\n\nUkazka prvnich 100 zaznamu nezpracovanych dat:\n");
		readDataFromFile.stream().limit(100).forEach(sample -> sb.append(sample.toString()+"\n"));
		List<DataSample> samples = DataSample.createDataSamples(readDataFromFile);
		
		sb.append("\n\nUkazka prvnich 100 zaznamu zpracovanych dat:\n");
		samples.stream().limit(100).forEach(sample -> sb.append(sample.toString()+"\n"));
		
		addAbsoluteFrequencies(sb, samples);
		addRelativeFrequencies(sb, samples);
		
		System.out.println(sb);
		

		// samples.forEach(System.out::println);

		NaiveBayesClassifier nbc = new NaiveBayesClassifier(samples);

		int x = 0;
		int y = 0;
		for (DataSample dataSample : samples) {
			if (dataSample.income)
				x++;
			else 
				y++;
		}
		System.out.println("Old" + x+ " - " + y);
		
		x = 0;
		y = 0;
		int i=0;
		for (DataSample dataSample : samples) {
			if (i == 10)
				break;
//			i++;
			if (nbc.classify(samples, dataSample))
				x++;
			else 
				y++;
		}
		System.out.println("New" + x+ " - " + y);

	}

	private static void addRelativeFrequencies(StringBuilder sb, List<DataSample> samples) {
		sb.append("\nRelative frequencies for Age\n");	
		appendMapToStringBuilder2(Frequencies.calculateAgeRelativeFrequencies(samples), sb);
		sb.append("\nRelative frequencies for WorkClass\n");
		appendMapToStringBuilder2(Frequencies.calculateWorkClassFrequencies(samples), sb);
		sb.append("\nRelative frequencies for Education\n");
		appendMapToStringBuilder2(Frequencies.calculateEducationFrequencies(samples), sb);
		sb.append("\nRelative frequencies for MaritalStatus\n");
		appendMapToStringBuilder2(Frequencies.calculateMaritalStatusFrequencies(samples), sb);
		sb.append("\nRelative frequencies for Occupation\n");
		appendMapToStringBuilder2(Frequencies.calculateOccupationFrequencies(samples), sb);
		sb.append("\nRelative frequencies for Relationship\n");
		appendMapToStringBuilder2(Frequencies.calculateRelationshipFrequencies(samples), sb);
		sb.append("\nRelative frequencies for Race\n");
		appendMapToStringBuilder2(Frequencies.calculateRaceFrequencies(samples), sb);
		sb.append("\nRelative frequencies for Sex\n");
		appendMapToStringBuilder2(Frequencies.calculateSexFrequencies(samples), sb);
		sb.append("\nRelative frequencies for CapitalGain\n");
		appendMapToStringBuilder2(Frequencies.calculateCapitalGainFrequencies(samples), sb);
		sb.append("\nRelative frequencies for CapitalLoss\n");
		appendMapToStringBuilder2(Frequencies.calculateCapitalLossFrequencies(samples), sb);
		sb.append("\nRelative frequencies for HoursPerWeek\n");
		appendMapToStringBuilder2(Frequencies.calculateHoursPerWeekFrequencies(samples), sb);
		sb.append("\nRelative frequencies for NativeCountry\n");
		appendMapToStringBuilder2(Frequencies.calculateNativeCountryFrequencies(samples), sb);
	}

	private static void addAbsoluteFrequencies(StringBuilder sb, List<DataSample> samples) {
		sb.append("\nAbsolute frequencies for Age\n");	
		appendMapToStringBuilder(Frequencies.calculateAgeAbsoluteFrequencies(samples), sb);
		sb.append("\nAbsolute frequencies for WorkClass\n");
		appendMapToStringBuilder(Frequencies.calculateWorkClassAbsoluteFrequencies(samples), sb);
		sb.append("\nAbsolute frequencies for Education\n");
		appendMapToStringBuilder(Frequencies.calculateEducationAbsoluteFrequencies(samples), sb);
		sb.append("\nAbsolute frequencies for MaritalStatus\n");
		appendMapToStringBuilder(Frequencies.calculateMaritalStatusAbsoluteFrequencies(samples), sb);
		sb.append("\nAbsolute frequencies for Occupation\n");
		appendMapToStringBuilder(Frequencies.calculateOccupationAbsoluteFrequencies(samples), sb);
		sb.append("\nAbsolute frequencies for Relationship\n");
		appendMapToStringBuilder(Frequencies.calculateRelationshipAbsoluteFrequencies(samples), sb);
		sb.append("\nAbsolute frequencies for Race\n");
		appendMapToStringBuilder(Frequencies.calculateRaceAbsoluteFrequencies(samples), sb);
		sb.append("\nAbsolute frequencies for Sex\n");
		appendMapToStringBuilder(Frequencies.calculateSexAbsoluteFrequencies(samples), sb);
		sb.append("\nAbsolute frequencies for CapitalGain\n");
		appendMapToStringBuilder(Frequencies.calculateCapitalGainAbsoluteFrequencies(samples), sb);
		sb.append("\nAbsolute frequencies for CapitalLoss\n");
		appendMapToStringBuilder(Frequencies.calculateCapitalLossAbsoluteFrequencies(samples), sb);
		sb.append("\nAbsolute frequencies for HoursPerWeek\n");
		appendMapToStringBuilder(Frequencies.calculateHoursPerWeekAbsoluteFrequencies(samples), sb);
		sb.append("\nAbsolute frequencies for NativeCountry\n");
		appendMapToStringBuilder(Frequencies.calculateNativeCountryAbsoluteFrequencies(samples), sb);
	}

	private static void appendMapToStringBuilder(Map<Attribute, Integer> calculateAgeAbsoluteFrequencies, final StringBuilder sb) {
		calculateAgeAbsoluteFrequencies.entrySet().forEach(entry -> sb.append(entry.getKey()+"="+entry.getValue()+"\n"));
	}
	
	private static void appendMapToStringBuilder2(Map<Attribute, Double> calculateAgeAbsoluteFrequencies, final StringBuilder sb) {
		calculateAgeAbsoluteFrequencies.entrySet().forEach(entry -> sb.append(entry.getKey()+"="+entry.getValue()+"\n"));
	}

}
