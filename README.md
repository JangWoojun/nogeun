# nogeun
> 노근
![Slide 16_9 - 1](https://github.com/JangWoojun/nogeun/assets/102157871/94928fe8-1cca-44b3-bb12-5a3409e251c8)

## 프로젝트 소개

- 플랫폼 : 안드로이드/모바일
- 제작 인원 : 6 인 (팀 구성 ― 안드로이드 2명, 백엔드 2명, 디자이너 1명, 기획자 1명)
- 제작 기간 : 1 일 (2024.01.27 ~ 2024.01.28)

- 사용 기술 : Kotlin, XML, ViewBinding, Glide, Room, Retrofit2, STT, Kakao Map API, Kakao OAuth

'노근'은 노인 근처의 커뮤니티'의 약자로 노인들을 위한 위치 기반 SNS 서비스 서비스입니다<br>
시작은 노인들의 신체적, 정신적 건강을 위해 어떻게 하면 노인들의 야외 활동 및 커뮤니팅을<br>
늘릴 수 있을까? 라는 생각으로 출발하여 기획한 서비스로 지도를 사용해 위치 기반으로 글을 쓰면<br>
이미지와 마커 글이 지도에 공유되는 기능이 메인으로 최대한 노인에게 맞춘 UI, UX 설계를 하였습니다.

## 사용 예제

### 시연 영상

https://github.com/JangWoojun/nogeun/assets/102157871/45c4c954-e44c-47f3-aace-1d6302c0d5bb

### 스샷

<div style="text-align: left;">
    <img src="https://github.com/JangWoojun/nogeun/assets/102157871/da7cbafc-c01e-458c-b109-c7379998270d"  width="200" height="400"/>
    <img src="https://github.com/JangWoojun/nogeun/assets/102157871/e2d4f616-5038-42fd-a99a-1e638da457ec"  width="200" height="400"/>
    <img src="https://github.com/JangWoojun/nogeun/assets/102157871/e74edbd2-2e52-4928-b1d7-762b4f326d2a"  width="200" height="400"/>
    <img src="https://github.com/JangWoojun/nogeun/assets/102157871/64a83b4a-704e-4d1e-ab9a-ff5d7b1eba5b"  width="200" height="400"/>
    <img src="https://github.com/JangWoojun/nogeun/assets/102157871/6997edfc-7bdb-4dbe-a657-0779ee689a3b"  width="200" height="400"/>
</div>


## 구현 기능

앱에서 지도 및 위치 기반 이미지 마커 부분과 카카오 OAuth 로그인 및 회원가입, 검색, 카메라, 네트워크 통신 부분을 맡았습니다.

- 회원가입, 로그인
    - 카카오 OAuth *
- 홈
    - 카카오 지도 (유저들의 게시물 이미지 마커, 내 위치) *
    - 내 정보 바로가기
    - 게시물 검색하기 (STT 검색, 텍스트로 검색) *
    - 게시물 이미지 선택 (이미지 파일, 카메라) *
- 게시물
    - 좋아요 
    - 댓글 작성하기
- 게시물 작성
    - 제목 설정
- 프로필
    - 프로필 정보
    - 로그아웃 
    - 내 게시물 목록

<br>

## 배운 점 & 아쉬운 점 & 이슈

배운 점 및 아쉬운 점, 이슈 등은 블로그 회로록을 정리하였습니다. 관심 있으시다면 해당 [포스트]()를 확인해주세요.

## 느낀 점

노근 프로젝트를 하면서 같은 직군인 안드로이드 개발자와 처음으로 협업해보는 경험과 그동안 생각해보지 않았지만 중요한 유저층인 노인들을 위한 UI, UX를 고려하며 개발하는 좋은 경험들을 할 수 있었습니다. 또한 임팩톤이라는 해커톤에 맞게
비즈니스 모델을 고려하며 기획하고 개발했던 점이 어려웠지만 개발자로써 그동안 고려해보지 못했던 부분이라 뜻깊은 시간이 되었던 거 같습니다.
