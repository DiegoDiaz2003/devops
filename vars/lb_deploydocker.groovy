package org.devops

def despliegueContenedor(String projectGitName, String containerName, int port) {
    // Detener y eliminar el contenedor si ya existe (opcional)
    // sh "docker stop ${containerName}"
    // sh "docker rm ${containerName}"
    
    // Descargar la imagen más reciente
    sh "docker pull diegodiaz12/${projectGitName}"

    // Ejecutar el contenedor
  sh """
  docker run -d --name ${projectGitName} \
    --network=jenkinsonar_network_containers -p 5174:5174 \
    --user root diegodiaz12/${projectGitName}
  """

}
