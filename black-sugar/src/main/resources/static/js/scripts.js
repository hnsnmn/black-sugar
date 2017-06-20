$('.answer-write button[type=submit]').click(addAnswer);
function addAnswer(e) {
	e.preventDefault();
	
	var queryString = $('.answer-write').serialize();
	console.log("query : " + queryString);
	
	var url = $('.answer-write').attr('action');
	console.log("url : " + url);
	
	$.ajax({
			type : 'post', 
			url : url, 
			data : queryString, 
			dataType : 'json', 
			error : onError, 
			success : onSuccess
	});
}

function onError() {
	
}
function onSuccess(data, status) {
	console.log(data);
	var answerTemplate = $('#answerTemplate').html();
	var template = answerTemplate.format(data.writer.userId, data.contents, data.formattedCreatedDate);
	$('#answer-area').append(template);
	$('textarea').val('');
}


//"{0} is dead, but {1} is alive! {0} {2}".format("ASP", "ASP.NET")
if (!String.prototype.format) {
  String.prototype.format = function() {
    var args = arguments;
    return this.replace(/{(\d+)}/g, function(match, number) { 
      return typeof args[number] != 'undefined'
        ? args[number]
        : match
      ;
    });
  };
}