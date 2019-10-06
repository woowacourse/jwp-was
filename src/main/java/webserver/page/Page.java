package webserver.page;

import http.ContentType;

public interface Page {
    // response 에서 page 를 가지고 바디를 만들어 낼 수 있어야 할 것임
    // - 필요한 것
    //      - contentType 알아내기
    //      - render (해당 데이터를 가지고 byte[] 형태롤 만들어내도록) -> 바디를 만들 때 필요한 정보
    //
    // - 확장 포인트
    //      - contentType + render 부분 추가 (
    // 이 부분 말고 추가적인 정보는 PageProvider 에서 response 를 통해서 접근하도록 하자
    ContentType getContentType();

    byte[] getBody();
}
