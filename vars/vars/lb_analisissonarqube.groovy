package org.devops

def testCoverage() { 
    sh 'npm test'
}

def analisisSonar(gitName) {
    def scannerHome = tool 'sonar-scaner' 
    
    if (scannerHome) {
        withSonarQubeEnv('ServerSonarqube') { 
            sh """
            ${scannerHome}/bin/sonar-scanner \
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
