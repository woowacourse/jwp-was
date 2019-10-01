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
        - [x] 파일 확장자에 따른 Content-Type 셋팅 (일단은  css, javascript)
            - .png -> image/png
            - .jpg -> image/jpeg
            - .js -> application/javascript // EI 를 위해선 text/javascript 를 써야하는 것 같구먼
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
    - [ ] contentType 생각하기 (참고 - https://webhint.io/docs/user-guide/hints/hint-content-type/)
        - [ ] Accept 요구사항 맞추기 (ex. Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3)
            - [ ] 클라이언트에서 원하는 타입순으로 정리
                - [x] 일단 Accept 에서 첫번째 항목으로 적용
                - [x] 여러 개 처리 ("," 로 나눠짐 ";" 는 부가정보를 위함) 
                - [ ] q 를 통한 우선 순위 처리 (https://restfulapi.net/q-parameter-in-http-accept-header/)
                - [ ] charSet 처리 (일단은 UTF-8 로 적용하자)
                - [x] "*/*" 처리
            - [ ] 서버에서 지원할 수 있는 타입들
                - [x] 파일 확장자
                - [ ] 서버에 힌트를 주기, ex. 특정 정규표현식을 만족하는 경우 (*/js/*) (+)
            - [x] 클라이언트 - 서버 타입들에서 최적 타입 찾기 
        - [ ] 타입 살펴보고 body 에서 적용할 타입을 추려보기
        - [ ] 여러 타입을 지원하는 api 만들기(+) (ex. 특정 요청에 대해서 json 도 지원하고 xml 도 지원하는)
    - [ ] body 파악
        - [ ] body 를 완성하고 연관된 정보 헤더에 셋팅
            - [ ] Content-Length
            - [ ] Content-Type
            - [ ] Content-Encoding
        - [ ]
        - 엔터티를 어떻게 잘 보낼 것인지..
    - [ ] response 리팩토링
        - 그냥 여러... controller 를 먼저 짜보면 어떻게 해야 좋은 형태가 될지 감이 올듯하다..
        
- [x] 테스트
    - [x] httpRequestTest -> 행위를 고려해서 테스트 작성하기
    - [ ] 인수 테스트 작성 (or controller 테스트)

- [x] 쿠기 
    - [x] Cookie 만들기
        - [x] 헤더의 cookie 로 생성
        - [x] Set-Cookie 를 위한 출력
    - [x] 헤더에 path 설정 (https://docs.microsoft.com/en-us/windows/win32/wininet/http-cookies),
    
- [ ] 세션
    - [x] 주어진 인터페이스에 맞게 구현
        - [x] AbstractSession
            - Session 에서 공통된 부분 모아놓음
        - [x] MapSession
        - [ ] ConcurrentMapSession
    - [x] SessionManager: 세션을 생성하고 관리하는 역할
        - [x] 생성
        - [ ] 세션 만료? (생성된 세션이 언제 지워지면 될지 알아보자)
        - [x] invalidate 된 애들 관리
            - 일단 세션이 invalidate() 를 호출하면 SessionManager 에서 알 방법이 필요
                - 일단은 콜백함수 이용 
        - 시나리오
            - 클라이언트가 서버에 존재하지 않는, 만료 or invalidate 된 SESSION_ID 로 접근할 경우 (그 SESSION_ID로 쓰는 건 말이 안되는 듯, 이런 경우 어디서 SESSION_ID 를 수정해주어야 할까?)
                - SessionManager 에서 해당 SESSION_ID 에 대한 접근을 방지 (ex. Map 에서 해당 SESSION_ID 를 지우기)
                - 그러면 기존의 SESSION_ID 가 없다고 생각하고 새로운 세션을 만들어서 쓰게됨
    - [ ] 테스트
        - [ ] 세션 만료 (해당 시간만큼 기다리게 하고, 실제로 만료 되었는지)

- [ ] 애러처리
    - [ ] 에러 페이지 (for 클라이언트)
        - [ ] 익셉션과 애러코드 매칭
        - [ ] 해당 메세지 화면에 보여주기 (애러코드에 따른 에러페이지를 두어도 좋을)
        - [ ] 재미있는 그림 보여주기 (+ image/png 적용)
    - [ ] 에러 메시지 로깅
        - [ ] 파일에 남기기
        
 - [x] 템플릿이 적용된 페이지 처리
    - [x] /user/list 핸들바 사용해서 요청처리
    - [x] /user/profile 핸들바 사용해서 요청처리 (2페이지 정도는 동적으로 만들어야 공통된 로직을 빼고 싶을 것 같아서)
        - [x] 로그인 된 경우만 정보 보여주기, 아니면 login.html 로 리다이렉트
    - [x] tika 사용해서 mimeType 알아내기 (ex. .hbs 확장자 지원)
    - [ ] 템플릿 적용할 컨트롤러를 위해서 공통되는 로직 모으기


### 고려할 상황들 (추가되는 요구사항들?? 이렇게 요구사항을 추가해가면서 리팩토링 하면 좋을 듯..!!)
- [ ] 에러 상황 처리
    - 에러의 경우 파일에 로깅하기
- [x] post 에서 url 에도 파라미터가 존재하고, 바디에도 존재할경우 (Content-Type: application/x-www-form-urlencoded)
- [ ] json 형태로 요청이 올 경우 ()
- [ ] 압축을 해달라고 요청이 올 경우 (gzip 같은 경우 지원해보자..! 그리고 브라우저에서 확인하기)
    - 이 경우 효과가 있는지 elapsedTime 을 측정해봐도 좋을듯


### 구현 우선순위
- 세션 구현
- 애러처리
- PR 날리기
- 컨트롤러에서 중복제거
    - 어떻게 구현할지 감이 안와서 일단 어떤 부분이 어디 있으면 좋을지에 대한 고민을 많이 적용을 못했음

### 질문 드리고 싶은 부분
1. Optional 을 어떻게 사용하면 좋을지?? 
    - 현재는 해당 값이 없을 수도 있구나 (null 이 될 수도 있는지)를 명시적으로 표현해야 할 것 같은 곳에서 사용 중
        - 그런데 그만큼 사용하는데서 한 번은 벗겨내야해서 불편하기도?
            - exception 을 던지면 괜찮은데 불편한 경우가 있기도 합니다.. ex. if(!optionalObject.isPresent()) { object = optionalObject.get() .... return  무언가; } 


### 참고
- 아파치에서는 어떤것들을 지원해주는지 
    - 설정파일을 통해 살펴보기, https://www.sitepoint.com/htaccess-for-all/
- MimeType
    - https://developer.mozilla.org/ko/docs/Web/HTTP/Basics_of_HTTP/MIME_types
    - 파일에서 MimeType 알아내기, https://www.baeldung.com/java-file-mime-type
- 쿠키와 세션
    - https://docs.microsoft.com/en-us/windows/win32/wininet/http-cookies
    - https://docs.microsoft.com/en-us/windows/win32/wininet/http-sessions
- Content negotiation
    - 각 브라우저에서 Accept 헤더 기본 값, https://developer.mozilla.org/en-US/docs/Web/HTTP/Content_negotiation/List_of_default_Accept_values
    - https://developer.mozilla.org/en-US/docs/Web/HTTP/Content_negotiation

### 흐름?
결국엔 두 프로그램 간에서 서로 통신을 하기 위한?
그래서 각자가 서로가 원하는 것을 쉽게 이해할 수 있도록 규약을 맺은 것??
그렇다면 주고 받을 때 어떤 것에 집중해야 할까?

무엇을 전달할 것인지? (어떤 정보를 어떤 식으로?)

일단 내가 전달하고 싶은 타입들을 정해보아야 할 듯
그리고 그에 맞게 필요한 정보들이 무엇인지 확인 (먼가 사용하는 곳이 있어야 짜고 싶은 마음도 생기고 좀 더 에 맞는 설계가 나올 것 같음)



### 미뤄놓은 똥들..
Post에 body 가 없는 경우 어떻게 처리되는게 맞을지?
