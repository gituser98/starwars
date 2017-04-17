package starwars.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Temporary class to provide test data.
 * 
 * @author taner.alkaya
 *
 */
public class TestData {

	// id = 1
	String tatooineJSON = "{\"name\":\"Tatooine\",\"rotation_period\":\"23\",\"orbital_period\":\"304\",\"diameter\":\"10465\",\"climate\":\"arid\",\"gravity\":\"1 standard\",\"terrain\":\"desert\",\"surface_water\":\"1\",\"population\":\"200000\",\"residents\":[\"http://swapi.co/api/people/1/\",\"http://swapi.co/api/people/2/\",\"http://swapi.co/api/people/4/\",\"http://swapi.co/api/people/6/\",\"http://swapi.co/api/people/7/\",\"http://swapi.co/api/people/8/\",\"http://swapi.co/api/people/9/\",\"http://swapi.co/api/people/11/\",\"http://swapi.co/api/people/43/\",\"http://swapi.co/api/people/62/\"],\"films\":[\"http://swapi.co/api/films/5/\",\"http://swapi.co/api/films/4/\",\"http://swapi.co/api/films/6/\",\"http://swapi.co/api/films/3/\",\"http://swapi.co/api/films/1/\"],\"created\":\"2014-12-09T13:50:49.641000Z\",\"edited\":\"2014-12-21T20:48:04.175778Z\",\"url\":\"http://swapi.co/api/planets/1/\"}";
	
	// id = 30
	String socorroJSON = "{\"name\": \"Socorro\", \"rotation_period\": \"20\", \"orbital_period\": \"326\", \"diameter\": \"0\", \"climate\": \"arid\", \"gravity\": \"1 standard\", \"terrain\": \"deserts, mountains\", \"surface_water\": \"unknown\", \"population\": \"300000000\", \"residents\": [\"http://swapi.co/api/people/25/\"], \"films\": [], \"created\": \"2014-12-15T12:56:31.121000Z\", \"edited\": \"2014-12-20T20:58:18.469000Z\", \"url\": \"http://swapi.co/api/planets/30/\"}";

	// id = 61
	String jakkuJSON = "{\"name\": \"Jakku\", \"rotation_period\": \"unknown\", \"orbital_period\": \"unknown\", \"diameter\": \"unknown\", \"climate\": \"unknown\", \"gravity\": \"unknown\", \"terrain\": \"deserts\", \"surface_water\": \"unknown\", \"population\": \"unknown\", \"residents\": [], \"films\": [ \"http://swapi.co/api/films/7/\"], \"created\": \"2015-04-17T06:55:57.556495Z\", \"edited\": \"2015-04-17T06:55:57.556551Z\", \"url\": \"http://swapi.co/api/planets/61/\"}";
	
	
	Map<String, Planet> testPlanets = new HashMap<>();

	public TestData() {
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			
			Planet socorro = mapper.readValue(socorroJSON, Planet.class);
			testPlanets.put(socorro.getName(), socorro);
			
			Planet tatooine = mapper.readValue(tatooineJSON, Planet.class);
			testPlanets.put(tatooine.getName(), tatooine);
			
			Planet jakku = mapper.readValue(jakkuJSON, Planet.class);
			testPlanets.put(jakku.getName(), jakku);
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public Planet getPlanet(String planetName) {		
		return testPlanets.get(planetName);
	}

}
