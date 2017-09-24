package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import domain.DataSample;

public class Utils {
	private static final String CSV_FILE = "D://weather_nominal.csv";
	private static final String CSV_FILE_SPLITTER = ";";
	
	public static List<DataSample> readDataFromFile() {
		String line = "";
		List<DataSample> gameConditions = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE))) {
			line = br.readLine();
			while ((line = br.readLine()) != null) {
				gameConditions.add(new DataSample(line, CSV_FILE_SPLITTER));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return gameConditions;
	}
}
