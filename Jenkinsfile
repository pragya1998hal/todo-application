
pipeline {
  agent any

  environment {
    DOCKERHUB_REPO = "pragjais98/todo-application"
    IMAGE_NAME     = "todo-application-image"
    IMAGE_TAG      = "latest"
  }

  stages {
    stage('Checkout') {
      steps {
        checkout([$class: 'GitSCM',
          branches: [[name: '*/master']],
          userRemoteConfigs: [[
            url: 'https://github.com/pragya1998hal/todo-application.git',
            credentialsId: 'github-credentials' // remove if public
          ]]
        ])
      }
    }

    stage('Build with Maven') {
      steps {
        sh 'mvn -DskipTests clean package'
      }
    }

    stage('Build Docker Image') {
      steps {
        sh "docker build -t ${IMAGE_NAME}:${IMAGE_TAG} ."
      }
    }

    stage('Tag & Push to Docker Hub') {
      steps {
        withCredentials([usernamePassword(credentialsId: 'docker-hub-credentials', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
          sh 'echo "$DOCKER_PASS" | docker login -u "$DOCKER_USER" --password-stdin'
          sh "docker tag ${IMAGE_NAME}:${IMAGE_TAG} ${DOCKERHUB_REPO}:${IMAGE_TAG}"
          sh "docker push ${DOCKERHUB_REPO}:${IMAGE_TAG}"
        }
      }
    }

    stage('Deploy with Docker Compose') {
      steps {
        sh 'docker compose down || true'
        sh 'docker compose up -d'
        sh 'docker compose ps'
      }
    }

    stage('Verify Services') {
      steps {
        sh 'docker ps'
        sh 'curl -sSf http://localhost:8082 >/dev/null && echo "App reachable on 8082"'
      }
    }

    stage('Clean Workspace') {
      steps {
        sh 'rm -rf *'
      }
    }
  }

  post {
    always {
      sh 'docker logout || true'
    }
    success {
      echo 'Build successful!'
    }
    failure {
      echo 'Build failed!'
    }
  }
}

