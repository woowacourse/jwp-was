package application.dto;

import request.HttpRequest;

public class HttpRequestToDtoConverter {

    public static UserCreateRequest toUserCreateRequest(HttpRequest httpRequest) {
        return new UserCreateRequest(
            httpRequest.getValueFromFormData("userId"),
            httpRequest.getValueFromFormData("password"),
            httpRequest.getValueFromFormData("name"),
            httpRequest.getValueFromFormData("email")
        );
    }

    public static LoginRequest toLoginRequest(HttpRequest httpRequest) {
        return new LoginRequest(
            httpRequest.getValueFromFormData("userId"),
            httpRequest.getValueFromFormData("password")
        );
    }
}
