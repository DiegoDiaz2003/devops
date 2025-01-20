def call(Map args) {
    pipeline {
        agent any
        tools {
            nodejs "${args.nodeVersion ?: 'nodejs'}" // Usa el valor pasado o un valor por defecto
        }
        environment {
            projectName = env.GIT_URL.tokenize('/').last().replace('.git', '')
            gitBranch = "${args.gitBranch ?: 'main'}" // Usa el valor pasado o un valor por defecto
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
    }
}
