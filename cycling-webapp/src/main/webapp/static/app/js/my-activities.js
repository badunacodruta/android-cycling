var activitiesCount = 0;
var pageSize = 10;
var pageNumber = 1;

$(document).ready(function() {
    getActivitiesCount();
    getActivities();
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

function getActivities() {
    $.ajax({
        type: "GET",
        url: "/api/v1/activities?pageNumber=" + pageNumber + "&pageSize=" + pageSize,
        success: populateTableWithActivities,
        error: displayErrorMessage,
        contentType: "application/json"
    });
}

function populateTableWithActivities(response) {
    $("#activities-table tr td").remove();

    console.log(response);

    for (var i = 0; i < response.length; i++) {
        var activity = response[i];
        var lastUpdateDate = activity.createdDate;
        if (activity.updatedDate) {
            lastUpdateDate = activity.updatedDate;
        }

        $("#activities-table").append( "<tr onclick='viewActivity(" + activity.id + ")'>\
                                            <td>" + activity.name + "</td>\
                                            <td>" + activity.activityAccessType + "</td>\
                                            <td>" +  new Date(activity.createdDate).yyyymmdd() + "</td>\
                                            <td>" + new Date(lastUpdateDate).yyyymmdd() + "</td>\
                                        </tr>"
        );
    }
}

function viewActivity(activityId) {
    window.location.href = "activity.html?id=" + activityId;
}

function setActivitiesCount(response) {
    activitiesCount = response;

    disablePreviousPage();
    if (activitiesCount <= pageSize) {
        disableNextPage();
    }
}

function previousPage() {
    if (pageNumber > 1) {
        pageNumber--;
        getActivities();
        enableNextPage();
    }

    if (pageNumber <= 1) {
        disablePreviousPage();
    }
}

function nextPage() {
    if (pageNumber * pageSize < activitiesCount) {
        pageNumber++;
        getActivities();
        enablePreviousPage();
    }

    if (pageNumber * pageSize >= activitiesCount) {
        disableNextPage();
    }
}

function enablePreviousPage() {
    $("#previous-page").removeClass("disabled");
}

function disablePreviousPage() {
    $("#previous-page").addClass("disabled");
}

function enableNextPage() {
    $("#next-page").removeClass("disabled");
}

function disableNextPage() {
    $("#next-page").addClass("disabled");
}

function displayErrorMessage() {
    $("#error-message").text("An error has occurred while trying to retrieve the activities for the current user!");
    $("#error-message").show();
}
