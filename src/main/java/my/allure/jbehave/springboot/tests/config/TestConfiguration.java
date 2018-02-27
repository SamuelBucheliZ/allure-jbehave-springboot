package my.allure.jbehave.springboot.tests.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfiguration {
    @Value("${test.value}")
    private String testValue;

    public String getTestValue() {
        return testValue;
    }

    public void setTestValue(String testValue) {
        this.testValue = testValue;
    }

    @Override
    public String toString() {
        return "TestConfiguration{" +
                "testValue='" + testValue + '\'' +
                '}';
    }
}
