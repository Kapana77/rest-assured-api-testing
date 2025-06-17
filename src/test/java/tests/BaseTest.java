package tests;

import ge.tbc.testautomation.util.AllureRestAssuredFilterSetup;
import org.testng.annotations.BeforeSuite;

public class BaseTest {
    @BeforeSuite
    public void setAllure() {
        AllureRestAssuredFilterSetup.setup();
    }
}
