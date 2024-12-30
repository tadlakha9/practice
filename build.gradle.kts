plugins {
	java
	application
	id("org.springframework.boot") version "3.3.2"
	id("org.jetbrains.kotlin.jvm") version "1.9.0"
	id("io.spring.dependency-management") version "1.1.6"
	id("org.graalvm.buildtools.native") version "0.10.3"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.graalvm.buildtools:native-gradle-plugin:0.10.3")
	runtimeOnly("org.graalvm.buildtools:graalvm-reachability-metadata:0.10.3")
	implementation("org.projectlombok:lombok")
	implementation("com.squareup.retrofit2:retrofit:2.11.0")
	implementation("io.projectreactor:reactor-core:3.6.10")
	implementation("jakarta.inject:jakarta.inject-api:2.0.1")
	implementation("org.springframework.boot:spring-boot-starter-web")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}


tasks.withType<Test> {
	useJUnitPlatform()
}


graalvmNative {
	binaries {
		named("main") {
			buildArgs.addAll(listOf(
					"--verbose",
//					"--initialize-at-build-time=com.example.practice.client.CustomerClient", // Initialize class at build time
//					"--initialize-at-run-time=com.example.practice.commons.RetrofitClientProvider", // Initialize class at run time
					"-H:ThrowMissingRegistrationErrors=",
					"-H:+ReportUnsupportedElementsAtRuntime",
					"-H:Log=registerResource:",
					"-R:MissingRegistrationReportingMode=Exit"
			))
		}
	}
	agent {
		defaultMode = "standard"
	}
	metadataRepository{
		enabled = true
	}

}

application {
	mainClass.set("com.example.practice.PracticeApplication") // Replace with your actual main class
}
