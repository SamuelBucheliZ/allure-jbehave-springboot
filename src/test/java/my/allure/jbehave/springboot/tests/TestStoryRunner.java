package my.allure.jbehave.springboot.tests;

import my.allure.jbehave.springboot.tests.allure.MetaAllureJBehaveReporter;
import org.jbehave.core.Embeddable;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.io.CodeLocations;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.model.ExamplesTableFactory;
import org.jbehave.core.model.TableTransformers;
import org.jbehave.core.parsers.RegexStoryParser;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.ParameterConverters;
import org.jbehave.core.steps.spring.SpringStepsFactory;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.List;

import static org.jbehave.core.io.CodeLocations.codeLocationFromClass;
import static org.jbehave.core.reporters.Format.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { DummyTestApplication.class})
public class TestStoryRunner extends JUnitStories {

    private static final Logger theLogger = LoggerFactory.getLogger(TestStoryRunner.class);

    @Autowired
    private ApplicationContext applicationContext;

    public TestStoryRunner() {
        initJBehaveConfiguration();
    }

    private void initJBehaveConfiguration() {
        final Class<? extends Embeddable> embeddableClass = this.getClass();
        final ParameterConverters parameterConverters = new ParameterConverters();
        final TableTransformers tableTransformers = new TableTransformers();
        final ExamplesTableFactory examplesTableFactory = new ExamplesTableFactory(
                new LoadFromClasspath(embeddableClass),
                parameterConverters,
                tableTransformers);
        parameterConverters.addConverters(new ParameterConverters.DateConverter(new SimpleDateFormat("yyyy-MM-dd")),
                new ParameterConverters.ExamplesTableConverter(examplesTableFactory));
        Configuration configuration = new MostUsefulConfiguration()
                .useStoryLoader(new LoadFromClasspath(embeddableClass))
                .useStoryParser(new RegexStoryParser(examplesTableFactory))
                .useStoryReporterBuilder(new StoryReporterBuilder()
                        .withCodeLocation(CodeLocations.codeLocationFromClass(embeddableClass)).withDefaultFormats()
                        .withFormats(CONSOLE)
                        .withReporters(new MetaAllureJBehaveReporter())
                )
                .useParameterConverters(parameterConverters);
        useConfiguration(configuration);
    }

    @Override
    public InjectableStepsFactory stepsFactory() {
        return new SpringStepsFactory(configuration(), applicationContext);
    }

    @Override
    protected List<String> storyPaths() {
        final String myStoryNumberFromSystemEnv = System.getenv("StoryNumber");
        if (StringUtils.isEmpty(StringUtils.trimAllWhitespace(myStoryNumberFromSystemEnv))) {
            theLogger.info("No StoryNumber specified, running all stories");
            return new StoryFinder().findPaths(codeLocationFromClass(this.getClass()), "**/*.story",
                    "**/excluded*.story");
        } else {
            theLogger.info("Got StoryNumber: {}", myStoryNumberFromSystemEnv);
            return new StoryFinder().findPaths(codeLocationFromClass(this.getClass()),
                    "**/*" + myStoryNumberFromSystemEnv + "*.story", "**/excluded*.story");
        }
    }

}
