package JUnitTesting;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
//import org.apache.commons.io.FileUtils;
import java.io.File;
import java.time.Duration;

public class SearchTest {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();
  JavascriptExecutor js;
  @Before
  public void setUp() throws Exception {
    System.setProperty("webdriver.chrome.driver", "C:\\Users\\brett\\Downloads\\Spring 2023\\Software Engeenering\\Project\\Test\\chromedriver.exe");
    driver = new ChromeDriver();
    baseUrl = "https://www.google.com/";
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
    js = (JavascriptExecutor) driver;
  }

  @Test
  public void testSearch() throws Exception {
    driver.get("http://localhost:8080/CSCI4830TermProject/LogIn.html");
    driver.findElement(By.name("uname")).click();
    driver.findElement(By.name("uname")).clear();
    driver.findElement(By.name("uname")).sendKeys("User100");
    driver.findElement(By.name("pwd")).clear();
    driver.findElement(By.name("pwd")).sendKeys("Password");
    driver.findElement(By.name("pwd")).sendKeys(Keys.ENTER);
    driver.get("http://localhost:8080/CSCI4830TermProject/HomePage.html");
    driver.findElement(By.id("month")).click();
    new Select(driver.findElement(By.id("month"))).selectByVisibleText("May");
    driver.findElement(By.id("year")).click();
    driver.findElement(By.id("year")).clear();
    driver.findElement(By.id("year")).sendKeys("2023");
    driver.findElement(By.xpath("//input[@value='Search']")).click();
    driver.get("http://localhost:8080/CSCI4830TermProject/MonthPage");
    driver.findElement(By.id("searchTitle")).click();
    driver.findElement(By.id("searchTitle")).clear();
    driver.findElement(By.id("searchTitle")).sendKeys("Testing");
    driver.findElement(By.id("searchYear")).click();
    driver.findElement(By.id("searchYear")).clear();
    driver.findElement(By.id("searchYear")).sendKeys("2023");
    driver.findElement(By.xpath("//input[@value='Submit']")).click();
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
