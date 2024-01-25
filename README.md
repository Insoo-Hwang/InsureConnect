# InsureConnect
SpingBoot를 활용한 보험 중개 서비스

## 🖥️ 프로젝트 소개
보험 설계사와 보험 가입자를 연결해주는 웹 서비스

### 🧑 참여 인원
* [황인수](https://github.com/Insoo-Hwang)
  - 사용자, 관리자 관련 페이지 및 OAuth2.0 및 JWT관련 파트 제작
* [이한얼](https://github.com/Machrie)
  - 설계사, 리뷰 관련 페이지 및 ModelMapper관련 파트 제작

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
- **Language** : `Java 17`
- **IDE** : `IntelliJ`
- **Framework** : `SpringBoot 3.2.1`
- **Featured Library** : `OAuth 2.0` + `JWT 0.9.1` + `ModelMapper 3.1.1`
- **Sevlet Engine** : `Apache Tomcat 10.1.17`
- **Database** : `Postgresql 16`
- **ORM** : `Hibernate 6.4.1` + `JPA 3.2.1`
- **Web** : `HTML`(+`thymeleaf`) + `JavaScript`(+`JQuery`) + `CSS`(+`bootstrap`)

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
<img width="1440" alt="스크린샷 2024-01-25 오후 7 16 32" src="https://github.com/Insoo-Hwang/InsureConnect/assets/125466323/51bd9646-a9f7-4b34-a50f-b19b2a922f30">

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
#### 1. 인기 설계사 선정 및 호출 문제
* 문제 파악
  - 인기 설계사 선정
    + 단순 리뷰 점수의 합으로 나열하기에는 최근에 시작한 설계사가 불리한 면이 있음
    + 리뷰 평균 점수로 나열하기에는 보험 가입자의 피드백을 수정받아 더 좋은 서비스를 제공해도 과거의 낮은 점수의 리뷰가 발목을 잡을 수 있음
  - 인기 설계사 계산 함수 호출
    + 메인 페이지에 접속할 때마다 인기 설계사 계산 함수 호출시 서버에 과부하가 걸릴 가능성이 있음 
* 문제 해결
  - 인기 설계사 선정
    + ~~최근 1년 리뷰만 반영 후 평균 점수로 나열~~ -> 최근의 리뷰와 1년 전 리뷰가 같은 가치를 갖는 것은 2번 문제점을 해결하지 못함
    + ~~기간별로 가중치를 두어 최근의 리뷰가 더 높은 점수를 받도록 조정~~ -> 리뷰 100개 평균 점수 4.8인 설계사보다 리뷰 1개 평균 점수 5.0인 설계사가 더 높은 점수를 받는 문제 발생
    + **기간별로 가중치를 두고 평균 점수를 구한 후 전체 리뷰 수에 대한 점수 추가 부여 -> 최근 리뷰에 대한 가치 상승 및 평균의 함정 해결 가능**
  - 인기 설계사 계산 함수 호출
    + ~~리뷰가 추가될 때마다 점수 계산~~ -> 실시간으로 점수 반영이 가능하나 리뷰 작성 사용자 수가 많을 경우 서버 과부하 문제 해결 불가
    + **1시간마다 한번씩 점수 계산 -> 실시간 점수 반영이 불가능하나 1시간에 많은 상담을 진행하지 못한다는 상황을 고려하여 점수 실시간 반영보다 과부하 방지가 더 큰 이점이 있다고 판단**
* 해결 과정
  - 인기 설계사 선정
    + ~~기간에 따른 선형적 가중치 값 활용~~ -> 단순하여 합리적인 점수 부여가 불가능하다고 판단 
    + **가중치 값으로 망각 곡선의 망각률 활용 -> 상담에 대한 기억(리뷰)은 인간의 기억과 유사할 것이라 판단**
      + ![망각곡선](https://github.com/Insoo-Hwang/InsureConnect/assets/70841847/cabecc55-5de3-4b19-b42c-3dba088d526c)
      + (1달(58점)+3달(44점)+6달(33점)+1년(20점))*리뷰 점수/전체 리뷰수+리뷰 1개당(30점)
        ```java
        public int getRecommendRating(){
            int score = 0;
            if(review != null && !review.isEmpty()){
                int cnt = 0;
                for(Review reviewScore : review){
                    Timestamp before = reviewScore.getWrite();
                    Timestamp now = new Timestamp(System.currentTimeMillis());
                    Instant beforeIns = before.toInstant();
                    Instant nowIns = now.toInstant();
                    Duration duration = Duration.between(beforeIns, nowIns);
                    long day = Math.abs(duration.toDays());
                    if(day > 365) continue; //1년 이상 0점
                    else if(day > 180){
                        score+=reviewScore.getRate()*20; //6개월~1년 20점
                        cnt++;
                    }
                    else if(day > 90){
                        score+=reviewScore.getRate()*33; //3개월~6개월 33점
                        cnt++;
                    }
                    else if(day > 30){
                        score+=reviewScore.getRate()*44; //1개월~3개월 30점
                        cnt++;
                    }
                    else{
                        score+=reviewScore.getRate()*58; //1개월 이하 58점
                        cnt++;
                    }
                }
                score = (score/cnt)+(cnt*30); //가중치 점수 평균 + 리뷰 1개당 30점점
            }
            return score;
        }
        ```
  - 인기 설계사 계산 함수 호출
    + **1시간 마다 계산한 내용을 DB에 저장 후 호출 -> 짧은 시간에 발생하는 동일한 계산을 방지하여 서버 과부하 방지**
    + ```java
      public RecommendDto recommendPlanner(){
          List<PlannerDto> plannerDtos = new ArrayList<>();
          RecommendPlanner recommendPlanner = recommendPlannerRepository.findFirstByOrderByTimeDesc(); //가장 최근에 저장된 인기 설계사 리스트 호출
          Timestamp time = null;
          if(recommendPlanner != null){ //저장된 내용이 있는 경우 1시간 이내의 데이터를 호출
              time = recommendPlanner.getTime();
              Timestamp now = new Timestamp(System.currentTimeMillis());
              Instant beforeIns = time.toInstant();
              Instant nowIns = now.toInstant();
              Duration duration = Duration.between(beforeIns, nowIns);
              long hour = Math.abs(duration.toHours());
              if(hour < 1){
                  String [] s = recommendPlanner.getList().split(",");
                  for(int i = 0; i < 5; i++){ //최대 5개까지 호출
                      if(s[i].equals("A")) break; //A는 설계사 정보가 없는 경우
                      Planner planner = plannerRepository.findById(Long.parseLong(s[i])).orElseThrow(IllegalArgumentException::new);
                      plannerDtos.add(modelMapper.map(planner, PlannerDto.class));
                  }
              }
              else recommendPlanner = null;
          }
          if(recommendPlanner == null) { //저장된 내용이 없는 경우나 저장된지 1시간이 초과한 경우 새로운 계산 후 DB에 저장
              List<Planner> planners = plannerRepository.findAllPermitPlanner();
              List<Recommend> recommends = new ArrayList<>();
              for (Planner planner : planners) {
                  recommends.add(new Recommend(planner.getReview().size(), planner.getRecommendRating(), modelMapper.map(planner, PlannerDto.class)));
              }
              Collections.sort(recommends);
              String s = "";
              for (Recommend recommend : recommends) {
                  plannerDtos.add(recommend.getPlannerDto());
                  s+=recommend.getPlannerDto().getId();
                  s+=","; //설계사 ID간 ,를 활용하여 데이터 분리
              }
              s+="A,A,A,A,A"; //설계사가 0명인 경우를 대비하여 항상 빈 데이터를 5개 추가
              time = new Timestamp(System.currentTimeMillis());
              RecommendPlanner created = RecommendPlanner.builder()
                      .time(time)
                      .list(s)
                      .build();
              recommendPlannerRepository.save(created);
          }
          RecommendDto recommendDto = RecommendDto.builder()
                  .list(plannerDtos)
                  .time(time)
                  .build();
          return recommendDto;
      }
      ```

### 📖 배운점
