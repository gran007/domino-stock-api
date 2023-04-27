## Domino Stock Api

### 실행방법
1. spring boot와 mongodb간의 통신을 위해 다음 네트워크를 만들어주세요
   - docker network create docker-network
2. docker compose를 실행합니다
   - docker compose up -d

- docker-compose에 mongodb와 jib으로 docker에 업로드한 된 프로그램을 넣어두었습니다
- 만약 개인 저장소에 업로드된 jib container의 다운로드가 안되면 다음 절차로 실행해주세요 
  - docker compose에 domino-stock-api 항목을 제거 
  - docker compose up명령을 실행 
  - local에서 실행

### swagger 
    http://localhost:8080/swagger-ui

### API 
    삼성 주식 정보를 가져옵니다
    http://localhost:8080/domino/samsung/{interval}/{totalDays}

``example: http://localhost/domino/samsung/1/5``

