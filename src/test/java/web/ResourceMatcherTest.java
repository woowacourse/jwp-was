package web;

import static org.junit.jupiter.api.Assertions.*;
import static web.HttpRequestFixture.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ResourceMatcherTest {

    @DisplayName("uri에 해당하는 Resource를 찾아준다.")
    @Test
    void fromUri() {
        ResourceMatcher actual = ResourceMatcher.fromUri(INDEX_HTML).get();
        assertEquals(ResourceMatcher.HTML, actual);
    }
}
