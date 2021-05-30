package fi.company;

import java.util.*;
import javax.enterprise.context.*;
import javax.annotation.*;
import javax.enterprise.inject.*;
import javax.inject.*;
import java.io.*;
import java.sql.*;

@ApplicationScoped
@Named("derby")
public class DerbyRepository implements CrudRepository {

    final static String URL = "jdbc:derby:this-is-my-db;create=true";
    final static String CLASS_NAME = "org.apache.derby.jdbc.EmbeddedDriver";

    @PostConstruct
    public void init() {
        var CREATE_TABLE = """
                          CREATE TABLE customers (id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY
                                                  (START WITH 1, INCREMENT BY 1),
                                                  name VARCHAR(1024),
                                                  CONSTRAINT primary_key PRIMARY KEY (id))
                          """;
        try {
            Class.forName(CLASS_NAME);
            try (Connection conn = DriverManager.getConnection(URL);
                Statement statement = conn.createStatement()) {
                statement.executeUpdate(CREATE_TABLE);
            }
        } catch(SQLException e) {
            System.out.println("error code: " + e.getErrorCode());
            e.printStackTrace();
        } catch(ClassNotFoundException e) {
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