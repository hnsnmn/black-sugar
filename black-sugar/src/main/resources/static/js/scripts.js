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
	var template = answerTemplate.format(data.writer.userId, data.contents, data.formattedCreatedDate, data.question.id, data.id);
	$('.answer-grid').append(template);
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

$('.link-delete-article').click(delAnswer);

function delAnswer(e) {
	e.preventDefault();
	var delBtnObj = $(this);
	var url = delBtnObj.attr('href');
	console.log("delete :" + url);

	$.ajax({
			type : 'delete', 
			url : url, 
			dataType : 'json', 
			error : function(xhr, status) {
				console.log("error");
			}, 
			success : function(data, status) {
				console.log(data);
				if (data.valid) {
					delBtnObj.closest('.answer-area').remove();
				}
			}
	});
}