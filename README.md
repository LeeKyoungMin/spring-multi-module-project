# 환급 시스템

# 요구사항

---

1) 요구사항 구현여부 : 구현완료

2) 구현 방법 : 하기에 기재하였습니다.

3) 검증결과 : 단위 테스트코드 완료 및 Swagger api 테스트 완료
<img width="642" alt="1" src="https://user-images.githubusercontent.com/22589581/227997257-a318c7e5-fcad-449e-b5bb-f6e64941cfb9.png">


# 환경구성

---

- java11, Spring Boot 2.7.10, gradle, h2, JPA

# 프로젝트 구성

---

<img width="210" alt="2" src="https://user-images.githubusercontent.com/22589581/227997256-4acdd80b-c765-493a-9a5a-fb639ba73c4c.png">

### 멀티모듈 구성도

<img width="561" alt="3" src="https://user-images.githubusercontent.com/22589581/227997252-77df1a94-83a9-444b-9501-33ee45fc58c0.png">

- **다음과 같이 멀티모듈로 구성하였습니다.**
- refund-user : 기능 요구사항 api서버 및 spring security 적용 도메인
- refund-common-repository : refund-common의 도메인 영역을 사용할 repository 도메인
- refund-common : refund 서비스의 도메인(entity, dto) 모듈
- refund-config : refund 서비스의 configuration (api white list, 회원가입 유저 list 관리, swagger, exception 처리 등)

# Swagger test

---

- swagger 주소 : [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

<img width="1599" alt="4" src="https://user-images.githubusercontent.com/22589581/227997240-dff79ed9-baf3-498e-8612-a5a0c292947c.png">


- api 테스트 순서

1) 회원가입 → 2) 로그인 → 3) Swagger의 Authorize 값에 로그인에서 발급한 accessToken 주입 → 4) 그 외 API 테스트

** 회원가입 후 패스워드 및 주민번호의 경우 암호화 된 값으로 API 요청하면 됩니다.

# H2 DDL (스크랩 api 및 세액조회 api용)

---

```sql
INSERT INTO SALARY (id, income_statement, total_payment, work_start_date, company, name, payment_date, work_end_date, reg_no, business_no)
VALUES ('1', '2022년 1월 ~ 12월', '10000000', '2022-01-01', '회사명', '홍길동', '2023-03-27', '2022-12-31', '860824-1655068', '123-45-67890');

INSERT INTO DEDUCTION (id, price, gubun, szs_user_id) VALUES 
('1', '5000', '보험료', '1'), 
('2', '10000', '교육비', '1'),
('3', '10000', '기부금', '1'),
('4', '10000', '의료비', '1'),
('5', '3000000', '퇴직연금', '1');

INSERT INTO TAX_AMOUNT (id, calculated_tax_amount, szs_user_id) VALUES 
('1', '10000', '1');
```

→ 회원가입 후 스크랩 api 및 세액조회 api 전에 해당 DDL 실행 후 API요청하면 됩니다.
