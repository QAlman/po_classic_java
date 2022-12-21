package web.ops.service;

import org.apache.log4j.Logger;

import java.sql.*;

public class SQLService {
    private Statement statement;
    private Connection connection;
    private final Logger log = Logger.getLogger(this.getClass().getName());

    public void establishSQLDBConnection(String jdbcUrl, String userName, String password) {
        try {
            connection = DriverManager.getConnection(jdbcUrl, userName, password);

            statement = connection.createStatement();
            log.trace("Connection to database was succesfull");

        } catch (SQLException throwables) {
            log.trace("Connection to DB failed");
            throwables.printStackTrace();
        }
    }

    public ResultSet executeQuerryAndGetResult(String querry) throws SQLException {
        ResultSet resultSet = statement.executeQuery(querry);
        log.trace(querry + " execution succesfull");

        return resultSet;
    }

    public void executeQuerry(String querry) {
        try {
            statement.executeQuery(querry);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            connection.close();
            log.trace("DB Connection closed");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
