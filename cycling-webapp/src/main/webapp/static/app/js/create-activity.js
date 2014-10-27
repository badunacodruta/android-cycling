google.maps.event.addDomListener(window, 'load', createMap);

var map;
var markersByUnigueId = {};
var coordinatesByUniqueId = {};
var coordinatesForTrack = [];

function addMarker(point) {
    var lat = point.latLng.lat();
    var lng = point.latLng.lng();
    var coordinates = getLatLng(lat, lng);
    var markerId = getMarkerUniqueId(lat, lng);

    var marker = new google.maps.Marker({
        position: coordinates,
        map: map,
        id: 'marker_' + markerId
    });

    markersByUnigueId[markerId] = marker;
    coordinatesByUniqueId[markerId] = coordinates;
    coordinatesForTrack.push(coordinates);

    bindMarkerEvents(marker);

    recreateTrack();
}

function getMarkerUniqueId(lat, lng) {
    return lat + '_' + lng;
}

function getLatLng(lat, lng) {
    return new google.maps.LatLng(lat, lng);
}

function bindMarkerEvents(marker) {
    google.maps.event.addListener(marker, "rightclick", function (point) {
        var markerId = getMarkerUniqueId(point.latLng.lat(), point.latLng.lng()); // get marker id by using clicked point's coordinate
        removeMarker(markerId);
    });
};

function removeMarker(markerId) {
    var marker = markersByUnigueId[markerId];
    marker.setMap(null);
    delete markersByUnigueId[markerId];

    var coordinates = coordinatesByUniqueId[markerId];
    delete coordinatesByUniqueId[markerId];
    var index = coordinatesForTrack.indexOf(coordinates);
    if (index != -1) {
        coordinatesForTrack.splice(index, 1);
    }

    recreateTrack();
};

var polyline;

function recreateTrack(fitBounds) {
    clearTrack();

    polyline = new google.maps.Polyline({
        path: coordinatesForTrack,
        map: map,
        strokeColor: "#ff0000",
        strokeOpacity: .7,
        strokeWeight: 4
    });

    if (fitBounds && coordinatesForTrack.length > 0) {
        var bounds = new google.maps.LatLngBounds();
        for (var i = 0; i < coordinatesForTrack.length; i++) {
            bounds.extend(coordinatesForTrack[i]);
        }
        map.fitBounds(bounds);
    }
}

function clearTrack() {
    if (polyline) {
        polyline.setMap(null);
    }
}

function clearFile() {
    var fileInput = $('#fileInput');
    if (fileInput.length > 0) {
        fileInput[0].value = "";
    }
}

function clearAllPins() {
    clearFile();
    clearTrack();

    var markerIds = Object.keys(markersByUnigueId);
    for (i = 0; i < markerIds.length; i++) {
        var markerId = markerIds[i];
        markersByUnigueId[markerId].setMap(null);
    }

    markersByUnigueId = {};
    coordinatesByUniqueId = {};
    coordinatesForTrack = [];
}

function changeAddPins() {
    if ($('#add-pins-switch').hasClass('btn-primary')) {
        google.maps.event.clearListeners(map, 'click');
        google.maps.event.addListener(map, 'click', function(point) {
            addMarker(point);
        });
    } else {
        google.maps.event.clearListeners(map, 'click');
    }
}


//TODO: verify the name of the activity is unique and display en error message
function saveActivity() {
    hideMessages();

    var activityName = $('#activity-name').val();
    var startDate = $('#start-date').val();
    var accessType = getAccessType();

    var activity = {};
    activity.name = activityName;
    activity.startDate = startDate;
    activity.activityAccessType = accessType;
    activity.coordinates = JSON.stringify(coordinatesForTrack);


    if (typeof activityId != 'undefined') {
        activity.id = activityId;
    }

    $.ajax({
        type: "POST",
        url: "/api/v1/activities",
        data: JSON.stringify(activity),
        success: showSuccessMessage,
        error: function() { displayErrorMessage("An error has occurred while trying to create the activity!") },
        contentType: "application/json"
    });
}

function getAccessType() {
    if ($('#access-type-switch').hasClass('btn-primary')) {
       return "PUBLIC";
    } else {
        return "REQUEST";
    }
}

function showSuccessMessage(response) {
    var message = "Activity " + response.name + " has been successfully saved";

    displaySuccessMessage(message);

    setTimeout(function()
        { location.reload(); }
        , 1000);

}

