
# 웹 애플리케이션 서버

## 1단계 요구사항 - HTTP 웹 서버 구현
1. http://localhost:8080/index.html 로 접속했을 때 webapp 디렉토리의 index.html 파일을 읽어 클라이언트에 응답한다.
2. “회원가입” 메뉴를 클릭하면 http://localhost:8080/user/form.html 으로 이동하면서 회원가입할 수 있다. 회원가입한다.
3. http://localhost:8080/user/form.html 파일의 form 태그 method를 get에서 post로 수정한 후 회원가입 기능이 정상적으로 동작하도록 구현한다.
4. “회원가입”을 완료하면 /index.html 페이지로 이동하고 싶다. 현재는 URL이 /user/create 로 유지되는 상태로 읽어서 전달할 파일이 없다. 따라서 redirect 방식처럼 회원가입을 완료한 후 “index.html”로 이동해야 한다. 즉, 브라우저의 URL이 /index.html로 변경해야 한다.
5. 지금까지 구현한 소스 코드는 stylesheet 파일을 지원하지 못하고 있다. Stylesheet 파일을 지원하도록 구현하도록 한다.
  
### http://localhost:8080/index.html 로 접속했을 때 webapp 디렉토리의 index.html 파일을 읽어 클라이언트에 응답한다.
    - 모든 Request Header를 출력한다.
    - Request Line에서 path를 분리한다.
    - path에 해당하는 파일을 읽어 응답한다.
    
### “회원가입” 메뉴를 클릭하면 http://localhost:8080/user/form.html 으로 이동하면서 회원가입할 수 있다. 회원가입한다.
    - HTML과 URL을 비교하여 사용자가 입력한 값을 파싱해 model.User 클래스에 저장한다.
    - Request Parameter를 추출한다.
     
### http://localhost:8080/user/form.html 파일의 form 태그 method를 get에서 post로 수정한 후 회원가입 기능이 정상적으로 동작하도록 구현한다.
    - Request Body의 값을 추출한다.
        
### “회원가입”을 완료하면 /index.html 페이지로 이동하고 싶다. 현재는 URL이 /user/create 로 유지되는 상태로 읽어서 전달할 파일이 없다. 따라서 redirect 방식처럼 회원가입을 완료한 후 “index.html”로 이동해야 한다. 즉, 브라우저의 URL이 /index.html로 변경해야 한다.
    - Redirect 후, Status Code를 302로 응답한다.
    
### 지금까지 구현한 소스 코드는 stylesheet 파일을 지원하지 못하고 있다. Stylesheet 파일을 지원하도록 구현하도록 한다.
    - Request Header에서 Content-Type을 처리한다.
    
## 2단계 요구사항 - HTTP 웹 서버 리팩토링
1. WAS 기능 요구사항
    - 다수의 사용자 요청에 대해 Queue에 저장한 후 순차적으로 처리가 가능하도록 해야 한다.
    - 서버가 모든 요청에 대해 Thread를 매번 생성하는 경우 성능상 문제가 발생할 수 있다. Thread Pool을 적용해 일정 수의 사용자를 동시에 처리가 가능하도록 한다.
2. 코드 리팩토링 요구사항
    - 메소드 및 클래스를 분리한다.
    - 클라이언트 요청 데이터를 처리하는 로직을 별도의 클래스로 분리한다.
    - 클라이언트 응답 데이터를 처리하는 로직을 별도의 클래스로 분리한다.
    - 다형성을 활용해 클라이언트 요청 URL에 대한 분기 처리를 제거한다.

### 메소드 및 클래스를 분리한다.
    - RequestHandler 클래스의 책임을 분리한다.
    - 요청 처리, 응답 처리, 요청 URL에 대한 분기 처리 제거 3개의 역할로 분리한다.

### 클라이언트 요청 데이터를 처리하는 로직을 별도의 클래스로 분리한다.
    - 클라이언트 요청 데이터를 담고 있는 InputStream을 생성자로 받아 HTTP 메소드, URL, 헤더, 본문을 분리하는 작업을 한다.
    - 헤더는 Map<String, String>에 저장해 관리하고 getHeader(“필드 이름”) 메소드를 통해 접근 가능하도록 구현한다.
    - GET과 POST 메소드에 따라 전달되는 인자를 Map<String, String>에 저장해 관리하고 getParameter(“인자 이름”) 메소드를 통해 접근 가능하도록 구현한다.
    - RequestHandler가 새로 추가한 HttpRequest를 사용하도록 리팩토링한다.

### 클라이언트 응답 데이터를 처리하는 로직을 별도의 클래스로 분리한다.
    - RequestHandler 클래스를 보면 응답 데이터 처리를 위한 많은 중복이 있다. 이 중복을 제거해 본다.
    - 응답 헤더 정보를 Map<String, String>으로 관리한다.
    - 응답을 보낼 때 HTML, CSS, 자바스크립트 파일을 직접 읽어 응답으로 보내는 메소드는 forward(), 다른 URL로 리다이렉트하는 메소드는 sendRedirect() 메소드를 나누어 구현한다.
    - RequestHandler가 새로 추가한 HttpResponse를 사용하도록 리팩토링한다.
    
### 다형성을 활용해 클라이언트 요청 URL에 대한 분기 처리를 제거한다.
    - 각 요청과 응답에 대한 처리를 담당하는 부분을 추상화해 인터페이스로 만든다.
    - 각 분기문을 Controller 인터페이스를 구현하는(implements) 클래스를 만들어 분리한다.
    - 이렇게 생성한 Controller 구현체를 Map<String, Controller>에 저장한다. Map의 key에 해당하는 String은 요청 URL, value에 해당하는 Controller는 Controller 구현체이다.
    - 클라이언트 요청 URL에 해당하는 Controller를 찾아 service() 메소드를 호출한다.
    - Controller 인터페이스를 구현하는 AbstractController 추상클래스를 추가해 중복을 제거하고, service() 메소드에서 GET과 POST HTTP 메소드에 따라 doGet(), doPost() 메소드를 호출하도록 한다.
    
## 진행 방법
* 웹 애플리케이션 서버 요구사항을 파악한다.
* 요구사항에 대한 구현을 완료한 후 자신의 github 아이디에 해당하는 브랜치에 Pull Request(이하 PR)를 통해 코드 리뷰 요청을 한다.
* 코드 리뷰 피드백에 대한 개선 작업을 하고 다시 PUSH한다.
* 모든 피드백을 완료하면 다음 단계를 도전하고 앞의 과정을 반복한다.

## 우아한테크코스 코드리뷰
* [온라인 코드 리뷰 과정](https://github.com/woowacourse/woowacourse-docs/blob/master/maincourse/README.md)