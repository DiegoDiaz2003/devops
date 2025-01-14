pipeline {
    agent any
    tools {
        nodejs 'nodejs' // Nombre configurado en Jenkins
    }
    stages {
        stage('Preparar') {
            steps {
                echo "Preparación completada. Scripts disponibles globalmente."
            }
        }
        stage('Clonar repositorio') {
            steps {
                script {
                    lb_buildartefacto.clone(
                        url: 'https://github.com/DiegoDiaz2003/devops.git',
                        branch: 'develop',
                        credentialsId: 'GitHub-Diego'
                    )
                }
            }
        }
        stage('Instalar dependencias') {
            steps {
                script {
                    echo "Instalando dependencias..."
                    lb_buildartefacto.install()
                }
            }
        }
        stage('Correr el test para análisis en SonarQube') {
            steps {
                script {
                    echo "Ejecutando pruebas de cobertura..."
                    lb_analisissonarqube.testCoverage()
                }
            }
        }
        stage('Análisis con SonarQube') {
            steps {
                script {
                    echo "Ejecutando análisis con SonarQube..."
                    lb_analisissonarqube.analisisSonar(env.GIT_BRANCH)
                }
            }
        }
    }
    post {
        always {
            echo "Pipeline finalizado."
        }
        success {
            echo "Pipeline ejecutado correctamente."
        }
        failure {
            echo "El pipeline falló. Revisar los logs."
        }
    }
}
