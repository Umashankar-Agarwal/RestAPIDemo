package cucumber.options;


import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasspathResource("features") // Standard JUnit 5 way to locate your feature files
@ConfigurationParameter(key = "cucumber.glue", value = "StepDefinitions")
public class RunCucumberTest {
    // Leave empty
}
