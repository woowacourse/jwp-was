# 웹 애플리케이션 서버
## 1단계 요구사항
- [x] http://localhost:8080/index.html 로 접속했을 때 webapp 디렉토리의 index.html 파일을 읽어 클라이언트에 응답한다.
- [x] “회원가입” 메뉴를 클릭하면 http://localhost:8080/user/form.html 으로 이동하면서 회원가입할 수 있다. 
- [x] http://localhost:8080/user/form.html 파일의 form 태그 method를 get에서 post로 수정한 후 회원가입 기능이 정상적으로 동작하도록 구현한다.
- [x] “회원가입”을 완료하면 /index.html 페이지로 이동하고 싶다. 현재는 URL이 /user/create 로 유지되는 상태로 읽어서 전달할 파일이 없다. 따라서 redirect 방식처럼 회원가입을 완료한 후 “index.html”로 이동해야 한다.
- [x] 지금까지 구현한 소스 코드는 stylesheet 파일을 지원하지 못하고 있다. Stylesheet 파일을 지원하도록 구현하도록 한다.

## 2단계 요구사항
- [x] 다수의 사용자 요청에 대해 Queue 에 저장한 후 순차적으로 처리가 가능하도록 해야 한다.
- [x] 서버가 모든 요청에 대해 Thread를 매번 생성하는 경우 성능상 문제가 발생할 수 있다. Thread Pool을 적용해 일정 수의 사용자 동시에 처리가 가능하도록 한다.
- [x] HTTP 요청 Header/Body 처리, 응답 Header/Body 처리만을 담당하는 역할을 분리해 재사용 가능하도록 한다.
- [x] 다형성을 활용해 클라이언트 요청 URL에 대한 분기 처리를 제거한다.

## 3단계 요구사항
- [x] 로그인할 수 있다. 로그인이 성공하면 index.html로 이동하고, 로그인이 실패하면 /user/login_failed.html로 이동해야 한다.
- [x] 앞에서 회원가입한 사용자로 로그인할 수 있어야 한다.
- [x] 로그인이 성공하면 cookie를 활용해 로그인 상태를 유지할 수 있어야 한다. 
- [x] 로그인이 성공할 경우 요청 header의 Cookie header 값이 logined=true, 로그인이 실패하면 Cookie header 값이 logined=false로 전달되어야 한다.
- [x] 사용자가 “로그인” 상태일 경우(Cookie 값이 logined=true) 경우 http://localhost:8080/user/list 로 접근했을 때 사용자 목록을 출력한다. 만약 로그인하지 않은 상태라면 로그인 페이지(login.html)로 이동한다.
- [x] 서블릿에서 지원하는 HttpSession API의 일부를 지원해야 한다.
- [x] SessionStore를 통해 백엔드에서 세션에 접근할 수 있어야 한다.
- [x] 로그인 여부 확인을 세션으로 대체한다.
## 우아한테크코스 코드리뷰
* [온라인 코드 리뷰 과정](https://github.com/woowacourse/woowacourse-docs/blob/master/maincourse/README.md)