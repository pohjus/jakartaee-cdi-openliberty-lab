package fi.company;

import java.util.*;
import javax.enterprise.context.*;
import javax.annotation.*;
import javax.enterprise.inject.*;
import javax.inject.*;
import java.io.*;

@ApplicationScoped
@Named("ArrayList")
// @Default
public class ArrayListRepository implements CrudRepository, Serializable {

    private List<String> db;

    @PostConstruct
    public void init() {
        db = Collections.synchronizedList(new ArrayList<String>());
        db.add("array");
        db.add("list");
    }

    public List<String> getAll() {
        return db;
    }
    public void save(String name) {
        // System.out.println(Thread.currentThread().getName());
        // for(int i=0; i<10; i++) {
        //     System.out.println(i);
        //     try {
        //         Thread.sleep(1000);
        //     } catch(InterruptedException e) {
        //         e.printStackTrace();
        //     }
        // }
        db.add(name);
    }

}