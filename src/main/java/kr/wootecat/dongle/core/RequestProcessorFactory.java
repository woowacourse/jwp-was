package kr.wootecat.dongle.core;

import kr.wootecat.dongle.core.handler.HandlerMappingsFactory;
import kr.wootecat.dongle.http.session.SessionStorage;
import kr.wootecat.dongle.http.session.SessionValidator;
import kr.wootecat.dongle.utils.IdGeneratorFactory;

public class RequestProcessorFactory {

    private RequestProcessorFactory() {
    }

    public static RequestProcessor create() {
        return new RequestProcessor(
                HandlerMappingsFactory.create(),
                new SessionValidator(SessionStorage.ofEmpty(), IdGeneratorFactory.create())
        );
    }
}
