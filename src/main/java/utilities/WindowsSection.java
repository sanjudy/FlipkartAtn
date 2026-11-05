package utilities;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Set;

public class practice2 {
    private static WebDriver driver;
    public static void main(String[] args) {

       driver = new ChromeDriver();

       try{

           driver.get("file:///C:/Users/sanja/IdeaProjects/FlipKartFindImages/HtmlPages/InsuranceMain.html");

           String parentWindow = driver.getWindowHandle();

           System.out.println("parent window: " + parentWindow);

           driver.findElement(By.id("openReport")).click();

           Set<String> allWindows = driver.getWindowHandles();

           System.out.println("Total windows: " + allWindows.size());

           for(String window : allWindows){
               if(!window.equals(parentWindow)){
                   driver.switchTo().window(window);

                   break;
               }

               System.out.println("Child title: " + driver.getTitle());

               driver.findElement(By.id("approveClaim")).click();

               String status = driver.findElement(
                       By.id("claimStatus")).getText();

               System.out.println("Claim Status: " + status);

               driver.findElement(By.id("downloadReport")).click();

               System.out.println(
                       driver.findElement(By.id("message")).getText()
               );

               driver.switchTo().window(parentWindow);

               System.out.println("Parent Title: " + driver.getTitle());

           }


       }catch(Exception e){
           e.printStackTrace();
       }finally{
           driver.quit();
       }


    }
}