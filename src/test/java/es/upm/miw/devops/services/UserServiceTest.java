package es.upm.miw.devops.services;

import es.upm.miw.devops.data.UserRepository;
import es.upm.miw.devops.data.model.Role;
import es.upm.miw.devops.data.model.User;
import es.upm.miw.devops.rest.dtos.UserActiveDto;
import es.upm.miw.devops.rest.dtos.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
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

    // Feature 2: GET /user

    @Test
    void findAll_noFilters_returnsAllUsers() {
        User customer = new User("2", "Jane", "Smith", "jane@example.com",
                "87654321B", "Oak Ave 5", "Barcelona", "Barcelona", "08001", true, Role.CUSTOMER);
        when(userRepository.findAll()).thenReturn(List.of(user, customer));

        List<User> result = userService.findAll(null, null, null);

        assertThat(result).hasSize(2);
    }

    @Test
    void findAll_filterByRole_returnsOnlyMatching() {
        User customer = new User("2", "Jane", "Smith", "jane@example.com",
                "87654321B", "Oak Ave 5", "Barcelona", "Barcelona", "08001", true, Role.CUSTOMER);
        when(userRepository.findAll()).thenReturn(List.of(user, customer));

        List<User> result = userService.findAll("ADMIN", null, null);

        assertThat(result).hasSize(1).allMatch(u -> u.getRole() == Role.ADMIN);
    }

    @Test
    void findAll_filterByProvince_returnsOnlyMatching() {
        User customer = new User("2", "Jane", "Smith", "jane@example.com",
                "87654321B", "Oak Ave 5", "Barcelona", "Barcelona", "08001", true, Role.CUSTOMER);
        when(userRepository.findAll()).thenReturn(List.of(user, customer));

        List<User> result = userService.findAll(null, "Madrid", null);

        assertThat(result).hasSize(1).allMatch(u -> "Madrid".equalsIgnoreCase(u.getProvince()));
    }

    @Test
    void findAll_filterByBillableTrue_returnsOnlyBillable() {
        User incomplete = new User("3", null, null, null, null, null, null, null, null, false, Role.CUSTOMER);
        when(userRepository.findAll()).thenReturn(List.of(user, incomplete));

        List<User> result = userService.findAll(null, null, true);

        assertThat(result).hasSize(1).allMatch(User::isBillable);
    }

    @Test
    void findAll_filterByBillableFalse_returnsOnlyNonBillable() {
        User incomplete = new User("3", null, null, null, null, null, null, null, null, false, Role.CUSTOMER);
        when(userRepository.findAll()).thenReturn(List.of(user, incomplete));

        List<User> result = userService.findAll(null, null, false);

        assertThat(result).hasSize(1).noneMatch(User::isBillable);
    }

    @Test
    void findAll_filterByRole_whenUserHasNullRole_excludesUser() {
        User nullRoleUser = new User("5", "Test", "User", "test@example.com",
                "11111111A", "Street 1", "City", "Province", "11111", true, null);
        when(userRepository.findAll()).thenReturn(List.of(user, nullRoleUser));

        List<User> result = userService.findAll("ADMIN", null, null);

        assertThat(result).hasSize(1).allMatch(u -> u.getRole() == Role.ADMIN);
    }

    // Feature 6.1: PUT /user/{id}

    @Test
    void update_whenExists_updatesAllFieldsAndSaves() {
        when(userRepository.findById("1")).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        UserDto dto = new UserDto("Updated", "Name", "updated@example.com",
                "99999999Z", "New Street 1", "Seville", "Andalucía", "41001", Role.CUSTOMER);

        User result = userService.update("1", dto);

        assertThat(result.getFirstName()).isEqualTo("Updated");
        assertThat(result.getEmail()).isEqualTo("updated@example.com");
        assertThat(result.getRole()).isEqualTo(Role.CUSTOMER);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void update_whenNotFound_throws404() {
        when(userRepository.findById("99")).thenReturn(Optional.empty());

        UserDto dto = new UserDto(null, null, null, null, null, null, null, null, null);
        assertThatThrownBy(() -> userService.update("99", dto))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("User not found");

        verify(userRepository, never()).save(any());
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

    // Feature 6.2: PATCH /user

    @Test
    void updateActive_whenAllExist_updatesActiveForEach() {
        User user2 = new User("2", "Jane", "Smith", "jane@example.com",
                "87654321B", "Oak Ave 5", "Barcelona", "Barcelona", "08001", true, Role.CUSTOMER);
        when(userRepository.findById("1")).thenReturn(Optional.of(user));
        when(userRepository.findById("2")).thenReturn(Optional.of(user2));
        when(userRepository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        userService.updateActive(List.of(
                new UserActiveDto("1", false),
                new UserActiveDto("2", false)
        ));

        assertThat(user.isActive()).isFalse();
        assertThat(user2.isActive()).isFalse();
        verify(userRepository, times(2)).save(any(User.class));
    }

    @Test
    void updateActive_whenOneNotFound_throws404() {
        when(userRepository.findById("1")).thenReturn(Optional.of(user));
        when(userRepository.findById("99")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.updateActive(List.of(
                new UserActiveDto("1", false),
                new UserActiveDto("99", true)
        )))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("User not found");
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
