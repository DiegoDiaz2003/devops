// lb_owasp.groovy
def call(projectGitName) {
    sh """
    docker run --rm -v ProjectOwasp:/zap/wrk/:rw \
    --user root --network=${env.NameNetwork} \
    -t edansama96/zap2docker-stable:latest \
    zap-full-scan.py \
    -t ${env.dominio} \
    -r ${projectGitName}.html -I
    """
}
