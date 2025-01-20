def call(String url) {
    sh """
    docker run --rm -v ProjectOwasp:/zap/wrk/:rw \
    --user root --network=jenkinsonar_network_containers \
    -t edansama96/zap2docker-stable:latest \
    zap-full-scan.py -t http://172.22.0.2:5174 -r Projectowasp.html -I
    """

}
