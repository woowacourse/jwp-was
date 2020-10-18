# 웹 애플리케이션 서버
## 진행 방법
* 웹 애플리케이션 서버 요구사항을 파악한다.
* 요구사항에 대한 구현을 완료한 후 자신의 github 아이디에 해당하는 브랜치에 Pull Request(이하 PR)를 통해 코드 리뷰 요청을 한다.
* 코드 리뷰 피드백에 대한 개선 작업을 하고 다시 PUSH한다.
* 모든 피드백을 완료하면 다음 단계를 도전하고 앞의 과정을 반복한다.

## 1단계 HTTP 웹 서버 구현

### 요구사항 1

- http://localhost:8080/index.html 로 접속했을 때 webapp 디렉토리의 index.html 파일을 읽어 클라이언트에 응답한다.

### 요구사항 2

- “회원가입” 메뉴를 클릭하면 http://localhost:8080/user/form.html 으로 이동하면서 회원가입할 수 있다. 회원가입한다.

  회원가입을 하면 다음과 같은 형태로 사용자가 입력한 값이 서버에 전달된다.

### 요구사항 3

- http://localhost:8080/user/form.html 파일의 form 태그 method를 get에서 post로 수정한 후 회원가입 기능이 정상적으로 동작하도록 구현한다.

### 요구사항 4

- “회원가입”을 완료하면 /index.html 페이지로 이동하고 싶다. 현재는 URL이 /user/create 로 유지되는 상태로 읽어서 전달할 파일이 없다. 따라서 redirect 방식처럼 회원가입을 완료한 후 “index.html”로 이동해야 한다. 즉, 브라우저의 URL이 /index.html로 변경해야 한다.

### 요구사항 5

- 지금까지 구현한 소스 코드는 stylesheet 파일을 지원하지 못하고 있다. Stylesheet 파일을 지원하도록 구현하도록 한다.

## 2단계 - HTTP 웹 서버 리팩토링

### 웹 애플리케이션 서버(이하 WAS) 요구사항
  
- 앞 단계에서 구현한 코드는 WAS 기능, HTTP 요청/응답 처리, 개발자가 구현할 애플리케이션 기능이 혼재되어 있다.
  이와 같이 여러 가지 역할을 가지는 코드가 혼재되어 있으면 재사용하기 힘들다.
  
- 각각의 역할을 분리해 재사용 가능하도록 개선한다.
  즉, WAS 기능, HTTP 요청/응답 처리 기능은 애플리케이션 개발자가 신경쓰지 않아도 재사용이 가능한 구조가 되도록 한다.

### WAS 기능 요구사항
- 다수의 사용자 요청에 대해 Queue 에 저장한 후 순차적으로 처리가 가능하도록 해야 한다.
  서버가 모든 요청에 대해 Thread를 매번 생성하는 경우 성능상 문제가 발생할 수 있다. Thread Pool을 적용해 일정 수의 사용자 동시에 처리가 가능하도록 한다.

## 3단계 - 로그인 및 세션 구현

### 요구사항 1
    
    “로그인” 메뉴를 클릭하면 http://localhost:8080/user/login.html 으로 이동해 로그인할 수 있다. 
    로그인이 성공하면 index.html로 이동하고, 로그인이 실패하면 /user/login_failed.html로 이동해야 한다.
    
    앞에서 회원가입한 사용자로 로그인할 수 있어야 한다. 
    로그인이 성공하면 cookie를 활용해 로그인 상태를 유지할 수 있어야 한다. 
    로그인이 성공할 경우 요청 header의 Cookie header 값이 logined=true, 로그인이 실패하면 Cookie header 값이 logined=false로 전달되어야 한다.

### 요구사항 2

    접근하고 있는 사용자가 “로그인” 상태일 경우(Cookie 값이 logined=true) 경우 http://localhost:8080/user/list 로 접근했을 때 사용자 목록을 출력한다. 
    만약 로그인하지 않은 상태라면 로그인 페이지(login.html)로 이동한다.
    
    동적으로 html을 생성하기 위해 handlebars.java template engine을 활용한다.
    
### 요구사항 3

    서블릿에서 지원하는 HttpSession API의 일부를 지원해야 한다.
    HttpSession API 중 구현할 메소드는 getId(), setAttribute(String name, Object value), getAttribute(String name), removeAttribute(String name), invalidate() 5개이다. 
    HttpSession의 가장 중요하고 핵심이 되는 메소드이다.
