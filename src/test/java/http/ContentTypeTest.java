package http;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class ContentTypeTest {

    @Test
    void fromMimeType_notExistMimeType() {
        String existMimeType = "text/css";
        assertThat(ContentType.fromMimeType(existMimeType))
                .isEqualTo(Optional.of(ContentType.CSS));
    }

    @Test
    void fromMimeType_existMimeType() {
        String notExistMimeType = "not/exist";
        assertThat(ContentType.fromMimeType(notExistMimeType))
                .isEqualTo(Optional.empty());
    }

    @Test
    void fromExtension_notExistExtension() {
        String existExtension = "css";
        assertThat(ContentType.fromExtension(existExtension))
                .isEqualTo(Optional.of(ContentType.CSS));
    }

    @Test
    void fromExtension_existExtension() {
        String notExistExtension = "not/exist";
        assertThat(ContentType.fromExtension(notExistExtension))
                .isEqualTo(Optional.empty());
    }

    @Test
    void isSupportedMimeType_existMimeType() {
        String existMimeType = "text/css";
        assertThat(ContentType.isSupportedMimeType(existMimeType)).isTrue();
    }

    @Test
    void isSupportedMimeType_notExistMimeType() {
        String notExistMimeType = "not/exist";
        assertThat(ContentType.isSupportedMimeType(notExistMimeType)).isFalse();
    }

    @Test
    void canAccept_ALL() {
        ContentType all = ContentType.ALL;

        for (ContentType contentType : ContentType.values()) {
            assertThat(all.canAccept(contentType)).isTrue();
        }
    }

    @Test
    void canAccept_notALL() {
        for (ContentType contentType : ContentType.values()) {
            if (contentType.ALL.equals(contentType)) {
                continue;
            }
            assertContentTypeCanAcceptItSelf(contentType);
        }
    }

    private void assertContentTypeCanAcceptItSelf(ContentType contentType) {
        for (ContentType opponentType : ContentType.values()) {
            if (contentType.equals(opponentType)) {
                assertThat(contentType.canAccept(opponentType)).isTrue();
                continue;
            }
            assertThat(contentType.canAccept(opponentType)).isFalse();
        }
    }
}