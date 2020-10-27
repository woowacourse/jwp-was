package util;

import web.application.dto.ResponseDto;

@FunctionalInterface
public interface TemplateEngine {

    String apply(String path, ResponseDto<? extends ResponseDto<?>> responseDto);
}
