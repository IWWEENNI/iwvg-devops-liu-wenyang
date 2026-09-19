package es.upm.miw.devops.data;

import es.upm.miw.devops.data.model.User;
import es.upm.miw.devops.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DatabaseSeederTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private DatabaseSeeder databaseSeeder;

    @Test
    void seed_whenRepositoryIsEmpty_savesUsers() {
        when(userRepository.count()).thenReturn(0L);

        databaseSeeder.seed();

        verify(userService, atLeastOnce()).create(any(User.class));
    }

    @Test
    void seed_whenDataAlreadyExists_doesNotSave() {
        when(userRepository.count()).thenReturn(1L);

        databaseSeeder.seed();

        verify(userService, never()).create(any(User.class));
    }
}
