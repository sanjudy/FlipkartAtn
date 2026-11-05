package utilities;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class DailyPractice {
    private static WebDriver driver;
    private  static WebDriverWait wait;

    public static void main(String[] args) {

        driver = new ChromeDriver();
        try{
            //1 open bikedekho website
            driver.get("https://www.bikedekho.com/");
            driver.manage().window().maximize();

            //2 search for pulsar n160 bike
            wait = new WebDriverWait(driver, Duration.ofSeconds(15));

            By  searchBoxLocater = By.xpath("//input[@aria-label = 'Search Bikes or Brands']");

            WebElement searchBox = wait.until(
                    ExpectedConditions.elementToBeClickable(searchBoxLocater)
            );

            searchBox.sendKeys("pulsar n160");

            By  searchBoxBikeListLocater = By.xpath("//div[contains(@class,'typeHeadContainer')]//li[normalize-space(@data-value)='Bajaj Pulsar N160']");

            WebElement searchBoxBikeList =
                    wait.until(
                    ExpectedConditions.elementToBeClickable(searchBoxBikeListLocater)
            );

            searchBoxBikeList.click();




            //3 click on pulser n160 images

            By imagesLocater = By.xpath(
                    "//div[contains(@class,'app-content')]//li[contains(@class,'hover')]//img[@alt='Bajaj Pulsar N160']"
            );
            WebElement images =  wait.until(
                           ExpectedConditions.elementToBeClickable(imagesLocater)
            );

            images.click();

            Thread.sleep(10000);



            //4 count pulser n160 images

            List<WebElement> bikeimages = driver.findElements(
              By.xpath("//ul[@id='galleryslider']/li[.//img]")
            );

            int totalImages = bikeimages.size();
            System.out.println("Total Images: " + totalImages);




            //5 print the images count on console


        }catch(Exception e){
            e.printStackTrace();

        }finally{
            driver.quit();
        }

    }
}
