package server.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import web.application.controller.Controller;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class UrlMappingCreateDto {

    private final String url;
    private final Controller controller;

    public static UrlMappingCreateDto of(String url, Controller controller) {
        return new UrlMappingCreateDto(url, controller);
    }
}
