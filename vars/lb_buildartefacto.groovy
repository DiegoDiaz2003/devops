package org.devops

def clone() {
    echo "Ejecutando clone() desde lb_buildartefacto"
    git branch: "${env.nameBranch}", url: "${env.UrlGitHub}"
}

def install() {
    echo "Ejecutando install() desde lb_buildartefacto"
    sh 'npm install'
}
