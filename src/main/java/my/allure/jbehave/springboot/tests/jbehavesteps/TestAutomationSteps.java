package my.allure.jbehave.springboot.tests.jbehavesteps;

import my.allure.jbehave.springboot.tests.config.TestConfiguration;
import my.allure.jbehave.springboot.tests.more.SomeTestFunctions;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@Component
public class TestAutomationSteps {

    private static final Logger theLogger = LoggerFactory.getLogger(TestAutomationSteps.class);

    private final TestConfiguration theTestConfiguration;
    private final SomeTestFunctions theTestFunctions;

    @Autowired
    public TestAutomationSteps(TestConfiguration theTestConfiguration, SomeTestFunctions theTestFunctions) {
        this.theTestConfiguration = theTestConfiguration;
        this.theTestFunctions = theTestFunctions;
    }

    @Given("the initial system state")
    public void givenInitialSystemState() {
        // do nothing
    }

    @When("I do nothing")
    public void whenIDoNothing() {
        theTestFunctions.theTimeWarp();
    }

    @Then("the configuration is automatically loaded and injected")
    public void thenConfigurationIsAutomaticallyLoadedAndInjected() {
        assertThat(this.theTestConfiguration, is(notNullValue()));
        theLogger.info("Loaded tests configuration: {}", this.theTestConfiguration);
        assertThat(this.theTestConfiguration.getTestValue(), not(isEmptyOrNullString()));
    }
}
