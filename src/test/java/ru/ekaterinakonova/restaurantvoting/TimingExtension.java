package ru.ekaterinakonova.restaurantvoting;

import org.junit.jupiter.api.extension.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

public class TimingExtension implements BeforeTestExecutionCallback,
        AfterTestExecutionCallback, BeforeAllCallback, AfterAllCallback {
    private static final Logger log = LoggerFactory.getLogger("result");
    private StopWatch stopWatch;

    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {
        stopWatch = new StopWatch("Execution time of " + extensionContext.getRequiredTestClass().getSimpleName());
    }

    @Override
    public void beforeTestExecution(ExtensionContext extensionContext) throws Exception {
        log.info("Start stopWatch");
        stopWatch.start(extensionContext.getRequiredTestClass().getSimpleName());
    }

    @Override
    public void afterTestExecution(ExtensionContext extensionContext) throws Exception {
        log.info("Stop stopWatch");
        stopWatch.stop();
    }

    @Override
    public void afterAll(ExtensionContext extensionContext) throws Exception {
        log.info('\n' + stopWatch.prettyPrint() + '\n');
    }
}
