package fi.company;

import javax.enterprise.context.*;

@ApplicationScoped
public class HtmlHelperImpl implements HtmlHelper {
    // Returns valid HTML5 page with given title and content. Content
    // is displayed inside of <body>...</body> and title inside of
    // <title>...</title>
    @Override
    public String generateHtml(String title, String content) {
        return """
                <!doctype html>
                <html lang="en">
                    <head>
                        <meta charset="utf-8">
                        <title>%s</title>
                    </head>
                    <body>
                        %s
                    </body>
                </html>""".formatted(title, content);
    }
}