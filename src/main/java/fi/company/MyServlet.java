package fi.company;

import java.io.*;

import javax.servlet.http.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.inject.*;
import java.util.stream.*;
import java.util.*;

@WebServlet("/database")
public class MyServlet extends HttpServlet {

    @Inject
    @Named("derbyjta")
    CrudRepository repository;

    @Inject
    HtmlHelper helper;

    public void doGet (HttpServletRequest req,
                        HttpServletResponse res) throws ServletException, IOException {

        testObjects(req, res);
        // testStrings(req, res);

    }

    public void testStrings(HttpServletRequest req,
                            HttpServletResponse res) throws ServletException, IOException {
        Optional.ofNullable(req.getParameter("name")).ifPresent((value) -> repository.save(value));

        String list = repository.getAll()
                .stream()
                .map(name -> "<li>" + name + "</li>")
                .collect(Collectors.joining(""));

        try (PrintWriter out = res.getWriter()) {
            out.println(helper.generateHtml("Database", "<ul>" + list + "</ul>"));
        }

    }

    public void testObjects(HttpServletRequest req,
                            HttpServletResponse res) throws ServletException, IOException  {
        Optional.ofNullable(req.getParameter("name")).ifPresent((value) -> repository.save(new Customer(value)));

        String list = repository.getAllCustomers()
                .stream()
                .map(customer -> "<li>" + customer.getName() + "</li>")
                .collect(Collectors.joining(""));

        try (PrintWriter out = res.getWriter()) {
            out.println(helper.generateHtml("Database", "<ul>" + list + "</ul>"));
        }
    }
}