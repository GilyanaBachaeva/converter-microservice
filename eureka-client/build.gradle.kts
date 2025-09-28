plugins {
	java
	id("org.springframework.boot") version "2.6.2"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "spring-micro"
version = "0.0.1-SNAPSHOT"
description = "Demo project for Spring Boot"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

repositories {
	mavenCentral()
}

extra["springCloudVersion"] = "2021.0.0"

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-devtools")
	implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	implementation("com.itextpdf:itext7-core:7.1.15")
	implementation("io.minio:minio:8.3.0")
	compileOnly("org.projectlombok:lombok:1.18.28")
	annotationProcessor("org.projectlombok:lombok:1.18.28")
	implementation("com.squareup.okhttp3:okhttp:4.9.3")
}

dependencyManagement {
	imports {
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.bootJar {
	mainClass.set("spring_micro.eureka_client.EurekaClientApplication")
}

tasks.register<org.springframework.boot.gradle.tasks.run.BootRun>("bootRun") {
	group = "application"
	description = "Runs the application."
	classpath = sourceSets["main"].runtimeClasspath
	mainClass.set("spring_micro.eureka_client.EurekaClientApplication")
}