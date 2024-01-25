# InsureConnect
SpingBoot를 활용한 보험 중개 서비스

## 🖥️ 프로젝트 소개
보험 설계사와 보험 가입자를 연결해주는 웹 서비스

### 🧑 참여 인원
* 황인수
* 이한얼

### 🎬 프로젝트 기획 동기
1. 보험에 대해 무지하여 자신에게 필요한 보험의 카테고리에 대해 잘 모르는 사람이 많음
2. 기존 보험 설계는 다양한 설계사를 비교할 수 없어 설계사 선택에 제약 사항이 많음
3. 다양한 보험 회사에서 제공하는 보험 설계 서비스를 한 곳에서 볼 수 있는 서비스가 부재함
* 가입자 입장에서 자신이 필요한 보험이 무엇인지 손쉽게 파악하고 인기 설계사를 골라서 상담할 수 있는 중립적인 플랫폼에 대한 필요성이 대두되어 해당 프로젝트 기획 

### 🎯 프로젝트 목표 및 기대 효과
* Chat GPT와의 대화를 통해 자신에게 맞는 보험 카테고리를 알 수 있음
* 카테고리를 통해 보험 가입자가 자신에게 맞는 다양한 설계사를 확인 및 상담 요청할 수 있음
* 상담을 마친 후 남긴 리뷰를 통해 다음 가입자에게 설계사에 대한 정보를 남길 수 있음

### 📆 개발 기간
* 2024.01.02 ~ 2024.01.25

### ⚙️ 개발 환경
- `Java 17`
- **IDE** : IntelliJ
- **Framework** : SpringBoot 3.2.1
- **Featured Library** : OAuth 2.0 + JWT 0.9.1 + ModelMapper 3.1.1
- **Sevlet Engine** : Apache Tomcat 10.1.17
- **Database** : Postgresql 16
- **ORM** : Hibernate 6.4.1 + JPA 3.2.1
- **Web** : `HTML`(+thymeleaf) + `JavaScript`(+JQuery) + `CSS`(+bootstrap)

### 🌳 ERD
![earl - public](https://github.com/Insoo-Hwang/InsureConnect/assets/125466323/87d754b6-0c60-416d-81fc-4d7285280440)

## 🧐 프로젝트 특징

### 🛠️ 주요 기능
* 보험 가입자
1. Chat GPT를 활용하여 가입하고자 하는 보험 카테고리 확인
2. 보험 카테고리를 활용하여 설계사 검색 및 설계사 홍보글 확인
3. 자신에게 맞는 보험 설계사와 연결
4. 상담 후 설계사에 대한 리뷰 작성

* 보험 설계사
1. 자신을 나타낼 수 있는 홍보글 작성
2. 가입자가 첨부한 AI 채팅 내역 확인 후 상담

### 1. 메인 페이지
![screencapture-localhost-8080-2024-01-25-16_39_00](https://github.com/Insoo-Hwang/InsureConnect/assets/125466323/47674a52-e6a5-4060-bd61-6220b3e89559)

1. 인기 상담사 확인
2. 최근 리뷰 확인

### 2. 관리 페이지
<img width="1440" alt="스크린샷 2024-01-25 오후 3 54 16" src="https://github.com/Insoo-Hwang/InsureConnect/assets/125466323/e181fc96-e11d-4410-bcbd-cf59be4b4429">
<img width="726" alt="스크린샷 2024-01-25 오후 2 39 48" src="https://github.com/Insoo-Hwang/InsureConnect/assets/125466323/1e2bccfe-9c2d-48fb-a604-64540c8a9667">
<img width="1439" alt="스크린샷 2024-01-25 오후 2 08 55" src="https://github.com/Insoo-Hwang/InsureConnect/assets/125466323/7a24f155-8555-4a33-ab87-dd4993ebc343">

1. 설계사 신청 내역 확인 및 등록
2. 유저 관리
3. 리뷰 관리
4. 카테고리 관리

### 3. 설계사 관리 페이지
<img width="1440" alt="스크린샷 2024-01-25 오후 3 53 11" src="https://github.com/Insoo-Hwang/InsureConnect/assets/125466323/2fefeef8-f489-4aa0-810a-40be06f3ed2a">

1. 요청 상담자 AI채팅 내역 확인
2. 홍보글 관리

### 4. 마이 페이지
<img width="1437" alt="스크린샷 2024-01-25 오후 3 43 01" src="https://github.com/Insoo-Hwang/InsureConnect/assets/125466323/8c72a89a-d88f-4961-9971-b07090856932">

1. 내 정보 수정
2. 내가 쓴 리뷰 관리
3. 회원 탈퇴

### 5. AI 채팅
<img width="1440" alt="스크린샷 2024-01-25 오후 3 48 14" src="https://github.com/Insoo-Hwang/InsureConnect/assets/125466323/23b33442-2228-4b9e-99d6-b5e0b3b0fc24">

1. Chat GPT 기반 보험 정보 획득

### 6. 보험 설계사 홍보 페이지
<img width="1116" alt="스크린샷 2024-01-25 오후 3 38 53" src="https://github.com/Insoo-Hwang/InsureConnect/assets/125466323/27449ff8-7f1f-49c5-a3dd-500de04261e2">
<img width="1437" alt="스크린샷 2024-01-25 오후 3 52 10" src="https://github.com/Insoo-Hwang/InsureConnect/assets/125466323/b0e12b63-3ff2-429e-91b5-9a93b4ec36c6">

1. 가입자의 상황에 맞는 설계사 확인 및 상담 신청
2. 상담사 카카오톡 채널 연결

### 7. 리뷰 페이지
<img width="1325" alt="스크린샷 2024-01-25 오후 3 38 12" src="https://github.com/Insoo-Hwang/InsureConnect/assets/125466323/4ec3b5a6-ad94-426b-b240-e5168bd7a27a">

1. 전체 리뷰 확인

### ⚠️ 문제 발생 및 해결

### 📖 배운점
