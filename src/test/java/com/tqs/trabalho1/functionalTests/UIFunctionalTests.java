package com.tqs.trabalho1.functionalTests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.tqs.trabalho1.Trabalho1Application;

import io.github.bonigarcia.seljup.SeleniumExtension;

import org.openqa.selenium.JavascriptExecutor;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Keys;
import java.util.*;
import java.net.MalformedURLException;
import java.net.URL;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT, classes = Trabalho1Application.class)
@ExtendWith(SeleniumExtension.class)
public class UIFunctionalTests {
	
	private WebDriver driver;
	  private Map<String, Object> vars;
	  JavascriptExecutor js;
	  
	  @BeforeEach
	  public void setUp() {
		  System.setProperty("webdriver.gecko.driver","/home/edgar/Transferências/geckodriver");
		  driver = new FirefoxDriver();
		  js = (JavascriptExecutor) driver;
		  vars = new HashMap<String, Object>();
	  }
	  @AfterEach
	  public void tearDown() {
	    driver.quit();
	  }
	  
	  @Test
	  public void successfulsearch() {
	    driver.get("http://localhost:8080/index/");
	    driver.manage().window().setSize(new Dimension(594, 663));
	    driver.findElement(By.id("country")).click();
	    driver.findElement(By.id("country")).sendKeys("Portugal");
	    driver.findElement(By.id("state")).click();
	    driver.findElement(By.id("state")).sendKeys("Aveiro");
	    driver.findElement(By.id("city")).click();
	    driver.findElement(By.id("city")).sendKeys("Aveiro");
	    driver.findElement(By.cssSelector(".btn")).click();
	    assertThat(driver.findElement(By.cssSelector("p:nth-child(1) > span")).getText()).isEqualTo("Portugal");
	    assertThat(driver.findElement(By.cssSelector("p:nth-child(2) > span")).getText()).isEqualTo("Aveiro");
	    assertThat(driver.findElement(By.cssSelector("p:nth-child(3) > span")).getText()).isEqualTo("Aveiro");
	    driver.findElement(By.linkText("Search another city")).click();
	    assertThat(driver.findElement(By.xpath("//h1[2]")).getText()).isEqualTo("Get Air Quality");
	  }
	  
	  @Test
	  public void unsuccessfullsearch() {
	    driver.get("http://localhost:8080/index/");
	    driver.manage().window().setSize(new Dimension(594, 663));
	    driver.findElement(By.id("country")).click();
	    driver.findElement(By.id("country")).sendKeys("asd");
	    driver.findElement(By.id("state")).click();
	    driver.findElement(By.id("state")).sendKeys("asd");
	    driver.findElement(By.id("city")).click();
	    driver.findElement(By.id("city")).sendKeys("asd");
	    driver.findElement(By.cssSelector(".btn")).click();
	    assertThat(driver.findElement(By.cssSelector(".alert-heading")).getText()).isEqualTo("Error:");
	    assertThat(driver.findElement(By.cssSelector("p")).getText()).isEqualTo("Location specified not found.");
	    driver.findElement(By.linkText("Try to search another city")).click();
	    assertThat(driver.findElement(By.xpath("//h1[2]")).getText()).isEqualTo("Get Air Quality");
	  }

}
