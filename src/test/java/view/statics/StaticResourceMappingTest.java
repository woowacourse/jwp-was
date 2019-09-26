package view.statics;

import org.junit.jupiter.api.Test;
import view.statics.StaticResourceMapping;

import static org.assertj.core.api.Assertions.assertThat;
import static view.statics.StaticResourceMapping.DEFAULT_HTML_LOCATION;

class StaticResourceMappingTest {
    private StaticResourceMapping mapping = new StaticResourceMapping();

    @Test
    void html_Location_확인() {
        assertThat(mapping.getLocation("html")).isEqualTo(DEFAULT_HTML_LOCATION);
        assertThat(mapping.getLocation("htm")).isEqualTo(DEFAULT_HTML_LOCATION);
    }

    @Test
    void setAllLocationsTest() {
        // when
        final String location = "/web";
        mapping.setAllLocations(location);

        // then
        assertThat(mapping.getLocation("css")).isEqualTo(location);
        assertThat(mapping.getLocation("html")).isEqualTo(DEFAULT_HTML_LOCATION);
        assertThat(mapping.getLocation("htm")).isEqualTo(DEFAULT_HTML_LOCATION);
    }

    @Test
    void addStaticResourceTest() {
        // given
        final String extension = "extension";
        final String location = "/static";

        // when
        mapping.addStaticResource(extension, location);

        // then
        assertThat(mapping.getLocation(extension)).isEqualTo(location);
    }
}