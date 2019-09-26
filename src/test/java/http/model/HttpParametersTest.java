package http.model;

import http.model.request.HttpParameters;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class HttpParametersTest {
    @Test
    void 기본동작() {
        HttpParameters parameters = new HttpParameters();
        parameters.addParameter("name", "name");
        parameters.addParameter("password", "password");

        assertThat(parameters.getParameter("name")).isEqualTo("name");
        assertThat(parameters.getParameter("password")).isEqualTo("password");
    }

    @Test
    void 외부에서_변경하려_할때_거부() {
        HttpParameters parameters = new HttpParameters();

        Map<String, String> field = parameters.getParameters();
        assertThatThrownBy(() -> field.put("some", "thing")).isInstanceOf(UnsupportedOperationException.class);
    }

}