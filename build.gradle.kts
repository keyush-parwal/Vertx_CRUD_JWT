import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.gradle.api.tasks.testing.logging.TestLogEvent.*

plugins {
  java
  application
  id("com.github.johnrengelman.shadow") version "7.1.2"
  id("io.ebean") version "14.1.0"
}

group = "com.example"
version = "1.0.0-SNAPSHOT"

repositories {
  mavenCentral()
}

val vertxVersion = "4.5.8"
val junitJupiterVersion = "5.9.1"
val vertxWebVersion = "4.3.8"
val ebeanVersion = "12.12.1"
val queryBeanVersion = "12.12.1"
val mysqlConnectorVersion = "8.0.28"
val hikariCPVersion = "4.0.3"
val logbackVersion = "1.2.11"
val jaxbApiVersion = "2.3.1"

val mainVerticleName = "com.Keyush.CRUD_And_JWT.MainVerticle"
val launcherClassName = "io.vertx.core.Launcher"

val watchForChange = "src/*/"
val doOnChange = "${projectDir}/gradlew classes"

application {
  mainClass.set(launcherClassName)
}

dependencies {
  implementation(platform("io.vertx:vertx-stack-depchain:$vertxVersion"))
  implementation("io.vertx:vertx-core")
  implementation("io.vertx:vertx-web:$vertxWebVersion")
  implementation("io.vertx:vertx-auth-jwt:$vertxVersion")
  implementation("io.jsonwebtoken:jjwt:0.9.1")
  implementation("io.ebean:ebean:$ebeanVersion") {
    exclude(group = "org.slf4j", module = "slf4j-log4j12")
  }
  implementation("io.ebean:ebean-querybean:$queryBeanVersion")
  implementation("mysql:mysql-connector-java:$mysqlConnectorVersion")
  implementation("com.zaxxer:HikariCP:$hikariCPVersion")
  implementation("ch.qos.logback:logback-classic:$logbackVersion")
  implementation("org.slf4j:slf4j-api:1.7.32")
  implementation("org.projectlombok:lombok:1.18.22")
  implementation("javax.xml.bind:jaxb-api:$jaxbApiVersion")
  testImplementation("io.vertx:vertx-junit5")
  testImplementation("org.junit.jupiter:junit-jupiter:$junitJupiterVersion")
}

tasks.withType<ShadowJar> {
  archiveClassifier.set("fat")
  manifest {
    attributes(mapOf("Main-Class" to mainVerticleName))
  }
  mergeServiceFiles()
}

tasks.withType<Test> {
  useJUnitPlatform()
  testLogging {
    events = setOf(PASSED, SKIPPED, FAILED)
  }
}

tasks.withType<JavaExec> {
  args = listOf("run", mainVerticleName, "--redeploy=$watchForChange", "--launcher-class=$launcherClassName", "--on-redeploy=$doOnChange")
}
