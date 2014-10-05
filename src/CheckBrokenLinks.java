import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;


public class CheckBrokenLinks {

	public static void main(String[] args) {
		
		WebDriver driver = new FirefoxDriver();
//      driver.get("http://www.forbes.com/real-time/");
        driver.get("http://www.forbes.com/");
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
//      driver.navigate().to("http://www.forbes.com/australia-billionaires/list/"); // Replace here with the link you wanna test for 404 errors
        driver.navigate().to("http://www.forbes.com/?view=mobile-a");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//      driver.findElement(By.xpath("/html/body/section[2]/div/section[2]/div[2]/div/ol/li/a")).click();
          List<WebElement> linksList = driver.findElements(By.tagName("a"));
       for(WebElement linkElement: linksList){
           String link =linkElement.getAttribute("href");
           if(link!=null){
               if(!isLink(link)){
                   continue;
               }
               verifyLinkActive(link);
           }
       }
 }

       public static boolean isLink(String link){
          //Change the Link container based on what URL you are looking for//
           return link.contains("http://") && !link.contains("mailto") && !link.contains("[URL]");
       }

        public static void verifyLinkActive(String linkUrl){
           try {
              URL url = new URL(linkUrl);
              HttpURLConnection httpURLConnect=(HttpURLConnection)url.openConnection();
              httpURLConnect.setConnectTimeout(3000);
              httpURLConnect.connect();
              if(httpURLConnect.getResponseCode()==HttpURLConnection.HTTP_NOT_FOUND){
                  System.out.println(linkUrl+" - "+httpURLConnect.getResponseMessage()
                          + " - "+ HttpURLConnection.HTTP_NOT_FOUND);
               }
              else{
                System.out.println(linkUrl+" - Link is redirecting to the correct URL");
              }
           } catch (MalformedURLException e) {
               e.printStackTrace();
           } catch (IOException e) {
               e.printStackTrace();
           }
           
	}

}
