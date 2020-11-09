# 웹 애플리케이션 서버

## 요구사항 1
- [x] 로그인 성공 시 `index.html`로 이동하고, 로그인 실패 시 `/user/login_failed.html`로 이동한다. 
- [x] 로그인 성공 시 cookie를 활용하여 로그인 상태를 유지해야 한다.
- [x] 로그인이 성공할 경우 요청 header의 Cookie header 값이 logined=true, 로그인이 실패하면 Cookie header 값이 logined=false로 전달되어야 한다.

## 요구사항 2
- [x] 로그인 상태인 경우 사용자 목록을 보여주고, 로그인 상태가 아니면 `login.html`로 이동한다.
- [x] 동적으로 html을 생성하기 위해 handlebars.java template engine을 활용한다.

## 요구사항 3
- [x] 서블릿에서 지원하는 HttpSession API의 일부를 지원한다.
    - [x] getId() 메서드를 구현한다.
    - [x] setAttribute(String name, Object value) 메서드를 구현한다.
    - [x] getAttribute(String name) 메서드를 구현한다.
    - [x] removeAttribute(String name) 메서드를 구현한다.
    - [x] invalidate() 메서드를 구현한다.

## 진행 방법
* 웹 애플리케이션 서버 요구사항을 파악한다.
* 요구사항에 대한 구현을 완료한 후 자신의 github 아이디에 해당하는 브랜치에 Pull Request(이하 PR)를 통해 코드 리뷰 요청을 한다.
* 코드 리뷰 피드백에 대한 개선 작업을 하고 다시 PUSH한다.
* 모든 피드백을 완료하면 다음 단계를 도전하고 앞의 과정을 반복한다.

## 우아한테크코스 코드리뷰
* [온라인 코드 리뷰 과정](https://github.com/woowacourse/woowacourse-docs/blob/master/maincourse/README.md)