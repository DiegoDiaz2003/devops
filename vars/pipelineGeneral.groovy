def call(Map args) {
    pipeline {
        agent any
        tools {
            nodejs 'nodejs' 
        }

        environment {
            projectName = env.GIT_URL.tokenize('/').last().replace('.git', '')
            gitBranch = "${args.gitBranch ?: 'main'}" // Usa el valor pasado o un valor por defecto
            DOCKER_HOST = 'tcp://host.docker.internal:2375' // Configura la conexión al daemon de Docker
        }

        stages {
            stage('Build Docker Image') {
                steps {
                    script {
                        echo "Ejecutando lb_buildimagen para ${env.projectName}"
                        lb_buildimagen("${env.projectName}")
                    }
                }
            }
            stage('Publish to Docker Hub') {
                steps {
                    script {
                        echo "Ejecutando lb_publicardockerhub para ${env.projectName}"
                        lb_publicardockerhub("${env.projectName}", "diegodiaz12")
                    }
                }
            }
            stage('Deploy Docker Container') {
                steps {
                    script {
                        echo "Ejecutando lb_deploydocker para ${env.projectName}"
                        lb_deploydocker("${env.projectName}", "${env.projectName}-container", 5174)
                    }
                }
            }
            stage('OWASP Scan') {
                steps {
                    script {
                        echo "Ejecutando lb_owasp para ${env.projectName}"
                        lb_owasp("http://localhost:5174")
                    }
                }
            }
        }
    }
}
