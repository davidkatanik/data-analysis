package mad1_cviceni_2;

import java.util.List;

import domain.DataSample;
import utils.Utils;

/**
 * 
 * Cvièení druhé, pøedmìtu Metody Analýzy Dat I. 
 * 
 * Úkoly:
 * 
 * 1. Vytvoøte všechna klasifikaèní pravidla pro atribut „play“, která (podle
 * zbývajících 4 atributù z tabulky) urèí (predikují), kdy je možné hrát (blíže
 * neurèenou) hru. Napø.:
 * 
 * 2. Vytvoøte asociaèní pravidla, která (s 100% pravdivostí) predikují hodnotu
 * kteréhokoliv atributu. Napø.
 * 
 * @author David Katanik
 *
 */
public class Main {


	public static void main(String[] args) {
		List<DataSample> gameConditions = Utils.readDataFromFile();

		applyClassificationRules(gameConditions);

		applyAssociationRules(gameConditions);
	}



	private static void applyClassificationRules(List<DataSample> gameConditions) {
		System.out.println("Apllying of classificaton rules:\n");
		gameConditions.forEach(gameCondition -> {
			// Only Yes in attribute play
			if (gameCondition.getOutlook().equalsIgnoreCase("overcast")) {
				System.out.println("(outlook == overcast) then play is yes [data:"+gameCondition.getLoadedData()+"]");
			} else {
				if (gameCondition.getOutlook().equalsIgnoreCase("sunny")) {
					if (gameCondition.getHumidity().equalsIgnoreCase("normal")) {
						System.out.println("(outlook == sunny AND humidity == normal) then play is yes [data:"+gameCondition.getLoadedData()+"]");
					}
				} else {
					if (gameCondition.getOutlook().equalsIgnoreCase("rainy")) {
						if (gameCondition.getWindy().equalsIgnoreCase("false")) {
							System.out.println("(outlook == rainy AND windy == false) then play is yes [data:"+gameCondition.getLoadedData()+"]");
						}
					}
				}
			}
			
			
			// Only No in attribute play
			if (gameCondition.getOutlook().equalsIgnoreCase("rainy")) {
				if (gameCondition.getWindy().equalsIgnoreCase("true")) {
					System.out.println("(outlook == rainy AND windy == true) then play is no [data:"+gameCondition.getLoadedData()+"]");
				}
			}
			if (gameCondition.getOutlook().equalsIgnoreCase("sunny")) {
				if (gameCondition.getHumidity().equalsIgnoreCase("high")) {
					System.out.println("(outlook == sunny AND humidity == high) then play is no [data:"+gameCondition.getLoadedData()+"]");
				}
			}
			
			
			
		});
		System.out.println();
	}

	private static void applyAssociationRules(List<DataSample> gameConditions) {
		System.out.println("\nApllying of association rules:\n");
		gameConditions.forEach(gameCondition -> {
			
			// Positive association only
			if (gameCondition.getOutlook().equalsIgnoreCase("overcast")) {
				if (gameCondition.getWindy().equalsIgnoreCase("false")){
					System.out.println("(outlook == overcast AND windy == false) then temperature is true [data:"+gameCondition.getLoadedData()+"]");
				}
			} else if (gameCondition.getOutlook().equalsIgnoreCase("rainy")) {
				if (gameCondition.getPlay().equalsIgnoreCase("no")){
					System.out.println("(outlook == rainy AND play == no) then windy is true [data:"+gameCondition.getLoadedData()+"]");
				}
				else {
					System.out.println("(outlook == rainy AND play == yes) then windy is false [data:"+gameCondition.getLoadedData()+"]");
				}
				if (gameCondition.getHumidity().equalsIgnoreCase("high")) {
					System.out.println("(outlook == rainy AND humidity == high) then temperature is mild [data:"+gameCondition.getLoadedData()+"]");
				}
				if (gameCondition.getWindy().equalsIgnoreCase("true")) {
					System.out.println("(outlook == rainy AND windy == true) then play is no [data:"+gameCondition.getLoadedData()+"]");
				}
			} else if (gameCondition.getOutlook().equalsIgnoreCase("sunny")) {
				if (gameCondition.getPlay().equalsIgnoreCase("no")) {
					System.out.println("(outlook == sunny AND play == no) then humidity is high [data:"+gameCondition.getLoadedData()+"]");
				}
				else {
					System.out.println("(outlook == sunny AND play == yes) then humidity is normal [data:"+gameCondition.getLoadedData()+"]");
				}
				if (gameCondition.getTemperature().equalsIgnoreCase("cool")) {
					System.out.println("(outlook == sunny AND temperature == cool) then humidity is normal [data:"+gameCondition.getLoadedData()+"]");
				}
			}   
			
			if (gameCondition.getTemperature().equalsIgnoreCase("cool")) {
				System.out.println("(temperature == cool) then humidity is normal [data:"+gameCondition.getLoadedData()+"]");
			}
			if (gameCondition.getTemperature().equalsIgnoreCase("cool")) {
				System.out.println("(temperature == cool) then humidity is normal [data:"+gameCondition.getLoadedData()+"]");
			}
			
			
			
			// Negative association only
			if (gameCondition.getOutlook().equalsIgnoreCase("rainy")) {
				if (gameCondition.getTemperature().equalsIgnoreCase("cool")) {
					System.out.println("WR: (outlook == rainy AND temperature == cool) then windy is true [data:"+gameCondition.getLoadedData()+"]");
				}
			}
			if (gameCondition.getTemperature().equalsIgnoreCase("cool")) {
				System.out.println("WR: (temperature == cool) then outlook is rainy [data:"+gameCondition.getLoadedData()+"]");
			}
			if (gameCondition.getTemperature().equalsIgnoreCase("mild")) {
				if (gameCondition.getHumidity().equalsIgnoreCase("normal")) {
					System.out.println("WR: (temperature == mild AND humidity == normal) then outlook is rainy [data:"+gameCondition.getLoadedData()+"]");
				}
			}
			if (gameCondition.getOutlook().equalsIgnoreCase("sunny")) {
				System.out.println("WR: outlook == sunny then windy is true [data:"+gameCondition.getLoadedData()+"]");
			}
			if (gameCondition.getPlay().equalsIgnoreCase("yes")) {
				System.out.println("WR: play == yes then outlook is overcast [data:"+gameCondition.getLoadedData()+"]");
			}
			if (gameCondition.getHumidity().equalsIgnoreCase("normal")) {
				if (gameCondition.getWindy().equalsIgnoreCase("true")) {
					System.out.println("WR: (humidity == normal AND windy == true) then play is yes [data:"+gameCondition.getLoadedData()+"]");
				}
			}
			
		});
		System.out.println();
	}
}
