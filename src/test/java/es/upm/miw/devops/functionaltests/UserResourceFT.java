package es.upm.miw.devops.functionaltests;

import es.upm.miw.devops.rest.UserResource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
class UserResourceFT {

    @Autowired
    private WebTestClient webTestClient;

    // Feature 1: GET /user/{id}

    @Test
    void readById_whenExists_returns200() {
        webTestClient.get()
                .uri(UserResource.USER + "/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo("1")
                .jsonPath("$.firstName").isEqualTo("John");
    }

    @Test
    void readById_whenNotFound_returns404() {
        webTestClient.get()
                .uri(UserResource.USER + "/999")
                .exchange()
                .expectStatus().isNotFound();
    }

    // Feature 2: GET /user con filtros

    @Test
    void readAll_noFilters_returnsAllUsers() {
        webTestClient.get()
                .uri(UserResource.USER)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.length()").value(count ->
                        org.assertj.core.api.Assertions.assertThat((Integer) count).isGreaterThan(0));
    }

    @Test
    void readAll_filterByRole_returnsOnlyAdmins() {
        webTestClient.get()
                .uri(UserResource.USER + "?role=ADMIN")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].role").isEqualTo("ADMIN");
    }

    @Test
    void readAll_filterByProvince_returnsOnlyMadrid() {
        webTestClient.get()
                .uri(UserResource.USER + "?province=Madrid")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].province").isEqualTo("Madrid");
    }

    @Test
    void readAll_filterByBillableTrue_returnsBillableUsers() {
        webTestClient.get()
                .uri(UserResource.USER + "?billable=true")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$").isArray();
    }

    @Test
    void readAll_filterByBillableFalse_returnsNonBillableUsers() {
        webTestClient.get()
                .uri(UserResource.USER + "?billable=false")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$").isArray();
    }

    // Feature 3: DELETE /user/{id}

    @Test
    void delete_whenExists_returns204() {
        webTestClient.delete()
                .uri(UserResource.USER + "/4")
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    void delete_whenNotFound_returns404() {
        webTestClient.delete()
                .uri(UserResource.USER + "/999")
                .exchange()
                .expectStatus().isNotFound();
    }

    // Feature 4: PUT /user/{id}/active

    @Test
    void activate_whenExists_returns200AndActiveTrue() {
        webTestClient.put()
                .uri(UserResource.USER + "/3/active")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo("3")
                .jsonPath("$.active").isEqualTo(true);
    }

    @Test
    void activate_whenNotFound_returns404() {
        webTestClient.put()
                .uri(UserResource.USER + "/999/active")
                .exchange()
                .expectStatus().isNotFound();
    }
}
