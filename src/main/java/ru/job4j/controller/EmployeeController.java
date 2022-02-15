package ru.job4j.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.job4j.domain.Employee;
import ru.job4j.domain.Person;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    RestTemplate restTemplate;

    private static final String API = "http://localhost:8080/person/";

    private static final String API_ID = "http://localhost:8080/person/{id}";

    @GetMapping("/")
    public List<Employee> findAll() {
        List<Employee> result = List.of(
                Employee.of(1, "Ivan", "Petrov", "121212"),
                Employee.of(2, "Egor", "Ivanov", "323232"),
                Employee.of(3, "Oleg", "Orehov", "909090")
        );
        List<Person> persons = restTemplate.exchange(
                API,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Person>>() { })
                .getBody();
        for (Employee employee: result) {
            for (Person person : persons) {
                employee.addAccount(person);
            }
        }
        return result;
    }

    @PostMapping("/")
    public ResponseEntity<Person> create(@RequestBody Person person) {
        Person result = restTemplate.postForObject(API, person, Person.class);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping("/")
    public ResponseEntity<Void> update(@RequestBody Person person) {
        restTemplate.put(API, person);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        restTemplate.delete(API_ID, id);
        return ResponseEntity.ok().build();
    }
}
