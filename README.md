# 웹 애플리케이션 서버
## 1단계 - HTTP 웹 서버 구현
### 요구사항1
> http://localhost:8080/index.html 로 접속했을 때 webapp 디렉토리의 index.html 파일을 읽어 클라이언트에 응답한다.
   
*구현 기능 목록*
- [x] Request Header를 파싱하여 원하는 정보 찾기
     - [x] 모든 Request header 출력하기
     - [x] Request에서 path 분리하기
- [x] path에 해당하는 파일 읽어 응답하기

### 요구사항2
> “회원가입” 메뉴를 클릭하면 http://localhost:8080/user/form.html 으로 이동하면서 회원가입할 수 있다.

*구현 기능 목록*
- [ ] Request Parameter 추출
- [ ] 사용자가 입력한 값 저장
