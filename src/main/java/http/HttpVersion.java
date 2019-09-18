package http;

import java.util.Optional;
import java.util.stream.Stream;

public enum HttpVersion {
    HTTP_0_9(0.9),
    HTTP_1(1.0),
    HTTP_1_1(1.1),
    HTTP_2(2.0);

    private final double version;

    HttpVersion(double version) {
        this.version = version;
    }

    public static Optional<HttpVersion> of(double version) {
        return Stream.of(values())
                    .filter(x -> Double.compare(x.version, version) == 0)
                    .findAny();
    }

    public static Optional<HttpVersion> of(String version) {
        return of(Double.parseDouble(version.split("/")[1]));
    }

    @Override
    public String toString() {
        return "HttpVersion{" +
                "version=" + this.version +
                '}';
    }
}