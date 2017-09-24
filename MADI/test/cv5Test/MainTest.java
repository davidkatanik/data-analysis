package cv5Test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import cviceni5.*;

public class MainTest {

	private static List<DataSample> data;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		data = Utils.readDataFromFile();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void calculateEuclideanDistanceTest() {
		Double[][] calculateEuclideanDistance = Main.calculateEuclideanDistance(data);
		assertNotNull(calculateEuclideanDistance);
	}

}
