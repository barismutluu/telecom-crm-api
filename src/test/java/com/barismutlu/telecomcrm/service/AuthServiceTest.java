package com.barismutlu.telecomcrm.service;

import com.barismutlu.telecomcrm.model.User;
import com.barismutlu.telecomcrm.repository.UserRepository;
import com.barismutlu.telecomcrm.security.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthService authService;

    @Test
    void register_shouldSaveUserAndReturnToken_whenUsernameIsUnique() {
        when(userRepository.findByUsername("baris")).thenReturn(Optional.empty());
        when(jwtService.generateToken("baris")).thenReturn("jwt-token");

        String token = authService.register("baris", "1234");

        assertThat(token).isEqualTo("jwt-token");

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        assertThat(userCaptor.getValue().getUsername()).isEqualTo("baris");
        assertThat(userCaptor.getValue().getPassword()).isEqualTo("1234");
        assertThat(userCaptor.getValue().getRole()).isEqualTo("USER");
    }

    @Test
    void register_shouldThrowException_whenUsernameAlreadyExists() {
        when(userRepository.findByUsername("baris")).thenReturn(Optional.of(new User()));

        assertThatThrownBy(() -> authService.register("baris", "1234"))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Username already exists");

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void login_shouldReturnToken_whenCredentialsAreCorrect() {
        User user = new User();
        user.setUsername("baris");
        user.setPassword("1234");

        when(userRepository.findByUsername("baris")).thenReturn(Optional.of(user));
        when(jwtService.generateToken("baris")).thenReturn("jwt-token");

        String token = authService.login("baris", "1234");

        assertThat(token).isEqualTo("jwt-token");
    }

    @Test
    void login_shouldThrowException_whenUserDoesNotExist() {
        when(userRepository.findByUsername("baris")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> authService.login("baris", "1234"))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("User not found");
    }

    @Test
    void login_shouldThrowException_whenPasswordIsWrong() {
        User user = new User();
        user.setUsername("baris");
        user.setPassword("1234");

        when(userRepository.findByUsername("baris")).thenReturn(Optional.of(user));

        assertThatThrownBy(() -> authService.login("baris", "wrong-password"))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Wrong password");
    }
}
