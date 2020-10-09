# 웹 애플리케이션 서버
## 진행 방법
* 웹 애플리케이션 서버 요구사항을 파악한다.
* 요구사항에 대한 구현을 완료한 후 자신의 github 아이디에 해당하는 브랜치에 Pull Request(이하 PR)를 통해 코드 리뷰 요청을 한다.
* 코드 리뷰 피드백에 대한 개선 작업을 하고 다시 PUSH한다.
* 모든 피드백을 완료하면 다음 단계를 도전하고 앞의 과정을 반복한다.

## 기능 목록

### 🚀 1단계 - HTTP 웹 서버 구현

- [X] http://localhost:8080/index.html 로 접속했을 때 webapp 디렉토리의 index.html 파일을 읽어 클라이언트에 응답한다.
    - [X] 모든 Request Header를 읽어온다.
    - [X] Request Line에서 path를 분리한다.
    - [X] path에 해당하는 파일 읽어 응답한다.
- [X] 회원가입 메뉴를 클릭하면 http://localhost:8080/user/form.html 으로 이동하면서 회원가입할 수 있다.
    - [X] Request Parameter를 추출한다.
    - [X] 사용자가 입력한 값을 파싱해 model.User 클래스에 저장한다.
- [X] 회원가입 기능이 정상적으로 동작하도록 구현한다.
    - [X] http://localhost:8080/user/form.html 파일의 form 태그 method를 get에서 post로 수정한다.
    - [X] Reuqest Body를 읽어온다.
    - [X] User 객체를 생성한다.
- [X] 회원가입을 완료하면 /index.html 페이지로 이동한다.
    - [X] 응답 헤더의 상태 코드로 302를 사용하여 리다이렉트 한다.
- [X] stylesheet 파일을 지원하도록 구현한다.
    - [X] Stylesheet인 경우 응답 헤더의 Content-Type을 text/css로 전송한다.

### 🚀 2단계 - HTTP 웹 서버 리팩토링

- WAS
    - [X] 다수의 사용자 요청에 대해 Queue 에 저장한 후 순차적으로 처리한다.
    - [X] 매번 thread를 만들지 않고 thread pool을 이용한다.

- HTTP 요청/응답 처리
    - [X] 요청 Header/Body 처리 역할을 분리한다.
    - [X] 응답 Header/Body 처리 역할을 분리한다.

- 코드 리팩토링
    - [X] RequestHandler 클래스의 책임을 분리한다.
        - [X] 요청을 처리할 수 있는 객체를 찾는 기능을 분리한다.
        - [X] 요청을 처리하는 기능을 분리한다.

## 우아한테크코스 코드리뷰
* [온라인 코드 리뷰 과정](https://github.com/woowacourse/woowacourse-docs/blob/master/maincourse/README.md)
