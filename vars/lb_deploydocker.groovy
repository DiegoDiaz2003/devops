package org.devops

def despliegueContenedor(projectGitName) {
    //sh "docker stop ${projectGitName}"
    //sh "docker rm ${projectGitName}"
    sh "docker pull diegodiaz12/react-test-jenkinsfile"
    sh """
    docker run -d --name ${projectGitName} \
        --network=${env.NameNetwork} -p 5174:5174 \
        --user root diegodiaz12/${projectGitName}
    """
}
