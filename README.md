# 웹 애플리케이션 서버

## 요구사항 1
- [x] index.html 요청을 보냈을 때, webapp 디렉토리의 index.html 파일을 읽어 클라이언트에 응답한다.
    - [x] BufferedReader로 InputStream을 읽는다.
    - [x] 공백을 기준으로 파싱하여 요청한 URL을 추출한다.
    - [x] loadFileFromClasspath 메서드에 정확한 자원의 경로를 전달하여 요청한 파일 정보를 받는다.

## 요구사항 2
- [x] GET 요청으로 회원가입을 한다.
    - [x] 요청된 URL에서 파라미터를 추출한다.
    - [x] User 객체를 만들어 저장한다.
    
## 요구사항 3
- [x] POST 요청으로 회원가입한다.
    - [x] RequestBody의 값을 추출한다.
    - [x] User 객체를 만들어 저장한다.
    
## 요구사항 4
- [x] 회원가입 후 index.html 페이지로 이동한다.
    - [x] status code 302를 통해 이동한다.
    
## 요구사항 5
- [x] stylesheet 파일을 지원하도록 한다.

## 진행 방법
* 웹 애플리케이션 서버 요구사항을 파악한다.
* 요구사항에 대한 구현을 완료한 후 자신의 github 아이디에 해당하는 브랜치에 Pull Request(이하 PR)를 통해 코드 리뷰 요청을 한다.
* 코드 리뷰 피드백에 대한 개선 작업을 하고 다시 PUSH한다.
* 모든 피드백을 완료하면 다음 단계를 도전하고 앞의 과정을 반복한다.

## 우아한테크코스 코드리뷰
* [온라인 코드 리뷰 과정](https://github.com/woowacourse/woowacourse-docs/blob/master/maincourse/README.md)