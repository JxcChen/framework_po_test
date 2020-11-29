package com.framework.page;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class BasePage {

    public static BasePage instance = null;
    // 储存所有page对象
    public static HashMap<String,BasePage> pages = new HashMap<>();
    public BasePage(WebDriver driver) {
        this.driver = driver;
    }
    public BasePage() { }
    public WebDriver driver;


    /**
     * 单例模式
     * 获取BasePage实例
     * @return BasePage实例
     */
    public static BasePage getInstance() {
        if(instance==null){
            instance = new BasePage();
        }
        return instance;
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


    /**
     * 通过反射初始化PO 并将页面存入pages
     * @param objectName po对象名
     * @param className po全类名
     */
    public void initPO(String objectName,String className){

        try {
            BasePage page = (BasePage) Class.forName(className).newInstance();
            pages.put(objectName,page);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过对象名获取对应的PO
     * @param objectName 对象名
     * @return PO
     */
    public BasePage getPO(String objectName) {
        BasePage page = pages.get(objectName);
        return page;
    }

    /**
     * 通过反射机制获取到实际页面的方法 并运行目标方法
     * @param methodName 方法名
     * @param objectName 对象名
     * @param args 参数
     */
    public void runStep(String methodName,String objectName,ArrayList<String> args) {
        try {
            Method runMethod = Arrays.stream(this.getClass().getMethods()).filter(method -> method.getName().equals(methodName)).findFirst().get();
            Object returnResult;
            returnResult = runMethod.invoke(this,args.toArray());
            // todo:判断返回的数据，如果是page就存到pages中
            if (returnResult instanceof BasePage){
                BasePage page = (BasePage) returnResult;
                if (objectName.equals("mainPage")){
                    pages.put(methodName,page);
                }else{
                    pages.put(objectName,page);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
