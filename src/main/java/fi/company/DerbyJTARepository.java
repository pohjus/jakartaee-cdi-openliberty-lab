package fi.company;

import java.util.*;
import javax.enterprise.context.*;
import javax.annotation.*;
import javax.enterprise.inject.*;
import javax.inject.*;
import java.io.*;
import java.sql.*;
import javax.persistence.*;
import java.util.stream.*;
import javax.transaction.*;

@ApplicationScoped
@Named("derbyjta")
public class DerbyJTARepository implements CrudRepository {

    @PersistenceContext(unitName = "jta-jpa")
    EntityManager em;

    public List<String> getAll() {
        Query query = em.createQuery("Select obj from Customer as obj");
        List<Customer> customers = query.getResultList();
        List<String> cust = customers.stream().map(customer -> customer.getName()).collect(Collectors.toList());
        return cust;
    }
    public void save(String name) {
        em.persist(new Customer(name));
    }

    @Override
    public List<Customer> getAllCustomers() {
        Query query = em.createQuery("Select obj from Customer as obj");
        List<Customer> customers = query.getResultList();
        return customers;
    }

    @Override
    @Transactional
    public void save(Customer c) {
        em.persist(new Customer("jack"));
    }
}