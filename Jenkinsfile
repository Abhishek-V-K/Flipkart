pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/Abhishek-V-K/Flipkart'
            }
        }

        stage('Build') {
            steps {
                sh './gradlew clean build'
            }
        }

        stage('Test') {
            steps {
                sh './gradlew test'
            }
        }

        stage('Allure Report') {
            steps {
                sh './gradlew allureReport'
                sh 'allure open build/reports/allure-report'
            }
        }
    }
}
