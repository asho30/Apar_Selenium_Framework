package com.apar.pages;

import java.awt.AWTException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.apar.base.TestBase;
import com.apar.utils.Plans;

public class SubscribePage extends TestBase {
	String countriesListCss = "div#country";
	
	public SubscribePage() {

		PageFactory.initElements(driver, this);

	}

	public void changeCountry(String country) throws Exception {
		String countryLable = null;
		WebDriverWait wait = new WebDriverWait(driver, 10);
		WebElement countriesList = driver.findElement(By.cssSelector(countriesListCss));
		wait.until(ExpectedConditions.visibilityOf(countriesList));
		System.out.println("Page has been loaded");
		countriesList.click();
		switch (country.toLowerCase()) {
        case "ksa":
        	countryLable = "sa";
            break;
                
        case "kuwait":
        	countryLable = "kw";
            break;
                     
        case "bahrain":
        	countryLable = "bh";
            break;
                    
        default:
            System.out.println("Wrong Country.");
            break;
            }
		driver.findElement(By.cssSelector("span#" + countryLable + "-contry-lable")).click();
		return;
	}

	public String getCurrency(Plans plan) throws InterruptedException, AWTException {
		String currencyPlan = null, currency;
		switch (plan) {
        case LITE:
        	currencyPlan = "currency-lite";
            break;
                
        case CLASSIC:
        	currencyPlan = "currency-classic";
            break;
                     
        case PREMIUM:
        	currencyPlan = "currency-premium";
            break;
                    
        default:
            System.out.println("Wrong Plans.");
            break;
            }
		System.out.println("Selected Plans: " + currencyPlan);
		currency = "div.price#" + currencyPlan + " i";
		WebElement currencyPerPlan = driver.findElement(By.cssSelector(currency));
		System.out.println("Selected Currency: " + currencyPerPlan.getText().replace("/month", ""));
		return currencyPerPlan.getText().replace("/month", "");
	}
	
	public String getPrice(Plans plan) throws InterruptedException, AWTException {
		String pricePlan = null, price;
		switch (plan) {
        case LITE:
        	pricePlan = "currency-lite";
            break;
                
        case CLASSIC:
        	pricePlan = "currency-classic";
            break;
                     
        case PREMIUM:
        	pricePlan = "currency-premium";
            break;
                    
        default:
            System.out.println("Wrong Plans.");
            break;
            }
		System.out.println("Selected Plans: " + pricePlan);
		price = "div.price#" + pricePlan + " b";
		WebElement pricePerPlan = driver.findElement(By.cssSelector(price));
		System.out.println("Selected Currency: " + pricePerPlan.getText());
		return pricePerPlan.getText();
	}
}
