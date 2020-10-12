# 웹 애플리케이션 서버

## 진행 방법
* 웹 애플리케이션 서버 요구사항을 파악한다.
* 요구사항에 대한 구현을 완료한 후 자신의 github 아이디에 해당하는 브랜치에 Pull Request(이하 PR)를 통해 코드 리뷰 요청을 한다.
* 코드 리뷰 피드백에 대한 개선 작업을 하고 다시 PUSH한다.
* 모든 피드백을 완료하면 다음 단계를 도전하고 앞의 과정을 반복한다.

## 1단계 요구사항

1. [X] http://localhost:8080/index.html 로 접속했을 때 index.html 파일을 읽어 클라이언트에 응답한다.
2. [X] "회원가입" 메뉴를 클릭하면 http://localhost:8080/user/form.html 으로 이동하면서 회원가입할 수 있게 한다.
3. [X] 사용자가 입력한 값을 파싱해 User 클래스에 저장한다.
4. [X] POST 방식으로 회원가입 기능이 정상적으로 동작하도록 구현한다.
5. [X] "회원가입"을 완료하면 redirect해서 /index.html 페이지로 이동한다.
6. [X] Stylesheet 파일을 지원하도록 구현한다.

## 2단계 요구사항 (리펙토링)

1. [X] Thread Pool을 적용해 일정 수의 사용자 동시에 처리가 가능하도록 한다.
2. [X] 코드 리펙토링
    1. [X] RequestHandler 클래스의 책임을 분리한다.
    2. [X] 클라이언트 요청 데이터를 처리하는 로직을 별도의 클래스로 분리한다.
    3. [X] 클라이언트 응답 데이터를 처리하는 로직을 별도의 클래스로 분리한다.
    4. [X] 다형성을 활용해 클라이언트 요청 URL에 대한 분기 처리를 제거한다.

## 3단계 요구사항
1. [ ] 로그인이 성공하면 index.html, 로그인이 실패하면 /user/login_failed.html
2. [ ] 회원가입한 사용자로 로그인할 수 있어야 함
    1. [ ] 로그인이 성공하면 logined=true, 실패하면 logined=false
3. [ ] http://localhost:8080/user/list로 접근했을 때 로그인 상태면 사용자 목록 출력, 로그인 상태가 아니라면 login.html
4. [ ] HttpSession API 지원


## 우아한테크코스 코드리뷰
* [온라인 코드 리뷰 과정](https://github.com/woowacourse/woowacourse-docs/blob/master/maincourse/README.md)
