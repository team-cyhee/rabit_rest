node {
  stage('Checkout') {
    git branch: 'develop', url: 'https://github.com/team-cyhee/rabit_rest.git', credentialsId: 'whgksdyd112'
  }
  
  stage('Bulid') {
    sh 'gradlew build -x test'
  }
  
  stage('Move') {
    echo 'Hi Hi'
  }  
}
