google.maps.event.addDomListener(window, 'load', drawTrack);
google.maps.event.addDomListener(window, 'load', displayParticipants);

var activityId = getActivityIdFromUrl();
var activity;
var participantMarkers = [];

var setedBounds = false;

function getActivityIdFromUrl() {
    return window.location.href.split("?")[1].split("=")[1];
}

$(document).ready(function() {
    getActivity(updateActivityInfo);

    setInterval(function() {
        getActivity(updateParticipants);
    },2000);
});

function getActivity(onSuccess) {
    $.ajax({
        type: "GET",
        url: "/api/v1/activities/activity?id=" + activityId,
        success: onSuccess,
        error: function() { displayErrorMessage("An error has occurred while trying to retrieve the activity info!") },
        contentType: "application/json"
    });
}

function updateActivityInfo(activityResponse) {
    activity = activityResponse;

    if (new Date(activity.startDate) > new Date()) {
        enableEditActivity();
    } else {
        enableWatchActivity();
    }

    $("#activity-name").val(activity.name);
    $('#start-date').val(activity.startDate);
    setAccessType(activity.activityAccessType);
    drawTrack();
    updateParticipants(activityResponse);
}

function setAccessType(accessType) {
    if (accessType == "PUBLIC") {
        $('#access-type-switch').addClass('btn-primary');
        $('#access-type-switch').removeClass('active off');
    } else {
        $('#access-type-switch').addClass('active off');
        $('#access-type-switch').removeClass('btn-primary');
    }
}

function drawTrack() {
    if (!activity || !map) {
        return;
    }

    var parsedCoordinates = JSON.parse(activity.coordinates);
    coordinatesForTrack = [];
    for (var i = 0; i < parsedCoordinates.length; i++) {
        var parsedCoordinate = parsedCoordinates[i];
        coordinatesForTrack.push(getLatLng(parsedCoordinate.k, parsedCoordinate.B));
    }

    recreateTrack();
}

function enableEditActivity() {
    $('.edit').show();
    $('.watch').hide();
}

function enableWatchActivity() {
    $('.watch').show();
    $('.edit').hide();
}

function changeDisplayTrack() {
    if ($('#display-track-switch').hasClass('btn-primary')) {
        clearTrack();
    } else {
        recreateTrack();
    }
}


function updateParticipants(activityResponse) {
    activity = activityResponse;

    if (new Date(activity.startDate) > new Date()) {
        enableEditActivity();
    } else {
        enableWatchActivity();
    }

    changeDisplayParticipants();
}

function onclickChangeDisplayParticipants() {
    if ($('#display-participants-switch').hasClass('btn-primary')) {
        hideParticipants();
    } else {
        displayParticipants();
    }
}

function changeDisplayParticipants() {
    if ($('#display-participants-switch').hasClass('btn-primary')) {
        displayParticipants();
    } else {
        hideParticipants();
    }
}

function hideParticipants() {
    for (var i = 0; i < participantMarkers.length; i++) {
        participantMarkers[i].setMap(null);
    }

    participantMarkers = [];
}

var bounds;
function displayParticipants() {
    if (!activity || !map || !activity.joinedUsers) {
        return;
    }

    hideParticipants();
    bounds = new google.maps.LatLngBounds();

    for (var i = 0; i < activity.joinedUsers.length; i++) {
        var coord = addParticipant(activity.joinedUsers[i]);
        bounds.extend(coord);
    }

    if (!setedBounds && activity.joinedUsers.length > 0) {
        map.fitBounds(bounds);
        setedBounds = true;
    }
}

function addParticipant(participant) {
//    var point = JSON.parse(participant.coordinates).pop();
    var point = participant.coordinates.pop();
    var coordinates = getLatLng(point.k, point.B);

    var marker = new google.maps.Marker({
        position: coordinates,
        map: map
    });

    participantMarkers.push(marker);

    addViewParticipantEvent(marker, participant.user);

    return coordinates;
}

function addViewParticipantEvent(marker, user) {
    google.maps.event.addListener(marker, "rightclick", function (point) {

        var content = "<div>" +
            "<div>" + user.email + "</div>" +
            "<img src=\"" + user.imageUrl + "\">" +
            "</div>";

        var infowindow = new google.maps.InfoWindow({
            content: content,
            maxWidth: 200
        });

        infowindow.open(map,marker);
    });
};
