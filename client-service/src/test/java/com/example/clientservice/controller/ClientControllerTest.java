package com.example.clientservice.controller;

import com.example.clientservice.model.Person;
import com.example.clientservice.sevicelayer.ClientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ClientController.class)
public class ClientControllerTest {
    @MockBean
    ClientService service;
    private ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
    }
    @Test
    public void getPeopleWithName() throws Exception {
        Person expected = new Person();
        expected.setAge(25);
        expected.setId(15);
        expected.setName("Mike");
        List<Person> personList = new ArrayList<>();
        personList.add(expected);
        String outputJson = mapper.writeValueAsString(personList);
        when(service.findPerson(expected.getName())).thenReturn(personList);
        mockMvc.perform(get("/clientfe/person/Mike"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }
    @Test
    public void addPerson() throws Exception{
        Person expected = new Person();
        expected.setAge(25);
        expected.setName("Mike");


  mockMvc.perform(MockMvcRequestBuilders.post("clientfe/person/Mike").content(mapper.writeValueAsString(expected)).contentType(MediaType.APPLICATION_JSON)
          .accept(MediaType.APPLICATION_JSON))
          .andExpect(status().isCreated())
          .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }

}