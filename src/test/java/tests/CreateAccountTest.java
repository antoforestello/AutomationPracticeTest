package tests;

import java.awt.AWTException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import pages.CreateAccountPage;
import pages.SignInPage;
import utilities.DeleteFiles;
import utilities.ExcelToDataProvider;
import utilities.MyScreenRecorder;


public class CreateAccountTest {
	WebDriver driver;
	String url = "http://automationpractice.com/index.php?controller=authentication&back=my-account";
	String ChromeDriverPath = "..\\AutomationPractice\\Drivers\\chromedriver.exe";
	String FirefoxDriverPath = "..\\AutomationPractice\\Drivers\\geckodriver.exe";
	CreateAccountPage create;
	SignInPage signIn;
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss");
	String fecha = dateFormat.format(new Date());
	@BeforeSuite
	public void setUp() {
		//System.setProperty("webdriver.chrome.driver", ChromeDriverPath);
		System.setProperty("webdriver.gecko.driver", FirefoxDriverPath);
		//driver = new ChromeDriver();
		driver = new FirefoxDriver();
		driver.get(url);
		driver.manage().window().maximize();
	}
	@AfterSuite
	public void close() {
		//driver.close();
	}
	@BeforeMethod
	public void startRecording(Method method) throws IOException, AWTException {
		MyScreenRecorder.startRecording("./recordings/CreateAccountTest-"+fecha+"/"+method.getName());
	
	}
	@AfterMethod 
	public void stopRecording() throws Exception{
		MyScreenRecorder.stopRecording();
	}
	@Test(dataProvider = "validAccountData")
	public void validAccount(String email, String gender,String firstName, String lastName,String email2,  String password, String day, String month, String year, 
			String firstNameAddress, String lastNameAddress, String address, String city, String state, String postCode, String country, String phone, String alias){
		signIn= new SignInPage(driver);
	
		signIn.createAccount(email,gender,firstName,lastName,email2,password,day,month,year,firstNameAddress,lastNameAddress,address,city,state,postCode,country,phone,alias);
		create = new CreateAccountPage(driver);
		create.createAccount(gender,firstName,lastName,email2, password,  day,  month,  year, 
				 firstNameAddress,  lastNameAddress,  address,  city,  state,  postCode,  country,  phone,  alias);
		driver.manage().timeouts().implicitlyWait(25 , TimeUnit.SECONDS);
		Assert.assertTrue(signIn.isDisplayed(By.cssSelector("a.logout")));
		Assert.assertEquals(driver.getCurrentUrl(), "http://automationpractice.com/index.php?controller=my-account");
		Assert.assertFalse(signIn.isDisplayed(By.cssSelector("body.authentication.hide-left-column.hide-right-column.lang_en:nth-child(2) div.columns-container div.container div.row:nth-child(3) div.center_column.col-xs-12.col-sm-12 > div.alert.alert-danger")));
		if(signIn.isDisplayed(By.cssSelector("a.logout"))) {
			driver.findElement(By.cssSelector("a.logout")).click();
		}
	}
	@DataProvider(name="validAccountData")
	public Object[][] getValidAccountData() {
		Object[][] data = new Object[2][18];
		String[] valid = {""};
		data[0] = this.getArray2(valid);
		data[1] = this.getArray2(valid);
		return data;
	}

	@Test(dataProvider = "invalidEmailtData")
	public void invalidEmail(String email, String gender,String firstName, String lastName,String email2,  String password, String day, String month, String year, 
			String firstNameAddress, String lastNameAddress, String address, String city, String state, String postCode, String country, String phone, String alias){
		signIn= new SignInPage(driver);
		signIn.createAccount(email,gender,firstName,lastName,email2,password,day,month,year,firstNameAddress,lastNameAddress,address,city,state,postCode,country,phone,alias);
		driver.manage().timeouts().implicitlyWait(25 , TimeUnit.SECONDS);
		Assert.assertFalse(signIn.isDisplayed(By.cssSelector("a.logout")));
		Assert.assertTrue(signIn.isDisplayed(By.id("create_account_error")));
		Assert.assertTrue(driver.findElement(By.id("create_account_error")).getText().equalsIgnoreCase("Invalid email address."));
	}
	@DataProvider(name="invalidEmailtData")
	public Object[][] getInvalidEmailtData() {
		Object[][] data = new Object[1][18];
		String[] email= {"email"};
		data[0] = this.getArray2(email);
		return data;
	}
	@Test(dataProvider = "existsAccountWithEmail")
	public void existAccountWithMail(String email, String gender,String firstName, String lastName,String email2,  String password, String day, String month, String year, 
			String firstNameAddress, String lastNameAddress, String address, String city, String state, String postCode, String country, String phone, String alias){
		signIn= new SignInPage(driver);
		signIn.createAccount(email,gender,firstName,lastName,email2,password,day,month,year,firstNameAddress,lastNameAddress,address,city,state,postCode,country,phone,alias);
		driver.manage().timeouts().implicitlyWait(25 , TimeUnit.SECONDS);
		Assert.assertFalse(signIn.isDisplayed(By.cssSelector("a.logout")));
		Assert.assertTrue(signIn.isDisplayed(By.id("create_account_error")));
		Assert.assertTrue(driver.findElement(By.id("create_account_error")).getText().equalsIgnoreCase("An account using this email address has already been registered. Please enter a valid password or request a new one."));
	}
	@DataProvider(name="existsAccountWithEmail")
	public Object[][] getDataExistsAccountWithMail() {
		Object[][] data = new Object[1][18];
		String[] mailExiste= {"emailExistente"};
		data[0] = this.getArray2(mailExiste);
		return data;
	}
	@Test(dataProvider = "invalidAccountData2")
	public void invalidAccount(String email, String gender,String firstName, String lastName,String email2,  String password, String day, String month, String year, 
			String firstNameAddress, String lastNameAddress, String address, String city, String state, String postCode, String country, String phone, String alias){
		signIn= new SignInPage(driver);
		signIn.createAccount(email,gender,firstName,lastName,email2,password,day,month,year,firstNameAddress,lastNameAddress,address,city,state,postCode,country,phone,alias);
		create = new CreateAccountPage(driver);
		create.createAccount(gender,firstName,lastName,email2, password,  day,  month,  year, 
				 firstNameAddress,  lastNameAddress,  address,  city,  state,  postCode,  country,  phone,  alias);
		driver.manage().timeouts().implicitlyWait(15 , TimeUnit.SECONDS);
		Assert.assertFalse(signIn.isDisplayed(By.cssSelector("a.logout")));
		Assert.assertTrue(signIn.isDisplayed(By.cssSelector("#center_column > div")));
		if(firstName.equals("") || firstNameAddress.equals("")) {
		Assert.assertTrue(driver.findElement(By.cssSelector("#center_column > div")).getText().contains("firstname"));
		}
		if(lastName.equals("") || lastNameAddress.equals("")) {
			Assert.assertTrue(driver.findElement(By.cssSelector("#center_column > div")).getText().contains("lastname"));
		}
		if(email2.equals("aa")) {
			Assert.assertTrue(driver.findElement(By.cssSelector("#center_column > div")).getText().contains("email"));
		}
		if(password.equals("")) {
			Assert.assertTrue(driver.findElement(By.cssSelector("#center_column > div")).getText().contains("passwd"));
		}
		if(address.equals("")) {
			Assert.assertTrue(driver.findElement(By.cssSelector("#center_column > div")).getText().contains("address1"));
		}
		if(city.equals("")) {
			Assert.assertTrue(driver.findElement(By.cssSelector("#center_column > div")).getText().contains("city"));
		}
		if(state.equals("")) {
			Assert.assertTrue(driver.findElement(By.cssSelector("#center_column > div")).getText().contains("State"));
		}
		if(postCode.equals("")) {
			Assert.assertTrue(driver.findElement(By.cssSelector("#center_column > div")).getText().contains("Postal code"));
		}
		if(country.equals("-")) {
			Assert.assertTrue(driver.findElement(By.cssSelector("#center_column > div")).getText().contains("country"));
		}
		if(phone.equals("")) {
			Assert.assertTrue(driver.findElement(By.cssSelector("#center_column > div")).getText().contains("phone"));
		}
		
		if(signIn.isDisplayed(By.cssSelector("a.logout"))) {
			driver.findElement(By.cssSelector("a.logout")).click();
		}
		System.out.println("esta o no:"+signIn.isDisplayed(By.cssSelector(".alert")));
		if(signIn.isDisplayed(By.cssSelector(".alert"))) {
			driver.navigate().back();
		}
	}
	@DataProvider(name="invalidAccountData2")
	public Object[][] getInvalidAccountData2() {
		Object[][] data = new Object[12][18];
		String[] firstName = {"firstname"};
		data[0] = this.getArray2(firstName);
		String[] lastName = {"lastName"};
		data[1] = this.getArray2(lastName);
		String[] email2 = {"email2"};
		data[2] = this.getArray2(email2);
		String[] password = {"password"};
		data[3]= this.getArray2(password);
		String[] firstNameAddress = {"firstNameAddress"};
		data[4] = this.getArray2(firstNameAddress);
		String[] lastNameAddress = {"lastNameAddress"};
		data[5] = this.getArray2(lastNameAddress);
		String[] address = {"address"};
		data[6] = this.getArray2(address);
		String[] city = {"city"};
		data[7] = this.getArray2(city);
		String[] state = {"state"};
		data[8] = this.getArray2(state);
		String[] postCode = {"postCode"};
		data[9] = this.getArray2(postCode);
		String[] country = {"country"};
		data[10] = this.getArray2(country);
		String[] phone = {"phone"};
		data[11] = this.getArray2(phone);
		return data;
	}

		private Object[] getArray2(String[] wrongData) {
			Object[] data = new Object[18];
			Faker faker = new Faker();
			String fName= faker.name().firstName();
			String lName= faker.name().lastName();
			String email = faker.internet().emailAddress();
			data[0]= email;
			String[] gender= {"Mr.","Mrs."};
			int rnd = new Random().nextInt(gender.length);
			data[1]= gender[rnd];  
			data[2]= fName;
			data[3]= lName;
			data[4]= email;
			data[5]= faker.internet().password();
			data[6]= String.valueOf((int)(Math.random()*(31-1))+1);
			data[7]= String.valueOf((int) (Math.random()*(12-1))+1);
			data[8]= String.valueOf((int) (Math.random()*(2022-1900))+1900);
			data[9]= fName;
			data[10]= lName;
			data[11]= "Av siempre viva 333";
			data[12]= "City";
			data[13]= "Florida";
			data[14]= String.valueOf((int) (Math.random()*(39999-30000))+30000);
			data[15]= "United States";
			data[16]= faker.phoneNumber().cellPhone();
			data[17]= "Alias";
			for(String w: wrongData) {
			switch(w) {
			case "email": data[0]="aa"; break;
			case "emailExistente": data[0]="correo1@correo.com"; break;
			case "firstName": data[2]=""; break;
			case "lastName": data[3]=""; break;
			case "email2": data[4]="aa"; break;
			case "password": data[5]=""; break;
			case "firstNameAddress": data[9]=""; break;
			case "lastNameAddress": data[10]=""; break;
			case "address": data[11]=""; break;
			case "city": data[12]=""; break;
			case "state": data[13]=""; break;
			case "postCode": data[14]=""; break;
			case "country": data[15]="-"; break;
			case "phone": data[16]=""; break;
			}
			}

		return data;
	}
	
}
