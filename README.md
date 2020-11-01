# 웹 애플리케이션 서버
## 진행 방법
* 웹 애플리케이션 서버 요구사항을 파악한다.
* 요구사항에 대한 구현을 완료한 후 자신의 github 아이디에 해당하는 브랜치에 Pull Request(이하 PR)를 통해 코드 리뷰 요청을 한다.
* 코드 리뷰 피드백에 대한 개선 작업을 하고 다시 PUSH한다.
* 모든 피드백을 완료하면 다음 단계를 도전하고 앞의 과정을 반복한다.

## 우아한테크코스 코드리뷰
* [온라인 코드 리뷰 과정](https://github.com/woowacourse/woowacourse-docs/blob/master/maincourse/README.md)



## 1단계 - 웹 서버 구현

1. http://localhost:8080/index.html 로 접속했을 때 webapp 디렉토리의 index.html 파일을 읽어 클라이언트에 응답한다.

2. “회원가입” 메뉴에서 회원가입을 하기 위해 사용자가 입력한 값이 URL로 서버에 전달되면, 이 값을 파싱해 model.User 클래스에 저장한다.

3. http://localhost:8080/user/form.html 파일의 form 태그 method를 get에서 post로 수정한 후 회원가입 기능이 정상적으로 동작하도록 구현한다.

4. “회원가입”을 완료하면 redirect 방식처럼 “index.html”로 이동해야 한다. 즉, 브라우저의 URL이 /index.html로 변경해야 한다.

5. Stylesheet 파일을 지원하도록 구현한다.

## 2단계 - HTTP 웹 서버 리팩토링

WAS 기능과 HTTP 요청/응답 처리 기능을 재사용이 가능한 구조가 되도록 한다.

- WAS 기능
  - 다수의 사용자 요청에 대해 Queue에 저장한 후 순차적으로 처리가 가능하도록 해야 한다.
  - Thread Pool을 적용해 일정 수의 사용자를 동시에 처리가 가능하도록 한다.
- HTTP 요청/응답 처리 기능
  - HTTP 요청 Header/Body 처리, 응답 Header/Body 처리만을 담당하는 역할을 분리해 재사용 가능하도록 한다.
    - 클라이언트 요청 데이터를 처리하는 로직을 별도의 클래스로 분리한다.
    - 클라이언트 응답 데이터를 처리하는 로직을 별도의 클래스로 분리한다.
    - 다형성을 활용해 클라이언트 요청 URL에 대한 분기 처리를 제거한다.
  - 추가 요구사항이나 변경이 발생하는 경우의 처리를 연습해 본다.
    - HTTP에서 POST 방식으로 데이터를 전달할 때 body를 통한 데이터 전달뿐만 아니라 Query String을 활용한 데이터 전달도 지원해야 한다.

## 3단계 - 로그인 및 세션 구현

1. “로그인” 메뉴를 클릭하면 http://localhost:8080/user/login.html 으로 이동해 로그인할 수 있다.

   a.  로그인이 성공하면 /index.html로 이동해야 한다.

   b.  로그인이 실패하면 /user/login_failed.html로 이동해야 한다.

   c.  회원가입한 사용자로 로그인할 수 있어야 한다.

   d.  로그인이 성공하면 cookie를 활용해 로그인 상태를 유지할 수 있어야 한다. (요청 header의 Cookie header 값이 logined=true)

   e.  로그인이 실패하면 Cookie header 값이 logined=false로 전달되어야 한다.

2. 접근 사용자가 “로그인” 상태(Cookie 값이 logined=true) 경우 http://localhost:8080/user/list 로 접근했을 때 사용자 목록을 출력한다.

   a.  만약 로그인하지 않은 상태라면 로그인 페이지(login.html)로 이동한다.

   b.  동적으로 html을 생성하기 위해 handlebars.java template engine을 활용한다.

3. 서블릿에서 지원하는 HttpSession API의 일부를 지원해야 한다. 구현할 메소드는 다음 5개이다.

   a.  String getId(): 현재 세션에 할당되어 있는 고유한 세션 아이디를 반환

   b.  void setAttribute(String name, Object value): 현재 세션에 value 인자로 전달되는 객체를 name 인자 이름으로 저장

   c.  Object getAttribute(String name): 현재 세션에 name 인자로 저장되어 있는 객체 값을 찾아 반환

   d.  void removeAttribute(String name): 현재 세션에 name 인자로 저장되어 있는 객체 값을 삭제

   e.  void invalidate(): 현재 세션에 저장되어 있는 모든 값을 삭제

## 4단계 - multi module 적용

- gradle의 multi module 개념을 적용해 아래 두 영역을 서로 다른 프로젝트로 분리한다.
  - Web Application Server(이하 WAS) 본연의 기능을 담당하는 부분
  - 사용자 관리라는 응용 애플리케이션을 구현하는 부분