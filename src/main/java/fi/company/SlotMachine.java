package fi.company;

import java.io.*;

import javax.servlet.http.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.inject.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.*;
import java.util.*;

@WebServlet("/slotmachine")
public class SlotMachine extends HttpServlet {


    @Inject
    HtmlHelper helper;

    @Inject
    Player player;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet (HttpServletRequest req,
                       HttpServletResponse res) throws ServletException, IOException {


        Optional.ofNullable(req.getParameter("name")).ifPresent((value) -> player.setName(value));


        var numbers = List.of(getRandomNumber(), getRandomNumber(), getRandomNumber());
        var victory = numbers.stream().allMatch(value -> value == numbers.get(0));
        var ui = numbers.stream().map(number -> "<img src=\"" + number + ".png\"/>").collect(Collectors.joining());

        if(player.getScore() > 0) {
            player.setScore(player.getScore() - 1);
        } else {
            player.setScore(20);
        }

        if(player.getName().isPresent()) {
            ui = ui + "<p>name = " + player.getName().get() + "</p>";
        }

        if(victory) {
            ui = ui + "<p>You won!</p>";
            player.setScore(player.getScore() + 5);
        }

        ui = "<p>Money = " + player.getScore() + "</p>" + ui;

        try (PrintWriter out = res.getWriter()) {
            out.println(helper.generateHtml("SlotMachine", ui));
        }
    }


    public int getRandomNumber() {
        return (int) (Math.random() * 3) + 1;
    }

}