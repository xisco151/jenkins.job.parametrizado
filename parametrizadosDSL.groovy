job('ejemplo2-job-DSL') {
	description('Job DSL de ejemplo para el curso jenkins') 
  scm {
    git('https://github.com/xisco151/jenkins.job.parametrizado.git', 'master') { node -> 
      node / gitConfigName('xisco')
      node / gitConfigEmail('franciscoelrio@gmail.com')
      
    }
  }
  parameters {
    	stringParam('nombre', defaultValue = 'Fran', description = 'Parámetro de cadena')
    	choiceParam('planeta', ['Mercurio', 'Venus', 'Tierra', 'Marte', 'Jupiter', 'Saturno', 'Urano', 'Nepturno'])
    	booleanParam('agente', false)
  }
  triggers {
    	cron('H/7 * * * *')
        githubPush()
  }
  steps {
    	dockerBuildAndPublish {
            repositoryName('xisco151/nodejsapp')
            tag('${GIT_REVISION,length=7}')
            registryCredentials('docker-hub')
            forcePull(false)
            createFigerprints(false)
            skipDecorate()
        }
  }
  publishers {
   		mailer('franciscoelrio@gmail.com',true,true)
        slackNotifier {
            notifyAborted(true)
            notifyEveryFailure(true)
            notifyNotBuilt(false)
            notifyUnstable(false)
            notifyBackToNormal(true)
            notifySuccess(true)
            notifyRepeatedFailure(false)
            startNotification(false)
            includeTestSummary(false)
            includeCustomMessage(false)
            customMessage(null)
            sendAs(null)
            commitInfoChoice('NONE')
            teamDomain(null)
            authToken(null)
        }
  }
}