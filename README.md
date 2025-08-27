# 빵글 (Android)
> 빵 따라 걷는 부산의 맛있는 순간들
<p align="left">
  <img src="https://github.com/user-attachments/assets/e7f0d7b9-c483-46e9-b146-30f98a06a245" alt="Frame 1707482694" width="200"/>
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
