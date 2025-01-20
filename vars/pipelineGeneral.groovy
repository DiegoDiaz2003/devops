def call() {
    pipeline {
        agent any
        tools {
            nodejs 'nodejs'
        }
        environment {
            projectName = env.GIT_URL.tokenize('/').last().replace('.git', '')
        }
        stages {
            stage('Build Docker Image') {
                steps {
                    script {
                        lb_buildimagen("${env.projectName}")
                    }
                }
            }
            stage('Publish to Docker Hub') {
                steps {
                    script {
                        lb_publicardockerhub("${env.projectName}", "your-dockerhub-username")
                    }
                }
            }
            stage('Deploy Docker Container') {
                steps {
                    script {
                        lb_deploydocker("${env.projectName}", "${env.projectName}-container", 5174)
                    }
                }
            }
            stage('OWASP Scan') {
                steps {
                    script {
                        lb_owasp("http://localhost:5174")
                    }
                }
            }
        }
        triggers {
        scm('H/5 * * * *') // Verifica cambios cada 5 minutos
        }

    }
}
