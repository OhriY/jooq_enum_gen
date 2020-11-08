import com.rohanprabhu.gradle.plugins.kdjooq.*
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    kotlin("jvm") version "1.4.10"
    //id("nu.studer.jooq") version "5.2"
    id("com.rohanprabhu.kotlin-dsl-jooq") version "0.4.6"
}

/*buildscript {
    repositories {


    }

    dependencies {
        classpath("org.jooq:jooq-codegen:3.14.1")
        classpath("org.postgresql:postgresql:42.2.14")
    }
}*/
group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()

}

val depVersion = mapOf(
    "postgresql" to "42.2.14",
    "jooq" to "3.14.1")

dependencies {
    implementation(kotlin("stdlib"))
    testImplementation("junit", "junit", "4.12")


    implementation ("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation("org.jooq:jooq:${depVersion["jooq"]}")
    implementation("org.jooq:jooq-meta:${depVersion["jooq"]}")
    implementation("org.jooq:jooq-codegen:${depVersion["jooq"]}")
    implementation("org.jooq:jooq-parent:${depVersion["jooq"]}")
    implementation("org.jooq:jooq-meta-extensions:${depVersion["jooq"]}")
    implementation("org.jooq:jooq-codegen-maven:${depVersion["jooq"]}")
    implementation("org.jooq:jooq-kotlin:${depVersion["jooq"]}")

    jooqGeneratorRuntime("org.postgresql:postgresql:${depVersion["postgresql"]}")
}



jooqGenerator {

    jooqEdition = JooqEdition.OpenSource
    jooqVersion = "3.14.1"
    configuration("primary", project.the<SourceSetContainer>()["main"]) {


        configuration = jooqCodegenConfiguration {

            jdbc {
                username = "admin" // DBのユーザ名
                password = "pass_admin" // DBのパスワード
                driver = "org.postgresql.Driver"
                url = "jdbc:postgresql://localhost:5432/testdb" // DBの接続先
            }

            generator {
                name ="org.jooq.codegen.KotlinGenerator"


                target {
                    packageName = "jooq_generated" // 自動生成するソースのパッケージを指定
                    directory   = "src/main/kotlin" // 自動生成するソースの配置先ディレクトリを指定
                }

                database {
                    name = "org.jooq.meta.postgres.PostgresDatabase"
                    inputSchema = "public"
                    includes = ".*"
                    excludes = ""
                }
            }
        }
    }
}
tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}
