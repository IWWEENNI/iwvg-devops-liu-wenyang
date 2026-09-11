package es.upm.miw.devops.data;

import es.upm.miw.devops.data.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
