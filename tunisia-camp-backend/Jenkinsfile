pipeline {
    agent any
    tools {
        maven 'Maven_3.9.0' // Name of the Maven installation defined in Jenkins
    }
    stages {
        stage('Build') {
            steps {
                sh 'mvn clean'
                sh 'mvn clean package -DskipTests'

            }
        }
      /* stage('Start Services') {
          steps {
            sh 'docker images'
            // start your services using Docker Compose
            sh 'dockerCompose up -d'
          }
      }*/
      stage('SRC Analysis Testing') {
        steps {
             sh "mvn sonar:sonar -Dsonar.login=admin -Dsonar.password=admin123"
          }
      }
      /*stage("Publish to Nexus Repository Manager") {
                steps {
                    script {
                        pom = readMavenPom file: "pom.xml";
                        filesByGlob = findFiles(glob: "target/*.${pom.packaging}");
                        echo "${filesByGlob[0].name} ${filesByGlob[0].path} ${filesByGlob[0].directory} ${filesByGlob[0].length} ${filesByGlob[0].lastModified}"
                        artifactPath = filesByGlob[0].path;
                        artifactExists = fileExists artifactPath;
                        if(artifactExists) {
                            echo "*** File: ${artifactPath}, group: ${pom.groupId}, packaging: ${pom.packaging}, version ${pom.version}";
                            nexusArtifactUploader(
                                nexusVersion: NEXUS_VERSION,
                                protocol: NEXUS_PROTOCOL,
                                nexusUrl: NEXUS_URL,
                                groupId: pom.groupId,
                                version: pom.version,
                                repository: NEXUS_REPOSITORY,
                                credentialsId: NEXUS_CREDENTIAL_ID,
                                artifacts: [
                                    [artifactId: pom.artifactId,
                                    classifier: '',
                                    file: artifactPath,
                                    type: pom.packaging],
                                    [artifactId: pom.artifactId,
                                    classifier: '',
                                    file: "pom.xml",
                                    type: "pom"]
                                ]
                            );
                        } else {
                            error "*** File: ${artifactPath}, could not be found";
                        }
                    }
                }
            }*/

        /*stage('Docker Build') {
            steps {
                sh "docker build -t nidhalbm/springapp ." // the . in the end is very important : tells jenkins to use DockerFile in the current directory
            }
       }*/
        /*stage('Docker Push') {
            steps {
              sh 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin'
            sh "docker push nidhalbm/springapp"

            }
       }*/
      /*stage('Deploy') {
           steps {
               sh 'docker-compose up -d'
           }
       }*/
    }
}
