# 🍞 빵글 - 빵덕후들의 여정, 취향을 기록하다
> 내가 먹은 빵, 취향까지 정산해주는 기록형 빵지순례 앱
<img width="500" src="https://github.com/user-attachments/assets/348a4dfd-ea28-455a-940e-96f5380a7d51" />

# Introduce
먹고 기록하면, 취향이 보입니다.  
나의 빵생활을 데이터로 예쁘게 정리하는 **기록형 베이커리 아카이브**.

## 무엇을 할 수 있나요?
- **🍞 빵집 추천** : 내 취향과 지역에 맞는 빵집을 추천받아요.
- **🏖️ 관광지 추천** : 내가 가려는 지역의 관광지를 추천받아요.
- **🚶 방문 기록** : 다녀온 빵집과 먹은 빵을 메뉴·사진·메모로 리뷰해서 깔끔하게 저장해요.
- **📊 빵말정산** : 소금빵 vs 쿠키 비율, 최애 빵집, 요일·시간대 패턴, 누적 지출, 지역 분포까지 한눈에.
- **💖 취향 프로필** : 크루아상파? 단팥러버? 먹은 기록이 쌓일수록 내 취향이 선명해져요.
- **🏅 뱃지** : 인증할수록 도장 콕! 사진 리뷰·좋아요·새로운 동네 탐험 등 활동에 따른 뱃지를 수집해요.

## 빵말정산, 이렇게 나와요
- `빵 종류 TOP3` : 예) 소금빵 52% · 쿠키 31% · 바게트 17%
- `최다 방문/최애 빵집` : 한 달 동안 내가 가장 사랑한 그곳
- `요일·시간대 패턴` : 화·금 오후에 디저트 비중↑와 같은 생활 리듬 분석
- `지출 리포트` : 이번 달 총액과 전월 대비 증감, 평균 단가 흐름
- `지역 분포` : 내가 자주 가는 동네/상권, 새로 개척한 동네 표시
- `활동 하이라이트` : 받은 좋아요, 작성 리뷰 수, 획득 뱃지 요약

## 왜 빵글일까요?
- `기록이 곧 취향이 되는 경험` : ‘어디를 갔는가’보다 ‘무엇을 좋아하는가’에 초점을 맞춘 데이터 아카이브
- `보는 재미, 모으는 재미` : 예쁜 인포그래픽과 뱃지 수집으로 꾸준히 쓰게 되는 UX

## 이렇게 쓰면 좋아요
- “내가 진짜 좋아하는 빵이 뭔지” 데이터로 확인하고 싶을 때
- 지출을 체크하며 맛있는 빵 소비를 하고 싶을 때
- 나와 비슷한 취향의 유저 기록을 보며 다음 방문지를 고를 때

> **지금, 빵글 앱으로 당신의 빵생활을 멋지게 아카이브하세요.**  
> **먹고, 기록하고, 정산받는 즐거움을 시작해요! 🍞✨**

# Screenshot
<p align="center">
  <img src="https://github.com/user-attachments/assets/e590d748-e0b7-4e79-b7ec-e0ebd24255aa" alt="Frame 1707482694" width="300"/>
  <img src="https://github.com/user-attachments/assets/4843df0b-b3ce-4477-81a4-a047845b56f9" alt="Frame 1707482694" width="300"/>
  <img src="https://github.com/user-attachments/assets/0c8717f3-9363-4c46-a827-e1c3a64fcb31" alt="Frame 1707482694" width="300"/>
  <img src="https://github.com/user-attachments/assets/0c472c74-171c-4fe8-8d42-a51d03790599" alt="Frame 1707482694" width="300"/>
  <img src="https://github.com/user-attachments/assets/5df925c6-4d80-41ae-b05b-274600a71234" alt="Frame 1707482694" width="300"/>
  <img src="https://github.com/user-attachments/assets/d008b191-8500-4887-b8a1-309a438a3c40" alt="Frame 1707482694" width="300"/>
</p>

# Development

### Required
- IDE : `Android Studio Narwhal`
- JDK : `17+`
- Kotlin : `2.1.10`
- AGP : `8.12.0`

### Language
- `Kotlin`

### Libraries
- AndroidX
    - Lifecycle
        - `lifecycle-runtime-compose`
        - `lifecycle-viewmodel-compose`
    - Navigation
        - `navigation-compose`
        - `hilt-navigation-compose`
    - Compose
        - `compose-bom`
        - `foundation`
        - `material3`
        - `ui-tooling`
        - `activity-compose`
    - Splash Screen
    - DataStore
        - `datastore-preferences`
        - `datastore`
    - Paging
- KotlinX
    - Coroutines
    - Serialization Json
    - Collections Immutable
- Hilt
- Okhttp3
- Retrofit
    - `converter-kotlinx-serialization`
- Gson
- Protobuf
    - `protobuf-kotlin-lite`
    - `protoc`
- Coil
- Kakao
    - `v2-user`
    - `v2-share`
- Timber
- Lottie

### Gradle Dependency
- Gradle Version Catalog

# Project Structure

```
📦 빵글
├─ app                  # 애플리케이션 및 메인화면 모듈
├─ build-logic          # Gradle Convention Plugins
├─ core                 # 공통 모듈
│  ├─ common            # 유틸
│  │  ├─ android        # 안드로이드 유틸
│  │  └─ kotlin         # 코틀린 유틸
│  ├─ data              # 데이터 계층 (Repository)
│  ├─ datastore         # 데이터 저장소 (Preferences, Proto)
│  ├─ designsystem      # 디자인 시스템 (기본 컴포넌트, 테마)
│  ├─ domain            # 도메인 계층 (UseCase)
│  ├─ eventbus          # 이벤트 버스 (모듈 간 데이터 전달)
│  ├─ exception         # 에러
│  ├─ model             # 공용 모델
│  ├─ navigator         # 네비게이션 (모듈 간 이동)
│  ├─ remote            # 네트워크 계층 (DataSource)
│  └─ ui                # 앱 컴포넌트
├─ feature              # 기능 단위 모듈
│  ├─ badge             # 뱃지 목록
│  ├─ bakery            # 빵집
│  │  ├─ detail         # 빵집 상세
│  │  └─ list           # 빵집 목록
│  ├─ home              # 홈 탭
│  ├─ intro             # 인트로 (스플래시, 로그인)
│  ├─ mybakery          # 내 빵집 탭
│  ├─ mypage            # 나의 빵글 탭
│  ├─ onboard           # 온보딩 (취향 및 닉네임 설정)
│  ├─ report            # 빵말정산
│  ├─ review            # 리뷰
│  │  ├─ myreviews      # 내가 쓴 리뷰
│  │  └─ write          # 리뷰 작성
│  ├─ search            # 검색 탭
│  └─ settings          # 설정
└─ gradle               # Gradle Version Catalog
```

# Architecture

> 💡 Based on [Now in Android](https://github.com/android/nowinandroid) with Clean Architecture

- **Multi-Module**
    - Convention Plugins
        `build-logic` 
        멀티 모듈을 적용하기 때문에 공통적으로 사용하는 모듈의 설정이 생기게 된다.
        이에 따라, 불필요한 중복 코드를 제거하기 위해 **Convention Plugins** 을 정의한다.
        
        > 💡 만약 특정 모듈에만 필요한 일회성 로직이라면, 공통 플러그인을 만들기 보다는  
        > 해당 모듈의 `build.gradle` 파일에 직접 정의하는 것이 바람직하다.
        
- **MVI (Model-View-Intent)**
    - **State** : `View` 를 나타내기 위한 상태값
        - **Recomposition** 을 줄이기 위해 stable 하게 작성
    - **Intent** : `View` → `ViewModel`
        - `ViewModel` 에서 Intent 에 맞게 State 를 **reduce**
    - **SideEffect** : `ViewModel` → `View`
        - `ViewModel` 에서 State reduce 를 제외한 **또다른 처리**를 필요로 할 때 사용  
        (ex. Toast, Alert, Navigation..)
