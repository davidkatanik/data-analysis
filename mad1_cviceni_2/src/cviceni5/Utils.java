package cviceni5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Utils {
	private static final String CSV_FILE = "iris.csv";
	private static final String CSV_FILE_SPLITTER = ";";
	
	public static List<DataSample> readDataFromFile() {
		String line = "";
		List<DataSample> data = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE))) {
			line = br.readLine();
			while ((line = br.readLine()) != null) {
				line = line.replaceAll(",", ".");
				data.add(new DataSample(line, CSV_FILE_SPLITTER));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}
}
