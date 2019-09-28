import groovy.json.JsonSlurperClassic
import groovy.json.JsonBuilder
import groovy.json.JsonOutput

def call(def server_name, def port, def repo_id, def auth_id) {
	
    def trkorr = ""
    def toolURL = 'http://'+server_name+':'+port+'/sap/bc/cts_abapvcs/repository/'+repo_id+'/getHistory'
    String ABAPURL = toolURL.toURL()
    def resultJSON = 'results/ABAP_'+server_name+'_gCTS_history_'+env.BUILD_NUMBER+'.json'
    def resp = httpRequest authentication: auth_id, url: ABAPURL, outputFile: resultJSON
    echo 'gCTS History : '+ resp.content
    def historyJson = resp.content	
 
    try{
	    def gCTSMap = new JsonSlurperClassic().parseText(historyJson)
	    trkorr = gCTSMap.result[0].request
     } 
     catch (exc)
      {
         echo 'xml parser failed'
         echo 'exception : '+exc
      }
     echo 'trkorr is :'+ trkorr
     return trkorr
}
