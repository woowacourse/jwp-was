package kr.wootecat.dongle.application;

import kr.wootecat.dongle.model.handler.HandlerMappingsFactory;
import kr.wootecat.dongle.model.http.session.IdGeneratorFactory;
import kr.wootecat.dongle.model.http.session.SessionStorage;
import kr.wootecat.dongle.model.http.session.SessionValidator;

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
