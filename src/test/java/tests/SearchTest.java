package tests;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pages.MainPage;
import pages.ProductListPage;

public class SearchTest {
	WebDriver driver;
	String url= "http://automationpractice.com/index.php";
	String chromeDriverPath= "..\\AutomationPractice\\Drivers\\chromedriver4.exe";
	MainPage main;
	ProductListPage plp;
	@BeforeTest
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", chromeDriverPath);
		driver = new ChromeDriver();
		driver.get(url);
		driver.manage().window().maximize();
	}
	
	@AfterTest
	public void clos() {
		driver.close();
	}
	
	@Test(dataProvider="searchText")
	public void search(String search) {
		main = new MainPage(driver);
		main.search(search);
		plp = new ProductListPage(driver);
		List<WebElement> list = plp.listElements();
		String text = null;
		for(WebElement l:list) {
			try {
			Assert.assertTrue(l.getText().toLowerCase().contains(search.toLowerCase()));
			}catch(java.lang.AssertionError e) {
				text=l.getText();
				System.out.println(text);
			}
		}
		Assert.assertNull(text);
	}
	
	@DataProvider(name="searchText")
	public Object[][] getData(){
		Object[][] data = new Object[2][1];
		data[0][0]="Dress";
		data[1][0]="Blouse";
		return data;
	}
	
	@Test(dataProvider="searchTextNoProduct")
	public void searchNoProduct(String search) {
		main = new MainPage(driver);
		main.search(search);
		plp = new ProductListPage(driver);
		List<WebElement> list = plp.listElements();
		if(list.size()==0) {
			Assert.assertTrue(driver.findElement(By.xpath("//*[@id=\"center_column\"]/p")).getText().contains("No results were found for your search"));
			Assert.assertTrue(driver.findElement(By.xpath("//*[@id=\"center_column\"]/h1/span")).getText().contains("0 results have been found."));
		}else {
			System.out.println("There are results for the search");
		}

	}
	
	@DataProvider(name="searchTextNoProduct")
	public Object[][] getDataNoProduct(){
		Object[][] data = new Object[2][1];
		data[0][0]="Jean";
		data[1][0]="Dress";
		return data;
	}
}
