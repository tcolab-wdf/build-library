def call(json) {
	echo json.RESULTS.ID
	echo json.RESULTS.STATE
	
	json.RESULTS.LOG.each {
		echo it.LINE
	}
	
	if (json.RESULTS.STATE == "FAILED") {
		currentBuild.result = 'FAILURE'
		error("Issues found")
	}
}