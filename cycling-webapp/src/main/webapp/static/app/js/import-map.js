window.onload = function() {
    var fileInput = $('#fileInput')[0];
    var fileDisplayArea = $('#fileDisplayArea')[0];

    fileInput.addEventListener('change', function(e) {
        var file = fileInput.files[0];
        var textType = /gpx/;
        fileDisplayArea.innerText = "";


//        if (file.type.match(textType)) {
        if (file.name.match(textType)) {
            var reader = new FileReader();

            reader.onloadend = function(e) {
                createTrackFromImportedXml(reader.result);
            }

            reader.readAsText(file);
        } else {
            fileDisplayArea.innerText = "File not supported!";
        }
    });
}

function createTrackFromImportedXml(xml) {
    var points = [];
//    var bounds = new google.maps.LatLngBounds();
    $(xml).find("trkpt").each(function () {
        var lat = $(this).attr("lat");
        var lon = $(this).attr("lon");
        var p = new google.maps.LatLng(lat, lon);
        points.push(p);
//        bounds.extend(p);
    });
//    var poly = new google.maps.Polyline({
//        // use your own style here
//        path: points,
//        strokeColor: "#ff0000",
//        strokeOpacity: .7,
//        strokeWeight: 4
//    });
//    poly.setMap(map);
//    // fit bounds to track
//    map.fitBounds(bounds);

    coordinatesForTrack = points;
    recreateTrack();
}
