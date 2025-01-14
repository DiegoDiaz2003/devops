def lb_buildartefacto
def lb_analisissonarqube

def call(Map config) {
    // Validación de parámetros
    if (!config.containsKey('nodeVersion')) {
        error "El parámetro 'nodeVersion' es obligatorio."
    }
    if (!config.containsKey('gitBranch')) {
        error "El parámetro 'gitBranch' es obligatorio."
    }

    pipeline {
        agent any
        tools {
            nodejs 'nodejs' // Usa el nombre configurado en Jenkins
        }
        environment {
            GIT_BRANCH = "${config.gitBranch}" // Define la rama
        }
        stages {
            stage('Preparar') {
                steps {
                    script {
                        // Obtener el directorio de trabajo
                        def workspaceDir = pwd()

                        // Cargar scripts dinámicamente desde la carpeta actual
                        lb_buildartefacto = load("${workspaceDir}/src/org/devops/lb_buildartefacto.groovy")
                        lb_analisissonarqube = load("${workspaceDir}/src/org/devops/lb_analisissonarqube.groovy")

                        echo "Scripts cargados correctamente."
                    }
                }
            }
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
