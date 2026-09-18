package es.upm.miw.devops.rest.exceptionshandler;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.assertj.core.api.Assertions.assertThat;

class ApiExceptionHandlerTest {

    private final ApiExceptionHandler handler = new ApiExceptionHandler();

    @Test
    void noResourceFoundRequest_returnsNotFoundErrorMessage() {
        ResponseStatusException ex = new ResponseStatusException(HttpStatus.NOT_FOUND, "not found");
        ErrorMessage result = handler.noResourceFoundRequest(ex);

        assertThat(result.getCode()).isEqualTo(404);
        assertThat(result.getError()).isEqualTo("RuntimeException");
        assertThat(result.getMessage()).contains("Ruta no encontrada");
    }

    @Test
    void exception_returnsInternalServerErrorMessage() {
        Exception ex = new RuntimeException("unexpected error");
        ErrorMessage result = handler.exception(ex);

        assertThat(result.getCode()).isEqualTo(500);
        assertThat(result.getError()).isEqualTo("RuntimeException");
        assertThat(result.getMessage()).isEqualTo("ERROR");
    }
}
