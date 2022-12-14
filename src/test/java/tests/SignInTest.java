package tests;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.Document;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.internal.TestNGMethod;

import pages.SignInPage;
import utilities.DeleteFiles;
import utilities.ExcelToDataProvider;
import utilities.MyScreenRecorder;



public class SignInTest {
	WebDriver driver;
	String url = "http://automationpractice.com/index.php?controller=authentication";
	String ChromeDriverPath = "..\\AutomationPractice\\Drivers\\chromedriver5.exe";
	String FirefoxDriverPath = "..\\AutomationPractice\\Drivers\\geckodriver.exe";
	SignInPage signInPage;
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss");
	String date = dateFormat.format(new Date());
	File file = new File("..\\AutomationPractice\\Evidencias\\SignIn "+date+".docx");
	XWPFDocument docx;
	
	@BeforeTest
	@Parameters("navegador")
	public void setUp(String navegador) {
		if(navegador.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", ChromeDriverPath);
			driver = new ChromeDriver();
		}else if(navegador.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver", FirefoxDriverPath);
			driver = new FirefoxDriver();
		}
		driver.get(url);
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
	}
	@BeforeMethod
	public void startRecording(Method method) throws IOException, AWTException {
		MyScreenRecorder.startRecording("./recordings/SignInTest-"+date+"/"+method.getName());
	}
	
		@AfterMethod
	public void tomarEvidencia(ITestResult result, Object[] args) throws IOException, InvalidFormatException {

		String imagePath = "..\\AutomationPractice\\Evidencias\\Screenshot.png";
		File screen = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(screen, new File(imagePath));
		if(!file.exists()) {
			docx = new XWPFDocument();
		}else {
			FileInputStream fileStream = new FileInputStream(file);
			docx = new XWPFDocument(fileStream);
		}
		XWPFParagraph paragraph = docx.createParagraph();
		XWPFRun run = paragraph.createRun();
		run.setText(result.getName()+"- email:"+args[0].toString()+", password: "+args[1].toString());
		run.setFontSize(13);
		
		InputStream pic = new FileInputStream(imagePath);

		run.addPicture(pic, Document.PICTURE_TYPE_PNG, imagePath,Units.toEMU(500), Units.toEMU(300));
		pic.close();

		FileOutputStream out = new FileOutputStream("..\\AutomationPractice\\Evidencias\\SignIn "+date+".docx");
		
		docx.write(out);
		out.flush();
		out.close();
		docx.close();
		
	}
	
	
	@AfterMethod 
	public void stopRecording() throws Exception{
		MyScreenRecorder.stopRecording();		
	}
	@AfterTest
	public void close(){
		driver.close();
	}
	
	
	@Test(dataProvider = "invalidEmailData")
	public void signInEmailInvalido(String email, String password){
		
		signInPage = new SignInPage(driver);
		signInPage.signIn(email, password);
		if(email!=null && password!=null) {
		Assert.assertTrue(signInPage.isDisplayed(By.xpath("//*[@id=\"center_column\"]/div[1]"))); 
		Assert.assertEquals(signInPage.getText(signInPage.findElement(By.xpath("//*[@id=\"center_column\"]/div[1]/ol/li"))), "Invalid email address." );
		Assert.assertFalse(signInPage.isDisplayed(By.cssSelector("a.logout")));
		Assert.assertEquals(driver.getCurrentUrl(), url);
		}
		
	}
	
	@DataProvider(name= "invalidEmailData")
	public Object[][] getDataSignInInvalidEmail(){
		Object[][] data = new Object[3][2];
		try {
			data= ExcelToDataProvider.getTable("..\\AutomationPractice\\ExcelData\\SignInTest.xlsx", "InvalidEmail");
		} catch(FileNotFoundException e1) {
			System.out.println(e1.getMessage());
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}
	
	@Test(dataProvider = "invalidPasswordData")
	public void signInInvalidPassword(String email, String password){
		signInPage = new SignInPage(driver);
		signInPage.signIn(email, password);
		if(email!=null && password!=null) {
		Assert.assertTrue(signInPage.isDisplayed(By.xpath("//*[@id=\"center_column\"]/div[1]"))); 
		Assert.assertEquals(signInPage.getText(signInPage.findElement(By.xpath("//*[@id=\"center_column\"]/div[1]/ol/li"))), "Invalid password." );
		Assert.assertFalse(signInPage.isDisplayed(By.cssSelector("a.logout")));
		Assert.assertEquals(driver.getCurrentUrl(), url);
		}
	}
	
	@DataProvider(name= "invalidPasswordData")
	public Object[][] getDataSignInInvalidPassword(){
		Object[][] data = new Object[3][2];
			try {
				data=ExcelToDataProvider.getTable("..\\AutomationPractice\\ExcelData\\SignInTest.xlsx", "InvalidPassword");
			} catch(FileNotFoundException e1) {
				System.out.println(e1.getMessage());
			}catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return data;
	}
	@Test(dataProvider = "requiredEmailData")
	public void signInRequiredEmail(String email, String password){
		signInPage = new SignInPage(driver);
		signInPage.signIn(email, password);
		if(email!=null && password!=null) {
		Assert.assertTrue(signInPage.isDisplayed(By.xpath("//*[@id=\"center_column\"]/div[1]"))); 
		Assert.assertEquals(signInPage.getText(signInPage.findElement(By.xpath("//*[@id=\"center_column\"]/div[1]/ol/li"))), "An email address required." );
		Assert.assertFalse(signInPage.isDisplayed(By.cssSelector("a.logout")));
		Assert.assertEquals(driver.getCurrentUrl(), url);
		}
	}
	
	@DataProvider(name= "requiredEmailData")
	public Object[][] getDataSignInRequiredEmail(){
		Object[][] data = new Object[3][2];
			try {
				data=ExcelToDataProvider.getTable("..\\AutomationPractice\\ExcelData\\SignInTest.xlsx", "RequiredEmail");
			} catch(FileNotFoundException e1) {
				System.out.println(e1.getMessage());
			}catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return data;
	}
	@Test(dataProvider = "requiredPasswordData")
	public void signInRequiredPassword(String email, String password){
		signInPage = new SignInPage(driver);
		signInPage.signIn(email, password);
		if(email!=null && password!=null) {
		Assert.assertTrue(signInPage.isDisplayed(By.xpath("//*[@id=\"center_column\"]/div[1]"))); 
		Assert.assertEquals(signInPage.getText(signInPage.findElement(By.xpath("//*[@id=\"center_column\"]/div[1]/ol/li"))), "Password is required." );
		Assert.assertFalse(signInPage.isDisplayed(By.cssSelector("a.logout")));
		Assert.assertEquals(driver.getCurrentUrl(), url);
		}
	}
	
	@DataProvider(name= "requiredPasswordData")
	public Object[][] getDataSignInRequiredPassword(){
		Object[][] data = new Object[3][2];
			try {
				data=ExcelToDataProvider.getTable("..\\AutomationPractice\\ExcelData\\SignInTest.xlsx", "RequiredPassword");
			} catch(FileNotFoundException e1) {
				System.out.println(e1.getMessage());
			}catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return data;
	}
	@Test(dataProvider = "authenticationFailedData")
	public void signInAuthenticationFailed(String email, String password){
		signInPage = new SignInPage(driver);
		signInPage.signIn(email, password);
		if(email!=null && password!=null) {
		Assert.assertTrue(signInPage.isDisplayed(By.xpath("//*[@id=\"center_column\"]/div[1]"))); 
		Assert.assertEquals(signInPage.getText(signInPage.findElement(By.xpath("//*[@id=\"center_column\"]/div[1]/ol/li"))), "Authentication failed." );
		Assert.assertFalse(signInPage.isDisplayed(By.cssSelector("a.logout")));
		}
	}
	
	
	@DataProvider(name= "authenticationFailedData")
	public Object[][] getDataSignInAuthenticationFailed(){
		Object[][] data = new Object[1][2];
			try {
				data=ExcelToDataProvider.getTable("..\\AutomationPractice\\ExcelData\\SignInTest.xlsx", "AuthenticationFailed");
			} catch(FileNotFoundException e1) {
				System.out.println(e1.getMessage());
			}catch (IOException e) {
				e.printStackTrace();
			}

		return data;
	}
	@Test
	public void validSignIn(){
		signInPage = new SignInPage(driver);
		signInPage.signIn("micorreo1@correo.com", "micontra");
		Assert.assertTrue(signInPage.isDisplayed(By.cssSelector("a.logout")));
		Assert.assertEquals(driver.getCurrentUrl(), "http://automationpractice.com/index.php?controller=my-account");
	}
	

	
	
}
