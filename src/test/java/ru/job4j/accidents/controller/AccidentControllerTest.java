package ru.job4j.accidents.controller;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.accidents.Main;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.service.SimpleAccidentsService;

import java.util.Optional;
import java.util.Set;

@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AccidentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    SimpleAccidentsService accidentsService;

    @Test
    @Transactional
    @WithMockUser
    public void whenGetAllAccident() throws Exception {
        this.mockMvc.perform(get("/accident"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("accident/list"));
    }

    @Test
    @WithMockUser
    public void whenGetCreate() throws Exception {
        this.mockMvc.perform(get("/accident/createAccident"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("accident/createAccident"))
                .andExpect(model().attributeExists("types", "rules"));
    }

    @Test
    @WithMockUser
    @Transactional
    public void whenGetUpdate() throws Exception {
        var accident = new Accident(1, "name1", "description1", "address1",
                new AccidentType(), Set.of(new Rule()));

        when(accidentsService.findById(accident.getId())).thenReturn(Optional.of(accident));

        this.mockMvc.perform(get("/accident/updateAccident").param("id", String.valueOf(accident.getId())))
                .andExpect(status().isOk())
                .andExpect(view().name("accident/updateAccident"))
                .andExpect(model().attributeExists("accident", "types", "rules"));
    }

    @Test
    @WithMockUser
    @Transactional
    public void whenGetUpdateReturn404() throws Exception {
        this.mockMvc.perform(get("/accident/updateAccident").param("id", "115"))
                .andExpect(status().isOk())
                .andExpect(view().name("errors/404"));
    }
}