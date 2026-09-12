package es.upm.miw.devops.rest;

import es.upm.miw.devops.data.model.User;
import es.upm.miw.devops.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(UserResource.USER)
public class UserResource {
    public static final String USER = "/user";
    static final String USER_ID = "/{id}";

    private final UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> readAll(@RequestParam(required = false) String role,
                              @RequestParam(required = false) String province,
                              @RequestParam(required = false) Boolean billable) {
        return this.userService.findAll(role, province, billable);
    }
    @GetMapping(USER_ID)
    public User read(@PathVariable String id) {
        return this.userService.findById(id);
    }

    @DeleteMapping(USER_ID)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        this.userService.deleteById(id);
    }
}
