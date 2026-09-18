package es.upm.miw.devops.data.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

    @Test
    void noArgConstructor_createsUserWithNullFields() {
        User user = new User();
        assertThat(user.getId()).isNull();
        assertThat(user.getFirstName()).isNull();
    }

    @Test
    void setters_andGetters_workCorrectly() {
        User user = new User();
        user.setId("10");
        user.setFirstName("Alice");
        user.setFamilyName("Wonder");
        user.setEmail("alice@example.com");
        user.setIdentity("99999999Z");
        user.setAddress("Calle Falsa 123");
        user.setCity("Sevilla");
        user.setProvince("Andalucía");
        user.setPostalCode("41001");
        user.setActive(true);
        user.setRole(Role.ADMIN);

        assertThat(user.getId()).isEqualTo("10");
        assertThat(user.getFirstName()).isEqualTo("Alice");
        assertThat(user.getFamilyName()).isEqualTo("Wonder");
        assertThat(user.getEmail()).isEqualTo("alice@example.com");
        assertThat(user.getIdentity()).isEqualTo("99999999Z");
        assertThat(user.getAddress()).isEqualTo("Calle Falsa 123");
        assertThat(user.getCity()).isEqualTo("Sevilla");
        assertThat(user.getProvince()).isEqualTo("Andalucía");
        assertThat(user.getPostalCode()).isEqualTo("41001");
        assertThat(user.isActive()).isTrue();
        assertThat(user.getRole()).isEqualTo(Role.ADMIN);
    }

    @Test
    void isBillable_whenFirstNameBlank_returnsFalse() {
        User user = new User("1", "   ", "Doe", "john@example.com",
                "12345678A", "Main St 1", "Madrid", "Madrid", "28001", true, Role.ADMIN);
        assertThat(user.isBillable()).isFalse();
    }

    @Test
    void isBillable_whenFamilyNameNull_returnsFalse() {
        User user = new User("1", "John", null, "john@example.com",
                "12345678A", "Main St 1", "Madrid", "Madrid", "28001", true, Role.ADMIN);
        assertThat(user.isBillable()).isFalse();
    }

    @Test
    void isBillable_whenEmailNull_returnsFalse() {
        User user = new User("1", "John", "Doe", null,
                "12345678A", "Main St 1", "Madrid", "Madrid", "28001", true, Role.ADMIN);
        assertThat(user.isBillable()).isFalse();
    }

    @Test
    void isBillable_whenIdentityNull_returnsFalse() {
        User user = new User("1", "John", "Doe", "john@example.com",
                null, "Main St 1", "Madrid", "Madrid", "28001", true, Role.ADMIN);
        assertThat(user.isBillable()).isFalse();
    }

    @Test
    void isBillable_whenAddressNull_returnsFalse() {
        User user = new User("1", "John", "Doe", "john@example.com",
                "12345678A", null, "Madrid", "Madrid", "28001", true, Role.ADMIN);
        assertThat(user.isBillable()).isFalse();
    }

    @Test
    void isBillable_whenCityNull_returnsFalse() {
        User user = new User("1", "John", "Doe", "john@example.com",
                "12345678A", "Main St 1", null, "Madrid", "28001", true, Role.ADMIN);
        assertThat(user.isBillable()).isFalse();
    }

    @Test
    void isBillable_whenProvinceNull_returnsFalse() {
        User user = new User("1", "John", "Doe", "john@example.com",
                "12345678A", "Main St 1", "Madrid", null, "28001", true, Role.ADMIN);
        assertThat(user.isBillable()).isFalse();
    }

    @Test
    void isBillable_whenPostalCodeNull_returnsFalse() {
        User user = new User("1", "John", "Doe", "john@example.com",
                "12345678A", "Main St 1", "Madrid", "Madrid", null, true, Role.ADMIN);
        assertThat(user.isBillable()).isFalse();
    }
}
