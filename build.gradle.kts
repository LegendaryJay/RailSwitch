/*
 * This file was generated by the Gradle 'init' task.
 */

plugins {
    `java-library`
    `maven-publish`
    id("io.papermc.paperweight.userdev") version "1.3.1"
}

group = "net.civmc"
version = "1.7.0-SNAPSHOT"
description = "RailSwitch"

repositories {
    fun civRepo(name: String) {
    	maven {
    		url = uri("https://maven.pkg.github.com/CivMC/${name}")
    		credentials {
    			// These need to be set in the user environment variables
    			username = System.getenv("GITHUB_ACTOR")
    			password = System.getenv("GITHUB_TOKEN")
    		}
    	}
    }
    mavenCentral()
    civRepo("CivModCore")
    civRepo("NameLayer")
    civRepo("Citadel")
}

dependencies {
    paperDevBundle("1.18-R0.1-SNAPSHOT")

    implementation("net.civmc:civmodcore:2.0.0-SNAPSHOT:dev-all")
    implementation("net.civmc:namelayer-spigot:3.0.0-SNAPSHOT:dev")
    implementation("net.civmc:citadel:5.0.0-SNAPSHOT:dev")
}

java {
	toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

tasks {
	build {
		dependsOn(reobfJar)
	}

	compileJava {
		options.encoding = Charsets.UTF_8.name() // We want UTF-8 for everything

		// Set the release flag. This configures what version bytecode the compiler will emit, as well as what JDK APIs are usable.
		// See https://openjdk.java.net/jeps/247 for more information.
		options.release.set(17)
	}
	javadoc {
		options.encoding = Charsets.UTF_8.name() // We want UTF-8 for everything
	}
	processResources {
		filteringCharset = Charsets.UTF_8.name() // We want UTF-8 for everything
	}

	test {
		useJUnitPlatform()
	}
}

publishing {
	repositories {
		maven {
			name = "GitHubPackages"
			url = uri("https://maven.pkg.github.com/CivMC/RailSwitch")
			credentials {
				username = System.getenv("GITHUB_ACTOR")
				password = System.getenv("GITHUB_TOKEN")
			}
		}
	}
	publications {
		register<MavenPublication>("gpr") {
			from(components["java"])
		}
	}
}
