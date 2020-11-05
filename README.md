# 웹 애플리케이션 서버

## 기능요구사항
### 1단계
#### 요구사항 1
- [x] http://localhost:8080/index.html 로 접속했을 때 webapp 디렉토리의 index.html 파일을 읽어 클라이언트에 응답한다.

#### 요구사항 2
- [x] “회원가입” 메뉴를 클릭하면 http://localhost:8080/user/form.html 으로 이동하면서 회원가입할 수 있다.

    회원가입을 하면 다음과 같은 형태로 사용자가 입력한 값이 서버에 전달된다.

    ```http request
    /create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net
    ```
- [x] HTML과 URL을 비교해 보고 사용자가 입력한 값을 파싱해 model.User 클래스에 저장한다.

#### 요구사항 3
- [x] http://localhost:8080/user/form.html 파일의 form 태그 method를 get에서 post로 수정한 후 회원가입 기능이 정상적으로 동작하도록 구현한다.

#### 요구사항 4
- [x] “회원가입”을 완료하면 /index.html 페이지로 이동하고 싶다. 현재는 URL이 /user/create 로 유지되는 상태로 읽어서 전달할 파일이 없다. 따라서 redirect 방식처럼 회원가입을 완료한 후 “index.html”로 이동해야 한다. 즉, 브라우저의 URL이 /index.html로 변경해야 한다.

#### 요구사항 5
- [x] 지금까지 구현한 소스 코드는 stylesheet 파일을 지원하지 못하고 있다. Stylesheet 파일을 지원하도록 구현하도록 한다.


### 2단계
#### 요구사항
- [x] ThreadPoolExecutor를 적용해 일정 수의 사용자 동시에 처리가 가능하도록 한다.
- [x] HTTP 응답 Header/Body 처리만을 담당하는 역할을 분리
- [x] Servlet을 Controller로 변경

### 3단계
#### 요구사항 1
- [x] "로그인" 메뉴를 클릭하면 http://localhost:8080/user/login.html 으로 이동한다.
    - [x] 로그인 성공 시
        - [x] index.html로 이동
        - [x] 요청 header의 Cookie header 값이 logined=true로 전달된다.
    - [x] 로그인 실패 시
        - [x] /user/login_failed.html로 이동
        - [x] 요청 header의 Cookie header 값이 logined=false로 전달된다.

#### 요구사항 2
- [ ] http://localhost:8080/user/list로 접근했을 때
    - [ ] 로그인 상태(`Cookie: logined=true`)이면 사용자 목록을 출력한다.
    - [x] 로그인하지 않은 상태이면 로그인 페이지(login.html)로 이동한다.
    
#### 요구사항 3
- [ ] HttpSession API 중 getId(), setAttribute(String name, Object value), getAttribute(String name), removeAttribute(String name), invalidate() 를 구현한다.