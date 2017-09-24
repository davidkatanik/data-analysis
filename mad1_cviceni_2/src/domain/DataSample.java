package domain;

/**
 * 
 * @author David Katanik
 *
 */
public class DataSample {
	private String loadedData;
	private String outlook;
	private String temperature;
	private String humidity;
	private String windy;
	private String play;
	
	public DataSample() {
		// TODO Auto-generated constructor stub
	}
	
	public DataSample(String loadedData, String splitter) {
		super();
		
		this.loadedData = loadedData;
		String[] data = loadedData.split(splitter);
		this.outlook = data[0];
		this.temperature = data[1];
		this.humidity = data[2];
		this.windy = data[3];
		this.play = data[4];
	}

	public String getLoadedData() {
		return loadedData;
	}

	public void setLoadedData(String loadedData) {
		this.loadedData = loadedData;
	}

	public String getOutlook() {
		return outlook;
	}

	public void setOutlook(String outlook) {
		this.outlook = outlook;
	}

	public String getTemperature() {
		return temperature;
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}

	public String getHumidity() {
		return humidity;
	}

	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}

	public String getWindy() {
		return windy;
	}

	public void setWindy(String windy) {
		this.windy = windy;
	}

	public String getPlay() {
		return play;
	}

	public void setPlay(String play) {
		this.play = play;
	}

}
