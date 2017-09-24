package projekt;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import projekt.domain.PreprocessDataSample;

public class Utils {
	private static final String CSV_FILE = "adult.data";
	private static final String CSV_FILE_SPLITTER = ",";
	
	public static List<PreprocessDataSample> readDataFromFile() {
		String line = "";
		List<PreprocessDataSample> data = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE))) {
			while ((line = br.readLine()) != null) {
				data.add(new PreprocessDataSample(line, CSV_FILE_SPLITTER));
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
