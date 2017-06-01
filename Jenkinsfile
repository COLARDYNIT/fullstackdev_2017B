stage('build'){
	node {
    	mvnHome = tool 'M3'
    	JAVA_HOME = tool 'java 8'
    	checkout scm
    	sh "'${mvnHome}/bin/mvn' clean test-compile"
    }
}
stage ('test & package'){
    node {
        sh "./mvnw -Pprod package"
        archiveArtifacts 'target/*.war'
    }
}
