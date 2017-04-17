package starwars.api;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.testng.Assert;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PlanetUtils {

	private HttpGet httpGet;
	private CloseableHttpClient httpClient;
	private ObjectMapper mapper = new ObjectMapper();

	public HttpResponse getHttpResponse(String uri, String format) {

		if (uri == null || uri.isEmpty()) {
			throw new IllegalArgumentException("String can't be null or empty!");
		}

		httpGet = new HttpGet(uri);
		httpClient = HttpClientBuilder.create().build();

		if (format.equals("xml")) {
			httpGet.addHeader("Accept", "application/xml");
		}

		HttpResponse httpResponse = null;

		try {
			httpResponse = httpClient.execute(httpGet);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return httpResponse;
	}

	public JsonNode getJsonNode(String jsonContent) {

		if (jsonContent == null || jsonContent.isEmpty()) {
			throw new IllegalArgumentException("String can't be null or empty!");
		}

		JsonNode node = null;

		try {
			node = mapper.readTree(jsonContent);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return node;

	}

	public String getJsonContent(HttpResponse httpResponse) {

		if (httpResponse == null) {
			throw new IllegalArgumentException("HttpResponse can't be null!");
		}

		String jsonContent = null;

		try {
			jsonContent = IOUtils.toString(httpResponse.getEntity().getContent());
		} catch (UnsupportedOperationException | IOException e) {
			e.printStackTrace();
		}

		return jsonContent;

	}

	/**
	 * Compare two planets. Should be replaced with @Override equals() method in
	 * Planet class.
	 * 
	 * Planet class should have its own equals() method for object equality
	 * comparison.
	 * 
	 * @param p1
	 * @param p2
	 */
	public void comparePlanets(Planet p1, Planet p2) {

		Assert.assertEquals(p1.getName(), p2.getName());
		Assert.assertEquals(p1.getGravity(), p2.getGravity());
		Assert.assertEquals(p1.getResidents(), p2.getResidents());
		Assert.assertEquals(p1.getFilms(), p2.getFilms());
		Assert.assertEquals(p1.getEdited(), p2.getEdited());
		Assert.assertEquals(p1.getUrl(), p2.getUrl());
	}
}
