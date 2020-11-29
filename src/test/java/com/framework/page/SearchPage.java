package com.framework.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SearchPage extends BasePage {
    private By searchInput = By.name("wd");
    private By searchBtn = By.id("su");

    public SearchPage(WebDriver driver) {
        this.driver = driver;
    }

    public SearchPage search(String searchMsg){
        sendKey(searchInput,searchMsg);
        clickElement(searchBtn);
        return this;
    }


}
