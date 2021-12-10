package testcase;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.google.gson.JsonObject;

import io.github.bonigarcia.wdm.WebDriverManager;
import pages.ApiHomePage;

public class TestHomePage {

	private static final int TOTAL_PEOPLE_COUNT = 82;
	private static final Set<String> NAMES_LIST = Set.of("Darth Vader", "Chewbacca", "Roos Tarpals", "Rugor Nass",
			"Yarael Poof", "Lama Su", "Taun We", "Grievous", "Tarfful", "Tion Medon");
	private WebDriver webDriver = null;
	private ApiHomePage apiHomePage = null;

	@BeforeTest
	public void setup() {
		WebDriverManager.chromedriver().setup();
		webDriver = new ChromeDriver();
		webDriver.manage().window().maximize();
		webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		webDriver.get("https://swapi.dev/");
	}

	@Test
	public void test_home_page_title() {
		apiHomePage = new ApiHomePage(webDriver);
		Assert.assertEquals(apiHomePage.getHomePageTitle(), "SWAPI - The Star Wars API");
	}

	@Test
	public void test_endpoint_people_success() {
		apiHomePage = new ApiHomePage(webDriver);
		String response = apiHomePage.getResponseForEndpoint("people");
		Assert.assertTrue(StringUtils.isNotEmpty(response));

	}

	@Test
	public void test_api_people_height_greater_than_200() {
		apiHomePage = new ApiHomePage(webDriver);
		int count = 0;
		for (int i = 1; i <= 83; i++) {
			String response = apiHomePage.getResponseForEndpoint("people/" + i);
			if (!"404 error".equals(response)) {
				JSONObject results = new JSONObject(response);
				String heightStr = results.getString("height");
				if (StringUtils.isNumeric(heightStr)) {
					int height = Integer.parseInt(heightStr);
					if (height > 200) {
						count++;
					}
				}
			}
			apiHomePage.clearApiEndpoint();

		}

		Assert.assertEquals(10, count);
	}

	@Test
	public void test_api_people_names_in_list() {
		apiHomePage = new ApiHomePage(webDriver);
		int count = 0;
		Set<String> resultNames = new HashSet();
		for (int i = 1; i <= 83; i++) {
			String response = apiHomePage.getResponseForEndpoint("people/" + i);
			if (!"404 error".equals(response)) {
				JSONObject results = new JSONObject(response);
				String name = results.getString("name");
				if (NAMES_LIST.contains(name) && resultNames.size() < NAMES_LIST.size()) {
					resultNames.add(name);
				}
			}
			apiHomePage.clearApiEndpoint();

		}
		
		System.out.println(resultNames);

		Assert.assertEquals(NAMES_LIST.size(), resultNames.size());
	}

	@Test

	public void test_api_people_total_count() {
		apiHomePage = new ApiHomePage(webDriver);
		String response = apiHomePage.getResponseForEndpoint("people");

		JSONObject jsonObject = new JSONObject(response);
		Assert.assertEquals(TOTAL_PEOPLE_COUNT, jsonObject.getInt("count"));
	}
	
	@AfterTest
  	public void closeDriver() {
    		webDriver.close();
  	}

}
