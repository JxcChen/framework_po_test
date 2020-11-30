package com.framework.testcase_reflect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BaseTestCase {

    public List<String> data;
    public List<HashMap<String,Object>> steps;
    public int index = 0;

    /**
     * 测试用例裂变，基于数据自动生成多份测试用例
     * @return
     */
    public List<POTestCase> testcaseGenerate() {
        List<POTestCase> testCaseList=new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            POTestCase testcaseNew = new POTestCase();
            testcaseNew.index=i;
            testcaseNew.steps=steps;
            testcaseNew.data=data;
            testCaseList.add(testcaseNew);
        }
        return testCaseList;
    }

    /**
     * 将yaml文件中对应方法的参数进行替换
     * @param step 步骤
     * @param methodName 方法名
     * @return 替换后的值
     */
    public Object getValue(HashMap<String, Object> step, String methodName) {
        Object value = step.get(methodName);
        ArrayList<String> dataList= new ArrayList<>();
        if(value instanceof String){
            dataList.add( ((String) value).replace("${data}", data.get(index)));
            return dataList;
        }
        return value;
    }

    public void run() {
    }
}
