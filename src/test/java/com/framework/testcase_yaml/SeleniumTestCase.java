package com.framework.testcase_yaml;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class SeleniumTestCase extends BaseTestCase {
    public WebDriver driver;
    public WebElement currentElement;
    public By locator;

    public void run(){
        steps.forEach(step ->{
            if(step.containsKey("chrome")){
                driver = new ChromeDriver();
            }
            if(step.containsKey("implicitlyWait")){
                driver.manage().timeouts().implicitlyWait((int) getValue(step,"implicitlyWait"), TimeUnit.SECONDS);
            }
            if (step.containsKey("get")){
                driver.get(getValue(step,"get").toString());
            }
            if(step.containsKey("find")){
                HashMap<String,String> value = (HashMap<String, String>) step.get("find");

                if (value.containsKey("id")){
                    locator = By.id(value.get("id"));
                }else if (value.containsKey("name")){
                    locator = By.name(value.get("name"));
                }else if (value.containsKey("xpath")){
                    locator = By.xpath(value.get("xpath"));
                }
                currentElement = driver.findElement(locator);
            }
            if(step.containsKey("send_key")){
                sendKey(locator, getValue(step,"send_key").toString());
            }
            if (step.containsKey("click")){
                clickElement(locator);
            }
            if (step.containsKey("quit")){
                driver.quit();
            }
        });
    }


    public WebElement findElementVisibility(By locator){
        WebDriverWait webDriverWait = new WebDriverWait(driver,10);
        return webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement findElementClickable(By locator){
        WebDriverWait webDriverWait = new WebDriverWait(driver,10);
        return webDriverWait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public void clickElement(By locator){
        findElementClickable(locator).click();
    }

    public void sendKey(By locator,String key){
        findElementVisibility(locator).sendKeys(key);
    }


}
