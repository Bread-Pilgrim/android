# 빵지순례 (Bake Load)
## Development

### Required

- IDE : Android Studio Meerkat
- JDK : `17+`
- Kotlin : `2.1.10`

### Language

- `Kotlin`

### Libraries

- Compose
    - Material3
- Hilt
- Coil (compose)
- Kakao Auth
- Timber

### Gradle Dependency

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
