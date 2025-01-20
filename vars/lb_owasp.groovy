package org.devops

class lb_owasp {
    def call(String url) {
        // Lógica para el escaneo OWASP
        sh """
        docker run --rm -v ProjectOwasp:/zap/wrk/:rw \
            --user root --network=${env.NameNetwork} \
            -t owasp/zap2docker-stable \
            zap-full-scan.py \
            -t ${url} \
            -r Projectowasp.html -I
        """
    }
}
