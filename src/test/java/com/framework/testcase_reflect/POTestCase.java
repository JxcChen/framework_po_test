package com.framework.testcase_reflect;

import com.framework.reflect_page.BasePage;


import java.util.ArrayList;


public class POTestCase extends BaseTestCase {



    public void run(){
        // 遍历各步骤
        steps.forEach(step ->{
            // 获取到每个key 用作判断依据
            String key = step.keySet().iterator().next();
            // init表示初始化
            if (key.equals("init")){
                // todo: 通过key获取value（需要将参数进行替换）
                ArrayList<String> value = (ArrayList<String>) getValue(step,"init");
                // todo: 进行page初始化
                BasePage.getInstance().initPO(value.get(0),value.get(1));
            }
            // .表示执行方法
            if(key.contains(".")){
                ArrayList<String> value = (ArrayList<String>) getValue(step,key);
                // todo: 将object.method 切分开并获取到对象名和方法名
                String[] objectMethod = key.split("\\.");
                // 对象名
                String objectName = objectMethod[0];
                // 方法名
                String methodName = objectMethod[1];
                // todo: 调用PO相关方法 执行用例
                BasePage.getInstance().getPO(objectName).runStep(methodName, objectName, value);
            }
            // todo: 进行断言
        });
    }



}
