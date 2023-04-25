pipeline {
    agent any

    options {
        githubProjectProperty(projectUrlStr: "https://github.com/SirBlobman/Folia-Helper")
    }

    environment {
        MAVEN_DEPLOY = credentials('MAVEN_DEPLOY')
    }

    triggers {
        githubPush()
    }

    tools {
        jdk "JDK 17"
    }

    stages {
        stage("Gradle: Build") {
            steps {
                withGradle {
                    script {
                        if (env.BRANCH_NAME == "main") {
                            sh("./gradlew clean build publish --refresh-dependencies --no-daemon")
                        } else {
                            sh("./gradlew clean build --refresh-dependencies --no-daemon")
                        }
                    }
                }
            }
        }
    }

    post {
        success {
            archiveArtifacts artifacts: 'build/libs/Folia-Helper-*.jar', fingerprint: true
        }
    }
}
