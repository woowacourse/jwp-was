### TODO
- http request , response


### 리팩토링 할 것들
- [ ] was 부분
    - [ ] 라우팅 등록
        - [ ]
    - [ ] 라우팅 적용
    - [ ] thread pool
    - [ ] 경로에 따른 파일 전달 (파일이 존재하는지 확인하고 있으면 전달하자)
        - [ ] 상대 + 절대 경로 
        - [ ] static + templates 경로
    - [ ] 어느 부분이 서버에서 공유되는 부분인지 (그렇다면... lock 처리를 해주어야 하는가?)
    
- [x] 로깅 프록시 적용..!
    

### 미뤄놓은 똥들..
HttpResponse , HttpRequest 테스트 메소드 명 변경
Post에 body 가 없는 경우 어떻게 처리되는게 맞을지?
