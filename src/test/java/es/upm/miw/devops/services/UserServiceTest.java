package es.upm.miw.devops.services;

import es.upm.miw.devops.data.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

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
}
