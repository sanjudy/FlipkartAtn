
package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class FlipkartPage {

    public static void main(String[] args) {

        WebDriver driver = new ChromeDriver();

        WebDriverWait wait = new WebDriverWait(
                driver, Duration.ofSeconds(20)
        );

        try {
            // 1. Open OLX
            driver.get("https://www.olx.in/");

            // 2. Select location
            By locationSearch = By.cssSelector(
                    "input[placeholder='Search city, area or locality']"
            );

            WebElement locationBox = wait.until(
                    ExpectedConditions.elementToBeClickable(locationSearch)
            );

            locationBox.click();
            locationBox.sendKeys(Keys.CONTROL, "a");
            locationBox.sendKeys("Hyderabad, Telangana");

            // 3. Click the city suggestion
            By citySuggestion = By.xpath(
                    "//div[@data-aut-id='locationItem']" +
                            "[.//span[normalize-space()='Hyderabad, Telangana']]"
            );

            wait.until(
                    ExpectedConditions.elementToBeClickable(citySuggestion)
            ).click();

            System.out.println(
                    "Location selected: Hyderabad, Telangana"
            );

            // 4. Enter the bike search term
            By searchBox = By.cssSelector(
                    "input[data-aut-id='searchBox']"
            );

            WebElement searchInput = wait.until(
                    ExpectedConditions.elementToBeClickable(searchBox)
            );

            searchInput.clear();
            searchInput.sendKeys("Pulsar N160");

            // 5. Submit the search using Enter
            searchInput.sendKeys(Keys.ENTER);

            // 6. Wait for search result listings
            By bikeListings = By.cssSelector(
                    "li[data-aut-id='itemBox2']"
            );

            wait.until(
                    ExpectedConditions.presenceOfElementLocated(
                            bikeListings
                    )
            );

            // 7. Print all available bike titles
            List<WebElement> listings =
                    driver.findElements(bikeListings);

            System.out.println(
                    "Total bike listings found: " + listings.size()
            );

            for (WebElement listing : listings) {
                List<WebElement> titles = listing.findElements(
                        By.cssSelector("h3[data-aut-id='itemTitle']")
                );

                if (!titles.isEmpty()) {
                    System.out.println(
                            "Available bike: " + titles.get(0).getText()
                    );
                }
            }

            // 8. Locate the Pulsar N160 listing
            By targetBike = By.xpath(
                    "//li[@data-aut-id='itemBox2']" +
                            "[.//h3[@data-aut-id='itemTitle' and " +
                            "contains(translate(normalize-space(.), " +
                            "'ABCDEFGHIJKLMNOPQRSTUVWXYZ', " +
                            "'abcdefghijklmnopqrstuvwxyz'), 'pulsar n160')]]"
            );

            WebElement bike = wait.until(
                    ExpectedConditions.elementToBeClickable(targetBike)
            );

            String bikeName = bike.findElement(
                    By.cssSelector("h3[data-aut-id='itemTitle']")
            ).getText();

            System.out.println("Selected bike: " + bikeName);

            // 9. Click the selected bike
            bike.findElement(By.cssSelector("a")).click();

            // 10. Wait for navigation to the details page
            wait.until(
                    ExpectedConditions.urlContains("/item/")
            );

            wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.tagName("body")
                    )
            );

            System.out.println(
                    "Bike details URL: " + driver.getCurrentUrl()
            );

            // 11. Count images on the details page
            By bikeImages = By.cssSelector(
                    "img"
            );

            List<WebElement> images = wait.until(
                    ExpectedConditions.presenceOfAllElementsLocatedBy(bikeImages)
            );

            // 12. Print image URLs
            int imageCount = 0;

            for (WebElement img : images) {

                String imageUrl = img.getAttribute("src");
                String altText = img.getAttribute("alt");

                // Filter images using their alt text or URL
                if ((altText != null &&
                        altText.toLowerCase().contains("pulsar n160")) ||
                        (imageUrl != null &&
                                imageUrl.toLowerCase().contains("pulsar"))) {

                    if (imageUrl != null && !imageUrl.isBlank()) {
                        imageCount++;
                        System.out.println(
                                "Pulsar N160 Image " + imageCount + ": " + imageUrl
                        );
                    }
                }
            }

            System.out.println(
                    "Total Pulsar N160 images found: " + imageCount
            );

        } catch (Exception e) {

            System.err.println(
                    "Test failed: " + e.getMessage()
            );

            System.err.println(
                    "Current URL: " + driver.getCurrentUrl()
            );

            System.err.println(
                    "Page title: " + driver.getTitle()
            );

            e.printStackTrace();

        } finally {
            driver.quit();
        }
    }
}
