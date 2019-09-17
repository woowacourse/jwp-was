# 웹 애플리케이션 서버

## 요구사항

* `http://localhost:8080/index.html`로 접속했을 때, `index.html` 파일을 읽어 응답으로 보낸다.
* 정적 파일 요청에 대해서 `/static` 디렉터리 내의 파일을 찾아 응답으로 보낸다.
* 메인 페이지에서 `/user/form.html`로 이동할 수 있어야 하며, 폼의 POST 요청을 받아 `User` 클래스에 저장한다. 