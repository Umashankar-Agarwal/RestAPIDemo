package cucumber.options;


import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasspathResource("features") // Standard JUnit 5 way to locate your feature files
@ConfigurationParameter(key = "cucumber.glue", value = "StepDefinitions")
@ConfigurationParameter(key = "cucumber.plugin", value = "json:target/cucumber-reports/report.json")
public class RunCucumberTest {
    // Leave empty
}
