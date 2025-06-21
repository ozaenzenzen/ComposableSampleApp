pluginManagement {
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
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://storage.googleapis.com/download.flutter.io")
        maven("/Users/ozaenzenzen/Documents/Ozan/Kantor/Konnek/konnek_native_core/build/host/outputs/repo")
        maven("/Users/ozaenzenzen/Documents/Ozan/Kantor/Konnek/KonnekNativeAndroid/app/build")
    }
}

rootProject.name = "ComposableSampleApp"
include(":app")
