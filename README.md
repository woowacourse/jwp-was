# 웹 애플리케이션 서버
## 진행 방법
* 웹 애플리케이션 서버 요구사항을 파악한다.
* 요구사항에 대한 구현을 완료한 후 자신의 github 아이디에 해당하는 브랜치에 Pull Request(이하 PR)를 통해 코드 리뷰 요청을 한다.
* 코드 리뷰 피드백에 대한 개선 작업을 하고 다시 PUSH한다.
* 모든 피드백을 완료하면 다음 단계를 도전하고 앞의 과정을 반복한다.

## 브랜치 관리 전략
* 각 feature 별 기능 구현 후 develop에 merge
* 모든 기능 구현 완료 후 develop에서 pr 보냄

## 기능 요구사항 - 1단계
1. http header의 path를 분리하여 해당 path의 파일을 응답.
2. Request Parameter 추출하기
3. post 요청 처리하기 (request body 추출하기)
4. redirection 기능 추가
5. Content-type 처리하기

## 기능 요구사항 - 2단계
1. 로그인
    * 로그인 실패 시 /user/login_failed.html로 이동
    * 로그인 성공 시 index.html로 이동
        * cookie를 활용해 로그인 상태 유지
        * 요청 header의 Cookie header 값에 로그인 상태 전달
2. http://localhost:8080/user/list로 접근했을 때
    * 로그인 상태일 경우 사용자 목록 출력
    * 로그인하지 않은 경우 로그인 페이지(login.html)로 이동
3. HttpSession 기능 구현
    * getId()
    * setAttribute(String name, Object value)
    * getAttribute(String name)
    * removeAttribute(String name)
    * invalidate()
    * 고유한 아이디를 가지는 세션 구현
    * 아이디는 쿠키를 활용해 공유

## 우아한테크코스 코드리뷰
* [온라인 코드 리뷰 과정](https://github.com/woowacourse/woowacourse-docs/blob/master/maincourse/README.md)