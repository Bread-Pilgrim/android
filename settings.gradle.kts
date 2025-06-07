pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode = RepositoriesMode.FAIL_ON_PROJECT_REPOS
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        maven { url = java.net.URI("https://devrepo.kakao.com/nexus/content/groups/public/") }  // kakao
    }
}

rootProject.name = "bakeroad"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")  // 프로젝트 참조 방식 개선
include(":app")
include(":core:designsystem")
include(":core:common:android")
include(":core:common:kotlin")
include(":core:remote")
include(":feature:splash")
include(":feature:onboard")
