def call(def server_name, def port, def repo_id, def commit_id, def auth_id) {
	
    def trkorr = ""
    def toolURL = 'http://'+server_name+':'+port+'/sap/bc/cts_abapvcs/repository/'+repo_id+'/pullByCommit?request='+commit_id+'sap-client=000'
    String ABAPURL = toolURL.toURL()
    def resultJSON = 'results/ABAP_'+server_name+'_gCTS_result_'+env.BUILD_NUMBER+'.json'
    def resp = httpRequest authentication: auth_id, url: ABAPURL, outputFile: resultJSON
    echo 'gCTS PullByCommit Log : '+ resp.content
    trkorr = parseJsonAttributes (resp.content, 'trkorr')
    echo 'trkorr is :'+ trkorr
    if (trkorr == null)
    {
      echo 'No new changes to ABAP objects'
      currentBuild.result = 'FAILURE'
      throw err
  }
}
