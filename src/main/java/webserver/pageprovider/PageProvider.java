package webserver.pageprovider;

import http.request.HttpRequestAccessor;
import http.response.HttpResponseAccessor;
import webserver.page.Page;

public interface PageProvider {
    // render -> 내용물을 가지고 보낼 수 있는 형태로 만들 수 있어야??
    Page provide(HttpRequestAccessor request, HttpResponseAccessor response);
}
