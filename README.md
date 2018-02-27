# Testbed for Allure / JBehave / SpringBoot Integration

Sample of how to integrate [Allure](http://allure.qatools.ru/), [JBehave](http://jbehave.org/), and [SpringBoot](https://projects.spring.io/spring-boot/) in one project.

Run with
```
mvn clean integration-test -Dtest=TestStoryRunner allure:report -PenableIntegrationTests -Dspring.profiles.active=local allure:serve
```

To launch Allure separately
```
.allure/allure-2.0.1/bin/allure generate target/allure-results/
.allure/allure-2.0.1/bin/allure open
```