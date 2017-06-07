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
stage('img build'){
    input "Build raspberry image?"
    node {
        sh "sudo rm -rf image/work/**/export-image/*.img"
        dir("image"){
            git branch: 'develop', url: 'git@github.com:COLARDYNIT/pi-gen.git'
        }
        sh"mv target/*war image/stage2/01-sys-tweaks/files/"
        sh "sudo image/build.sh"
        archiveArtifacts 'image/work/*-dockerpi/export-image/*.img'
    }
}
