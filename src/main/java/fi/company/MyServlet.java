package fi.company;

import java.io.*;

import javax.servlet.http.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.inject.*;

@WebServlet("/helloworld")
public class MyServlet extends HttpServlet {


  @Inject
  HtmlHelper helper;

  public void doGet (HttpServletRequest req,
                     HttpServletResponse res)
    throws ServletException, IOException {
      try (PrintWriter out = res.getWriter()) {
        out.println(helper.generateHtml("title", "<p>Content</p>"));
      }
  }
}