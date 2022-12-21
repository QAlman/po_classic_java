# Repository for Autotests

[Description of all process](https://dev.azure.com/LentaSolutions/LentaTeam/_wiki/wikis/LentaTeam.wiki/783/%D0%90%D0%B2%D1%82%D0%BE%D1%82%D0%B5%D1%81%D1%82%D0%B8%D1%80%D0%BE%D0%B2%D0%B0%D0%BD%D0%B8%D0%B5)

[Тестовые данные лежат здесь](https://dev.azure.com/LentaSolutions/LentaAutoTests/_wiki/wikis/LentaAutoTests.wiki/1356/Test-Data)

[Конфигурационные параметры тут](https://dev.azure.com/LentaSolutions/LentaAutoTests/_wiki/wikis/LentaAutoTests.wiki/1354/Configuration-Params)


# Тесты сайта
## Костыли:
private SelenideElement goodAddress() {
/**ФЛС изменили название класса для товаров в еком. Так как сейчас работает оба способа, делаю костыль**/
if ($(byClassName("sku-store-container__store-name")).isDisplayed()) {
return $(byClassName("sku-store-container__store-name"));
} else {
return $(byClassName("sku-store-container__store-search-toggler"));
}
}

public void registrationError() {
/**Очередной метод-костыль, поле может принять код не с первого раза*/
try {
loginPage.alertForm().getText();
loginPage.submit().click();
log.info("verification number is not accepted");
} catch (Throwable ex) {
log.info("verification number is accepted");
}
}


log.info("Send email");
loyaltyPage.emailInputField().sendKeys(deleteString);
loyaltyPage.emailInputField().sendKeys(defaultUser.getEmail());
/**Ожидаем пока пройдет 120 секунд от предыдущей отправки емейла, во время регистрации**/
//        sleep(120000);
loyaltyPage.sendAgainButton().click();


    /**
     * ожидает выполнение задачи US_51358. Нужно раз в РЦ докапываться до Игоря, Паши и исполнителя, иначе не пофиксят
     **/
    @Ignore
    @Test
    @DisplayName("Перевод фишек")
    @Owner(value = "Максим")
    @Tag(Tags.CLEAN_CACHE)

## Проблемы
1. Битые данные на тестовых серверах

