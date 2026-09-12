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
}
