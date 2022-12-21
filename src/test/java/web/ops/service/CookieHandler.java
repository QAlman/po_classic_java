package web.ops.service;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class CookieHandler {

    private CookieHandler() {

    }

    public void writeCookiesToFile(List<?> cookies) {
        File file = new File("Cookies.data");
        try {
            // Delete old file if exists
            file.delete();
            file.createNewFile();
            FileWriter fileWrite = new FileWriter(file);
            BufferedWriter Bwrite = new BufferedWriter(fileWrite);

            // loop for getting the cookie information
            for (String ck : processCookies(cookies)) {
                Bwrite.write(ck.replaceAll("=", "").replaceAll("; ", ";"));
                Bwrite.newLine();
            }
            Bwrite.close();
            fileWrite.close();


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Set<Cookie> getCookiesFromFile() {
        Set<Cookie> cookies = new HashSet<>();
        try {
            File file = new File("Cookies.data");
            FileReader fileReader = new FileReader(file);
            BufferedReader Buffreader = new BufferedReader(fileReader);
            String strline = Buffreader.readLine();

            while (strline != null) {
                StringTokenizer token = new StringTokenizer(strline, ";");
                Cookie extracted = extracted(token);
                cookies.add(extracted);
                strline = Buffreader.readLine();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return cookies;
    }

    private Cookie extracted(StringTokenizer token) {
        while (token.hasMoreTokens()) {
            String name = token.nextToken();
            String value = token.nextToken();

            return new Cookie.Builder(name, value)
                    .path("/")
                    .domain("stage.lentatest.com")
                    .isSecure(true)
                    .expiresOn(null)
                    .build();
            // This will add the stored cookie to your current session
        }
        return null;
    }

    public void handleCookies(WebDriver driver) {
        Set<Cookie> cookies = getCookiesFromFile();

        cookies.forEach(value -> driver.manage().addCookie(value));
    }

    private List<String> processCookies(List<?> cookie) {
        List<String> strings;
        if (cookie.get(0) instanceof Cookie) {
            strings = formatCookieFromSelenium((List<Cookie>) cookie);

        } else if (cookie.get(0) instanceof io.restassured.http.Cookie) {
            strings = formatCookieFromRequest((List<io.restassured.http.Cookie>) cookie);
        }
        else throw new IllegalArgumentException("Input type is not valid");

        return strings;
    }

    private List<String> formatCookieFromSelenium(List<Cookie> cookies) {
        return cookies.stream().map(ck -> ck.getName() + ";" + ck.getValue()).collect(Collectors.toList());
    }


    private List<String> formatCookieFromRequest(List<io.restassured.http.Cookie> detailedCookies) {
        return detailedCookies.stream().map(ck -> ck.getName() + ";" + ck.getValue()).collect(Collectors.toList());
    }

    public static CookieHandler getInstance() {
        return new CookieHandler();
    }

}
