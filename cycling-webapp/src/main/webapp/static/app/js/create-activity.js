google.maps.event.addDomListener(window, 'load', initialize);

var map;
var markersByUnigueId = {};
var coordinatesByUniqueId = {};
var coordinatesForTrack = [];

function initialize() {

    /* position Amsterdam */
//    TODO: Bucharest
    var latlng = new google.maps.LatLng(52.3731, 4.8922);

    var mapOptions = {
        center: latlng,
        zoom: 13
    };

    map = new google.maps.Map(document.getElementById("map-canvas"), mapOptions);

    google.maps.event.addListener(map, 'click', function(point) {
        addMarker(point);
    });
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

    if (polyline) {
        polyline.setMap(null);
    }

    polyline = new google.maps.Polyline({
        path: coordinatesForTrack,
        map: map,
        strokeColor: "#ff0000"
    });
}


