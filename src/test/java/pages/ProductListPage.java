package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProductListPage extends Base{
	WebDriver driver1;

	@FindBy(xpath="//ul[@class='product_list grid row']/li") // busca mal, los del costado tamnbién tienen ese nomrbe
	List<WebElement> list;
	public ProductListPage(WebDriver driver) {
		super(driver);
		driver1 = driver;
	}
	
	public List<WebElement> listElements() {
		return list;
	}
	
	public void addToCart() {
		
	}
	

}
