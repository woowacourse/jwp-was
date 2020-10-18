# 웹 애플리케이션 서버

## 1단계 요구사항
1. [x] http://localhost:8080/index.html 로 접속했을 때 webapp 디렉토리의 index.html 파일을 읽어 클라이언트에 응답한다.
    - [x] BufferedReader.readLine() 메소드 활용해 라인별로 http header 읽는다.
    - [x] Request Line에서 path 분리한다.
    - [x] path에 해당하는 파일 읽어 응답한다.
2. [x] “회원가입” 메뉴를 클릭하면 http://localhost:8080/user/form.html 으로 이동하면서 회원가입할 수 있다. 회원가입한다.
    - [x] Header의 첫 번째 라인에서 요청 URL을 추출한다.
    - [x] 요청 URL에서 접근 경로와 이름=값을 추출해 User 클래스에 담는다.
3. [x] http://localhost:8080/user/form.html 파일의 form 태그 method를 get에서 post로 수정한 후 회원가입 기능이 정상적으로 동작하도록 구현한다.
4. [x] “회원가입”을 완료하면 /index.html 페이지로 이동하고 싶다. 현재는 URL이 /user/create 로 유지되는 상태로 읽어서 전달할 파일이 없다. 따라서 redirect 방식처럼 회원가입을 완료한 후 “index.html”로 이동해야 한다. 즉, 브라우저의 URL이 /index.html로 변경해야 한다.
5. [x] 지금까지 구현한 소스 코드는 stylesheet 파일을 지원하지 못하고 있다. Stylesheet 파일을 지원하도록 구현하도록 한다.

## 2단계 요구사항
1. [x] 다수의 사용자 요청에 대해 Queue 에 저장한 후 순차적으로 처리가 가능하도록 해야 한다.
    - [x] 서버가 모든 요청에 대해 Thread를 매번 생성하는 경우 성능상 문제가 발생할 수 있다. Thread Pool을 적용해 일정 수의 사용자 동시에 처리가 가능하도록 한다.
2. [x] HTTP 요청 Header/Body 처리, 응답 Header/Body 처리만을 담당하는 역할을 분리해 재사용 가능하도록 한다.

## 우아한테크코스 코드리뷰
* [온라인 코드 리뷰 과정](https://github.com/woowacourse/woowacourse-docs/blob/master/maincourse/README.md)