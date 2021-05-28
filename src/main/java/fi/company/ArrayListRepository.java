package fi.company;

import java.util.*;
import javax.enterprise.context.*;
import javax.annotation.*;

@ApplicationScoped
public class ArrayListRepository implements CrudRepository {

    List<String> db;

    @PostConstruct
    public void init() {
        db = Collections.synchronizedList(new ArrayList<String>());
        db.add("Jack");
        db.add("Tina");
    }

    public List<String> getAll() {
        return db;
    }
    public void save(String name) {
        System.out.println(Thread.currentThread().getName());
        for(int i=0; i<10; i++) {
            System.out.println(i);
            try {
                Thread.sleep(1000);
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
        db.add(name);
    }

}