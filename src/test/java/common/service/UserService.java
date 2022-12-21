package common.service;

import common.builder.UserBuilder;
import common.dto.User;
import web.ops.util.datautils.SQLUtil;

import java.sql.SQLException;
import java.util.Map;

public class UserService {
    UserBuilder userBuilder = new UserBuilder();

    public User generateDefaultUser() {

        String phone = generatePhoneNumber();

        return userBuilder
                .withFirstName("Дефолтный")
                .withLastName("Пользак")
                .withBirthDate("1993-08-28T00:00:00.000Z")
                .withSex("male")
                .withPhone(phone)
                .withEmail(phone + "@cat.com")
                .withDefaultPassword()
                .execute();
    }

    private long getLastRegisteredPhoneInRange() throws SQLException {
        /**
         * FYI: Выбранный диапазон 7(962)***
         * TODO: сделать логин при помощи реста, тогда можно уйти от 79
         */
        return Long.parseLong(SQLUtil.queryWithResult("SELECT TOP (1) [PhoneNumber]\n" +
                "  FROM [stagesite-episerver].[dbo].[AspNetUsers]\n" +
                "  where [phoneNumber] like'7962%'\n" +
                "  order by [phoneNumber] desc").get("PhoneNumber"));
    }

    public String generatePhoneNumber() {
        try {
            return String.valueOf(this.getLastRegisteredPhoneInRange() + 1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return "null";
    }

    public User getUserFromDataBase(String userVar) {
        User user = new User();

        try {
            Map<String, String> resultSet = SQLUtil.queryWithResult("" +
                    "SELECT [PhoneNumber],\n" +
                    "       [Email],\n" +
                    "       lg.[ProviderKey],\n" +
                    "       crm.fkClientId\n" +
                    "     \n" +
                    "  FROM [stagesite-episerver].[dbo].[AspNetUsers] us\n" +
                    "  Inner join [stagesite-episerver].[dbo].[AspNetUserLogins] lg on lg.UserId=id\n" +
                    "  Inner join [stagesite-crm].[dbo].[tblCards] crm on lg.ProviderKey=crm.CardNumber\n" +
                    "  where PhoneNumber = " + userVar + " or email='" + userVar + "' or fkClientId = " + userVar + "\n");

            userBuilder
                    .withEmail(resultSet.get("Email"))
                    .withPhone(resultSet.get("PhoneNumber"))
                    .withCard(resultSet.get("ProviderKey"))
                    .withUserId("fkClientId");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return user;
    }

    public String generateCardNumber() {
        int part1 = (int) (Math.random() * 10000);
        int part2 = (int) (Math.random() * 10000);
        int part3 = (int) (Math.random() * 10000);

        return convertCardNumber(part1, part2, part3);
    }

    /**
     * @param interval Первые 4 цифры номера карты являющиеся определяющими последовательность
     * @return возвращается сгенерированый номер карты
     */
    public String generateCardNumber(Integer interval) {
        int part2 = (int) (Math.random() * 10000);
        int part3 = (int) (Math.random() * 10000);

        return convertCardNumber(interval, part2, part3);
    }

    private String convertCardNumber(int part1, int part2, int part3) {
        String strpart1 = String.valueOf(part1);
        String strpart2 = String.valueOf(part2);
        String strpart3 = String.valueOf(part3);

        if (strpart1.length() < 4) {
            strpart1 = "1" + strpart1;
        }
        if (strpart2.length() < 4) {
            strpart2 = "0" + strpart2;
        }

        if (strpart3.length() < 4) {
            strpart3 = "0" + strpart3;
        }

        return strpart1 + strpart2 + strpart3;
    }
}
