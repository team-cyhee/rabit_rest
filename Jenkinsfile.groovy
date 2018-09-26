node {  
  stage('Checkout') {
    git branch: 'master', credentialsId: '1c530311be4bbab34809d65af76a5424e2c787a6', url: 'https://github.com/team-cyhee/rabit_rest.git'
  }
  
  stage('Bulid') {
    sh 'ls'
    sh 'pwd'
    sh 'gradlew build -x test'
  }
  
  stage('Move') {
    echo 'Hi Hi'
  }  
}
