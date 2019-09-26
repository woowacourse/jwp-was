### 해야 할 것들
- [ ] was 부분
    - [ ] 라우팅
        - [x] 어떤 정보로 컨트롤러를 찾을지 결정
        - [x] 컨트롤러 등록
        - [x] 컨트롤러 조회
        - [ ] 패턴 관련 조사
    - [x] thread pool
        - numThreads 는 몇개가 좋을까?
    - [ ] 경로에 따른 파일 전달 (파일이 존재하는지 확인하고 있으면 전달하자)
        - [x] 입력으로 들어온 경로에 파일이 존재하는지 확인 (어느 위치에서 이 역할을 해줄까? -> 컨트롤러도 설정)
        - [x] static + templates 경로
        - [ ] 파일 확장자에 따른 Content-Type 셋팅 (일단은  css, javascript)
            - .png -> image/png
            - .js -> application/javascript
            - .css -> text/css
            - .html -> text/html
        - [ ] 자바 프로그램 내부에서 경로를 어떻게 파악하는지 조사
        - [ ] classLoader + getResource
    - [ ] 어느 부분이 서버에서 공유되는 부분인지 (그렇다면... lock 처리를 해주어야 하는가?)
    - [ ] 테스트에서 파일시스템과 엮인 부분은 어떻게 처리하면 좋을지? (실제 경로에 테스트를 위한 파일을 만들기도 좀 거시기한 것 같아서...)
    
- [x] 프록시 적용..!
    - 접근 로그
    - 걸린 시간 측정
    
- [ ] http
    - [ ] statusCode
    - [ ] contentType 파악 
        - [ ] 타입 살펴보고 body 에서 적용할 타입을 추려보기
        - [ ] 여러 타입을 지원하는 api 만들기(+)
    - [ ] body 파악
        - [ ] body 를 완성하고 연관된 정보 헤더에 셋팅
            - [ ] Content-Length
            - [ ] Content-Type
            - [ ] Content-Encoding
        - [ ]
        - 엔터티를 어떻게 잘 보낼 것인지..
    - [ ] response 
        - 그냥 여러... controller 를 먼저 짜보면 어떻게 해야 좋은 형태가 될지 감이 올듯하다..
        
- [ ] 테스트
    - [x] httpRequestTest -> 행위를 고려해서 테스트 작성하기

- [ ] 쿠기 
    - [ ] 헤더에 path 설정 (https://docs.microsoft.com/en-us/windows/win32/wininet/http-cookies),


### 고려할 상황들 (추가되는 요구사항들?? 이렇게 요구사항을 추가해가면서 리팩토링 하면 좋을 듯..!!)
- [ ] 에러 상황 처리
    - 에러의 경우 파일에 로깅하기
- [x] post 에서 url 에도 파라미터가 존재하고, 바디에도 존재할경우 (Content-Type: application/x-www-form-urlencoded)
- [ ] json 형태로 요청이 올 경우 ()
- [ ] 압축을 해달라고 요청이 올 경우 (gzip 같은 경우 지원해보자..! 그리고 브라우저에서 확인하기)
    - 이 경우 효과가 있는지 elapsedTime 을 측정해봐도 좋을

### 흐름?
결국엔 두 프로그램 간에서 서로 통신을 하기 위한?
그래서 각자가 서로가 원하는 것을 쉽게 이해할 수 있도록 규약을 맺은 것??
그렇다면 주고 받을 때 어떤 것에 집중해야 할까?

무엇을 전달할 것인지? (어떤 정보를 어떤 식으로?)

일단 내가 전달하고 싶은 타입들을 정해보아야 할 듯
그리고 그에 맞게 필요한 정보들이 무엇인지 확인 (먼가 사용하는 곳이 있어야 짜고 싶은 마음도 생기고 좀 더 에 맞는 설계가 나올 것 같음)

### 미뤄놓은 똥들..
HttpResponse , HttpRequest 테스트 메소드 명 변경
Post에 body 가 없는 경우 어떻게 처리되는게 맞을지?
