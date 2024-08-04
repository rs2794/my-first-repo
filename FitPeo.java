package MyPackage;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

public class FitPeo {

	public static void main(String[] args) throws InterruptedException {

		System.setProperty("webdriver.chrome.driver", "C:\\Users\\rshekher\\Documents\\drivers\\chromedriver.exe");
		ChromeOptions option = new ChromeOptions();
		option.addArguments("--remote-allow-origins=*");
		WebDriver driver = new ChromeDriver(option);

		driver.manage().window().maximize();
      try {
		driver.get("https://www.fitpeo.com/");
		Thread.sleep(3000);
		driver.findElement(By.xpath("//div[contains(text(),'Revenue Calculator')]")).click();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,350)", "");
		WebElement sliderSection = driver.findElement(By.cssSelector(".MuiSlider-thumb"));
	
		Actions actions = new Actions(driver);
		int sliderWidth = sliderSection.getSize().width;
		int desiredValue = 820;
		int maxValue = 2000; // Adjust this based on the actual maximum value of the slider
		int xOffset = (int) (sliderWidth * (desiredValue / (double) maxValue));
		actions.clickAndHold(sliderSection).moveByOffset(xOffset, 0).release().perform();
		
		 // Update the Text Field to 560
        WebElement textField = driver.findElement(By.xpath("//input[@class='MuiInputBase-input MuiOutlinedInput-input MuiInputBase-inputSizeSmall css-1o6z5ng']]")); 
        textField.clear();
        textField.sendKeys("560");

        // Validate Slider Value
        WebElement updatedSlider = driver.findElement(By.xpath("//input[@class='MuiInputBase-input MuiOutlinedInput-input MuiInputBase-inputSizeSmall css-1o6z5ng']")); 
        // You may need to recalculate the offset if the slider adjusts dynamically
        int updatedValue = Integer.parseInt(textField.getAttribute("value"));
   

        // Select CPT Codes
        WebElement cpt99091Checkbox = driver.findElement(By.xpath("//input[@class='PrivateSwitchBase-input css-1m9pwf3'][1]"));
        WebElement cpt99453Checkbox = driver.findElement(By.xpath("//input[@class='PrivateSwitchBase-input css-1m9pwf3'][2]"));
        WebElement cpt99454Checkbox = driver.findElement(By.xpath("//input[@class='PrivateSwitchBase-input css-1m9pwf3'][3]"));
        WebElement cpt99474Checkbox = driver.findElement(By.xpath("//input[@class='PrivateSwitchBase-input css-1m9pwf3'][8]")); 
        cpt99091Checkbox.click();
        cpt99453Checkbox.click();
        cpt99454Checkbox.click();
        cpt99474Checkbox.click();

        // Validate Total Recurring Reimbursement
        WebElement totalReimbursementHeader = driver.findElement(By.xpath("//p[@class='MuiTypography-root MuiTypography-body2 inter css-1xroguk'][4]"));
        String reimbursementText = totalReimbursementHeader.getText();
        if (reimbursementText.contains("$110700")) {
            System.out.println("Total Recurring Reimbursement is correct: " + reimbursementText);
        } else {
            System.out.println("Total Recurring Reimbursement is incorrect: " + reimbursementText);
        }
	}finally {
        // Clean up
        driver.quit();
	}
}
}