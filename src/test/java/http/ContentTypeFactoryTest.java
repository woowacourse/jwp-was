package http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ContentTypeFactoryTest {

    @Test
    void canCreate() {
        String accept = "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3";

        assertThat(ContentTypeFactory.canCreate(accept, ContentType.HTML)).isTrue();
        assertThat(ContentTypeFactory.canCreate(accept, ContentType.OCTET_STREAM)).isTrue();
    }
}