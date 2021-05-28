package fi.company;

import java.util.List;

public interface CrudRepository {
    public List<String> getAll();
    public void save(String name);
}