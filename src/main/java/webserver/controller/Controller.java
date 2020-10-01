package webserver.controller;

import webserver.http.message.HttpMessage;
import webserver.http.message.HttpRequestMessage;

public interface Controller {
    HttpMessage createHttpResponseMessage(HttpRequestMessage httpRequestMessage);
}
