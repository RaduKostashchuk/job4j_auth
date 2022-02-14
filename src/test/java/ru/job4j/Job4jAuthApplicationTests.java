package ru.job4j;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.domain.Person;
import ru.job4j.repository.PersonRepository;

@SpringBootTest(classes = Job4jAuthApplication.class)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class Job4jAuthApplicationTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    PersonRepository repository;

    @Test
    void testFindAll() throws Exception {
        String result = "[{\"id\":1,\"login\":\"user\",\"password\":\"123\"},"
                + "{\"id\":2,\"login\":\"admin\",\"password\":\"123\"},"
                + "{\"id\":3,\"login\":\"operator\",\"password\":\"123\"}]";
        mockMvc.perform(get("/person/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(result));
    }

    @Test
    void testFindById() throws Exception {
        String jsonString = "{\"id\":2,\"login\":\"admin\",\"password\":\"123\"}";
        mockMvc.perform(get("/person/2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(jsonString));
    }

    @Test
    void testDelete() throws Exception {
        String result = "[{\"id\":1,\"login\":\"user\",\"password\":\"123\"},"
                + "{\"id\":3,\"login\":\"operator\",\"password\":\"123\"}]";
        mockMvc.perform(delete("/person/2"))
                .andDo(print())
                .andExpect(status().isOk());
        mockMvc.perform(get("/person/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(result));
    }

    @Test
    void testCreatePost() throws Exception {
        Gson gson = new Gson();
        Person person1 = Person.of(1, "user", "123");
        Person person2 = Person.of(2, "admin", "123");
        Person person3 = Person.of(3, "operator", "123");
        Person person4 = Person.of(4, "new", "123");
        String result = gson.toJson(new Person[] {person1, person2, person3, person4});
        String toAdd = gson.toJson(person4);

        mockMvc.perform(post("/person/")
                        .contentType("application/json")
                        .content(toAdd))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/json"));
        mockMvc.perform(get("/person/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(result));
    }

    @Test
    void testUpdatePerson() throws Exception {
        Gson gson = new Gson();
        Person person1 = Person.of(1, "user", "123");
        Person person2 = Person.of(2, "admin_updated", "123");
        Person person3 = Person.of(3, "operator", "123");
        String result = gson.toJson(new Person[] {person1, person2, person3});
        Person adminUpdated = Person.of(2, "admin_updated", "123");
        String toUpdate = gson.toJson(adminUpdated);

        mockMvc.perform(put("/person/")
                        .contentType("application/json")
                        .content(toUpdate))
                .andDo(print())
                .andExpect(status().isOk());
        mockMvc.perform(get("/person/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(result));
    }

}
