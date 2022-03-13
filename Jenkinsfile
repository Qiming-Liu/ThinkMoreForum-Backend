pipeline {
  agent any
  environment {
    BACKEND_URL = 'https://github.com/Qiming-Liu/ThinkMoreForum-Backend.git'

    AWS_REGION = 'ap-southeast-2'
    TAG = "${env.BUILD_ID}"
    //AWS ecr
    ECR_TAG = 'thinkmoreforumbackendimage'
    ECR_REPO = '981456608012.dkr.ecr.ap-southeast-2.amazonaws.com/thinkmoreforumbackendimage'
    AWS_CREDS = 'aws_accessid'
    AWS_ECS_CLUSTER = 't2m'
    AWS_ECS_SERVICE = 't22'
    //Dockerhub
    myapp = ''
    JENKINS_DOCKER_HUB_CREDNENT_ID = 'thinkmorebackend_dockerhub'
    DOCKER_HUB_REGISTRY = 'thinkmoreapp'
  }

  stages {
    stage('build project') {
      steps {
        echo "PATH IS :${PATH}"
        sh 'ls -la ./'
      }
    }
    stage('Build docker image for docker hub') {
      steps {
        echo '===========Build docker image=========='
        script {
          // sh 'docker  build .'
          // myapp = docker.build("${IMAGE}")
          myapp = docker.build DOCKER_HUB_REGISTRY + ":$BUILD_NUMBER"
        }
      }
    }
    stage('Push image to Dockerhub') {
      steps {
        echo '===========Push image to Dockerhub=========='
        script {
          docker.withRegistry('https://registry.hub.docker.com', JENKINS_DOCKER_HUB_CREDNENT_ID ) {
            myapp.push()
          }
        }
      }
    }
    stage('Cleaning up') {
      steps {
        sh "docker rmi $DOCKER_HUB_REGISTRY:$BUILD_NUMBER"
      }
    }
    stage('Push image') {
      steps {
        script {
          echo '===========Push image to AWS ECR and Update Service=========='
          withAWS(credentials: "${AWS_CREDS}", region: "${AWS_REGION}") {
            sh "aws ecr get-login-password  --region ${AWS_REGION} | docker login --username AWS --password-stdin ${ECR_REPO}"
            sh "aws ecr batch-delete-image --repository-name ${ECR_TAG} --image-ids imageTag=${ECR_TAG}:latest || aws ecr list-images --repository-name ${ECR_TAG}"
            sh "docker build -t ${ECR_TAG} ."
            sh "docker tag ${ECR_TAG}:latest ${ECR_REPO}:latest"
            sh "docker push ${ECR_REPO}:latest"
            // sh "docker push ${ECR_REPO}:${env.BUILD_ID}"
            sh("aws ecs update-service --cluster ${AWS_ECS_CLUSTER} --service ${AWS_ECS_SERVICE} --force-new-deployment >/dev/null")
          }
        }
      }
    }
  }
    post {
      success {
        echo 'Compiled succeed!'
      }
      failure {
        echo 'Build failed.'
      }
    }
}
