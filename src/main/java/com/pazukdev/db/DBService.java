package com.pazukdev.db;

import com.mysql.fabric.jdbc.FabricMySQLDriver;
//import com.mysql.jdbc.Connection;

import com.mysql.jdbc.Constants;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import liquibase.resource.FileSystemResourceAccessor;
import org.hibernate.SessionFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBService {

    static Connection connection;
    //static final String URL="jdbc:mysql://" + "localhost:3306/mydbtest?useSSL=true&amp;autoReconnect=true&amp;serverTimezone=UTC";
    static final String URL="jdbc:postgresql://ec2-46-137-109-220.eu-west-1.compute.amazonaws.com:5432/dfqrdiu6a3quhb?sslmode=require";
    static final String USERNAME="druuxeuwmnyzxn";
    static final String PASSWORD="83e5afb54a8e8c7387aaf8c3b9d99ad6643cbb83191be995d2a3357f4ca8b4ba";

    public static void main(String[] args) {
        try {
            DriverManager.registerDriver(new FabricMySQLDriver());
            connection=DriverManager.getConnection(URL, USERNAME, PASSWORD);

            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new
                    JdbcConnection(connection));
            Liquibase liquibase = new liquibase.Liquibase("db/db.changelog.xml",
                    new ClassLoaderResourceAccessor(), database);

            liquibase.update(new Contexts(), new LabelExpression());
            database.close();

        } catch (SQLException | LiquibaseException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e.getClass() + ": " + e.getMessage());
                }
            }
        }
    }

}
