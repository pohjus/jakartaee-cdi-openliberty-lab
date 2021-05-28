package fi.company;

import java.io.*;

import javax.servlet.http.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.inject.*;
import java.util.*;
import java.sql.*;
import javax.persistence.*;

@WebListener
@WebServlet("/test")
public class TestServlet extends HttpServlet implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent contextEvent) {
        System.out.println("sddssd ready!");
    }

    @Override
    public void contextDestroyed(ServletContextEvent contextEvent) {
        /* Do Shutdown stuff. */
    }

    public void doGet (HttpServletRequest req,
                        HttpServletResponse res) throws ServletException, IOException {
        test();
        try (PrintWriter out = res.getWriter()) {
            out.println(jdbcConnection());
        }
    }

    public void test() {
        System.out.println("JPA STUFF");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("mypersistance");

        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Customer p = new Customer();
            p.setName("jaska");
            em.persist(p);

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

    public String jdbcConnection() {
        var result = "";
        var URL = "jdbc:sqlite:test.db";
        var CLASS_NAME = "org.sqlite.JDBC";

        var CREATE_TABLE = "CREATE TABLE IF NOT EXISTS customers (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL)";
        var INSERT_SQL = "INSERT INTO customers (name) VALUES ('jack')";
        var SELECT_ALL = "SELECT * FROM customers";

        try {
            Class.forName(CLASS_NAME);
            try (Connection conn = DriverManager.getConnection(URL);
                Statement statement = conn.createStatement()) {
                statement.executeUpdate(CREATE_TABLE);
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
}