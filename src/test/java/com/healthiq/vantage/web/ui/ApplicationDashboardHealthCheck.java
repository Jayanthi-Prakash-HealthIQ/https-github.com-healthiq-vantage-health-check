package com.healthiq.vantage.web.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ApplicationDashboardHealthCheck {

	private WebDriver driver;
	private String baseUrl;
	private String userName = "jprakash@healthiq.co.uk";
	private String pwd = "PJKpjk_123";
	private static boolean loadedFirstTime = false;

	@Before
	public void before() throws Exception {
		final DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setJavascriptEnabled(true);
		capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
				"C:\\Program Files\\phantomjs-2.1.1-windows\\bin\\phantomjs.exe");
		driver = new PhantomJSDriver(capabilities);
		//baseUrl = "http://localhost:8080/";
		baseUrl = "https://www.vantageiq.co.uk/";
	}

	@After
	public void after() throws Exception {
		driver.close();
		driver.quit();
	}

	private void login(String userName, String pwd) {
		driver.get(baseUrl);
		final WebElement username = driver.findElement(By.name("username"));
		username.clear();
		username.sendKeys(userName);

		final WebElement password = driver.findElement(By.name("password"));
		password.clear();
		password.sendKeys(pwd);

		driver.findElement(By.cssSelector("button[type='submit']")).click();

	}
	
	private void logout() {
		driver.findElement(By.cssSelector(".vantagenav")).findElement(By.linkText("Logout")).click();
	}	

	private void goToHesDashboards() {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='diseasesArea.do']"))).click();		 
	}

	private void waitForLoadingToFinish(int timeOut, int pollEvery) {
		try {
			final Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(timeOut, TimeUnit.SECONDS)
					.pollingEvery(pollEvery, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);

			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".loading")));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void shouldHesDashboard() throws Exception {
		login(userName,pwd);
		goToHesDashboards();
		assertEquals(baseUrl + "diseasesArea.do", driver.getCurrentUrl());
	}	

	@Test
	public void shouldAudiologyGraph() throws Exception {
		login(userName,pwd);
		goToHesDashboards();
		List<WebElement> imageList = new ArrayList<WebElement> ();
		driver.findElement(By.xpath("//a[@href='dashboard.do?category=AUDIO&area=audio']")).click();
		if(loadedFirstTime)
			waitForLoadingToFinish(5,2);
		else
			waitForLoadingToFinish(60,10);
		imageList.add(driver.findElement(By.cssSelector("#chart_ma > svg")));
		imageList.add(driver.findElement(By.cssSelector("#chart_mp > svg")));
		imageList.add(driver.findElement(By.cssSelector("#chart_pa > canvas")));
		imageList.add(driver.findElement(By.cssSelector("#chart_cag > canvas")));
		imageList.add(driver.findElement(By.cssSelector("#chart_aap > svg")));
		imageList.add(driver.findElement(By.cssSelector("#chart_aa > svg")));
		logout();
		if(loadedFirstTime == false)
			loadedFirstTime = true;
		assertTrue(!imageList.contains(null));
	}
	
	@Test
	public void shouldAudiologyGraphCache() throws Exception {
		login(userName,pwd);
		goToHesDashboards();
		List<WebElement> imageList = new ArrayList<WebElement> ();
		driver.findElement(By.xpath("//a[@href='dashboard.do?category=AUDIO&area=audio']")).click();
		if(loadedFirstTime)
			waitForLoadingToFinish(5,2);
		else
			waitForLoadingToFinish(60,10);
		imageList.add(driver.findElement(By.cssSelector("#chart_ma > svg")));
		imageList.add(driver.findElement(By.cssSelector("#chart_mp > svg")));
		imageList.add(driver.findElement(By.cssSelector("#chart_pa > canvas")));
		imageList.add(driver.findElement(By.cssSelector("#chart_cag > canvas")));
		imageList.add(driver.findElement(By.cssSelector("#chart_aap > svg")));
		imageList.add(driver.findElement(By.cssSelector("#chart_aa > svg")));
		logout();
		if(loadedFirstTime == false)
			loadedFirstTime = true;		
		assertTrue(!imageList.contains(null));
	}	

}
