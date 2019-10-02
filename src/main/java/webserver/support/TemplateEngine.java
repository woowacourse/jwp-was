package webserver.support;

import java.util.Optional;

public interface TemplateEngine {
    Optional<String> apply(String path, Object object);
}
