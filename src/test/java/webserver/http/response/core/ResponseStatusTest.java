package webserver.http.response.core;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ResponseStatusTest {
    private ResponseStatus responseStatus;

    @Test
    @DisplayName("OK 상태 코드")
    void OKResponse() {
        responseStatus = ResponseStatus.of(200);
        assertThat(responseStatus.getHttpStatusCode()).isEqualTo(200);
        assertThat(responseStatus.getHttpStatus()).isEqualTo("OK");
    }

    @Test
    @DisplayName("Found 상태 코드")
    void FoundResponse() {
        responseStatus = ResponseStatus.of(302);
        assertThat(responseStatus.getHttpStatusCode()).isEqualTo(302);
        assertThat(responseStatus.getHttpStatus()).isEqualTo("Found");
    }

    @Test
    @DisplayName("Forbidden 상태 코드")
    void ForbiddenResponse() {
        responseStatus = ResponseStatus.of(403);
        assertThat(responseStatus.getHttpStatusCode()).isEqualTo(403);
        assertThat(responseStatus.getHttpStatus()).isEqualTo("Forbidden");
    }

    @Test
    @DisplayName("Not Found 상태 코드")
    void NotFoundResponse() {
        responseStatus = ResponseStatus.of(404);
        assertThat(responseStatus.getHttpStatusCode()).isEqualTo(404);
        assertThat(responseStatus.getHttpStatus()).isEqualTo("Not Found");
    }

    @Test
    @DisplayName("Internal Server Erro 상태 코드")
    void InternalServerErrorResponse() {
        responseStatus = ResponseStatus.of(500);
        assertThat(responseStatus.getHttpStatusCode()).isEqualTo(500);
        assertThat(responseStatus.getHttpStatus()).isEqualTo("Internal Server Error");
    }
}