# 웹 애플리케이션 서버

이미 구현되어 사용하기 좋은 형태로 제공되는 WAS를 간단하게 재구현 해봄으로써, 아래와 같은 지식들을 학습하고자 합니다.
- 특정 개념/라이브러리/프레임워크에 대한 이해도를 높이기 위한 학습 목적 - Low Level 구현 학습
- 응용 애플리케이션 개발과는 다른 관점에서의 설계, 개발하는 역량 - Low Level API 설계 
- 내가 구현한 API를 사용하는 개발자를 배려하면서 구현하는 역량 - API 설계

## 요구사항 1
> http://localhost:8080/index.html 로 접속했을 때 webapp 디렉토리의 index.html 파일을 읽어 클라이언트에 응답한다.

### TO-DO
- [x] 모든 Request Header 출력하기
- [x] Request Line에서 path 분리하기
- [x] path에 해당하는 파일 읽어 응답하기

## 요구사항 2 
> “회원가입” 메뉴를 클릭하면 http://localhost:8080/user/form.html 으로 이동하면서 회원가입할 수 있다. 회원가입한다.
> 회원가입을 하면 다음과 같은 형태로 사용자가 입력한 값이 서버에 전달된다.

### TO-DO
- [x] Request Parameter 추출하기 
- [x] 추출한 파라미터를 통해, 회원 생성

## 요구사항 3
> http://localhost:8080/user/form.html 파일의 form 태그 method를 get에서 post로 수정한 후 회원가입 기능이 정상적으로 동작하도록 구현한다.

### TO-DO
- [x] Request Body의 값 추출하기

## 요구사항 4
> “회원가입”을 완료하면 /index.html 페이지로 이동하고 싶다. 현재는 URL이 /user/create 로 유지되는 상태로 읽어서 전달할 파일이 없다. 따라서 redirect 방식처럼 회원가입을 완료한 후 “index.html”로 이동해야 한다. 즉, 브라우저의 URL이 /index.html로 변경해야 한다.

### TO-DO

- [x] redirect

## 요구사항 5
> 지금까지 구현한 소스 코드는 stylesheet 파일을 지원하지 못하고 있다. Stylesheet 파일을 지원하도록 구현하도록 한다.

### TO-DO

- [x] Content-Type 처리 힌트
 
## 요구사항 2주차
> 1단계에서 구현한 코드를 리팩토링 한다.

### TO-DO
- [x] 쓰레드 풀 생성
- [x] Http request & response 역할 분담 
- [x] Handler mapping - 다형성을 통해 if문 제거하기
- [ ] 깨지는 테스트 수정
- [ ] Http response - 상태에 따라 다른 메소드를 수행하도록 변경
- [x] ModelAndView를 Servlet response 안으로 리팩토링 

## 진행 방법
* 웹 애플리케이션 서버 요구사항을 파악한다.
* 요구사항에 대한 구현을 완료한 후 자신의 github 아이디에 해당하는 브랜치에 Pull Request(이하 PR)를 통해 코드 리뷰 요청을 한다.
* 코드 리뷰 피드백에 대한 개선 작업을 하고 다시 PUSH한다.
* 모든 피드백을 완료하면 다음 단계를 도전하고 앞의 과정을 반복한다.

## 우아한테크코스 코드리뷰
* [온라인 코드 리뷰 과정](https://github.com/woowacourse/woowacourse-docs/blob/master/maincourse/README.md)