package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class CommonMethods {

    public static WebDriver driver;


    public static void setUp(){
        ConfigReader.readProperties(Constants.CONFIGURATION_FILEPATH);

        switch (ConfigReader.getPropertyValue("browser")){
            case "chrome":
                WebDriverManager.chromedriver().setup();
                //System.setProperty("webdriver.chrome.driver", "src/drivers/chromedriver.exe");
                if(ConfigReader.getPropertyValue("headless").equals("true")) {
                    ChromeOptions chromeOptions=new ChromeOptions();
                    chromeOptions.setHeadless(true);
                    driver = new ChromeDriver(chromeOptions);
                } else {
                    driver = new ChromeDriver();
                }




                break;
            case "firefox":
                System.setProperty("webdriver.gecko.driver", "src/drivers/geckodriver.exe");
                driver = new FirefoxDriver();
                break;
            default:
                throw new RuntimeException("Inavlid name of browser");
        }

        driver.get(ConfigReader.getPropertyValue("url"));
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

//    @Test
//    public void test(){
//        System.out.println(System.getProperty("os.name"));
//    }
    public static void sendText(WebElement element,String textToSend) {
        element.clear();
        element.sendKeys(textToSend);
    }

    public static WebDriverWait getWait() {
        WebDriverWait wait=new WebDriverWait(driver, Constants.EXPLICIT_WAIT);
        return wait;
    }

    public static void waitForClickability(WebElement element) {
        getWait().until(ExpectedConditions.elementToBeClickable(element));
    }

    public static void click(WebElement element) {
        waitForClickability(element);
        element.click();
    }

    public static JavascriptExecutor getJSExecuter() {
        JavascriptExecutor js=(JavascriptExecutor) driver;
        return js;
    }

    public static void jsClick(WebElement element) {
        getJSExecuter().executeScript("argument[0].click()",element);
    }

    public static byte[] takeScreenshot(String fileName){
        TakesScreenshot ts=(TakesScreenshot)driver;
        byte[] picBytes=ts.getScreenshotAs(OutputType.BYTES);
        File sourcefile=ts.getScreenshotAs(OutputType.FILE);
        try {
        FileUtils.copyFile(sourcefile, new File(Constants.SCREENSHOT_FILEPATH + fileName+" "+getTimeStamp("yyyy-MM-dd-HH-mm-ss")+".png"));
        }  catch (IOException e) {
            e.printStackTrace();
        }
        return picBytes;
    }
    //yyyy-mm-hh-mm


    public static String getTimeStamp(String pattern) {
        Date date=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat(pattern);
        return sdf.format(date);
    }


    public static void tearDown(){
        if(driver!=null){
            driver.quit();
        }
    }

}

