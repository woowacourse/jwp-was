package resource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import java.net.URISyntaxException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ResourcesHandlerTest {

    private ResourcesHandler resourceHandler = new ResourcesHandler();

    @Test
    @DisplayName("uri 로 resource 얻기")
    void convertUriToResource() throws IOException, URISyntaxException {
        Resource resource = resourceHandler.convertUriToResource("/css/styles.css");
        assertThat(resource).isInstanceOf(Resource.class);
        assertThat(resource.getContentType()).isEqualTo(ContentType.CSS);
    }

    @Test
    @DisplayName("uri 로 resource 얻기 - uri 의 확장자가, 웹서버가 지원하지 않는 확장자인 경우 예외처리")
    void convertUriToResource_IfExtensionOfRequestFileIsNotSupported() {
        assertThatThrownBy(() -> resourceHandler.convertUriToResource("/css/styles.exe"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("cannot convert given uri to resource.");
    }
}
