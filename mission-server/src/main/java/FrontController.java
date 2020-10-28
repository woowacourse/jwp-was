import application.Controller;
import controller.StaticController;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import servlet.HttpRequest;
import servlet.HttpResponse;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class FrontController implements Controller {

    private static final StaticController STATIC_CONTROLLER = new StaticController();

    private final UrlMapper urlMapper;

    public static FrontController from(UrlMapper urlMapper) {
        return new FrontController(urlMapper);
    }

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (httpRequest.hasPathOfStaticFile()) {
            STATIC_CONTROLLER.service(httpRequest, httpResponse);
            return;
        }
        if (urlMapper.contains(httpRequest.getPath())) {
            urlMapper.getController(httpRequest.getPath()).service(httpRequest, httpResponse);
            return;
        }
        httpResponse.respondPageNotFound();
    }
}
