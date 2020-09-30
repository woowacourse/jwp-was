# 웹 애플리케이션 서버

## 🚀 1단계 - HTTP 웹 서버 구현

### 요구사항
- [x]  http://localhost:8080/index.html 로 접속했을 때 resources 디렉토리의 index.html 파일을 읽어 클라이언트에 응답한다.
    - [x]  Http Request Header를 읽는다.
    - [x]  Header에서 요청 URL을 추출한다.
    - [x]  요청 URL path에 맞는 파일로 응답한다.
- [x]  “회원가입” 메뉴를 클릭하면 http://localhost:8080/user/form.html 으로 이동하면서 회원가입할 수 있다. 회원가입한다. 회원가입을 하면 다음과 같은 형태로 사용자가 입력한 값이 서버에 전달된다.
- [x]  HTML과 URL을 비교해 보고 사용자가 입력한 값을 파싱해 model.User 클래스에 저장한다.
- [x]  [http://localhost:8080/user/form.html](http://localhost:8080/user/form.html) 파일의 form 태그 method를 get에서 post로 수정한 후 회원가입 기능이 정상적으로 동작하도록 구현한다.
    - [x] HttpRequest Body를 읽는다.
    - [x] POST 요청에도 회원가입 기능이 정상적으로 동작하도록 한다. 
- [x]  “회원가입”을 완료하면 /index.html 페이지로 이동하고 싶다. 현재는 URL이 /user/create 로 유지되는 상태로 읽어서 전달할 파일이 없다. 따라서 redirect 방식처럼 회원가입을 완료한 후 “index.html”로 이동해야 한다. 즉, 브라우저의 URL이 /index.html로 변경해야 한다.
- [x]  지금까지 구현한 소스 코드는 stylesheet 파일을 지원하지 못하고 있다. Stylesheet 파일을 지원하도록 구현하도록 한다.

## 🚀 2단계 - HTTP 웹 서버 리팩토링

### 고려사항

- 클래스 내에 private 메소드가 많이 생성되었다. 메소드를 클래스로 분리하는 것이 좋을지 검토한다.
- private 메소드에 대해 테스트 코드를 생성하는 것이 좋겠다는 생각이 드는가? 메소드를 클래스로 분리하는 것이 좋을지에 대해 고려해 본다.
- 단위 테스트를 하기 어려운가? 단위 테스트를 하기 쉽도록 설계를 개선할 방법은 없는가? 로직을 구현하는 부분과 테스트를 하기 어렵게 만드는 부분을 분리해서 구현할 방법은 없는가?

### 요구사항

- [ ]  Request Handler를 WAS 기능, HTTP 요청/응답 처리, 개발자가 구현할 애플리케이션 기능으로 역할 분리한다.
    - [x]  클라이언트 요청 데이터를 처리하는 로직을 별도의 클래스로 분리한다.(HttpRequest)
        - [x]  Header/Body의 처리를 담당하는 역할을 분리한다.
        - [x]  클라이언트 요청 데이터를 담고 있는 InputStream을 생성자로 받아 HTTP 메소드, URL, 헤더, 본문을 분리하는 작업을 한다.
        - [x]  헤더는 Map<String, String>에 저장해 관리하고 getHeader(“필드 이름”) 메소드를 통해 접근 가능하도록 구현한다.
        - [ ]  GET과 POST 메소드에 따라 전달되는 인자를 Map<String, String>에 저장해 관리하고 getParameter(“인자 이름”) 메소드를 통해 접근 가능하도록 구현한다.
        - [x]  RequestHandler가 새로 추가한 HttpRequest를 사용하도록 리팩토링한다.
        - [x]  테스트 코드를 기반으로 구현한다.
    - [x]  클라이언트 응답 데이터를 처리하는 로직을 별도의 클래스로 분리한다.(HttpResponse)
        - [x]  Header/Body의 처리를 담당하는 역할을 분리한다.
        - [x]  RequestHandler 클래스를 보면 응답 데이터 처리를 위한 많은 중복이 있다. 이 중복을 제거해 본다.
        - [ ]  응답을 보낼 때 HTML, CSS, 자바스크립트 파일을 직접 읽어 응답으로 보내는 메소드는 forward(), 다른 URL로 리다이렉트하는 메소드는 sendRedirect() 메소드를 나누어 구현한다.
        - [x]  응답 헤더 정보를 Map<String, String>으로 관리한다.
        - [x]  RequestHandler가 새로 추가한 HttpResponse를 사용하도록 리팩토링한다.
        - [x]  테스트 코드를 기반으로 구현한다.
    - [ ]  다형성을 활용해 클라이언트 요청 URL에 대한 분기 처리를 제거한다.
        - [ ]  각 요청과 응답에 대한 처리를 담당하는 부분을 추상화해 인터페이스로 만든다. 인터페이스는 다음과 같이 구현할 수 있다.
        - [ ]  각 분기문을 Controller 인터페이스를 구현하는(implements) 클래스를 만들어 분리한다.
        - [ ]  이렇게 생성한 Controller 구현체를 Map<String, Controller>에 저장한다. Map의 key에 해당하는 String은 요청 URL, value에 해당하는 Controller는 Controller 구현체이다.
        - [ ]  클라이언트 요청 URL에 해당하는 Controller를 찾아 service() 메소드를 호출한다.
        - [ ]  Controller 인터페이스를 구현하는 AbstractController 추상클래스를 추가해 중복을 제거하고, service() 메소드에서 GET과 POST HTTP 메소드에 따라 doGet(), doPost() 메소드를 호출하도록 한다.
- [ ]  다수의 사용자 요청에 대해 Queue 에 저장한 후 순차적으로 처리가 가능하도록 해야 한다.
- [ ]  서버가 모든 요청에 대해 Thread를 매번 생성하는 경우 성능상 문제가 발생할 수 있다. Thread Pool을 적용해 일정 수의 사용자 동시에 처리가 가능하도록 한다.
- [ ]  추가 요구사항이나 변경이 발생하는 경우를 고려한다.
    - [ ]  HTTP에서 POST 방식으로 데이터를 전달할 때 body를 통한 데이터 전달뿐만 아니라 Query String을 활용한 데이터 전달도 지원하도록 한다.

## 진행 방법
* 웹 애플리케이션 서버 요구사항을 파악한다.
* 요구사항에 대한 구현을 완료한 후 자신의 github 아이디에 해당하는 브랜치에 Pull Request(이하 PR)를 통해 코드 리뷰 요청을 한다.
* 코드 리뷰 피드백에 대한 개선 작업을 하고 다시 PUSH한다.
* 모든 피드백을 완료하면 다음 단계를 도전하고 앞의 과정을 반복한다.

## 우아한테크코스 코드리뷰
* [온라인 코드 리뷰 과정](https://github.com/woowacourse/woowacourse-docs/blob/master/maincourse/README.md)