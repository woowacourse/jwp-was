package controller;

import java.util.Objects;
import model.general.ContentType;
import model.general.Status;
import model.request.Request;
import model.response.Response;
import model.response.StatusLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.ApiService;
import service.ResourceService;

public class Controller {

    private static final Logger logger = LoggerFactory.getLogger(Controller.class);

    public static Response executeOperation(Request request) {
        try {
            ContentType contentType = request.generateContentTypeFromRequestUri();
            if (Objects.nonNull(contentType)) {
                return ResourceService.execute(request);
            }
            return ApiService.execute(request);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Response.of(StatusLine.of(request, Status.INTERNAL_ERROR));
        }
    }
}
