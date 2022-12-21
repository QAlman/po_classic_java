package web.tests.ui;

import com.codeborne.selenide.SelenideElement;
import common.cnst.Tags;
import io.qameta.allure.Owner;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.junit4.Tag;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import web.ops.logic.site.MainPageOperations;
import web.ops.logic.site.ReceiptsOps;

import java.util.List;

public class ReceiptsTests extends BaseUIClass {
    private final Logger log = Logger.getLogger(this.getClass().getName());

    @Before
    public void setUp() {
        mainPageOperations = new MainPageOperations(driver);
        receiptsOps = new ReceiptsOps(driver);
    }

    @Test
    @DisplayName("Проверка ссылок в блоках Рецепты/Статьи/Видео")
    @Owner(value = "Антон")
    @Tag(Tags.CLEAN_CACHE)
    public void checkReceiptLinkTest() {
        mainPageOperations
                .goToReceipts()
                .acceptCookies();

        String titleLink;
        String catalogLink;

        List<SelenideElement> selenideElements;

        List<List<SelenideElement>> elementsWithLinkCollection = receiptsOps.getElementsWithLinkCollection();

        for (List<SelenideElement> elements : elementsWithLinkCollection) {
            selenideElements = elements;

            titleLink = selenideElements.get(0).getAttribute("href");
            log.info("titlelink: " + titleLink);

            catalogLink = selenideElements.get(selenideElements.size() - 1).getAttribute("href");
            log.info("catalogLink: " + catalogLink);

            Assert.assertEquals(titleLink, catalogLink);
        }
    }

}
