package fi.company;

import java.util.*;
import javax.enterprise.context.*;
import javax.annotation.*;
import javax.enterprise.inject.*;
import javax.inject.*;

@ApplicationScoped
@Named("LinkedList")
//@Alternative
public class LinkedListRepository implements CrudRepository {

    private List<String> db;

    @PostConstruct
    public void init() {
        db = Collections.synchronizedList(new LinkedList<String>());
        db.add("linked");
        db.add("list");
    }

    public List<String> getAll() {
        return db;
    }
    public void save(String name) {
        db.add(name);
    }

}