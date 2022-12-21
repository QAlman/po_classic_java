package web.ops.helpers;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonHelpers {

    public static String generateRandomCyrilicString(int len) {
        return generateRandomString(len, "АБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯабвгдежзийклмнопрстуфхцчшщъыьэюя");
    }

    public static String generateRandomLatinString(int len) {
        return generateRandomString(len, "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz");
    }


    private static String generateRandomString(int len, String letters) {
        SecureRandom rnd = new SecureRandom();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(letters.charAt(rnd.nextInt(letters.length())));
        return sb.toString();
    }


    public static void saveHTMLToFile(WebDriver driver, String filename) {
        File dirs = new File("report/html/");
        dirs.mkdirs();

        File output = new File(filename);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(output));
            writer.write(driver.getPageSource());
            writer.close();

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public static String getStringFromRegex(String message, String pattern) {
        Pattern pattern1 = Pattern.compile(pattern);
        Matcher matcher = pattern1.matcher(message);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    public static int generateRandomNumberInRange(int min, int max) {
        return (int) (Math.random() * (max-min)+min);
    }

    public static int generateRandomNumberInRange(int max) {
        return (int) (Math.random() * max);
    }

    public static String deleteString = Keys.chord(Keys.CONTROL, "a") + Keys.DELETE;

}
