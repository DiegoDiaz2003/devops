package org.devops

def buildImageDocker(projectGitName) {
    sh "docker build -t diegodiaz12/${projectGitName} ."
}