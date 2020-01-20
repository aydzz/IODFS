function callGraph(range){
	$('#line_chart').empty();
	$('#data-loading').show();
	/* $('.loader').show(); */
	$.ajax({
		type : "POST",
		contentType : "application/json",
		url :"/api/fetch-document-activity",
		data: JSON.stringify(range),
		dataType : 'json',
		cache : false,
		timeout : 600000,
		success : function(data) {

			getMorris('line_chart',data);
			console.log("SUCCESS : ", data);
		},
		complete : function(e){
			/* $('.loader').hide(); */
			$('#data-loading').hide();
		},
		error : function(e) {	
			console.log("ERROR : ", e);

		}

	});

}
function getMorris(element, data) {
	Morris.Line({
		element: element,
		data: data.activityList,
		xkey: 'dateOfActivity',
		ykeys: ['numberOfAction','transfer', 'release','creation','successfulTransfer'],
		labels: ['Actions','Transfers','Release','Creation','Delivered'],
		lineColors: ['rgb(233, 30, 99)','rgb(0, 241, 245)','rgb(36, 242, 50)','rgb(255, 152, 0)','rgb(247, 235, 0)'],
		lineWidth: 3,
		xLabels:["day"],
		parseTime: true
	}); 


}

$(function() {
	$('#data-loading').hide();
	callGraph(7);
});