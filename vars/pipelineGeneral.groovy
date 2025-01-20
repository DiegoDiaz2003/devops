def call(Map args) {
    pipeline {
        agent any
        tools {
            nodejs 'nodejs' 
        }

        environment {
            projectName = env.GIT_URL.tokenize('/').last().replace('.git', '')
            gitBranch = "${args.gitBranch ?: 'develop'}" // Usa el valor pasado o un valor por defecto
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
                        lb_deploydocker.despliegueContenedor('devops', 'devops-container', 5174)
                    }
                }
            }

    
            
  stage('OWASP Scan') {
    steps {
        script {
            if (fileExists('vars/lb_owasp.groovy')) {
                load 'vars/lb_owasp.groovy'
                AnalisisOwasp("devops")
            } else {
                error "Archivo lb_owasp.groovy no encontrado"
            }
        }
    }
}





        }
    }
}
