package webserver.http.headerfields;

import webserver.http.exception.InvalidHttpVersionException;

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

    public static HttpVersion of(String version) {
        try {
            version = version.split("/")[1].trim();

            return of(Double.parseDouble(version)).orElseThrow(InvalidHttpVersionException::new);
        } catch (NumberFormatException e) {
            throw new InvalidHttpVersionException();
        }
    }

    @Override
    public String toString() {
        return "HTTP/" + this.version;
    }
}