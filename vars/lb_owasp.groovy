def call(String containerName) {
    sh """
    docker run --rm -v ProjectOwasp:/zap/wrk/:rw \
    --user root --network=jenkinsonar_network_containers \
    -t edansama96/zap2docker-stable:latest \
    zap-full-scan.py -t http://${containerName}:5174 -r Projectowasp.html -I
    """
}
