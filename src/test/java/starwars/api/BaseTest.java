package starwars.api;

import com.fasterxml.jackson.databind.ObjectMapper;

public class BaseTest {
	
	PlanetUtils utils = new PlanetUtils();
	TestData testData = new TestData();
	ObjectMapper mapper = new ObjectMapper();
	
	protected static final String BASE_URI = "http://swapi.co/api/planets/";
	protected static final int NUMBER_OF_PLANETS = 61;
	protected static final int PLANETS_SHOWN_EACH_PAGE = 10;
	protected static final int NUMBER_OF_PAGES = (NUMBER_OF_PLANETS / PLANETS_SHOWN_EACH_PAGE) + 1;

	protected static final int STATUS_CODE_NOT_FOUND = 404;
	protected static final int STATUS_CODE_NOT_ACCEPTABLE = 406;
	protected static final int STATUS_CODE_OK = 200;

}