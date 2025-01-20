package org.devops

def call(String projectGitName) {
    echo "Construyendo la imagen Docker para: diegodiaz12/${projectGitName}"
    try {
        sh "docker build -t diegodiaz12/${projectGitName} ."
    } catch (Exception e) {
        error "Error al construir la imagen Docker: ${e.message}"
    }
}
