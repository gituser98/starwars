package starwars.api;

import org.apache.http.HttpResponse;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Tests for: GET by {id} http://swapi.co/api/planets/{id}.
 * 
 * @author taner.alkaya
 *
 */
public class GetPlanetsByID extends BaseTest {

	@Test(dataProvider = "invalidPlanetIdProvider")
	public void testInvalidID(int pageNumber) {

		HttpResponse httpResponse = utils.getHttpResponse(BASE_URI + pageNumber, "json");

		Assert.assertEquals(httpResponse.getStatusLine().getStatusCode(), STATUS_CODE_NOT_FOUND);
		Assert.assertEquals(utils.getJsonNode(utils.getJsonContent(httpResponse)).get("detail").asText(), "Not found");
	}

	@Test(dataProvider = "planetIdProvider")
	public void testGetPlanetByID(int pageNumber, String planetName) throws JsonProcessingException {

		HttpResponse httpResponse = utils.getHttpResponse(BASE_URI + pageNumber, "json");
		JsonNode node = utils.getJsonNode(utils.getJsonContent(httpResponse));

		Assert.assertEquals(httpResponse.getStatusLine().getStatusCode(), STATUS_CODE_OK);
		utils.comparePlanets(mapper.treeToValue(node, Planet.class), testData.getPlanet(planetName));
	}

	@DataProvider(name = "planetIdProvider", parallel = false)
	public static Object[][] planetIdProvider() {

		return new Object[][] { new Object[] { 1, "Tatooine" }, new Object[] { 30, "Socorro" },
				new Object[] { 61, "Jakku" } };
	}

	@DataProvider(name = "invalidPlanetIdProvider", parallel = false)
	public static Object[][] invalidPlanetIdProvider() {

		return new Object[][] { new Object[] { -1 }, new Object[] { 0 }, new Object[] { NUMBER_OF_PLANETS + 1 } };
	}

}
