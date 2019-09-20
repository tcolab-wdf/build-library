def call(def server_name, def port, def variant, def auth_id) {
	
    def toolURL = 'http://'+server_name+':'+port+'/sap/bc/sut/aunit/EXECUTE_VARIANT/'+ variant
    String ABAPURL = toolURL.toURL()
    def resultXML = 'results/ABAP_'+server_name+'_aunit_result_'+env.BUILD_NUMBER+'.xml'
    def resp = httpRequest authentication: auth_id, url: ABAPURL, outputFile: resultXML
    echo 'Unit Test Results : '+ resp.content
    junit testResults:'results/ABAP_'+server_name+'_aunit_result_*.xml'
    if (currentBuild.result == 'UNSTABLE')
	{
		echo 'Some of the Unit Tests Failed, please check the Test Results in the Build Log '
		currentBuild.result = 'FAILURE'
    		throw err
	}
}
