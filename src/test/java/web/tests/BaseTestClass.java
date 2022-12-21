package web.tests;

import common.builder.UserBuilder;
import common.dto.TestData;
import common.dto.User;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Rule;
import web.ops.util.rules.CheckStandRule;
import web.ops.util.rules.GetTestData;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import static web.ops.util.datautils.ConfigPropertiesUtil.getProperty;

public abstract class BaseTestClass {

    @Rule
    public GetTestData getTestData = new GetTestData();

    @Rule
    public CheckStandRule checkStandRule = new CheckStandRule();


    protected Logger log = Logger.getLogger(this.getClass().getName());
    protected TestData testData;

    protected User defaultUser = new UserBuilder()
            .withPhone(79+getProperty("site.username"))
            .withDefaultPassword()
            .withLastName("Александрос")
            .execute();

    protected String host = getProperty("site.host");
    protected String kibanaUser = System.getenv("kibana.login");
    protected String kibanaPassword = System.getenv("kibana.password");
    protected Properties prop = new Properties();

    @Before
    public void beforeAll() {
        testData = getTestData.getTestData();

        prop.setProperty("Stand", host);
        try {
            prop.store(new FileOutputStream("target/allure-results/environment.properties"), null);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
