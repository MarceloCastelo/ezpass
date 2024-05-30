package org.example.ezpass.securityTest;

import org.example.ezpass.entity.User;
import org.example.ezpass.entity.UserRole;
import org.example.ezpass.repository.UserRepository;
import org.example.ezpass.service.AuthorizationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class AuthorizationServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthorizationService authorizationService;

    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User("testuser", "password", UserRole.USER); // Assuming UserRole.USER is a valid role
    }

    @Test
    public void testLoadUserByUsernameSuccess() {
        when(userRepository.findByLogin("testuser")).thenReturn(user);

        UserDetails userDetails = authorizationService.loadUserByUsername("testuser");

        assertEquals(user.getLogin(), userDetails.getUsername());
    }

    @Test
    public void testLoadUserByUsernameNotFound() {
        when(userRepository.findByLogin("nonexistentuser")).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> {
            authorizationService.loadUserByUsername("nonexistentuser");
        });
    }
}
