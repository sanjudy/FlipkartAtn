
package utilities;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class Practice {

    public static void main(String[] args) {
    //sanjay
        ChromeDriver driver = new ChromeDriver();

        try {
            WebDriverWait wait =
                    new WebDriverWait(driver, Duration.ofSeconds(90));

            // Step 1: Open ChatGPT
            driver.get("https://chatgpt.com/");

            // Step 2: Wait for the prompt box
            WebElement prompt = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.id("mobile-composer-prompt")
                    )
            );

            // Step 3: Enter the question
            prompt.sendKeys(
                    "What is the weather in Hyderabad today? " +
                            "Tell me the current temperature, weather conditions, " +
                            "today's high and low."
            );

            // Step 4: Count existing completed assistant messages
            By completedMessages = By.cssSelector(
                    "li[data-message-role='assistant']" +
                            "[data-message-complete]"
            );

            int previousCount =
                    driver.findElements(completedMessages).size();

            // Step 5: Click the Send button
            WebElement sendButton = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.cssSelector("button[data-composer-submit]")
                    )
            );

            sendButton.click();

            // Step 6: Wait for a new completed assistant response
            wait.until(d -> {
                List<WebElement> messages =
                        d.findElements(completedMessages);

                if (messages.size() <= previousCount) {
                    return false;
                }

                WebElement latest =
                        messages.get(messages.size() - 1);

                String responseText = latest.getText().trim();

                return !responseText.isEmpty()
                        && !responseText.equals("ChatGPT said:");
            });

            // Step 7: Get the latest response
            List<WebElement> messages =
                    driver.findElements(completedMessages);

            WebElement latestResponse =
                    messages.get(messages.size() - 1);

            // Step 8: Print the response in the console
            System.out.println(
                    "\n========== CHATGPT RESPONSE ==========\n"
            );

            System.out.println(latestResponse.getText());

            System.out.println(
                    "\n=======================================\n"
            );

        } catch (Exception e) {

            System.out.println("An error occurred:");
            System.out.println(e.getMessage());

            // Uncomment this line to print the full error details.
            // e.printStackTrace();

        } finally {

            // Step 9: Close the browser
            driver.quit();
        }
    }
}

