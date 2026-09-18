package es.upm.miw.devops;

import org.junit.jupiter.api.Test;

class ApplicationTest {

    @Test
    void main_runsWithoutException() {
        Application.main(new String[]{
                "--spring.profiles.active=test",
                "--server.port=0",
                "--spring.datasource.url=jdbc:h2:mem:apptest;DB_CLOSE_DELAY=-1"
        });
    }
}
