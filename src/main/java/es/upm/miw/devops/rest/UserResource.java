package es.upm.miw.devops.rest;

import es.upm.miw.devops.data.model.User;
import es.upm.miw.devops.services.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(UserResource.USER)
public class UserResource {
    static final String USER = "/user";
    static final String USER_ID = "/{id}";

    private final UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(USER_ID)
    public User read(@PathVariable String id) {
        return this.userService.findById(id);
    }
}
