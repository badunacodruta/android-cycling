var activitiesCount = 0;
var pageSize = 10;
var pageNumber = 1;
var numberOfPages = 1;

$(document).ready(function() {
    getActivitiesCount();
    getActivities(pageNumber, pageSize);
});

function getActivitiesCount() {
    $.ajax({
        type: "GET",
        url: "/api/v1/activities/count",
        success: setActivitiesCount,
        error: displayErrorMessage,
        contentType: "application/json"
    });
}

function getActivities(pageNumber, pageSize) {
    $.ajax({
        type: "GET",
        url: "/api/v1/activities?pageNumber=" + pageNumber + "&pageSize=" + pageSize,
        success: populateTableWithActivities,
        error: displayErrorMessage,
        contentType: "application/json"
    });
}



function setActivitiesCount(response) {
    activitiesCount = response;
    updateNumberOfPages();
    updatePagination();
}

function updateNumberOfPages() {
    numberOfPages = activitiesCount / pageSize;
    if (activitiesCount % pageNumber != 0) {
        numberOfPages++;
    }
}

function updatePagination() {
//    TODO
}

function populateTableWithActivities(response) {
    pageNumber++;

    console.log(response);

    for (var i = 0; i < response.length; i++) {

    }
}

function displayErrorMessage() {
    $("#error-message").text("An error has occurred while trying to retrieve the activities for the current user!");
    $("#error-message").show();
}


