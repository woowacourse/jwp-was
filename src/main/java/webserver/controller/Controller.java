package webserver.controller;

import webserver.http.message.HttpRequestMessage;
import webserver.http.message.HttpResponseMessage;

public interface Controller {
    HttpResponseMessage createHttpResponseMessage(HttpRequestMessage httpRequestMessage);
}
