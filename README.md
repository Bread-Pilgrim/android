# 빵글 (Android)
> 빵 따라 걷는 부산의 맛있는 순간들
<p align="left">
  <img src="https://github.com/user-attachments/assets/e7f0d7b9-c483-46e9-b146-30f98a06a245" alt="Frame 1707482694" width="300"/>
</p>

## Development

### 1. Required
- IDE : Android Studio Meerkat
- JDK : `17+`
- Kotlin : `2.1.10`

### 2. Language
- Kotlin

### 3. Libraries
- AndroidX
    - Lifecycle & ViewModel Compose
    - Navigation (Compose)
    - Compose
        - Foundation
        - Material3
        - UI Tooling
    - Hilt
        - Navigation Compose
    - Splash Screen
    - DataStore
- Kotlinx
    - Coroutines
    - Serialization Json
    - Collections Immutable
- Hilt
- Okhttp3 (Logging Interceptor)
- Retrofit
    - Converter Kotlinx Serialization
- Coil (Compose)
- Kakao Auth
- Timber

### 4. Gradle Dependency
- Gradle Version Catalog

## Architecture
> 💡 Based on [Now in Android](https://github.com/android/nowinandroid) with Clean Architecture

- Multi Module
    - Convention Plugins : `build-logic`
        
        멀티 모듈을 적용하기 때문에 공통적으로 사용하는 모듈의 설정이 생기게 된다.  
        이에 따라, 불필요한 중복 코드를 제거하기 위해 **Convention Plugins** 을 정의한다.

        >💡 만약 특정 모듈에만 필요한 일회성 로직이라면, 공통 플러그인을 만들기 보다는  
        > 해당 모듈의 `build.gradle` 파일에 직접 정의하는 것이 바람직하다.
        
- MVI
    - `UiState` `Intent` `SideEffect`
