package webserver.router;

import webserver.BadRequestException;
import webserver.pageprovider.PageProvider;

public interface Router {

    default PageProvider retrieve(String pattern) {
        throw new BadRequestException(pattern);
    }

    default boolean canHandle(String pattern) {
        return false;
    }
}
