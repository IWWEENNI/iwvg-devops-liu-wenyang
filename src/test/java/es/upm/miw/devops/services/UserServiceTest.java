package es.upm.miw.devops.services;

import es.upm.miw.devops.data.UserRepository;
import es.upm.miw.devops.data.model.Role;
import es.upm.miw.devops.data.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User("1", "John", "Doe", "john@example.com",
                "12345678A", "Main St 1", "Madrid", "Madrid", "28001", true, Role.ADMIN);
    }

    // Feature 1: GET /user/{id}

    @Test
    void findById_whenExists_returnsUser() {
        when(userRepository.findById("1")).thenReturn(Optional.of(user));

        User result = userService.findById("1");

        assertThat(result.getId()).isEqualTo("1");
        assertThat(result.getFirstName()).isEqualTo("John");
    }

    @Test
    void findById_whenNotFound_throws404() {
        when(userRepository.findById("99")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.findById("99"))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("User not found");
    }

    // Feature 3: DELETE /user/{id}

    @Test
    void deleteById_whenExists_deletesUser() {
        when(userRepository.existsById("1")).thenReturn(true);

        userService.deleteById("1");

        verify(userRepository, times(1)).deleteById("1");
    }

    @Test
    void deleteById_whenNotFound_throws404() {
        when(userRepository.existsById("99")).thenReturn(false);

        assertThatThrownBy(() -> userService.deleteById("99"))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("User not found");

        verify(userRepository, never()).deleteById(any());
    }

    // Feature 4: PUT /user/{id}/active

    @Test
    void activate_whenExists_setsActiveTrue() {
        user.setActive(false);
        when(userRepository.findById("1")).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        User result = userService.activate("1");

        assertThat(result.isActive()).isTrue();
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void activate_whenNotFound_throws404() {
        when(userRepository.findById("99")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.activate("99"))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("User not found");

        verify(userRepository, never()).save(any());
    }
}
