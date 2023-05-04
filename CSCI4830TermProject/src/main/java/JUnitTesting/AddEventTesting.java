package com.example.FinalProj;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.apache.commons.io.FileUtils;
import java.io.File;

public class AddEventTesting {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();
  JavascriptExecutor js;
  @Before
  public void setUp() throws Exception {
    System.setProperty("webdriver.chrome.driver", "");
    driver = new ChromeDriver();
    baseUrl = "https://www.google.com/";
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
    js = (JavascriptExecutor) driver;
  }

  @Test
  public void testAddEventTesting() throws Exception {
    driver.get("http://localhost:8080/CSCI4830TermProject/LogIn.html");
    driver.findElement(By.name("uname")).click();
    driver.findElement(By.name("uname")).clear();
    driver.findElement(By.name("uname")).sendKeys("user100");
    driver.findElement(By.name("pwd")).click();
    driver.findElement(By.name("pwd")).clear();
    driver.findElement(By.name("pwd")).sendKeys("Password");
    driver.findElement(By.xpath("//input[@value='Log In']")).click();
    driver.get("http://localhost:8080/CSCI4830TermProject/HomePage.html");
    driver.findElement(By.id("year")).click();
    driver.findElement(By.id("year")).clear();
    driver.findElement(By.id("year")).sendKeys("2000");
    driver.findElement(By.id("year")).sendKeys(Keys.ENTER);
    driver.get("http://localhost:8080/CSCI4830TermProject/MonthPage");
    driver.findElement(By.linkText("Add Event")).click();
    driver.get("http://localhost:8080/CSCI4830TermProject/AddEvent.html");
    driver.findElement(By.id("date")).click();
    driver.findElement(By.id("date")).clear();
    driver.findElement(By.id("date")).sendKeys("2023-05-11");
    driver.findElement(By.id("title")).click();
    driver.findElement(By.id("title")).clear();
    driver.findElement(By.id("title")).sendKeys("Testing");
    driver.findElement(By.id("text2")).click();
    driver.findElement(By.id("text2")).clear();
    driver.findElement(By.id("text2")).sendKeys("Testing");
    driver.findElement(By.xpath("//input[@value='Create']")).click();
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}
