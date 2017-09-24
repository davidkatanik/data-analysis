package cv09;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Utils {
	private static final String CSV_FILE = "KarateClub.csv";
	
	public static List<String> readDataFromFile() {
		String line = "";
		List<String> data = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE))) {
			while ((line = br.readLine()) != null) {
				line = line.replaceAll(",", ".");
				data.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}

	public static void saveToCsvFile(String csv1, String fileName) {
		try (BufferedWriter br = new BufferedWriter(new FileWriter(fileName))) {
			br.write(csv1);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
