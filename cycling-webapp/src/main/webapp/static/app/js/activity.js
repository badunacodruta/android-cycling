var activityId = getActivityIdFromUrl();
var map;

function getActivityIdFromUrl() {
    return window.location.href.split("?")[1].split("=")[1];
}

$(document).ready(function() {
    createMap();
    getActivity();
});

function getActivity() {
    $.ajax({
        type: "GET",
        url: "/api/v1/activities/activity?id=" + activityId,
        success: updateActivityInfo,
        error: function() { displayErrorMessage("An error has occurred while trying to retrieve the activity info!") },
        contentType: "application/json"
    });
}

function updateActivityInfo(response) {
    console.log(response);

    $("#activity-name").attr("placeholder", response.name);


//    TODO
}

function saveActivity() {
//    TODO
}