package pages;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SignInPage extends Base{

	@FindBy(id="email")
	WebElement txtEmail;
	@FindBy(id="passwd")
	WebElement txtPassword;
	@FindBy(id="SubmitLogin")
	WebElement btnSignIn;
	
	@FindBy(id="email_create")
	WebElement txtEmailCreate;
	@FindBy(id="SubmitCreate")
	WebElement btnCreate;
	
	CreateAccountPage create;
	SignInPage signIn;
	
	public SignInPage(WebDriver driver) {
		super(driver);
	}
	
	public void signIn(String email, String password) {
		try {
		this.clear(txtEmail);
		this.sendkeys(email, txtEmail);
		
		this.clear(txtPassword);
		this.sendkeys(password, txtPassword);
		this.click(btnSignIn);
		}catch(IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void createAccount(String email, String gender,String firstName, String lastName, String email2, String password, String day, String month, String year, 
			String firstNameAddress, String lastNameAddress, String address, String city, String state, String postCode, String country, String phone, String alias) {
		this.clear(txtEmailCreate);
		this.sendkeys(email, txtEmailCreate);
		this.click(btnCreate);
		create = new CreateAccountPage(driver);
		//create.createAccount(gender,firstName,lastName,email2, password,  day,  month,  year, 
	//			 firstNameAddress,  lastNameAddress,  address,  city,  state,  postCode,  country,  phone,  alias);

	}
	
	
}
