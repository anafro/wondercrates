plugins {
    id("java")
}

group = "ru.anafro"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots")
    maven("https://oss.sonatype.org/content/groups/public/")
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.21-R0.1-SNAPSHOT")
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

val targetJavaVersion = 21
java {
    val javaVersion = JavaVersion.toVersion(targetJavaVersion)
    sourceCompatibility = javaVersion
    targetCompatibility = javaVersion

    if (JavaVersion.current() < javaVersion) {
        toolchain.languageVersion = JavaLanguageVersion.of(targetJavaVersion)
    }
}

tasks.withType<JavaCompile>().configureEach {
    if (targetJavaVersion >= 10 || JavaVersion.current().isJava10Compatible()) {
        options.release = targetJavaVersion
    }
}

val serverDir = "D:\\minecraft servers\\polygon"

tasks.register<Exec>("runServer") {
    group = "minecraft server"
    workingDir(file(serverDir))
    commandLine("cmd.exe", "/c", "start Launch.bat ^& exit")
    dependsOn(tasks.jar)
}


tasks.jar {
    destinationDirectory.set(file(serverDir + "\\plugins"))
//    doLast {
//        copy {
//            from("/build/libs/gradle-playground-1.0-SNAPSHOT.jar")
//            into("D:\\minecraft servers\\polygon\\plugins")
//        }
//    }
}

tasks.test {
    useJUnitPlatform()
}