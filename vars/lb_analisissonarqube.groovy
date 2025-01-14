package org.devops

def testCoverage() { 
    sh 'npm test'
}

def analisisSonar(gitName) {
    // Asegúrate de que el nombre del scanner coincida con el configurado en Jenkins
    def scannerHome = tool 'sonar-scaner' // Cambiado de 'sonar-scaner' a 'sonar-scanner'
    
    if (scannerHome) {
        // Asegúrate de que el nombre del servidor coincida con el configurado en Jenkins
        withSonarQubeEnv('ServerSonarqube') { // Cambiado de 'sonar-scaner ' a 'ServerSonarqube'
            sh """
            ${scannerHome}/bin/sonar-scaner \
                -Dsonar.projectKey=${gitName} \
                -Dsonar.projectName=${gitName} \
                -Dsonar.sources=src \
                -Dsonar.tests=src/__test__ \
                -Dsonar.exclusions=**/*.test.js \
                -Dsonar.testExecutionReportPaths=./test-report.xml \
                -Dsonar.javascript.lcov.reportPaths=./coverage/lcov.info
            """
        }
    } else {
        error 'SonarQube Scanner not found'
    }
}

