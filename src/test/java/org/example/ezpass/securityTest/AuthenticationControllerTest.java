package org.example.ezpass.securityTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.ezpass.dto.AuthenticationDTO;
import org.example.ezpass.dto.LoginResponseDTO;
import org.example.ezpass.entity.RegisterDTO;
import org.example.ezpass.entity.User;
import org.example.ezpass.entity.UserRole;
import org.example.ezpass.infra.security.TokenService;
import org.example.ezpass.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private TokenService tokenService;

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User("testuser", "password", UserRole.USER);
    }

    @Test
    public void testLogin() throws Exception {
        AuthenticationDTO authenticationDTO = new AuthenticationDTO("testuser", "password");

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(authenticationDTO.login(), authenticationDTO.password());
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

        Mockito.when(authenticationManager.authenticate(authToken)).thenReturn(authentication);
        Mockito.when(tokenService.generateToken(user)).thenReturn("testtoken");

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authenticationDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token", is("testtoken")));
    }

    @Test
    public void testRegister() throws Exception {
        RegisterDTO registerDTO = new RegisterDTO("newuser", "newpassword", UserRole.USER);

        Mockito.when(userRepository.findByLogin(registerDTO.login())).thenReturn(null);
        Mockito.when(userRepository.save(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerDTO)))
                .andExpect(status().isOk());
    }

    @Test
    public void testRegisterExistingUser() throws Exception {
        RegisterDTO registerDTO = new RegisterDTO("existinguser", "password", UserRole.USER);

        Mockito.when(userRepository.findByLogin(registerDTO.login())).thenReturn(user);

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCheckEmail() throws Exception {
        Map<String, String> request = new HashMap<>();
        request.put("email", "testemail@example.com");

        Mockito.when(userRepository.findByLogin("testemail@example.com")).thenReturn(null);

        mockMvc.perform(post("/auth/check-email")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.available", is(true)));
    }

    @Test
    public void testCheckEmailUnavailable() throws Exception {
        Map<String, String> request = new HashMap<>();
        request.put("email", "testemail@example.com");

        Mockito.when(userRepository.findByLogin("testemail@example.com")).thenReturn(user);

        mockMvc.perform(post("/auth/check-email")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.available", is(false)));
    }
}
