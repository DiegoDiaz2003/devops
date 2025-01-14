def call(Map config) {
    // Validación de parámetros 
    if (!config.containsKey('nodeVersion')) {
        error "El parámetro 'nodeVersion' es obligatorio."
    }
    if (!config.containsKey('gitBranch')) {
        error "El parámetro 'gitBranch' es obligatorio."
    }

    // Cargar scripts dinámicamente desde la carpeta actual
    def lb_buildartefacto = load("${WORKSPACE}/org/devops/lb_buildartefacto.groovy")
    def lb_analisissonarqube = load("${WORKSPACE}/org/devops/lb_analisissonarqube.groovy")

    pipeline {
        agent any
        tools {
            nodejs config.nodeVersion // Configuración dinámica del entorno NodeJS
        }
        environment {
            GIT_BRANCH = config.gitBranch // Configuración dinámica del branch
        }
        stages {
            stage('Clonar repositorio') {
                steps {
                    script {
                        echo "Clonando el repositorio..."
                        lb_buildartefacto.clone()
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
}
