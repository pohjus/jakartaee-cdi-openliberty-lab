package fi.company;

import javax.enterprise.context.*;
import javax.enterprise.inject.*;

@ApplicationScoped
public class Configuration {
    @Produces
    @RequestScoped
    public HtmlHelper getInstance() {
        return new HtmlHelperImpl((int) (Math.random() * 10));
    }
}