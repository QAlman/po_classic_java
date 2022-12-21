package web.tests.rest;

import org.junit.Rule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import web.tests.BaseTestClass;

import java.util.Arrays;
import java.util.List;

public class BaseRestClass extends BaseTestClass {

    @Rule
    public TestWatcher testWatcher = new TestWatcher() {

        @Override
        public Statement apply(Statement base, Description description) {
            return super.apply(base, description);
        }

        @Override
        protected void succeeded(Description description) {
            log.info("Test complete successfully");
            super.succeeded(description);
        }

        @Override
        protected void failed(Throwable e, Description description) {
            super.failed(e, description);
        }

        @Override
        protected void starting(Description description) {
            log.info("Test started: "+description.getMethodName());
            try {
                Thread.sleep(Math.round(Math.random()+1));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            super.starting(description);

        }

        @Override
        protected void finished(Description description) {
            super.finished(description);
        }
    };

    protected Boolean ìsRequiredFieldsInResponse(String fieldsList, String response) {
        List<String> fields = Arrays.asList(fieldsList.split(",\\s"));
        for (String field : fields) {
            if (!response.contains(field)) {
                log.info("Response does not contains required field: "+field);
                return false;
            }
        }
        return true;
    }

}
