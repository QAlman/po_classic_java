import io.qameta.allure.junit4.Tag;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import web.tests.rest.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        SkuAPITests.class,
        EcomAPITests.class,
        LentochkaAPITests.class,
        UserAPITests.class,
        WalletAPITests.class
})

public class EcomTests {
}
