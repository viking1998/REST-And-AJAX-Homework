var pageToLoad;
var playersPerPage = 4;
var endOfPlayersTableReached;
var previousAjaxRequestHasFinished = true;

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
	$('#fname').val("");
	$('#lname').val("");
	$('#height').val("");
	$('#age').val("");
	$('#tname').val("");
	$('#jnum').val("");
}

function loadNextPlayers() {
	$.ajax({
		url: "http://localhost:8080/rest/api/players",
		type: "GET",
		dataType: "json",
		contentType: "text/plain",
		data: {
			page: pageToLoad,
			dataPerPage: playersPerPage
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
				$('#errMsg-div').append("<p><b>No more players to load.</b></p>");
			}
			else if(xhr.status == 400){
				alert("Bad request!");
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
			firstName: $('#fname').val(),
			lastName: $('#lname').val(),
			height: $('#height').val(),
			age: $('#age').val(),
			teamName: $('#tname').val(),
			jerseyNumber: $('#jnum').val()
		}),
		success: function(newPlayer) {
			appendPlayerToTable(newPlayer);
		},
		error: function() {
			alert("Such player already exists!");
		},
		complete: function() {
			clearFormFields();
		}
	});
}

function resetInfiniteScrollAndLoadInitialPlayers() {
	$.ajax({
		url: "http://localhost:8080/rest/api/players",
		type: "GET",
		dataType: "json",
		contentType: "text/plain",
		data: {
			page: pageToLoad,
			dataPerPage: playersPerPage
		},
		success: function(players) {
			pageToLoad++;
			$.each(players, function(index) {
				appendPlayerToTable(players[index]);
			});
		},
		error: function(xhr) {
			if(xhr.status == 400){
				alert("Bad request!");
			}
		}
	});
}

$(document).ready(function() {
	pageToLoad = 1;
	endOfPlayersTableReached = false;
	resetInfiniteScrollAndLoadInitialPlayers();
	$('form').submit(function(e) {
		e.preventDefault();
		if(!$('#fname').val() || !$('#lname').val() || !$('#height').val()
			|| !$('#age').val() || !$('#tname').val() || !$('#jnum').val()) {
			alert("Please fill all of the fields!");
		}
		else {
			createPlayer();
		}
	});
	$(window).scroll(function() {
		if($(window).scrollTop() + $(window).height() >= $('body').height()
			&& !endOfPlayersTableReached && previousAjaxRequestHasFinished) {
			previousAjaxRequestHasFinished = false;
			$('#loader-div').append('<img id="loadingGif" src="ajax-loader.gif" alt="Loading...">');
			setTimeout(loadNextPlayers, 1000);
		}
	});
});