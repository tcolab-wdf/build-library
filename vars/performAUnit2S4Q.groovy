def call(def input) {
	def resp = httpRequest url: "http://wdflbmd22543.wdf.sap.corp:50002/sap/bc/sut/aunit/start_variant/${input}",
							   authentication: 'Build', 
							   httpMode: 'GET'			
	
	sleep 30
	
	def response = httpRequest ignoreSslErrors: true,
							   authentication: 'Build', 
							   url: "http://wdflbmd22543.wdf.sap.corp:50002/sap/bc/sut/aunit/GET_RESULTS/EXT-${input}"		
	
	Integer ErrorCount = 0
	 
	try {
		def xml = new XmlSlurper().parseText(response.content)
		def ErrorNode = xml.'**'.find { node ->
		  node.name() == 'testsuites' }
		  
		echo 'XML parser nodename '+ ErrorNode.name()
		echo 'XML parser node attributes '+ ErrorNode.attributes()
		echo "AUnit uname : "+ErrorNode.attributes().get('uname')
		echo "AUnit errors : "+ErrorNode.attributes().get('errors')
		
		def Errors = ErrorNode.attributes().get('errors')
		echo "No of AUT errors :"+Errors
		 
		ErrorCount = Errors.toInteger()
	} catch (exc) {
	}
		
	if (ErrorCount != 0) {
		currentBuild.result = 'FAILURE'
		error ("AUnit failed")	
	}
}
