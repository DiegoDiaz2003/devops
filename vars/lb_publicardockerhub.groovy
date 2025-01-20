def call(String projectName, String dockerHubUsername) {
    withCredentials([usernamePassword(credentialsId: "${env.TOKEN_ID}", 
    passwordVariable: 'DOCKERHUB_PASSWORD', usernameVariable: 'DOCKERHUB_USERNAME')]) {
        sh "docker login -u ${env.DOCKERHUB_USERNAME} -p ${env.DOCKERHUB_PASSWORD}"
        sh "docker tag ${projectName} ${env.DOCKERHUB_USERNAME}/${projectName}"
        sh "docker push ${env.DOCKERHUB_USERNAME}/${projectName}"
    }
}
