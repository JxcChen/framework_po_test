package com.framework.testcase_yaml;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import junit.framework.TestCase;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.List;

public class SearchTest {



    @ParameterizedTest
    @MethodSource()
    void search(BaseTestCase testCase){

        testCase.run();

    }

    public static List<BaseTestCase> search() {
        try {
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory()){};
            BaseTestCase testCase = mapper.readValue(SearchTest.class.getResourceAsStream("/framework/po_yaml_test.yaml"), POTestCase.class);
            return testCase.testcaseGenerate();
        } catch (IOException e) {
            e.printStackTrace();
        }
            return null;
    }
}
