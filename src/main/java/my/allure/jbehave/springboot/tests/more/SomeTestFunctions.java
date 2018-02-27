package my.allure.jbehave.springboot.tests.more;

import org.springframework.stereotype.Component;

import io.qameta.allure.Step;

@Component
public class SomeTestFunctions {
    public static final class LogUtil {

        @Step("{0}")
        public static void log(final String message) {
            // intentionally empty
        }

        private LogUtil() {
        }
    }

    @Step("It's astounding")
    private void itsAstounding() {
        // do nothing
    }

    @Step("Madness takes its toll")
    private void madnessTakesItsToll(final String message) {
        // do nothing
    }

    @Step("The Time Warp")
    public void theTimeWarp() {
        itsAstounding();
        timeIsFleeting();
        madnessTakesItsToll("Madness sucks!");
    }

    @Step("Time is fleeting")
    private void timeIsFleeting() {
        LogUtil.log("What's your favorite rock group Riff?");
    }
}
