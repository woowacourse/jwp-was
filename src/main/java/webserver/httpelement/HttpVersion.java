package webserver.httpelement;

import java.util.Optional;
import java.util.stream.Stream;

public enum HttpVersion {
    HTTP_0_9,
    HTTP_1_0,
    HTTP_1_1,
    HTTP_2_0;

    public static Optional<HttpVersion> of(double version) {
        return Stream.of(values())
                    .filter(x -> Double.compare(x.version(), version) == 0)
                    .findAny();
    }

    public static Optional<HttpVersion> of(String version) {
        final String[] args = version.trim().split("\\s*/\\s*");
        try {
            return args[0].equalsIgnoreCase("HTTP") ? of(Double.parseDouble(args[1])) : Optional.empty();
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    public double version() {
        return Double.parseDouble(this.name().replaceAll("HTTP", "").replaceAll("_", "")) / 10.0;
    }

    @Override
    public String toString() {
        return this.name().replace("_", ".").replaceFirst("\\.", "/");
    }
}