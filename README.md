# 웹 애플리케이션 서버

## TODO
- HttpTestClient
    - Address already in use (Bind failed) 해결
    - ResponseSpec 리팩토링!

- Content-Type: application/x-www-form-urlencoded 외 타입 처리
    - josn
- ~~Request Builder Pattern~~
- RequestHeader -> Response에서도 사용하면 어떨까?
- getParameter - NPE 처리
- HttpResponse 개선 해보기
- http.handler 패키지 이름 변경하기 viewResolver? 
- Cookie 를 항상 만들지 않고 있을 때만 만들게 변경하기.?

- ~~로그인~~
   1. id로 db 조회 
   2. 패스워드 확인
      - 성공 - sendRedirect("/"), Cookie: logined=true
      - 실패 - sendRedirect("/user/login_failed.html"), Cookie: logined=false
- ~~회원목록 (/user/list)~~
   - 로그인 상태면 출력
   - 로그인 아니면 login.html 이동
- ~~쿠키~~
- 세션
   - getId() 
   - setAttribute(String name, Object value)
   - getAttribute(String name)
   - removeAttribute(String name)
   - invalidate() 
- handlebars template engine 사용


## 고민 
- 웹서버를 만드는건데 Servlet에서 사용하는 기능들 중복제거를 위해서 부가적인 기능을 지금 넣는게 맞을까?
- Cookie MultiValueMap 사용?
