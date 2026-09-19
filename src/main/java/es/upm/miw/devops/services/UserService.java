package es.upm.miw.devops.services;

import es.upm.miw.devops.data.UserRepository;
import es.upm.miw.devops.data.model.User;
import es.upm.miw.devops.rest.dtos.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void create(User user) {
        this.userRepository.save(user);
    }

    public User findById(String id) {
        return this.userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found: " + id));
    }

    public List<User> findAll(String role, String province, Boolean billable) {
        return this.userRepository.findAll().stream()
                .filter(user -> role == null || (user.getRole() != null && user.getRole().name().equalsIgnoreCase(role)))
                .filter(user -> province == null || province.equalsIgnoreCase(user.getProvince()))
                .filter(user -> billable == null || user.isBillable() == billable)
                .toList();
    }

    public User update(String id, UserDto dto) {
        User user = this.findById(id);
        user.setFirstName(dto.getFirstName());
        user.setFamilyName(dto.getFamilyName());
        user.setEmail(dto.getEmail());
        user.setIdentity(dto.getIdentity());
        user.setAddress(dto.getAddress());
        user.setCity(dto.getCity());
        user.setProvince(dto.getProvince());
        user.setPostalCode(dto.getPostalCode());
        user.setRole(dto.getRole());
        return this.userRepository.save(user);
    }

    public User activate(String id) {
        User user = this.findById(id);
        user.setActive(true);
        return this.userRepository.save(user);
    }

    public void deleteById(String id) {
        if (!this.userRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found: " + id);
        }
        this.userRepository.deleteById(id);
    }
}
