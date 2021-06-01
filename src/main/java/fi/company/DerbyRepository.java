package fi.company;

import java.util.*;
import javax.enterprise.context.*;
import javax.annotation.*;
import javax.enterprise.inject.*;
import javax.inject.*;
import java.io.*;
import java.sql.*;
import javax.persistence.*;
import javax.sql.DataSource;

@ApplicationScoped
@Named("derby")
public class DerbyRepository implements CrudRepository {

    // final static String URL = "jdbc:derby:this-is-my-db;create=true";
    // final static String CLASS_NAME = "org.apache.derby.jdbc.EmbeddedDriver";

    @Resource(lookup = "jdbc/myDataSource")
    DataSource myDB;

    @PostConstruct
    public void init() {
         var CREATE_TABLE = "CREATE TABLE customers (id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY" +
                             "(START WITH 1, INCREMENT BY 1)," +
                             "name VARCHAR(1024)," +
                             "CONSTRAINT primary_key PRIMARY KEY (id))";
         try {
             // Class.forName(CLASS_NAME);
             try (Connection conn = myDB.getConnection();
                 Statement statement = conn.createStatement()) {
                 try {
                     statement.executeUpdate(CREATE_TABLE);
                 } catch(SQLException e) {
                     System.out.println("table already exists!");
                 }
             }
         } catch(SQLException e) {
             System.out.println("error code: " + e.getErrorCode());
             e.printStackTrace();
         }
    }
    public List<String> getAll() {
        List<String> db = Collections.synchronizedList(new ArrayList<String>());

        try {
            // Class.forName(CLASS_NAME);

            try (Connection conn = myDB.getConnection();
                Statement statement = conn.createStatement()) {
                ResultSet rs = statement.executeQuery("SELECT * FROM customers");
                while (rs.next()) {
                    db.add(rs.getString("id") + " - " + rs.getString("name"));
                }
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return db;
    }
    public void save(String name) {
         final String INSERT_SQL = "INSERT INTO customers (name) VALUES ('%s')".formatted(name);
         try {
            // Class.forName(CLASS_NAME);
            try (Connection conn = myDB.getConnection();
                Statement statement = conn.createStatement()) {
                statement.executeUpdate(INSERT_SQL);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Customer> getAllCustomers() {
        List<Customer> customers = null;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa");
        // Create Entity Manager
        EntityManager em = emf.createEntityManager();
        try {
            // Start transaction
            em.getTransaction().begin();

            Query query = em.createQuery("Select obj from Customer as obj");
            customers = query.getResultList();
            // Select *
            em.getTransaction().commit();
            em.close();
        } catch(Exception e) {
            try {
                em.getTransaction().rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return customers;
    }

    @Override
    public void save(Customer c) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa");
        // Create Entity Manager
        EntityManager em = emf.createEntityManager();
        try {
            // Start transaction
            em.getTransaction().begin();
            // Insert into
            em.persist(c);
            // Select *
            em.getTransaction().commit();
            em.close();
        } catch(Exception e) {
            try {
                em.getTransaction().rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }



}