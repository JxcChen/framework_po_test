package com.framework.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class MainPage extends BasePage {


    public MainPage() {
        initDriver();
    }

    public void initDriver(){
        System.setProperty("webdriver.chrome.driver", "src\\main\\resources\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.get("https://www.baidu.com");
    }

    public SearchPage searchPage(){
        return new SearchPage(driver);
    }

}
