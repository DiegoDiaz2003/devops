package org.devops

def clone() {
    echo "Ejecutando clone() desde lb_buildartefacto"
    git url: 'https://github.com/DiegoDiaz2003/devops.git', branch: 'develop', credentialsId: 'GitHub-Diego'
}

def install() {
    echo "Ejecutando install() desde lb_buildartefacto"
    sh 'npm install'
}
