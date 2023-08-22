includeBuild("build-logic")

include(":app")
include(":lib-core")
include(":lib-geocoder-core")
include(":lib-geocoder-mapquest")
include(":lib-geocoder-positionstack")
include(":lib-geocoder-what3words")
include(":lib-http")
include(":lib-settings")
include(":lib-tak")
include(":lib-widget-mapcentre")
include(":lib-widget-self")

include(":lib-test")

// https://docs.gradle.org/current/userguide/declaring_dependencies.html#sec:type-safe-project-accessors
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

// https://docs.gradle.org/current/userguide/configuration_cache.html#config_cache:stable
enableFeaturePreview("STABLE_CONFIGURATION_CACHE")
