package pages;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class CreateAccountPage extends Base{

	@FindBy(xpath="//*[@id=\"account-creation_form\"]/div[1]/div[1]/div[1]")
	WebElement radioBtnMr;
	@FindBy(xpath="//*[@id=\"account-creation_form\"]/div[1]/div[1]/div[2]")
	WebElement radioBtnMrs;
	@FindBy(id="customer_firstname")
	WebElement txtFirstName;
	@FindBy(id="customer_lastname")
	WebElement txtLastName;
	@FindBy(id="email")
	WebElement txtEmail;
	@FindBy(id="passwd")
	WebElement txtPassword;
	@FindBy(id="days")
	WebElement selectDay;
	Select day = new Select(selectDay);
	@FindBy(id="months")
	WebElement selectMonth;
	Select month = new Select(selectMonth);
	@FindBy(id="years")
	WebElement selectYear;
	Select year = new Select(selectYear);
	@FindBy(id="firstname")
	WebElement txtFirstNameAddress;
	@FindBy(id="lastname")
	WebElement txtLastNameAddress;
	@FindBy(id="address1")
	WebElement txtAddress;
	@FindBy(id="city")
	WebElement txtCity;
	@FindBy(id="id_state")
	WebElement selectState;
	Select state = new Select(selectState);
	@FindBy(id="postcode")
	WebElement txtPostCode;
	@FindBy(id="id_country")
	WebElement selectCountry;
	Select country = new Select(selectCountry);
	@FindBy(id="phone_mobile")
	WebElement txtMobilePhone;
	@FindBy(id="alias")
	WebElement txtAlias;
	@FindBy(id="submitAccount")
	WebElement btnSubmitAccount;
	public CreateAccountPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	public  void createAccount(String gender,String firstName, String lastName,String email2, String password, String day, String month, String year, 
			String firstNameAddress, String lastNameAddress, String address, String city, String state, String postCode, String country, String phone, String alias) {

		if(gender.equalsIgnoreCase("mr")) {
			radioBtnMr.click();
		}else {
			radioBtnMrs.click();
		}
		txtFirstName.clear();
		txtFirstName.sendKeys(firstName);
		txtLastName.clear();
		txtLastName.sendKeys(lastName);
		txtEmail.clear();
		txtEmail.sendKeys(email2);
		txtPassword.clear();
		txtPassword.sendKeys(password);
		this.day.selectByValue(day);
		this.month.selectByValue(month);
		this.year.selectByValue(year);
		txtFirstNameAddress.clear();
		txtFirstNameAddress.sendKeys(firstNameAddress);
		txtLastNameAddress.clear();
		txtLastNameAddress.sendKeys(lastNameAddress);
		txtAddress.clear();
		txtAddress.sendKeys(address);
		txtCity.clear();
		txtCity.sendKeys(city);
		try {
		this.state.selectByVisibleText(state);
		}catch(NoSuchElementException e) {
			
		}
		txtPostCode.clear();
		txtPostCode.sendKeys(postCode);
		this.country.selectByVisibleText(country);
		txtMobilePhone.clear();
		txtMobilePhone.sendKeys(phone);
		btnSubmitAccount.click();
		
	}

}
