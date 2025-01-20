def call(String projectName, String dockerHubUsername) {
    withCredentials([usernamePassword(credentialsId: "${env.TOKEN_ID}", 
    passwordVariable: 'DOCKERHUB_PASSWORD', usernameVariable: 'DOCKERHUB_USERNAME')]) {
        // Iniciar sesión en Docker Hub de manera segura
        sh "echo \$DOCKERHUB_PASSWORD | docker login -u ${dockerHubUsername} --password-stdin"
        
        // Etiquetar la imagen correctamente
        sh "docker tag diegodiaz12/${projectName} ${dockerHubUsername}/${projectName}:latest"
        
        // Publicar la imagen en Docker Hub
        sh "docker push ${dockerHubUsername}/${projectName}:latest"
    }
}
