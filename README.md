<div align=center>
<img src="https://capsule-render.vercel.app/api?type=waving&color=auto&height=200&section=header&text=Sparta-Study-Keep&fontSize=75" />
</div>
<div align=center>
	<h3>📚 Tech Stack 📚</h3>
</div>
<div align="center">
	<img src="https://img.shields.io/badge/Java-007396?style=flat&logo=Conda-Forge&logoColor=white" />
  <img src="https://img.shields.io/badge/MySQL-4479A1?style=flat&logo=MySQL&logoColor=white" />
  <br>
	<img src="https://img.shields.io/badge/Spring-6DB33F?style=flat&logo=Spring&logoColor=white" />
  <img src="https://img.shields.io/badge/SpringBoot-6DB33F?style=flat&logo=SpringBoot&logoColor=white" />
  <img src="https://img.shields.io/badge/SpringSecurity-6DB33F?style=flat&logo=SpringSecurity&logoColor=white" />
</div>

<div align=center>
	<h3>🛠️ Tools 🛠️</h3>
</div>
<div align="center">
	<img src="https://img.shields.io/badge/Java-007396?style=flat&logo=Conda-Forge&logoColor=white" />
  <img src="https://img.shields.io/badge/MySQL-4479A1?style=flat&logo=MySQL&logoColor=white" />
  <br>
	<img src="https://img.shields.io/badge/Spring-6DB33F?style=flat&logo=Spring&logoColor=white" />
  <img src="https://img.shields.io/badge/SpringBoot-6DB33F?style=flat&logo=SpringBoot&logoColor=white" />
  <img src="https://img.shields.io/badge/SpringSecurity-6DB33F?style=flat&logo=SpringSecurity&logoColor=white" />
</div>

# 📕프로젝트 소개
* **프로젝트 컨셉**
  * 뉴스피드 프로젝트
  * 공부를 하면서 떠오른 화두를 Keep에 업로드하여 리마인드하고, 친구끼리 공유하여 함께 생각해보자!
* **프로젝트명**
  * SSK(Sparta Study Keep)
* **프로젝트 기간**
  * 2024.09.03 ~ 2024.09.06
* **인원**
  * 3명
* **담당자 / 담당 기능**
  * **정재호(팀장)**
    * 스프링 시큐리티와 jwt토큰을 활용한 인증/인가 및 로그인/로그아웃
    * 북마크 기능
    * 친구 기능
  * **정원석(팀원)**
    * 유저 기능
  * **안동환(팀원)**
    * 게시글 기능
* **시연 영상**
  * <a href="https://www.youtube.com/watch?v=GtFrYQ21SpE"><img src="https://img.shields.io/badge/youtube-FF0000?style=flat&logo=youtube&logoColor=white" /></a>
   
# ♻️ERD
![image](https://github.com/user-attachments/assets/a4214bd4-e9ad-47e4-8d92-1847abf8b1ca)

# 🗒️기능 구현 리스트
## JWT 토큰
* jwt 액세스, 리프레쉬 토큰 발행
* jwt 액세스, 리프레쉬 토큰 재발행
* jwt 토큰 인증, 인가
* 로그인 / 로그아웃
## 유저
* 유저 추가(회원가입)
* 유저 목록 조회(다건 조회)
* 유저 상세 조회(프로필 조회)
* 유저 정보 수정(프로필 정보 수정)
* 유저 삭제(회원탈퇴 / 탈퇴 후에도 회원 정보 남기기)
## 친구
* 친구 요청
* 친구 요청 수락
  * 친구 맺을 시 friends 테이블에 두개의 row 생김
* 친구 요청 거절
* 친구목록 가져오기
* 친구요청목록 가져오기
* 친구 삭제
* 친구 전체 삭제
## 게시글
* 게시글 작성
* 게시글 전체 조회
* 게시글 단건 조회
* 게시글 수정
* 게시글 삭제
* 뉴스피드 목록 조회
## 북마크
* 북마크 등록
* 북마크 해제
* 북마크된 글 목록 조회
* 북마크 모두 삭제
