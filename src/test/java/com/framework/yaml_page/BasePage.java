package com.framework.yaml_page;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.framework.testcase_yaml.SearchTest;
import com.framework.testcase_yaml.SeleniumTestCase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class BasePage {

    public static BasePage instance = null;
    // 储存所有page对象
    public static HashMap<String,BasePage> pages = new HashMap<>();
    private SeleniumTestCase seleniumTestCase = new SeleniumTestCase();

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }
    public BasePage() { }
    public WebDriver driver;
    public HashMap<String,ArrayList<HashMap<String,Object>>> yamlSource = new HashMap<>();

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


    /**
     * 通过反射初始化PO 并将页面存入pages
     * @param objectName po对象名
     * @param className po全类名
     */
    public void initPO(String objectName,String className){


        // 数据驱动
        // todo: 读取相关yaml文件进行初始化
        BasePage newPage = new BasePage();
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
            System.out.println(className);
            TypeReference<HashMap<String,ArrayList<HashMap<String,Object>>>> typeReference = new TypeReference<HashMap<String, ArrayList<HashMap<String, Object>>>>() {};
            newPage.yamlSource = mapper.readValue(SearchTest.class.getResourceAsStream(String.format("/framework/%s", className)), typeReference);
            pages.put(objectName,newPage);
            newPage.runStep("init");

        } catch (IOException e) {
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
    public void runStep(String methodName) {

        // 数据驱动执行方法
        ArrayList<HashMap<String, Object>> steps = yamlSource.get(methodName);
        // 不能够重新初始化seleniumTestCase 每个对应页面都有相应的seleniumTestCase对象
        // 如果重新初始化了 页面相对应的seleniumTestCase属性就会被清空
        seleniumTestCase.steps = steps;
        // 暂时为用到参数化 用Arrays.asList("")代替
        seleniumTestCase.data = Arrays.asList("");
        seleniumTestCase.run();

    }
}
