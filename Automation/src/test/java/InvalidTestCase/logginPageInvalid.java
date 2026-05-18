package InvalidTestCase;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import java.time.Duration;

public class logginPageInvalid {
    @DataProvider(name = "loginInvalidData")
    public Object[][] getData() {
        return new Object[][] {

                {"locked_out_user", "secret_sauce"},
                {"IncorrectUsername", "secret_sauce"},
                {"performance_glitch_user","incorectPassword"},
                {"IncorrectUsername","incorectPassword"},
                {"  visual_user","    secret_sauce"},
                {" ","secret_sauce"},
                {"standard_user"," "}
                ,{" "," "}
        };
        }
    @Test(priority = 3,enabled = true,dataProvider ="loginInvalidData" , groups = "somke")
    public void validateLoginWithInvalidData(String username, String password) {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/");

        WebElement usernames = driver.findElement(By.xpath("//input[@placeholder=\"Username\"]"));
        usernames.sendKeys(username);

        WebElement passwords = driver.findElement(By.xpath("//input[@placeholder=\"Password\"]"));
        passwords.sendKeys(password);

        WebElement Button = driver.findElement(By.xpath("//input[@type=\"submit\"]"));
        Button.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

// wait for visibility
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[@data-test='error']")));

        WebElement massage = driver.findElement(By.xpath("//h3[@data-test='error']"));
        Assert.assertTrue(massage.isDisplayed());

        WebElement Closs = driver.findElement(By.xpath("//button[@class='error-button']"));
        Closs.click();

// wait for invisibility after clicking close
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//h3[@data-test='error']")));

//  re-check using findElements instead of stale WebElement
        Assert.assertTrue(driver.findElements(By.xpath("//h3[@data-test='error']")).isEmpty());

        driver.quit();
    }

}