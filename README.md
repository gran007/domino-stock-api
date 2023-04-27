## Domino Stock Api

### 실행방법
1. docker compose를 실행합니다
   - docker compose up -d

2. http 폴더에 domino.http를 실행해서 결과를 볼수 있습니다.

- docker-compose에 mongodb와 jib으로 docker에 업로드한 된 프로그램의 설정을 넣어두었습니다
- 만약 개인 저장소에 업로드된 jib container의 다운로드가 안되면 다음 절차로 실행해주세요 
  - docker compose에 domino-stock-api 항목을 제거 
  - docker compose up -d 명령을 실행 
  - local에서 실행

### swagger 
    http://localhost:8080/swagger-ui

### API 
    삼성 주식 정보를 가져옵니다
    http://localhost:8080/domino/samsung/{interval}/{totalDays}

``example: http://localhost/domino/samsung/1/5``

