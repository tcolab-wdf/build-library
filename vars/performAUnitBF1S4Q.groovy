def call(def input) {
	def pipelinename = "${env.JOB_BASE_NAME}_${env.BUILD_NUMBER}"
	
	def responseGET = httpRequest url: "http://wdflbmd16752.wdf.sap.corp:50002/sap/bc/build/sync?", 
							   authentication: 'Build', 
							   httpMode: 'GET', 
							   customHeaders: [[maskValue: true, name: 'x-csrf-token', value: 'Fetch']]
					
	def token = responseGET.getHeaders().'x-csrf-token'[0]
	def cookies = responseGET.getHeaders().'set-cookie'

	def cookieList = []
		cookies.each {cookie ->
		def splitCookie = cookie.split(';')[0]
		cookieList.add(splitCookie)
		}

	def responsePOST = httpRequest url: "http://wdflbmd16752.wdf.sap.corp:50002/sap/bc/build/sync?trkorr=${input}&phase=AUNIT&pipeline_build_id=${pipelinename}",
					     authentication: 'Build', 
						 httpMode: 'POST', 
						 customHeaders: [[maskValue: true, name: 'x-csrf-token', value: token], [name: 'Cookie', value: cookieList.join('; ')]]

	def json = new groovy.json.JsonSlurper().parseText(responsePOST.content)
	generateLogOutput(json)
}
