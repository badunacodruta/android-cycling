window.onload = function() {
    var fileInput = $('#fileInput')[0];
    var fileDisplayArea = $('#fileDisplayArea')[0];

    fileInput.addEventListener('change', function(e) {
        var file = fileInput.files[0];
        var textType = /gpx/;
        fileDisplayArea.innerText = "";

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
    $(xml).find("trkpt").each(function () {
        var lat = $(this).attr("lat");
        var lon = $(this).attr("lon");
        var p = new google.maps.LatLng(lat, lon);
        points.push(p);
    });

    coordinatesForTrack = points;
    recreateTrack(true);
}
