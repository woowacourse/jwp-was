# 웹 애플리케이션 서버

## 1단계 HTTP 웹 서버 구현

### 요구사항

- `http://localhost:8080/index.html` 로 접속했을 때 webapp 디렉토리의 index.html 파일을 읽어 클라이언트에 응답한다.
  
- “회원가입” 메뉴를 클릭하면 `http://localhost:8080/user/form.html` 으로 이동하면서 회원가입할 수 있다. 회원가입한다.
  
- `http://localhost:8080/user/form.html` 파일의 form 태그 method를 get에서 post로 수정한 후 회원가입 기능이 정상적으로 동작하도록 구현한다.
  
- “회원가입”을 완료하면 `/index.html` 페이지로 이동하고 싶다. 현재는 URL이 /user/create 로 유지되는 상태로 읽어서 전달할 파일이 없다. 따라서 redirect 방식처럼 회원가입을 완료한 후 “index.html”로 이동해야 한다. 즉, 브라우저의 URL이 /index.html로 변경해야 한다.

- Stylesheet 파일 지원

## 2단계 HTTP 웹 서버 리팩터링

- 다수의 사용자 요청에 대해 Queue에 저장한 후 순차적으로 처리가 가능하도록 해야 한다.

- 서버가 모든 요청에 대해 Thread를 매번 생성하는 경우 성능상 문제가 발생할 수 있다. Thread Pool을 적용해 일정 수의 사용자 동시에 처리가 가능하도록 한다.

- HTTP 요청 Header/Body 처리, 응답 Header/Body 처리만을 담당하는 역할을 분리해 재사용 가능하도록 한다.

- 코드 리팩터링을 한다.

## 3단계 로그인 및 세션 구현

#### 요구사항 1

- “로그인” 메뉴를 클릭하면 http://localhost:8080/user/login.html 으로 이동해 로그인할 수 있다.

- 로그인이 성공하면 index.html로 이동하고, 로그인이 실패하면 /user/login_failed.html로 이동해야 한다.

- 앞에서 회원가입한 사용자로 로그인할 수 있어야 한다.
 
- 로그인이 성공하면 cookie를 활용해 로그인 상태를 유지할 수 있어야 한다.

- 로그인이 성공할 경우 요청 header의 Cookie header 값이 logined=true, 로그인이 실패하면 Cookie header 값이 logined=false로 전달되어야 한다.

#### 요구사항 2

- 접근하고 있는 사용자가 “로그인” 상태일 경우(Cookie 값이 logined=true) 경우 http://localhost:8080/user/list 로 접근했을 때 사용자 목록을 출력한다.

- 만약 로그인하지 않은 상태라면 로그인 페이지(login.html)로 이동한다.

- 동적으로 html을 생성하기 위해 handlebars.java template engine을 활용한다.
  
#### 요구사항 3

- 서블릿에서 지원하는 HttpSession API의 일부를 지원해야 한다.

- HttpSession API 중 구현할 메소드는 getId(), setAttribute(String name, Object value), getAttribute(String name), removeAttribute(String name), invalidate() 5개이다.

    - HttpSession의 가장 중요하고 핵심이 되는 메소드이다.
  
    - 각 메소드의 역할은 다음과 같다.
  
        - String getId(): 현재 세션에 할당되어 있는 고유한 세션 아이디를 반환
        
        - void setAttribute(String name, Object value): 현재 세션에 value 인자로 전달되는 객체를 name 인자 이름으로 저장
        
        - Object getAttribute(String name): 현재 세션에 name 인자로 저장되어 있는 객체 값을 찾아 반환
        
        - void removeAttribute(String name): 현재 세션에 name 인자로 저장되어 있는 객체 값을 삭제
        
        - void invalidate(): 현재 세션에 저장되어 있는 모든 값을 삭제
  
- 세션은 클라이언트와 서버 간에 상태 값을 공유하기 위해 고유한 아이디를 활용하고, 이 고유한 아이디는 쿠키를 활용해 공유한다.

## 4단계 MULTI MODULE 적용

- HTTP 웹 서버 미션은 HTTP request, response를 파싱, 쿠키/세션 처리, Thread Pool과 같이 Web Application Server(이하 WAS) 본연의 기능을 담당하는 부분과 이 WAS를 기반으로 사용자 관리라는 응용 애플리케이션을 구현하는 부분으로 나뉜다.

    - 이 두 가지 영역은 성격이 완전히 다른 영역이기 때문에 분리하는 것이 향후 유지보수나 변화가 발생했을 때 빠르게 대응하는 것이 가능할 것으로 판단된다.

    - 따라서 이 두 영역을 gradle의 multi module 개념을 적용해 서로 다른 프로젝트로 분리한다.
