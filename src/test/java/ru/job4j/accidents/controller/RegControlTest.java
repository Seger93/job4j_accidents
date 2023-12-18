package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.accidents.Main;
import ru.job4j.accidents.model.Authority;
import ru.job4j.accidents.model.User;
import ru.job4j.accidents.service.SimpleUserService;

import java.util.Optional;

@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class RegControlTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SimpleUserService userService;

    @Test
    @Transactional
    @WithMockUser
    public void whenGetDefaultPage() throws Exception {
        this.mockMvc.perform(get("/reg"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("/reg"));
    }

    @Test
    @Transactional
    @WithMockUser
    public void whenPostSaveNewUser() throws Exception {
        String password = "test";
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashedPassword = encoder.encode(password);
        User user = new User(0, hashedPassword, "test", new Authority(), true);
        when(userService.save(any(User.class))).thenReturn(Optional.of(user));
        mockMvc.perform(post("/reg")
                        .param("username", user.getUsername())
                        .param("password", password))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userService).save(userCaptor.capture());
        assertThat(userCaptor.getValue().getUsername()).isEqualTo("test");
        assertThat(encoder.matches(password, userCaptor.getValue().getPassword())).isTrue();
    }
}