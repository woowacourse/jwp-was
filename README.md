# 웹 애플리케이션 서버

# 1단계 - HTTP 웹 서버 구현
- 정적 파일 제공 기능
  - stylesheet 파일 지원
- HTTP Request 처리 기능
  - POST 요청을 통한 회원가입 기능 구현
- redirect 기능
  - 회원가입 완료시 /index.html 페이지로 이동
- 리뷰 반영

# 2단계 - HTTP 웹 서버 리팩토링
- Thread pool을 사용하여 성능 개선
  - 사용자 요청은 순차적으로 처리
- 재사용 가능하도록 전체적인 리팩토링