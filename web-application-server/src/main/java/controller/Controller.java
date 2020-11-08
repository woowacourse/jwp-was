package controller;

import http.message.HttpRequestMessage;
import http.message.HttpResponseMessage;

public interface Controller {
    HttpResponseMessage createHttpResponseMessage(HttpRequestMessage httpRequestMessage);
}
