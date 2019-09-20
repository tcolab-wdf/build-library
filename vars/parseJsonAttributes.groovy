import groovy.json.JsonSlurperClassic
import groovy.json.JsonBuilder
import groovy.json.JsonOutput

def call (jsonString, attribute)
{
   def attValue = ""
   try{
    
    def gCTSMap = new JsonSlurperClassic().parseText(jsonString)
    attValue = gCTSMap.result[0].request
    echo 'Attribute '+attribute+' Value is :'+attValue
     } 
     catch (exc)
      {
         echo 'xml parser failed'
         echo 'exception : '+exc
        //echo 'Exception stack trace : '+exc.printStackTrace()
         echo 'In catch : Attribute '+attribute+' Value is :'+attValue
         return attValue
     }
    return attValue
}
