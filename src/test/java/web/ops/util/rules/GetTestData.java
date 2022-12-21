package web.ops.util.rules;

import com.google.gson.JsonParser;
import common.dto.TestData;
import org.apache.log4j.Logger;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import web.ops.util.datautils.SQLUtil;

import java.sql.SQLException;

public class GetTestData implements TestRule {
    Logger log = Logger.getLogger(this.getClass().getName());
    TestData testData;

    @Override
    public Statement apply(Statement statement, Description description) {

        log.trace("Current test: " + description.getMethodName().replaceAll("(\\[.*])", ""));

        try {
            String obj = SQLUtil.queryWithResult("SELECT \"testValue\" FROM [stagesite-episerver].[dbo].[autotestdata] Where \"testName\" like '" + description.getMethodName() + "'").get("testValue");

            log.trace(obj);

            testData = new TestData(JsonParser.parseString(obj));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (NullPointerException | IndexOutOfBoundsException ex) {
            log.error("Test data was not found");
        }

        return statement;
    }

    public TestData getTestData() {
        return testData;
    }
}
