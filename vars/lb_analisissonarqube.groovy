package org.devops

def testCoverage(){ 
    sh 'npm test'
}

def analisisSonar(gitName){
    def scannerHome = tool 'sonar-scaner'
    if (scannerHome) {
        withSonarQubeEnv ( 'sonar-scaner ' ){
            sh "${scannerHome}/bin/sonar-scaner -Dsonar.projectKey=${gitName} -Dsonar.projectName=${gitName} -Dsonar.sources=${env.source} -Dsonar.tests=src/_test__ -Dsonar.exclusions='**/*.test.js' -Dsonar.testExecutionReportPaths=./test-report.xml -Dsonar.javascript.lcov.reportPaths=./coverage/lcov.info"
        }
    } else{
        error 'SonarQube Scanner not found'
    }
}
