package fi.company;

import javax.enterprise.context.*;

public class HtmlHelperImpl implements HtmlHelper {


    private int indentation = 2;

    public HtmlHelperImpl(int indentation) {
        this.indentation = indentation;
    }

    // Returns valid HTML5 page with given title and content. Content
    // is displayed inside of <body>...</body> and title inside of
    // <title>...</title>
    @Override
    public String generateHtml(String title, String content) {
        // Java 16
        /*return """
                <!doctype html>
                <html lang="en">
                 <head>
                 %1$s<meta charset="utf-8">
                 %1$s%1$s<title>%2$s</title>
                 %1$s</head>
                 %1$s<body>
                 %1$s%1$s%3$s
                 %1$s</body>
                 </html>""".formatted(" ".repeat(this.indentation), title, content);
        */
        // Java 11

        String template = "<!doctype html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "%1$s<meta charset=\"utf-8\">\n" +
                "%1$s%1$s<title>HTML5 Page</title>\n" +
                "%1$s</head>\n" +
                "%1$s<body>\n" +
                "%1$s%1$s%3$s" +
                "%1$s</body>\n" +
                "</html>";

        return template.format(template, " ".repeat(this.indentation), title, content);

    }
}