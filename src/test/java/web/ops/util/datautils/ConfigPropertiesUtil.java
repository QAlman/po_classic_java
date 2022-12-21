package web.ops.util.datautils;

import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.Map;

public class ConfigPropertiesUtil {
    private static final Logger log = Logger.getLogger(ConfigPropertiesUtil.class.getName());

    public static String getProperty(String name) {
        String property = null;
        try {
            property=getPropertyFromStorage(name);
            log.trace("property: "+name+" with value: "+ property+" found succesfull");
        } catch (NullPointerException | SQLException e) {
            log.trace("DB connection error");
        }

        return property;
    }

    public static String getPropertyFromStorage(String property) throws SQLException {
        Map<String, String> select = SQLUtil.queryWithResult("\n" +
                "USE [stagesite-episerver]\n" +
                "select paramValue from  [dbo].[autotestconfig]\n" +
                "where paramName like '%"+property+"%'");

            return select.get("paramValue");
    }

}
