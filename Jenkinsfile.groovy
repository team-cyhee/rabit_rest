node {  
  stage('Bulid') {
    sh 'ls'
    sh 'pwd'
    sh 'gradlew build -x test'
  }
  
  stage('Move') {
    echo 'Hi Hi'
  }  
}
