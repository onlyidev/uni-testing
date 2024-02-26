pipeline {
    agent any
        stage('Test') {
            steps {
                sh './gradlew test --stacktrace'
            }
        }
    }
}
