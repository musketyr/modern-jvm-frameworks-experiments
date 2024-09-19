plugins {
    id("io.micronaut.library")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":model"))

    annotationProcessor("io.micronaut:micronaut-http-validation")
    annotationProcessor("io.micronaut.serde:micronaut-serde-processor")

    implementation("io.micronaut.email:micronaut-email-javamail")
    implementation("io.micronaut.email:micronaut-email-template")
    implementation("io.micronaut.views:micronaut-views-velocity")
    implementation("io.micronaut.serde:micronaut-serde-jackson")

    compileOnly("io.micronaut:micronaut-http-client")

    runtimeOnly("org.eclipse.angus:angus-mail")
    runtimeOnly("ch.qos.logback:logback-classic")

    testImplementation("io.micronaut:micronaut-http-client")
    testImplementation("org.mockito:mockito-core")
    testImplementation("org.yaml:snakeyaml")
}

micronaut {
    runtime("netty")
    testRuntime("junit5")

    processing {
        incremental(true)
        annotations("com.example.todoapp.notification.*")
    }

    aot {
        // Please review carefully the optimizations enabled below
        // Check https://micronaut-projects.github.io/micronaut-aot/latest/guide/ for more details
        optimizeServiceLoading = false
        convertYamlToJava = false
        precomputeOperations = true
        cacheEnvironment = true
        optimizeClassLoading = true
        deduceEnvironment = true
        optimizeNetty = true
        replaceLogbackXml = true
    }
}

