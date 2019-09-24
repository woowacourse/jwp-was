package http.support.resource;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ResourceTypeFactoryTest {

    @Test
    @DisplayName("해당 파일의 경로에 맞는 text/html 을 반환한다")
    public void getTextResourceType() {
        ResourceType resourceType = ResourceTypeFactory.getInstance("/index.html");
        assertThat(resourceType.getResourceType()).isEqualTo("text/html");
    }

    @Test
    @DisplayName("해당 파일의 경로에 맞는 image/jpeg 을 반환한다")
    public void getImageResourceType() {
        ResourceType resourceType = ResourceTypeFactory.getInstance("/good.jpeg");
        assertThat(resourceType.getResourceType()).isEqualTo("image/jpeg");
    }
}