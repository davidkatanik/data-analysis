/**
 * 
 */
package cv10a;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author David Katanik
 *
 * @version 1.0 from 25. 4. 2017
 *
 */
public class Main {

	private static final String CSV_FILE = "karate2.csv";

	public static void main(String[] args) {
		NClique nClique = new NClique();

		int[][] data = readCSVFile(CSV_FILE);
		
		
		//nClique.calculateNClique(data, 2);

		KCore.kcore(openCsv(CSV_FILE), 2);
		//KCore.calculate(data, 3);
	}

	static public int[][] readCSVFile(String csvFile) {
		String line = "";
		String cvsSplitBy = ";";
		int[][] records = new int[35][35];

		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
			br.readLine();
			while ((line = br.readLine()) != null) {

				// use comma as separator
				String[] splitedLine = line.split(cvsSplitBy);
				// System.out.println("" + splitedLine.length);

				// records.add(splitedLine[0],splitedLine[1]);
				records[Integer.valueOf(splitedLine[0]) - 1][Integer.valueOf(splitedLine[1]) - 1] = 1;
				records[Integer.valueOf(splitedLine[1]) - 1][Integer.valueOf(splitedLine[0]) - 1] = 1;

			}

		} catch (IOException e) {
		}
		return records;
	}
	
	public static List<Instances> openCsv(String fileName) {
        String line = "";
        String cvsSplitBy = ";";
        List<Instances> instancesList = new ArrayList();
        Map<Integer, List<List<Integer>>> map = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] tops = line.split(cvsSplitBy);

                if (map.containsKey(Integer.parseInt(tops[0]) - 1)) {
                    List<List<Integer>> values = map.get(Integer.parseInt(tops[0]) - 1);
                    List<Integer> list = new ArrayList<>();
                    list.add(Integer.parseInt(tops[1]) - 1);
                    list.add(Integer.parseInt(tops[2]));
                    values.add(list);
                    map.put(Integer.parseInt(tops[0]) - 1, values);
                } else {
                    List<List<Integer>> superList = new ArrayList<>();
                    List<Integer> list = new ArrayList<>();
                    list.add(Integer.parseInt(tops[1]) - 1);
                    list.add(Integer.parseInt(tops[2]));
                    superList.add(list);
                    map.put(Integer.parseInt(tops[0]) - 1, superList);
                }

                if (map.containsKey(Integer.parseInt(tops[1]) - 1)) {
                    List<List<Integer>> values = map.get(Integer.parseInt(tops[1]) - 1);
                    List<Integer> list = new ArrayList<>();
                    list.add(Integer.parseInt(tops[0]) - 1);
                    list.add(Integer.parseInt(tops[2]));
                    values.add(list);
                    map.put(Integer.parseInt(tops[1]) - 1, values);
                } else {
                    List<List<Integer>> superList = new ArrayList<>();
                    List<Integer> list = new ArrayList<>();
                    list.add(Integer.parseInt(tops[0]) - 1);
                    list.add(Integer.parseInt(tops[2]));
                    superList.add(list);
                    map.put(Integer.parseInt(tops[1]) - 1, superList);
                }
            }
            Set<Integer> keySet = map.keySet();
            for (int key : keySet) {
                List<Integer> values = new ArrayList<>();
                Map<Integer, Integer> weights = new HashMap<>();
                for (List<Integer> list : map.get(key)) {
                    weights.put(list.get(0), list.get(1));
                    values.add(list.get(0));
                }
                Instances inst = new Instances(key, values, weights);
                instancesList.add(inst);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return instancesList;
    }
}
