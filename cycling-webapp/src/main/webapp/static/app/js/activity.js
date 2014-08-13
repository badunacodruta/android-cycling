var activityId = getActivityIdFromUrl();
var map;

function getActivityIdFromUrl() {
    return window.location.href.split("?")[1].split("=")[1];
}

google.maps.event.addDomListener(window, 'load', createMap);

function createMap() {
    var latlng = new google.maps.LatLng(44.4325, 26.1039);

    var mapOptions = {
        center: latlng,
        zoom: 13
    };

    map = new google.maps.Map(document.getElementById("map-canvas"), mapOptions);
};



$(document).ready(function() {



    $("#activity-name").text("test TODO");
});
