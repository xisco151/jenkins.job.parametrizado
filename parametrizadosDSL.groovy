job('ejemplo:2-job-DSL') {
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
  }
  steps {
    	shell("bash jobscript.sh")
  }
  publishers {
   		mailer('franciscoelrio@gmail.com',true,true)
        slackNotifier {
            notifyAborted(true)
            notifyEveryFailure(true)
            notifyNotBuilt(false)
            notifyUnstable(false)
            notifyBackToNormal(true)
            notifySuccess(false)
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