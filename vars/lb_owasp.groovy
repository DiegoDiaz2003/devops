package org.devops

class lb_owasp {
    def AnalisisOwasp(String projectGitName) {
        sh """
        docker run --rm -v ProjectOwasp:/zap/wrk/:rw \
            --user root --network=${env.NameNetwork} \
            -t owasp/zap2docker-stable \
            zap-full-scan.py \
            -t ${env.dominio} \
            -r Projectowasp.html -I
        """
    }
}
