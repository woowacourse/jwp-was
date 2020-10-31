# 웹 애플리케이션 서버

## 2단계 - HTTP 웹 서버 리팩토링
앞 단계에서 구현한 코드는 WAS 기능, HTTP 요청/응답 처리, 개발자가 구현할 애플리케이션 기능이 혼재되어 있다.
이와 같이 여러 가지 역할을 가지는 코드가 혼재되어 있으면 재사용하기 힘들다.
각각의 역할을 분리해 재사용 가능하도록 개선한다.
즉, WAS 기능, HTTP 요청/응답 처리 기능은 애플리케이션 개발자가 신경쓰지 않아도 재사용이 가능한 구조가 되도록 한다.
### 요구 사항
##### 1. 1단계 피드백
  - [x] 사용자로부터 전달 받은(Query String, Request Body) 정보에 중복 key 값이 있는 경우 처리
  - [x] 변수의 타입 인터페이스로 수정하기
  - [x] HTTP 메서드를 상수로 관리하기
  - [ ] API 요청을 처리하는 부분에 대한 확장 고려하기 - doGet(request), doPost(request) 등을 갖는 interface를 구현 등
  - [x] 404, 405 등에 대한 처리 로직 구현하기
  - [x] equals()로 비교하는 문자열의 순서를 바꾸어서 NPE 방지하기
  - [x] Map으로 관리하는 query Params 를 일급 컬렉션으로 수정하기
##### 2. WAS 요구 사항
  - [x] 다수의 사용자 요청에 대해 Queue 에 저장한 후 순차적으로 처리가 가능하도록 해야 한다.
  - [x] 서버가 모든 요청에 대해 Thread를 매번 생성하는 경우 성능상 문제가 발생할 수 있다. Thread Pool을 적용해 일정 수의 사용자 동시에 처리가 가능하도록 한다.(java.util.concurrent.Executors에서 제공하는 api 활용)
##### 3. HTTP 요청/응답 처리 기능
  - [ ] HTTP 요청 Header/Body 처리, 응답 Header/Body 처리만을 담당하는 역할을 분리해 재사용 가능하도록 한다.      


## 1단계 - HTTP 웹 서버 구현
### 요구 사항
- [x] http://localhost:8080/index.html 로 접속했을 때 templatkes 디렉토리의 index.html 파일을 읽어 클라이언트에 응답한다
  - [x] Request Header 읽는다.
  - [x] Request Header에서 path 추출한다.
  - [x] path에 해당하는 파일을 찾아 읽고 응답한다.
- [x] “회원가입” 메뉴를 클릭하면 http://localhost:8080/user/form.html 으로 이동하면서 회원가입할 수 있다. 회원가입을 하면 다음과 같은 형태로 사용자가 입력한 값이 서버에 전달된다.
```text
/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net
```
  - [x] HTML과 URL을 비교해 보고 사용자가 입력한 값을 파싱한다.
  - [x] 파싱한 값을 model.User 클래스에 저장한다.
  - [x] 파싱한 User를 DataBase에 저장한다.
- [x] http://localhost:8080/user/form.html 파일의 form 태그 method를 get에서 post로 수정한 후 회원가입 기능이 정상적으로 동작하도록 구현한다.
  - [x] form.html 파일의 form 태그 method를 get에서 post로 수정한다.
  - [x] 요청 정보에서 Method를 추출한다.
  - [x] Body에 담긴 파라미터를 추출해서 User 클래스에 저장한다.
- [x] “회원가입”을 완료하면 /index.html 페이지로 이동하고 싶다. 현재는 URL이 /user/create 로 유지되는 상태로 읽어서 전달할 파일이 없다. 따라서 redirect 방식처럼 회원가입을 완료한 후 “index.html”로 이동해야 한다. 즉, 브라우저의 URL이 /index.html로 변경해야 한다.
- [x] 지금까지 구현한 소스 코드는 stylesheet 파일을 지원하지 못하고 있다. Stylesheet 파일을 지원하도록 구현하도록 한다.
  - [x] static 파일을 요청하는 경우인지 판단한다.
  - [x] 응답 헤더에  Content-Type을 추가한다.