package webserver.resolver;

import http.request.HttpRequest;
import http.response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

public class ResourceRequestResolver {
    private static final String TEMPLATE_PATH = "./templates";
    private static final String STATIC_PATH = "./static";
    private static final String PATH_SPLITTER = "\\.";
    private static final List<ResourceResolverRegistration> resourceResolverRegistrations = new ArrayList<>();
    private static final Set<String> templateExtensions = new HashSet<>(Arrays.asList("html", "ico"));
    private static final Set<String> staticExtensions = new HashSet<>(Arrays.asList("css", "js", "img", "ttf", "woff"));

    static {
        resourceResolverRegistrations.add(new ResourceResolverRegistration(templateExtensions, TEMPLATE_PATH));
        resourceResolverRegistrations.add(new ResourceResolverRegistration(staticExtensions, STATIC_PATH));
    }

    static void resolve(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        String[] splitPath = httpRequest.getPath().split(PATH_SPLITTER);
        String contentType = splitPath[splitPath.length - 1];

        ResourceResolverRegistration resourceResolverRegistration = resourceResolverRegistrations.stream()
                .filter(r -> r.existExtension(contentType))
                .findAny().orElseThrow(NotFoundException::new);

        resourceResolverRegistration.resolve(httpRequest.getPath(), contentType, httpResponse);
    }
}
