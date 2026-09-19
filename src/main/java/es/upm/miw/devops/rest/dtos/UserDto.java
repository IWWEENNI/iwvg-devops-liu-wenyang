package es.upm.miw.devops.rest.dtos;

import es.upm.miw.devops.data.model.Role;

public record UserDto(
        String firstName,
        String familyName,
        String email,
        String identity,
        String address,
        String city,
        String province,
        String postalCode,
        Role role
) {}
