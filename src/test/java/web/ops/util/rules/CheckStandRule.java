package web.ops.util.rules;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import static web.ops.util.datautils.ConfigPropertiesUtil.getProperty;

public class CheckStandRule implements TestRule {
    Logger log = Logger.getLogger(this.getClass().getName());

    @Override
    public Statement apply(Statement statement, Description description) {

        Response response = RestAssured
                .get(getProperty("site.host"))
                .andReturn();
        Assert.assertEquals("Standu PIZDA, check stand ", 200, response.getStatusCode());
        log.info("Stand - OK");

        return statement;
    }
}
