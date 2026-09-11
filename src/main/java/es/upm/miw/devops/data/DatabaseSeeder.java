package es.upm.miw.devops.data;

import es.upm.miw.devops.data.model.Role;
import es.upm.miw.devops.data.model.User;
import es.upm.miw.devops.services.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class DatabaseSeeder {
    private final UserRepository userRepository;
    private final UserService userService;

    public DatabaseSeeder(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @PostConstruct
    public void seed() {
        if (this.userRepository.count() == 0) {
            this.userService.create(new User("1", "John", "Doe", "john@example.com",
                    "12345678A", "Main St 1", "Madrid", "Madrid", "28001", true, Role.ADMIN));
            this.userService.create(new User("2", "Jane", "Smith", "jane@example.com",
                    "87654321B", "Oak Ave 5", "Barcelona", "Barcelona", "08001", true, Role.CUSTOMER));
            this.userService.create(new User("3", null, null, null,
                    null, null, null, null, null, false, Role.CUSTOMER));

        }
    }
}
