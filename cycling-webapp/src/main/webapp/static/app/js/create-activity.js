google.maps.event.addDomListener(window, 'load', createMap);

var map;
var markersByUnigueId = {};
var coordinatesByUniqueId = {};
var coordinatesForTrack = [];

function createMap() {
    /* position Amsterdam */
//    TODO: Bucharest
    var latlng = new google.maps.LatLng(52.3731, 4.8922);

    var mapOptions = {
        center: latlng,
        zoom: 13
    };

    map = new google.maps.Map(document.getElementById("map-canvas"), mapOptions);
};

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

function recreateTrack() {
    clearTrack();

    polyline = new google.maps.Polyline({
        path: coordinatesForTrack,
        map: map,
        strokeColor: "#ff0000"
    });
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
        var markerId = markerIds[i]
        markersByUnigueId[markerId].setMap(null);
    }

    markersByUnigueId = {};
    coordinatesByUniqueId = {};
    coordinatesForTrack = [];
}


//
//$(document).ready(function() {
//    $('#add-pins-on').change(
//        function(){
//        if ($(this).is(':checked')) {
//            google.maps.event.clearListeners(map, 'click');
//            google.maps.event.addListener(map, 'click', function(point) {
//                addMarker(point);
//            });
//        }
//        });
//
//    $('#add-pins-off').change(
//        function(){
//            if ($(this).is(':checked')) {
//                google.maps.event.clearListeners(map, 'click');
//            }
//        });
//});


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

