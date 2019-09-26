package view.statics;

import java.util.Objects;

public class StaticResource {
    private static final String DEFAULT_LOCATION = "./static";

    private final String extension;
    private String location;

    private StaticResource(final String extension, final String location) {
        this.extension = extension;
        this.location = location;
    }

    public static StaticResource of(final String extension) {
        return new StaticResource(extension, DEFAULT_LOCATION);
    }

    public static StaticResource of(final String extension, final String location) {
        return new StaticResource(extension, location);
    }

    public void changeLocation(final String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final StaticResource resource = (StaticResource) o;
        return Objects.equals(extension, resource.extension);
    }

    @Override
    public int hashCode() {
        return Objects.hash(extension);
    }
}
