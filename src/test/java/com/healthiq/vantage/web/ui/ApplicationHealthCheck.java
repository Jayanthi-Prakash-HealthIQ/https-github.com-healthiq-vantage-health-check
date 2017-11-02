package com.healthiq.vantage.web.ui;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

public class ApplicationHealthCheck {
	private WebDriver driver;
	private String baseUrl;
	private String userName = "jprakash@healthiq.co.uk";
	private String pwd = "PJKpjk_123";
	
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
	
	 @Test
	 public void shouldDashboards() throws Exception {
		 login(userName,pwd);
		 List<WebElement> dashboards = driver.findElements(By.cssSelector(".controlB ul li a"));
		 final List<String> expectedDashboards = new ArrayList<String>();
		 final List<String> dashboardsPresent = new ArrayList<String>();
		 expectedDashboards.add("HES Dashboards");
		 expectedDashboards.add("Prescribing Dashboards");
		 expectedDashboards.add("RxViewer");
		 expectedDashboards.add("QOF Dashboards");
		 expectedDashboards.add("Custom Dashboards");
		 expectedDashboards.add("Commissioning Dashboards");
		 expectedDashboards.add("Bespoke Procedural Dashboard");
		 expectedDashboards.add("Query Builder");
		 for (WebElement dashboard : dashboards) {
		      dashboardsPresent.add(dashboard.getText().trim());
		 }
		 logout();
		 assertEquals(expectedDashboards,dashboardsPresent); 
	 }	

}
