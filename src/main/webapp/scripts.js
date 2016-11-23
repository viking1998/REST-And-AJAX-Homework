var pageToLoad;
var playersPerPage = 10;
var endOfPlayersTableReached;
var previousAjaxRequestHasFinished;
var heightFilterValue;
var ageFilterValue;
var teamNameFilterValue;
var jerseyNumberFilterValue;

function appendPlayerToTable(player) {
	var tr = $('<tr>');
	tr.append('<td>' + player.id + '</td>');
	tr.append('<td>' + player.firstName + '</td>');
	tr.append('<td>' + player.lastName + '</td>');
	tr.append('<td>' + player.height + '</td>');
	tr.append('<td>' + player.age + '</td>');
	tr.append('<td>' + player.teamName + '</td>');
	tr.append('<td>' + player.jerseyNumber + '</td>');
	tr.append('</tr>');
	$('#playersTable').append(tr);
}

function clearFormFields() {
	$('#firstNameInput').val("");
	$('#lastNameInput').val("");
	$('#heightInput').val("");
	$('#ageInput').val("");
	$('#teamNameInput').val("");
	$('#jerseyNumberInput').val("");
}

function loadNextPlayersWithFilters() {
	$.ajax({
		url: "http://localhost:8080/rest/api/players",
		type: "GET",
		dataType: "json",
		contentType: "text/plain",
		data: {
			page: pageToLoad,
			dataPerPage: playersPerPage,
			age: $('#ageFilter').val(),
			height: $('#heightFilter').val(),
			tName: "",
			jNum: $('#jerseyNumberFilter').val()
		},
		success: function(players) {
			pageToLoad++;
			$.each(players, function(index) {
				appendPlayerToTable(players[index]);
			});
			previousAjaxRequestHasFinished = true;
		},
		error: function(xhr) {
			if(xhr.status == 404) {
				endOfPlayersTableReached = true;
				$('#errMsg-div').append("<p><b>" + xhr.responseText + "</b></p>");
			}
			else if(xhr.status == 400){
				alert(xhr.responseText);
			}
		},
		complete: function() {
			$('#loadingGif').remove();
		}
	}
	);
}

function createPlayer() {
	$.ajax({
		url: "http://localhost:8080/rest/api/players",
		type: "POST",
		contentType: "application/json",
		dataType: "json",
		data: JSON.stringify({
			firstName: $('#firstNameInput').val(),
			lastName: $('#lastNameInput').val(),
			height: $('#heightInput').val(),
			age: $('#ageInput').val(),
			teamName: $('#teamNameInput').val(),
			jerseyNumber: $('#jerseyNumberInput').val()
		}),
		success: function(newPlayer) {
			if(endOfPlayersTableReached) {
				appendPlayerToTable(newPlayer);
			}
		},
		error: function(xhr) {
			alert(xhr.responseText);
		},
		complete: function() {
			clearFormFields();
		}
	});
}

function resetTable() {
	$('#playersTableBody tr').remove();
	pageToLoad = 1;
	endOfPlayersTableReached = false;
	previousAjaxRequestHasFinished = true;
	$('#errMsg-div p').remove();
}

function setupFilters() {
	$('#heightFilter option#heightResetOption').prop('selected', 'selected');
	$('#ageFilter option#ageResetOption').prop('selected', 'selected');
	$('#jerseyNumberFilter option#jerseyNumberResetOption').prop('selected', 'selected');
	for(var i=180; i <= 220; i++) {
		$('#heightFilter').append('<option>' + i + '</option>');
	}
	for(var i=18; i <= 40; i++) {
		$('#ageFilter').append('<option>' + i + '</option>');
	}
	for(var i=1; i < 100; i++) {
		$('#jerseyNumberFilter').append('<option>' + i + '</option>');
	}
}

function showLoadingImage() {
	$('#loader-div').append('<img id="loadingGif" src="ajax-loader.gif" alt="Loading...">');
}

$(document).ready(function() {
	setupFilters();
	pageToLoad = 1;
	endOfPlayersTableReached = false;
	$('#playersTableBody tr').remove();
	loadNextPlayersWithFilters();
	$('form').submit(function(e) {
		e.preventDefault();
		if(!$('#firstNameInput').val() || !$('#lastNameInput').val() 
			|| !$('#heightInput').val() || !$('#ageInput').val() 
			|| !$('#teamNameInput').val() || !$('#jerseyNumberInput').val()) {
			alert("Please fill all of the fields!");
		}
		else {
			createPlayer();
		}
	});
	$('#ageFilter').change(function() {
		resetTable();
		loadNextPlayersWithFilters();
	});
	$('#heightFilter').change(function() {
		resetTable();
		loadNextPlayersWithFilters();
	});
	$('#jerseyNumberFilter').change(function() {
		resetTable();
		loadNextPlayersWithFilters();
	});
	$(window).scroll(function() {
		if($(window).scrollTop() + $(window).height() >= $('body').height()
			&& !endOfPlayersTableReached && previousAjaxRequestHasFinished) {
			previousAjaxRequestHasFinished = false;
			showLoadingImage();
			setTimeout(loadNextPlayersWithFilters, 1000);
		}
	});
});