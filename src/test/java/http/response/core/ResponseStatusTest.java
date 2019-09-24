package http.response.core;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ResponseStatusTest {
    private ResponseStatus responseStatus;

    @Test
    @DisplayName("OK 상태 코드")
    void OKResponse() {
        responseStatus = ResponseStatus.of(200);
        assertThat(responseStatus.getResponseStatusHeader()).isEqualTo("200 OK \r\n");
    }

    @Test
    @DisplayName("Found 상태 코드")
    void FoundResponse() {
        responseStatus = ResponseStatus.of(302);
        assertThat(responseStatus.getResponseStatusHeader()).isEqualTo("302 Found \r\n");
    }

    @Test
    @DisplayName("Forbidden 상태 코드")
    void ForbiddenResponse() {
        responseStatus = ResponseStatus.of(403);
        assertThat(responseStatus.getResponseStatusHeader()).isEqualTo("403 Forbidden \r\n");
    }

    @Test
    @DisplayName("Not Found 상태 코드")
    void NotFoundResponse() {
        responseStatus = ResponseStatus.of(404);
        assertThat(responseStatus.getResponseStatusHeader()).isEqualTo("404 Not Found \r\n");
    }

    @Test
    @DisplayName("Internal Server Erro 상태 코드")
    void InternalServerErrorResponse() {
        responseStatus = ResponseStatus.of(500);
        assertThat(responseStatus.getResponseStatusHeader()).isEqualTo("500 Internal Server Error \r\n");
    }
}