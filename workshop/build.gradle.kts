/*
 * ====================================================================
 * Project:     openMDX, http://www.openmdx.org/
 * Description: build.gradle.kts
 * Owner:       OMEX AG, Switzerland, http://www.omex.ch
 * ====================================================================
 *
 * This software is published under the BSD license as listed below.
 * 
 * Copyright (c) 2020-2021, OMEX AG, Switzerland
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or
 * without modification, are permitted provided that the following
 * conditions are met:
 * 
 * * Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 * 
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in
 *   the documentation and/or other materials provided with the
 *   distribution.
 * 
 * * Neither the name of the openMDX team nor the names of its
 *   contributors may be used to endorse or promote products derived
 *   from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND
 * CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS
 * BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED
 * TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
 * OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * 
 * ------------------
 * 
 * This product includes software developed by other organizations as
 * listed in the NOTICE file.
 */

import org.gradle.kotlin.dsl.*
import org.w3c.dom.Element
import java.util.*
import java.io.*
import org.gradle.api.tasks.bundling.Jar

plugins {
	java
	`java-library`
	eclipse
	distribution
}

repositories {
	mavenCentral()
	jcenter()
}

var env = Properties()
env.load(FileInputStream(File(project.getRootDir(), "build.properties")))
val targetPlatform = JavaVersion.valueOf(env.getProperty("target.platform"))

eclipse {
	project {
    	name = "openMDX 2 ~ Example Workshop"
    }
    jdt {
		sourceCompatibility = targetPlatform
    	targetCompatibility = targetPlatform
    	javaRuntimeName = "JavaSE-" + targetPlatform    	
    }
}

fun getProjectImplementationVersion(): String {
	return project.getVersion().toString();
}

fun getDeliverDir(): File {
	return File(project.getRootDir(), "jre-" + targetPlatform + "/" + project.getName());
}

fun touch(file: File) {
	ant.withGroovyBuilder { "touch"("file" to file, "mkdirs" to true) }
}

project.getConfigurations().maybeCreate("openmdxBootstrap")
project.getConfigurations().maybeCreate("openmdxBaseModels")
project.getConfigurations().maybeCreate("openmdxSecurityModels")
project.getConfigurations().maybeCreate("openmdxPortalModels")
project.getConfigurations().maybeCreate("tools")
project.getConfigurations().maybeCreate("webapps")
project.getConfigurations().maybeCreate("openmdxInspector")
val openmdxBootstrap by configurations
val openmdxBaseModels by configurations
val openmdxSecurityModels by configurations
val openmdxPortalModels by configurations
val tools by configurations
val webapps by configurations
val openmdxInspector by configurations

dependencies {
    implementation("org.openmdx:openmdx-base:2.17.+")
    implementation("javax:javaee-api:8.0.+")
    implementation("javax.jdo:jdo-api:3.1")
    implementation("junit:junit:4.12")
    openmdxBootstrap("org.openmdx:openmdx-base:2.17.+")
    openmdxBootstrap("javax:javaee-api:8.0.+")
    openmdxBaseModels("org.openmdx:openmdx-base-models:2.17.+")
    openmdxSecurityModels("org.openmdx:openmdx-security-models:2.17.+")
    openmdxPortalModels("org.openmdx:openmdx-portal-models:2.17.+")
	openmdxInspector("org.openmdx:openmdx-inspector:2.17.+")
	tools(files("$buildDir/classes/java/main"))
	tools(files("$buildDir/resources/main"))
    tools("javax:javaee-api:8.0.+")
    tools("org.openmdx:openmdx-base:2.17.+")
    tools("org.apache.openjpa:openjpa:2.4.+")
    tools("org.hsqldb:hsqldb:2.4.+")
    testImplementation("org.junit.jupiter:junit-jupiter:5.6.0")
	testRuntimeOnly(files("$buildDir/generated/sources/model/openmdx-workshop.openmdx-xmi.zip"))
    testRuntimeOnly("org.junit.jupiter:junit-jupiter:5.6.0")
    testRuntimeOnly("org.openmdx:openmdx-base:2.17.+")
    testRuntimeOnly("org.openmdx:openmdx-system:2.17.+")
    testRuntimeOnly("org.hsqldb:hsqldb:2.4.+")
    webapps("org.openmdx:openmdx-base:2.17.+")
    webapps("org.openmdx:openmdx-security:2.17.+")
    webapps("org.openmdx:openmdx-portal:2.17.+")
    webapps("org.codehaus.groovy:groovy:3.0.+")
}

sourceSets {
    main {
        java {
            srcDir("src/main/java")
            srcDir("$buildDir/generated/sources/java/main")
        }
        resources {
        	srcDir("src/main/resources")
        }        
    }
    test {
        java {
            srcDir("src/test/java")
            srcDir("$buildDir/generated/sources/java/test")
        }
        resources {
        	srcDir("src/test/resources")
        }
    }    
}

tasks.test {
    useJUnitPlatform()
    maxHeapSize = "4G"
    testLogging {
        events("passed","skipped","failed")
    }
}

tasks.register<org.openmdx.gradle.CreateSchemaTask>("create-schema") {
}

tasks.register<org.openmdx.gradle.CreateSqlTask>("create-sql") {
}

tasks.register<org.openmdx.gradle.GenerateModelsTask>("generate-model") {
    inputs.dir("${projectDir}/src/model/emf")
    inputs.dir("${projectDir}/src/main/resources")
    outputs.file("${buildDir}/generated/sources/model/openmdx-" + project.getName() + "-models.zip")
    outputs.file("${buildDir}/generated/sources/model/openmdx-" + project.getName() + ".openmdx-xmi.zip")
    classpath = configurations["openmdxBootstrap"]
    doFirst {
        project.copy {
            from(project.zipTree(project.getConfigurations().getByName("openmdxBaseModels").singleFile))
            into(File(project.getBuildDir(), "generated/sources/model/openmdx/base"))
        }
        project.copy {
            from(project.zipTree(project.getConfigurations().getByName("openmdxSecurityModels").singleFile))
            into(File(project.getBuildDir(), "generated/sources/model/openmdx/security"))
        }
        project.copy {
            from(project.zipTree(project.getConfigurations().getByName("openmdxPortalModels").singleFile))
            into(File(project.getBuildDir(), "generated/sources/model/openmdx/portal"))
        }
    }
    doLast {
        copy {
            from(
                zipTree("${buildDir}/generated/sources/model/openmdx-" + project.getName() + "-models.zip")
            )
            into("$buildDir/generated/sources/java/main")
            include(
                "**/*.java"
            )
        }
    }
}

tasks.compileJava {
    dependsOn("generate-model")
    options.release.set(Integer.valueOf(targetPlatform.getMajorVersion()))
}

tasks.register<Jar>("openmdx-workshop-rest.war") {
	var applicationName = "openmdx-workshop-rest"
    destinationDirectory.set(File(buildDir, "deployment-unit"))
    archiveFileName.set(applicationName + ".war")
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    includeEmptyDirs = false
    var dataHome = "src/data/" + applicationName
    var libs = getConfigurations().getByName("webapps")
    from(File(dataHome, "/META-INF")) { into("META-INF"); }
    from(File(dataHome, "/WEB-INF")) { into("WEB-INF"); include("web.xml", "*.xml"); exclude("*/*"); }
    from(File(dataHome)) { include("api-ui/**/*.*"); }
    from(File(buildDir, "classes/java/main")) { into("WEB-INF/classes"); }
    from(File(buildDir, "resources/main")) { into("WEB-INF/classes"); }
    from(zipTree(File(buildDir, "generated/sources/model/openmdx-workshop.openmdx-xmi.zip"))) { into("WEB-INF/classes"); include("org/openmdx/example/*/"); }
    from(libs) { into("WEB-INF/lib"); }
}

tasks.register<Jar>("openmdx-workshop-portal.war") {
	var applicationName = "openmdx-workshop-portal"
    destinationDirectory.set(File(buildDir, "deployment-unit"))
    archiveFileName.set(applicationName + ".war")
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    includeEmptyDirs = false
    var dataHome = "src/data/" + applicationName
    var libs = getConfigurations().getByName("webapps")
    var openmdxInspectorFiles: FileTree = project.zipTree(project.getConfigurations().getByName("openmdxInspector").singleFile)    
    from(File(dataHome))
    from(openmdxInspectorFiles)  
    from(File(buildDir, "classes/java/main")) { into("WEB-INF/classes"); }
    from(File(buildDir, "resources/main")) { into("WEB-INF/classes"); }
    from(zipTree(File(buildDir, "generated/sources/model/openmdx-workshop.openmdx-xmi.zip"))) { into("WEB-INF/classes"); include("org/openmdx/example/*/"); }
    from(libs) { into("WEB-INF/lib"); }
}

tasks {
	assemble {
		dependsOn(
			"openmdx-workshop-rest.war",
			"openmdx-workshop-portal.war"
        )
	}
}

distributions {
    main {
    	distributionBaseName.set("openmdx-" + getProjectImplementationVersion() + "-" + project.getName() + "-jre-" + targetPlatform)
        contents {
        	// test-core
        	from(".") { into(project.getName()); include("LICENSE", "*.LICENSE", "NOTICE", "*.properties", "build*.*", "*.xml", "*.kts") }
            from("src") { into(project.getName() + "/src") }
            // etc
            from("etc") { into(project.getName() + "/etc") }
            // rootDir
            from("..") { include("*.properties", "*.kts" ) }
            // jre-...
            from("../jre-" + targetPlatform + "/" + project.getName() + "/lib") { into("jre-" + targetPlatform + "/" + project.getName() + "/lib") }
            from("../jre-" + targetPlatform + "/gradle/repo") { into("jre-" + targetPlatform + "/gradle/repo") }
        }
    }
}
