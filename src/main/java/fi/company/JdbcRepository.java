package fi.company;

import java.util.*;
import javax.enterprise.context.*;
import javax.annotation.*;
import javax.enterprise.inject.*;
import javax.inject.*;
import java.io.*;
import java.sql.*;

@ApplicationScoped
@Named("jdbc")
public class JdbcRepository implements CrudRepository {

    final static String URL = "jdbc:sqlite:sample.db";
    final static String CLASS_NAME = "org.sqlite.JDBC";

    @PostConstruct
    public void init() {
        final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS customers (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL)";
        try {
            Class.forName(CLASS_NAME);
            try (Connection conn = DriverManager.getConnection(URL);
                Statement statement = conn.createStatement()) {
                statement.executeUpdate(CREATE_TABLE);
            }
        } catch(SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public List<String> getAll() {
        List<String> db = Collections.synchronizedList(new ArrayList<String>());

        try {
            Class.forName(CLASS_NAME);

            try (Connection conn = DriverManager.getConnection(URL);
                Statement statement = conn.createStatement()) {
                ResultSet rs = statement.executeQuery("SELECT * FROM customers");
                while (rs.next()) {
                    db.add(rs.getString("id") + " - " + rs.getString("name"));
                }
            }
        } catch(SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return db;
    }
    public void save(String name) {
         final String INSERT_SQL = "INSERT INTO customers (name) VALUES ('%s')".formatted(name);
         System.out.println(INSERT_SQL);
         try {
            Class.forName(CLASS_NAME);
            try (Connection conn = DriverManager.getConnection(URL);
                Statement statement = conn.createStatement()) {
                statement.executeUpdate(INSERT_SQL);
            }
        } catch(SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}