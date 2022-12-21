package web.ops.util.datautils;

import org.apache.log4j.Logger;
import web.ops.service.SQLService;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class SQLUtil {
    private static final Logger log = Logger.getLogger(SQLUtil.class.getName());
    private static final SQLService sqlService = new SQLService();
    private static final String jdbcURL = System.getProperty("jdbc.url");
    private static final String jdbcUsername = System.getProperty("jdbc.username");
    private static final String jdbcPassword = System.getProperty("jdbc.password");

    public static Map<String, String> queryWithResult(String query) throws SQLException {
        log.trace("Running querry: " + query);
        Map<String, String> result = new HashMap<>();
        sqlService.establishSQLDBConnection(jdbcURL, jdbcUsername, jdbcPassword);
        ResultSet resultSet = sqlService.executeQuerryAndGetResult(query);
        ResultSetMetaData rsmd = resultSet.getMetaData();
        int columnCount = rsmd.getColumnCount();

        if (resultSet.next()){
        for (int i = 1; i <= columnCount; i++) {
                result.put(rsmd.getColumnName(i), resultSet.getString(i));
            }
        }

        log.trace(result);

        sqlService.closeConnection();
        return result;
    }

    public static void queryWithoutResult(String query) {
        log.trace("Running query: " + query);
        sqlService.establishSQLDBConnection(jdbcURL, jdbcUsername, jdbcPassword);
        sqlService.executeQuerry(query);
        sqlService.closeConnection();
    }
}
