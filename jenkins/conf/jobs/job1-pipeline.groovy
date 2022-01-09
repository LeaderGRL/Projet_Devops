#!groovy

pipeline {
    agent any
    /*def mvn_version = 'M3'
    withEnv( ["PATH+MAVEN=${tool mvn_version}/bin"] ) {
    //sh "mvn clean package"
    }*/
    tools {
        maven 'M3'
    }
    environment {
        TEST = 'TEST'
    }
    options {
        buildDiscarder(logRotator(numToKeepStr: '100'))
        ansiColor('xterm')
    }
    stages {
        stage('Clone') {
            steps {
                script {
                    println('Env var: ' + env.TEST)
                    sh 'rm -rf *'
                    sh 'git clone https://github.com/Ozz007/sb3t.git'
                    git branch: "${params.BRANCH}",
                    url: 'https://github.com/Ozz007/sb3t.git'
                    //currentBuild.displayName = "#${BUILD_NUMBER} ${params.PARAM1}"
                }
            }
        }
        stage('compile') {
            steps {
                sh 'mvn compile'
            }
        }
        stage('testunit') {
            when {
            expression { params.SKIP_TESTS == false }
            }
            steps {
                sh 'mvn test'
            }
        }
        stage('package') {
            steps {
                sh 'mvn package'
            }
        }
        stage('testinteg') {
            when {
            expression { params.SKIP_TESTS == false }
            }
            steps {
                sh 'mvn verify'
            }
        }
        /*stage('Build') {
            steps {
                script {
                    sh 'cd sb3t/sb3t-ws/src/test/java/com/app/sb3t/ws && javac ApplicationTest.java'
                }
            }
        }*/
        stage('move') {
            steps {
                script{
                    sh "mv sb3t-ws/target/sb3t-ws-1.0-SNAPSHOT.jar sb3t-${params.VERSION}-${params.VERSION_TYPE}.jar"
                }
            }
        }
        /*stage('Run') {
            steps {
                script {
                    sh 'cd sb3t/sb3t-ws/src/test/java/com/app/sb3t/ws && java ApplicationTest'
                    currentBuild.displayName = "#${BUILD_NUMBER} ${params.PARAM1}"
                }
            }
        }*/
    }
}