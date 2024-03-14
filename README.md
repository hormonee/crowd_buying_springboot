# Crowd Buying
- Crowd Buying Project

# 프로젝트 주제
- 합동 구매 어플리케이션

# 프로젝트 내용
- 원하는 상품을 함께 구매할 구매자를 모집하여 1/n 가격으로 합동 구매할 수 있도록 도와주는어플리케이션입니다.
  (현재 백엔드 부분 완성하였고 프론트단(Flutter)과 Admin페이지(Thymeleaf)를 추가적으로 만들고 있습니다.

# 프로젝트 기획 의도
- 요즘 젊은 세대들은 SNS 에 상시 노출되어 있고 이로 인해 본인을 위한 삶보다 누군가에게보여주기 위한 삶을 살아가는 경우가 많습니다. 즉, 소위 말하는 YOLO 마인드를 장착하고사치를 부리는 젊은 층이 늘어났습니다. 이러한 경향성을 토대로 적은 돈으로 그들이 원하는상품을 구매하고 소유할 수 있는 기회를 제공하여, 그들의 니즈를 충족시켜주고자 합니다.

# 프로젝트 목표
- 익숙한 듯 익숙하지 않은 합동 구매라는 새로운 패러다임 구축

# 개인적인 목표
- Restful 한 API 설계
- 효율적이고 정형화된 개발 프로세스 체화
- JPA, JWT 학습 및 프로젝트 적용
- 웹 페이지 뿐 아니라 모바일 어플리케이션 개발 경험
  (어플리케이션 프론트 단에서의 백엔드와 상호 작용 프로세스 경험을 통한 프론트엔드 및모바일 앱 개발자의 관점을 이해함으로서 협업 능력 향상

# 개발 환경 및 개발 도구
#Front End
- Dart, Flutter, Thymeleaf, jQuery, HTML5, CSS3, JavaScript
  
#Back End
- Java, SpringBoot, MySQL, JPA, MyBatis, AWS S3, Spring Security(+JWT) (프론트와 어드민 페이지 완성 후 어플리케이션 배포 시, AWS RDS, EC2 활용 예정)

#개발 도구
- IntelliJ IDEA(Ultimate), Android Studio, Visual Studio Code

# 기능 설명
#User Domain
- 회원가입&로그인(Spring Security, JWT)
- 사용자 목록, 사용자 세부 정보 조회, 사용자 정보 수정, 회원 탈퇴

#Recruit Domain

리크루트(합동 구매 모집) Life Cycle : 등록 신청 → 관리자 심사 → 등록(반려) → 모집 마감(철회)
- 등록 신청
- 관리자 심사(통과 또는 반려 처리)
- 목록 및 세부 정보 조회, 관리자용 리크루트 조회(사용자는 심사 통과된 것만 조회 가능)
- 참여 및 참여 취소(참여 인원이 0명이 되면 리크루트 철회 처리)
- 리크루트 강제 종료

#Bookmark Domain
- 북마크 조회 : 사용자별 북마크 조회
- 북마크 추가 및 삭제

#Notification Domain
- 알림 조회 : 사용자별 알림 목록 조회
- 알림 보내기(생성) : 회원 가입, 리크루트 등록 신청 및 심사 결과, 북마크한 리크루트 마감 임박(모집 완료 까지 2명 이하로 남았을 경우), 리크루트 참여 및 참여 취소, 리크루트 마감 등의 경우에 알림 자동 발송

#Category Domain
- 카테고리 목록 및 세부 정보 조회
- 선택된 카테고리의 하위 카테고리 목록 조회

# Portfolio & Trouble Shooting
- https://hormone.tistory.com/145
