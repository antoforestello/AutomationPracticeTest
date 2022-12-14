package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MainPage extends Base{

	@FindBy(id= "search_query_top")
	WebElement txtSearch;
	@FindBy(name= "submit_search")
	WebElement btnSubmit;
	
	public MainPage(WebDriver driver) {
		super(driver);
	}
	
	public void search(String search) {
		txtSearch.clear();
		txtSearch.sendKeys(search);
		btnSubmit.click();
	}

}
