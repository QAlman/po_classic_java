# Repository for Autotests


# Тесты сайта


log.info("Send email");
loyaltyPage.emailInputField().sendKeys(deleteString);
loyaltyPage.emailInputField().sendKeys(defaultUser.getEmail());
/**Ожидаем пока пройдет 120 секунд от предыдущей отправки емейла, во время регистрации**/
//        sleep(120000);
loyaltyPage.sendAgainButton().click();
