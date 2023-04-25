package co.edureka.devops;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.Action;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class FlipkartTest {

	WebDriver driver;
	

	@BeforeTest
	public void setup() {
		// Set the Chrome driver path
		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\ermina\\eclipse-workspace\\FlipKartPrroject\\driver\\chromedriver.exe");
		// Create a new ChromeDriver instance
		driver = new ChromeDriver();
		// Navigate to the given URL
		driver.get("https://www.flipkart.com/");
		// Maximize the browser window
		driver.manage().window().maximize();
		// Set the implicit wait time to 10 seconds
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		
	}

	@Test(priority = 1)
	public void closeLoginModal() {
		// Close the Login Modal Screen
		driver.findElement(By.xpath("//button[@class='_2KpZ6l _2doB4z']")).click();
	}

	@Test(priority = 2)
	public void hoverOnElectronicsMenu() {
		// Hover the Menu Electronics >> MI link
		// Hover over the Electronics menu and move to Electronics All
		WebElement electronicsMenu = driver.findElement(By.xpath("//div[contains(text(),'Electronics')]"));
        Actions actions = new Actions(driver);
        actions.moveToElement(electronicsMenu).perform();
        
     // Select the Electronics GST Store option
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); 
                
        WebElement electronicsGstStoreOption = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@class='_6WOcW9 _2-k99T']")));
        electronicsGstStoreOption.click();
        
        WebElement electronicsSubMenu = driver.findElement(By.xpath("//*[@id=\"container\"]/div/div[2]/div/div/span[1]"));
        Actions actions2 = new Actions(driver);
        actions2.moveToElement(electronicsSubMenu).perform();
        WebElement MiOption = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@title='Mi']")));
        MiOption.click();
	}

	@Test(priority = 3)
	public void verifyLatestFromMI() {
		// Verify “Latest from MI” label on the search result page
		String expectedLabel = "Newest First";
		String actualLabel = driver.findElement(By.xpath("//div[normalize-space()='Newest First']")).getText();
		Assert.assertEquals(actualLabel, expectedLabel, "Newest First");
	}

	@Test(priority = 4)
	public void changePriceSlider() {
		// Change the PRICE Slider
		 
		// Change the PRICE Slider
        WebElement priceSlider = driver.findElement(By.xpath("//div[@class='_31Kbhn WC_zGJ']//div[@class='_3FdLqY']"));
        int width = priceSlider.getSize().getWidth();
        Actions move = new Actions(driver);
        org.openqa.selenium.interactions.Action action = move.dragAndDropBy(priceSlider, (int)(width*0.4), 0).build();
        action.perform();
	}

	@Test(priority = 5)
	public void searchProduct() {
		// Search for “redmi go (black, 8 gb)” in the search bar
		WebElement searchBox = driver.findElement(By.xpath("//input[@placeholder='Search for products, brands and more']"));
		searchBox.sendKeys("redmi");
		searchBox.submit();
	}

	@Test(priority = 6)
	public void clickOnFirstProduct() {
		// Click on the first product displayed on the screen
		List<WebElement> productList = driver.findElements(By.xpath("//div[normalize-space()='REDMI 10 (Midnight Black, 128 GB)']"));
		WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(10)); 
		w.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[normalize-space()='REDMI 10 (Midnight Black, 128 GB)']")));
		productList.get(0).click();
	}

	@Test(priority = 7)
	public void verifyProductAmount() {
		// Verify that the Product Amount should be >= 0 (use New window switch)
		String mainWindow = driver.getWindowHandle();
		for (String handle : driver.getWindowHandles()) {
			driver.switchTo().window(handle);
		}
		String productAmountString = driver.findElement(By.xpath("//div[@class='_30jeq3 _16Jk6d']")).getText()
				.substring(1); // Remove the first character (₹ symbol)
		double productAmount = Double.parseDouble(productAmountString.replace(",", "")); // Remove the commas from the
																							// number
		Assert.assertTrue(productAmount >= 0, "Product amount is less than zero");
		//driver.close();
		//driver.switchTo().window(mainWindow);
	}

	@Test(priority = 8)
	public void playProductVideo() throws InterruptedException {
		// Click on image thumbnail displayed on the left side which has a play video
		// icon (use IFrame)
		
		//Thread.sleep(3000);
		WebElement videoThumbnail = driver
				.findElement(By.xpath("//div[@class='_3g-Cpg']"));
	
		videoThumbnail.click();
		
		WebElement videoFrame = driver.findElement(By.cssSelector("iframe[class='_1JqCrR']"));
		driver.switchTo().frame(videoFrame);
		WebElement playButton = driver.findElement(By.id("movie_player"));
		WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(10)); 
		w.until(ExpectedConditions.visibilityOfElementLocated(By.id("movie_player")));
		playButton.click();
		//Thread.sleep(5000);
		driver.switchTo().defaultContent();
	}

	@Test(priority = 9)
	public void enterDeliveryPincode() {
		// Enter the Delivery pincode by using keyboard event
		WebElement pincodeInput = driver.findElement(By.id("pincodeInputId"));
		pincodeInput.sendKeys("560001");
		WebElement checkButton = driver.findElement(By.xpath("//span[@class='_2P_LDn']"));
		checkButton.click();
	}

	@Test(priority = 10)
	public void verifyModalPopup() {
		// Click on “View Details” link under the Delivery pincode and verify that the
		// modal pop up is opened or not. Close the Modal (Use Modal)
		WebElement viewDetailsLink = driver.findElement(By.xpath("//span[@class='YxlyDn']"));
		viewDetailsLink.click();
		WebElement modalPopup = driver.findElement(By.xpath("(//button[@class='_2KpZ6l _1KAjNd'])[1]"));
		Assert.assertTrue(modalPopup.isDisplayed(), "Modal popup not displayed");
		WebElement closeModalButton = driver.findElement(By.xpath("(//button[@class='_2KpZ6l _1KAjNd'])[1]"));
		closeModalButton.click();
	}

	@Test(priority = 11)
	public void addToCart() {
		// Click on “Add to Cart”
		WebElement addToCartButton = driver.findElement(By.xpath("//button[normalize-space()='Add to cart']"));
		addToCartButton.click();
	}

	@AfterTest
	public void teardown() {
		// Close the browser
		driver.quit();
	}

}
