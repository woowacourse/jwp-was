package webserver.resource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import java.net.URISyntaxException;
import org.junit.jupiter.api.Test;
import resource.ContentType;
import resource.Resource;
import resource.ResourcesHandler;

class ResourcesHandlerTest {

    private ResourcesHandler resourceHandler = new ResourcesHandler();

    @Test
    void convertUriToResource() throws IOException, URISyntaxException {
        Resource resource = resourceHandler.convertUriToResource("/css/styles.css");
        assertThat(resource).isInstanceOf(Resource.class);
        assertThat(resource.getContentType()).isEqualTo(ContentType.CSS);
    }

    @Test
    void convertUriToResource_IfExtensionOfRequestFileIsNotSupported() {
        assertThatThrownBy(() -> resourceHandler.convertUriToResource("/css/styles.exe"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("cannot convert given uri to resource.");
    }
}
