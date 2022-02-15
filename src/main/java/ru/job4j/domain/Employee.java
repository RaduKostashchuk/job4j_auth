package ru.job4j.domain;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Employee {
    private int id;
    private String name;
    private String surname;
    private String inn;
    private Timestamp hired;
    private List<Person> accounts = new ArrayList<>();

    public static Employee of(int id, String name, String surname, String inn) {
        Employee employee = new Employee();
        employee.id = id;
        employee.name = name;
        employee.surname = surname;
        employee.inn = inn;
        employee.hired = new Timestamp(System.currentTimeMillis());
        return employee;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    public Timestamp getHired() {
        return hired;
    }

    public List<Person> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Person> accounts) {
        this.accounts = accounts;
    }

    public void addAccount(Person person) {
        accounts.add(person);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Employee employee = (Employee) o;
        return id == employee.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
