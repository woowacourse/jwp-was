package http.support.resource;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ResourceTypeTest {

    @Test
    @DisplayName("text 형식의 타입 반환")
    public void getTextResourceType() {
        ResourceType resourceType = new TextResourceType("html");
        assertThat(resourceType.getResourceType()).isEqualTo("text/html");
    }

    @Test
    @DisplayName("image 형식의 타입 반환")
    public void getImageResourceType() {
        ResourceType resourceType = new ImageResourceType("jpeg");
        assertThat(resourceType.getResourceType()).isEqualTo("image/jpeg");
    }
}