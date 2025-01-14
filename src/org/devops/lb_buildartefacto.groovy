@Library('devops') _

node {
    stage('Preparar') {
        script {
            def lb_buildartefacto = new org.devops.lb_buildartefacto()
            lb_buildartefacto.clone()
            lb_buildartefacto.install()
        }
    }
}
