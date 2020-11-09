## web-server module
- HTTP 통신을 지원한다.
    - HTTP Request를 읽어들여 요청을 분석한다.
    - 요청 url에 맞는 controller가 있으면 controller 로직을 수행하고 없으면, 정적 자원을 찾는다.
    - 요청에 맞는 응답을 구성한 후, 적절한 상태코드(status code)와 함께 Http Response를 반환한다.
- 사용자 인증을 위해 HTTP Session을 사용하며 쿠키 방식을 통해서 세션을 관리한다.
