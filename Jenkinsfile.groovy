node {  
  stage('Bulid') {
    sh 'gradlew build -x test'
  }
  
  stage('Move') {
    echo 'Hi Hi'
  }  
}
