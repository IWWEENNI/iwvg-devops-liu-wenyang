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
            this.userService.create(new User("4", "Carlos", "Garcia", "carlos@example.com",
                    "11111111C", "Gran Via 10", "Madrid", "Madrid", "28010", true, Role.CUSTOMER));
            this.userService.create(new User("5", "Ana", "Lopez", "ana@example.com",
                    "22222222D", "Paseo de Gracia 50", "Barcelona", "Cataluña", "08007", false, Role.CUSTOMER));
            this.userService.create(new User("6", "Luis", "Martinez", "luis@example.com",
                    "33333333E", "Calle Larios 3", "Málaga", "Andalucía", "29005", true, Role.ADMIN));
            this.userService.create(new User("7", null, "Fernandez", null,
                    null, null, "Sevilla", "Andalucía", null, false, Role.CUSTOMER));
        }
    }
}
