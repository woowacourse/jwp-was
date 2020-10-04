package web.application.exception;

import web.application.vo.RequestVo;

public class ControllerNotFoundException extends IllegalArgumentException {

    public ControllerNotFoundException(RequestVo requestVo) {
        super("등록되지 않은 경로입니다. Path = " + requestVo.getPath() + " Method= " + requestVo.getRequestMethod());
    }
}
