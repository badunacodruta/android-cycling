google.maps.event.addDomListener(window, 'load', createMap);

var map;
var markersByUnigueId = {};
var coordinatesByUniqueId = {};
var markersCoordinates = [];
var trackCoordinates = [];

var distanceMin = 1;
var distanceMax = 100;

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
    markersCoordinates.push(coordinates);

    bindMarkerEvents(marker);
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
    var index = markersCoordinates.indexOf(coordinates);
    if (index != -1) {
        markersCoordinates.splice(index, 1);
    }
};

var polyline;

function recreateTrack(fitBounds) {
    clearTrack();

    polyline = new google.maps.Polyline({
        path: trackCoordinates,
        map: map,
        strokeColor: "#ff0000",
        strokeOpacity: .7,
        strokeWeight: 4
    });

    if (fitBounds && trackCoordinates.length > 0) {
        var bounds = new google.maps.LatLngBounds();
        for (var i = 0; i < trackCoordinates.length; i++) {
            bounds.extend(trackCoordinates[i]);
        }
        map.fitBounds(bounds);
    }
}

function clearTrack() {
    if (polyline) {
        polyline.setMap(null);
    }
}

function clearAllPins() {
    clearTrack();

    var markerIds = Object.keys(markersByUnigueId);
    for (i = 0; i < markerIds.length; i++) {
        var markerId = markerIds[i];
        markersByUnigueId[markerId].setMap(null);
    }

    markersByUnigueId = {};
    coordinatesByUniqueId = {};
    markersCoordinates = [];
}

function changeAddPins() {
    if ($('#add-pins-switch').hasClass('btn-primary')) {
        google.maps.event.clearListeners(map, 'click');
        google.maps.event.addListener(map, 'click', function (point) {
            addMarker(point);
        });
    } else {
        google.maps.event.clearListeners(map, 'click');
    }
}

function generateTrack() {
    clearTrack();

    var trackDetails = {};
    trackDetails.roadTypes = JSON.stringify(getTypeOfTheRoad());
    //trackDetails.distanceMin = distanceMin;
    //trackDetails.distanceMax = distanceMax;
    trackDetails.coordinates = [];
    for (i = 0; i < markersCoordinates.length; i++) {
        trackDetails.coordinates.push({"lat": markersCoordinates[i].lat(), "lng": markersCoordinates[i].lng()});
    }
    trackDetails.coordinates = JSON.stringify(trackDetails.coordinates);

    $.ajax({
        type: "POST",
        url: "/api/v1/track",
        data: JSON.stringify(trackDetails),
        success: function (track) {

            trackCoordinates = JSON.parse(track.coordinates);

            recreateTrack();
        },
        error: function () {
            displayErrorMessage("An error has occurred while trying to generate the track!")
        },
        contentType: "application/json"
    });
}

//TODO: verify the name of the activity is unique and display en error message
//TODO: verify the required fields
function saveActivity() {
    hideMessages();

    var activityName = $('#activity-name').val();
    var startDate = $('#start-date').val();
    var accessType = getAccessType();

    var activity = {};
    activity.name = activityName;
    activity.startDate = startDate;
    activity.activityAccessType = accessType;
    activity.coordinates = [];
    for (i = 0; i < trackCoordinates.length; i++) {
        activity.coordinates.push({"lat": trackCoordinates[i].lat, "lng": trackCoordinates[i].lng});
    }
    activity.coordinates = JSON.stringify(activity.coordinates);

    if (startDate == "") {
        displayErrorMessage("You must choose a start date for the activity!");
        return;
    }

    if (typeof activityId != 'undefined') {
        activity.id = activityId;
    }

    $.ajax({
        type: "POST",
        url: "/api/v1/activities",
        data: JSON.stringify(activity),
        success: showSuccessMessage,
        error: function () {
            displayErrorMessage("An error has occurred while trying to create the activity!")
        },
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

function getTypeOfTheRoad() {
    var types = [];

    if ($("#paved").prop("checked") == true) {
        types.push("PAVED");
    }

    if ($("#unpaved").prop("checked") == true) {
        types.push("UNPAVED");
    }

    if ($("#gravel").prop("checked") == true) {
        types.push("GRAVEL");
    }

    return types;
}

function showSuccessMessage(response) {
    var message = "Activity " + response.name + " has been successfully saved";

    displaySuccessMessage(message);

    setTimeout(function () {
            location.reload();
        }
        , 1000);

}




