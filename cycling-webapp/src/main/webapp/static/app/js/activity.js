google.maps.event.addDomListener(window, 'load', drawTrack);
google.maps.event.addDomListener(window, 'load', displayParticipants);

var activityId = getActivityIdFromUrl();
var activity;
var participants = [];

var setBounds = false;

//TODO add upload track to edit also

function getActivityIdFromUrl() {
    return window.location.href.split("?")[1].split("=")[1];
}

$(document).ready(function() {
    getActivity(updateActivityInfo);

    setInterval(function() {
        getActivity(updateParticipants);
    },5000);
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

    recreateTrack(true);
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

//TODO check the problem with the change display participants button (on mouse out or smth)
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
    for (var i = 0; i < participants.length; i++) {
        participants[i].marker.setMap(null);
    }
}


var bounds;
function displayParticipants() {
    if (!activity || !map || !activity.joinedUsers) {
        return;
    }

    for (var i = 0; i < participants.length; i++) {
        participants[i].active = false;
    }

    for (var i = 0; i < activity.joinedUsers.length; i++) {
        var joinedUser = activity.joinedUsers[i];
        var isNewUser = true;

        for (var j = 0; j < participants.length; j++) {
            var participant = participants[j];

            if (joinedUser.user.email == participant.user.email) {
                if (joinedUser.progressStatus != 'NOT_STARTED') {
                    updateParticipantPosition(participant, joinedUser.coordinates.pop());
                    participant.active = true;
                }
                isNewUser = false;
            }
        }

        if (isNewUser && joinedUser.progressStatus != 'NOT_STARTED') {
            addParticipant(joinedUser);
        }
    }

    var i = 0;
    while (i < participants.length) {
        if (participants[i].active == false) {
            participants.splice(0,1).marker.setMap(null);
        } else {
            i++;
        }
    }

    bounds = new google.maps.LatLngBounds();
    for (var i = 0; i < participants.length; i++) {
        var coord = participants[i].marker.position;
        bounds.extend(coord);
    }

    if (!setBounds && participants.length > 0) {
        map.fitBounds(bounds);
        setBounds = true;
    }
}

function updateParticipantPosition(participant, point) {
    var coordinates = getLatLng(point.k, point.B);

    var marker = new google.maps.Marker({
        position: coordinates,
        map: map
    });

    addViewParticipantEvent(marker, participant.user);
    participant.marker = marker;
}


function addParticipant(participant) {
    var point = participant.coordinates.pop();
    updateParticipantPosition(participant, point);
    participant.active = true;
    participants.push(participant);
}

function addViewParticipantEvent(marker, user) {
    google.maps.event.addListener(marker, "rightclick", function (point) {

        var content = "<div>" +
            "<div>" + user.email + "</div>" +
            "<img src=\"" + user.imageUrl + "\">" +
            "</div>";

        var infowindow = new google.maps.InfoWindow({
            content: content
        });

        infowindow.open(map,marker);
    });
};
