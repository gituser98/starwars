package starwars.api;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Tests for: GET all http://swapi.co/api/planets
 * 
 * @author taner.alkaya
 *
 */
public class GetPlanetsTest extends BaseTest {

	@Test
	public void testHeadersJSON() {
		HttpResponse httpResponse = utils.getHttpResponse(BASE_URI, "json");
		Assert.assertEquals(httpResponse.getStatusLine().getStatusCode(), STATUS_CODE_OK);
	}

	@Test
	public void testHeadersXML() {
		HttpResponse httpResponse = utils.getHttpResponse(BASE_URI, "xml");
		Assert.assertEquals(httpResponse.getStatusLine().getStatusCode(), STATUS_CODE_NOT_ACCEPTABLE);
	}

	@Test
	public void testNumberOfPlanets() {

		HttpResponse httpResponse = utils.getHttpResponse(BASE_URI, "json");
		JsonNode node = utils.getJsonNode(utils.getJsonContent(httpResponse));

		Assert.assertEquals(node.get("count").asInt(), NUMBER_OF_PLANETS);
	}

	@Test
	public void testResultsArray() {

		JsonNode node = utils.getJsonNode(utils.getJsonContent(utils.getHttpResponse(BASE_URI, "json")));
		JsonNode userArray = node.get("results");

		Assert.assertEquals(userArray.size(), PLANETS_SHOWN_EACH_PAGE);
	}
	
	@Test
	public void testPageHasPlanet() throws JsonParseException, JsonMappingException, IOException {

		int pageNumber = 3;
		
		HttpResponse httpResponse = utils.getHttpResponse(BASE_URI + "?page=" + pageNumber, "json");
		JsonNode userArray = utils.getJsonNode(utils.getJsonContent(httpResponse)).get("results");

		Planet planet = mapper.readValue(userArray.get(8).toString(), Planet.class);
		utils.comparePlanets(planet, testData.getPlanet("Socorro"));
	}

	@Test(dataProvider = "invalidPageNumberProvider")
	public void testInvalidPageNumbers(int pageNumber) {

		HttpResponse httpResponse = utils.getHttpResponse(BASE_URI + "?page=" + pageNumber, "json");
		Assert.assertEquals(httpResponse.getStatusLine().getStatusCode(), STATUS_CODE_NOT_FOUND);
	}

	@Test
	public void testFirstPageWithAndWithoutPageNumber() {

		HttpResponse httpResponseWithPageNumber = utils.getHttpResponse(BASE_URI + "?page=1", "json");
		HttpResponse httpResponseWithoutPageNumber = utils.getHttpResponse(BASE_URI, "json"); // should return page 1

		Assert.assertEquals(utils.getJsonNode(utils.getJsonContent(httpResponseWithPageNumber)), utils.getJsonNode(utils.getJsonContent(httpResponseWithoutPageNumber)));
	}

	@Test
	public void testFirstPage() {

		HttpResponse httpResponse = utils.getHttpResponse(BASE_URI + "?page=1", "json");
		Assert.assertEquals(httpResponse.getStatusLine().getStatusCode(), STATUS_CODE_OK);

		JsonNode node = utils.getJsonNode(utils.getJsonContent(httpResponse));
		JsonNode userArray = node.get("results");

		Assert.assertEquals(node.get("next").asText(), BASE_URI + "?page=2");
		Assert.assertTrue(node.get("previous").isNull());	
		Assert.assertEquals(userArray.size(), PLANETS_SHOWN_EACH_PAGE);
	}

	@Test
	public void testRandomPage() {

		int pageNumber = 3;

		HttpResponse httpResponse = utils.getHttpResponse(BASE_URI + "?page=" + pageNumber, "json");
		Assert.assertEquals(httpResponse.getStatusLine().getStatusCode(), STATUS_CODE_OK);

		JsonNode node = utils.getJsonNode(utils.getJsonContent(httpResponse));
		JsonNode userArray = node.get("results");

		Assert.assertEquals(node.get("next").asText(), BASE_URI + "?page=" + (pageNumber + 1));
		Assert.assertEquals(node.get("previous").asText(), BASE_URI + "?page=" + (pageNumber - 1));
		Assert.assertEquals(userArray.size(), PLANETS_SHOWN_EACH_PAGE);
	}

	@Test
	public void testLastPage() {

		HttpResponse httpResponse = utils.getHttpResponse(BASE_URI + "?page=" + NUMBER_OF_PAGES, "json");
		Assert.assertEquals(httpResponse.getStatusLine().getStatusCode(), STATUS_CODE_OK);

		JsonNode node = utils.getJsonNode(utils.getJsonContent(httpResponse));

		Assert.assertEquals(node.get("previous").asText(), BASE_URI + "?page=" + (NUMBER_OF_PAGES - 1));
		Assert.assertTrue(node.get("next").isNull());

		JsonNode userArray = node.get("results");
		Assert.assertEquals(userArray.size(), 1);
	}

	@DataProvider(name = "invalidPageNumberProvider", parallel = false)
	public static Object[][] invalidPageNumberProvider() {

		return new Object[][] { new Object[] { -1 }, new Object[] { 0 }, new Object[] { NUMBER_OF_PAGES + 1 } };
	}

}
