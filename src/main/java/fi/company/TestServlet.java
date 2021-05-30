package fi.company;

import java.io.*;

import javax.servlet.http.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.inject.*;
import java.util.*;
import java.sql.*;
import javax.persistence.*;
import javax.ws.rs.*;
import javax.inject.*;
import javax.transaction.*;

@WebServlet("/test")
public class TestServlet extends HttpServlet implements ServletContextListener {

    public void doGet (HttpServletRequest req,
                        HttpServletResponse res) throws ServletException, IOException {

        saveCustomer(new Customer("Jeppe"));

        try (PrintWriter out = res.getWriter()) {
            out.println("done");
        }
    }

    public String jdbcConnection() {
        var result = "";
        var URL = "jdbc:derby:this-is-my-db;create=true";
        var CLASS_NAME = "org.apache.derby.jdbc.EmbeddedDriver";

        var CREATE_TABLE = """
                          CREATE TABLE customers (id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY
                                                  (START WITH 1, INCREMENT BY 1),
                                                  name VARCHAR(1024),
                                                  CONSTRAINT primary_key PRIMARY KEY (id))
                          """;

        var INSERT_SQL = "INSERT INTO customers (name) VALUES ('jack')";
        var SELECT_ALL = "SELECT * FROM customers";

        try {
            Class.forName(CLASS_NAME);
            try (Connection conn = DriverManager.getConnection(URL);
                Statement statement = conn.createStatement()) {

                try {
                    statement.executeUpdate(CREATE_TABLE);
                } catch(SQLException e) {
                    System.out.println("Table already existed");
                }
                statement.executeUpdate(INSERT_SQL);
                ResultSet rs = statement.executeQuery(SELECT_ALL);
                while (rs.next()) {
                    result += (rs.getString("id") + " - " + rs.getString("name"));
                }

            }
        } catch(SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return result;
    }


    public void saveCustomer(Customer c) {
        System.out.println("********");
        // Open persistence.xml and it's unit called "donaldduck"
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("myderbyunit");
        // Create Entity Manager
        EntityManager em = emf.createEntityManager();
        try {
            // Start transaction
            em.getTransaction().begin();
            // Insert into
            em.persist(new Customer("jack"));
            // Select *
            Query query = em.createQuery("Select obj from Customer as obj");
            List<Customer> customers = query.getResultList();
            customers.forEach(System.out::println);
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