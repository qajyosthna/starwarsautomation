package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ApiHomePage {

	private WebDriver webDriver = null;

	@FindBy(xpath = "//*[@id='interactive']")
	private WebElement endpointTextBox = null;

	@FindBy(xpath = "/html/body/div/div[3]/div[2]/div[1]/span[2]/button")
	private WebElement apiRequestButton = null;

//	@FindBy(xpath = "//*[@id=\"interactive_output\"]")
	@FindBy(id = "interactive_output")
	private WebElement apiResponse = null;

	@FindBy(xpath = "//*[@id=\"interactive_output\"]/text()")
	private String test = null;

	public ApiHomePage(WebDriver webDriver) {
		this.webDriver = webDriver;
		PageFactory.initElements(webDriver, this);
	}

	public String getHomePageTitle() {
		return webDriver.getTitle();
	}

	public void setEndpoint(String endpointText) {
		this.endpointTextBox.sendKeys(endpointText);
	}

	public void clearApiEndpoint() {
		endpointTextBox.clear();
	}

	public void sendApiRequest() {
		apiRequestButton.click();
	}

	public String readApiResponse() {

		try {
			Thread.sleep(1000);
		} catch (Exception e) {

		}
		return apiResponse.getText();
	}

	public String getResponseForEndpoint(String endpointText) {
		setEndpoint(endpointText);
		sendApiRequest();
		return readApiResponse();
	}

}
