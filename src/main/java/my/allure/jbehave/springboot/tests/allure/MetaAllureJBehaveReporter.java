package my.allure.jbehave.springboot.tests.allure;

import io.qameta.allure.AllureLifecycle;
import io.qameta.allure.jbehave.AllureJbehave;
import io.qameta.allure.model.Label;
import io.qameta.allure.model.Link;
import io.qameta.allure.model.TestResult;
import io.qameta.allure.util.ResultsUtils;
import org.jbehave.core.model.Meta;

import java.util.function.Consumer;
import java.util.function.Function;

public class MetaAllureJBehaveReporter extends AllureJbehave {

    private interface TestResultUpdater {
        Consumer<TestResult> updateFromValue(String value);
    }

    private static TestResultUpdater linkUpdaterWith(Function<String, Link> linkCreator) {
        return linkString -> testResult -> testResult.getLinks().add(linkCreator.apply(linkString));
    }

    private static TestResultUpdater labelUpdaterWith(Function<String, Label> labelCreator) {
        return labelString -> testResult -> testResult.getLabels().add(labelCreator.apply(labelString));
    }

    private static TestResultUpdater descriptionUpdater() {
        return ownerString -> testResult -> testResult.setDescription(ownerString);
    }

    private enum MetaInfoFields {
        Issue(linkUpdaterWith(ResultsUtils::createIssueLink)),
        Owner(labelUpdaterWith(ResultsUtils::createOwnerLabel)),
        Feature(labelUpdaterWith(ResultsUtils::createFeatureLabel)),
        Description(descriptionUpdater());

        private final TestResultUpdater testResultUpdater;

        MetaInfoFields(TestResultUpdater testResultUpdater) {
            this.testResultUpdater = testResultUpdater;
        }

        public void addMetaToTestResult(Meta meta, AllureLifecycle lifecycle) {
            String propertyKey = this.name();
            if (meta.hasProperty(propertyKey)) {
                String property = meta.getProperty(propertyKey);
                Consumer<TestResult> testResultUpdater = this.testResultUpdater.updateFromValue(property);
                lifecycle.updateTestCase(testResultUpdater);
            }
        }
    }

    @Override
    public void scenarioMeta(Meta meta) {
        AllureLifecycle lifecycle = getLifecycle();
        for (MetaInfoFields metaInfoField : MetaInfoFields.values()) {
            metaInfoField.addMetaToTestResult(meta, lifecycle);
        }
    }

}
