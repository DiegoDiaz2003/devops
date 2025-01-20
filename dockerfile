FROM jenkins/jenkins:lts
USER root

# Instalar dependencias necesarias
RUN apt-get update && \
    apt-get install -y \
    apt-transport-https \
    ca-certificates \
    curl \
    gnupg2 \
    lsb-release \
    software-properties-common && \
    curl -fsSL https://download.docker.com/linux/debian/gpg | apt-key add && \
    echo "deb [arch=amd64] https://download.docker.com/linux/debian $(lsb_release -cs) stable" > /etc/apt/source && \
    apt-get update && \
    apt-get install -y docker-ce-cli maven && \
    groupadd docker && \
    usermod -aG docker jenkins && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

USER jenkins
