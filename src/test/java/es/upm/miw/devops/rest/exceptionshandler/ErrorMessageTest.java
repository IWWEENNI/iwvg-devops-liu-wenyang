package es.upm.miw.devops.rest.exceptionshandler;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ErrorMessageTest {

    @Test
    void constructor_setsAllFields() {
        RuntimeException ex = new RuntimeException("test message");
        ErrorMessage errorMessage = new ErrorMessage(ex, 404);

        assertThat(errorMessage.getError()).isEqualTo("RuntimeException");
        assertThat(errorMessage.getMessage()).isEqualTo("test message");
        assertThat(errorMessage.getCode()).isEqualTo(404);
    }

    @Test
    void toString_containsAllFields() {
        RuntimeException ex = new RuntimeException("test message");
        ErrorMessage errorMessage = new ErrorMessage(ex, 404);

        String result = errorMessage.toString();
        assertThat(result).contains("RuntimeException");
        assertThat(result).contains("test message");
        assertThat(result).contains("404");
    }
}
