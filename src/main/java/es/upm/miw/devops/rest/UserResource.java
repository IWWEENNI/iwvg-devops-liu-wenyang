package es.upm.miw.devops.rest;

import es.upm.miw.devops.data.model.User;
import es.upm.miw.devops.rest.dtos.UserActiveDto;
import es.upm.miw.devops.rest.dtos.UserDto;
import es.upm.miw.devops.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(UserResource.USER)
public class UserResource {
    public static final String USER = "/user";
    static final String USER_ID = "/{id}";
    static final String USER_ID_ACTIVE = USER_ID + "/active";

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

    @PutMapping(USER_ID)
    public User update(@PathVariable String id, @RequestBody UserDto userDto) {
        return this.userService.update(id, userDto);
    }

    @PutMapping(USER_ID_ACTIVE)
    public User activate(@PathVariable String id) {
        return this.userService.activate(id);
    }

    @PatchMapping
    public void updateActive(@RequestBody List<UserActiveDto> users) {
        this.userService.updateActive(users);
    }

    @DeleteMapping(USER_ID)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        this.userService.deleteById(id);
    }
}
