package starwars.api;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Base class for tests.
 * 
 * @author taner.alkaya
 *
 */
public class BaseTest {
	
	Utils utils = new Utils();
	TestData testData = new TestData();
	ObjectMapper mapper = new ObjectMapper();
	
	static final String BASE_URI = "http://swapi.co/api/planets/";
	static final int NUMBER_OF_PLANETS = 61;
	static final int PLANETS_SHOWN_EACH_PAGE = 10;
	static final int NUMBER_OF_PAGES = (NUMBER_OF_PLANETS / PLANETS_SHOWN_EACH_PAGE) + 1;

	static final int STATUS_CODE_NOT_FOUND = 404;
	static final int STATUS_CODE_NOT_ACCEPTABLE = 406;
	static final int STATUS_CODE_OK = 200;

}