package org.devops

def call(String projectGitName) {
    sh "docker build -t diegodiaz12/${projectGitName} ."
}
