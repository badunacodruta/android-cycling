var activitiesCount = 0;
var pageSize = 10;
var pageNumber = 1;

$(document).ready(function() {
    updateActivities();
});

function updateActivities() {
    $.ajax({
        type: "GET",
        url: "/api/v1/activities/count",
        success: getActivities,
        error: function() { displayErrorMessage("An error has occurred while trying to retrieve the activities count!") },
        contentType: "application/json"
    });
}

function getActivities(response) {
    activitiesCount = response;
    checkPreviousPage();
    checkNextPage();

    $.ajax({
        type: "GET",
        url: "/api/v1/activities/summary?pageNumber=" + pageNumber + "&pageSize=" + pageSize,
        success: populateTableWithActivities,
        error: function() { displayErrorMessage("An error has occurred while trying to retrieve the activities list!") },
        contentType: "application/json"
    });
}

//TODO: transform the enum values to some nicer strings
function populateTableWithActivities(response) {
    $("#activities-table tr td").remove();

    for (var i = 0; i < response.length; i++) {
        var activity = response[i];
        var lastUpdateDate = activity.createdDate;
        if (activity.updatedDate) {
            lastUpdateDate = activity.updatedDate;
        }

        var onClick = " onclick='viewActivity(" + activity.id + ")'";
        $("#activities-table").append( "<tr>\
                                            <td" + onClick + ">" + activity.name + "</td>\
                                            <td" + onClick + ">" + activity.activityAccessType + "</td>\
                                            <td" + onClick + ">" +  new Date(activity.createdDate).yyyymmdd() + "</td>\
                                            <td" + onClick + ">" + new Date(lastUpdateDate).yyyymmdd() + "</td>\
                                            <td" + onClick + ">" + new Date(activity.startDate).yyyymmdd() + "</td>\
                                            <td" + onClick + ">" + activity.progressStatus + "</td>\
                                            <td class='glyphicon glyphicon-trash' onclick='deleteActivity(" + activity.id + ")'></td>\
                                        </tr>"
        );
    }
}

function viewActivity(activityId) {
    window.location.href = "activity.html?id=" + activityId;
}

function deleteActivity(activityId) {
    $.ajax({
        type: "DELETE",
        url: "/api/v1/activities/activity?id=" + activityId,
        success: updateActivities,
        error: function() { displayErrorMessage("An error has occurred while trying to delete the activity!") },
        contentType: "application/json"
    });
}

function previousPage() {
    if (pageNumber > 1) {
        pageNumber--;
        updateActivities();
    }

    checkPreviousPage();
    checkNextPage();
}

function nextPage() {
    if (pageNumber * pageSize < activitiesCount) {
        pageNumber++;
        updateActivities();
    }

    checkPreviousPage();
    checkNextPage();
}

function checkPreviousPage() {
    if (pageNumber <= 1) {
        disablePreviousPage()
    } else {
        enablePreviousPage();
    }
}

function checkNextPage() {
    if (pageNumber * pageSize >= activitiesCount) {
        disableNextPage();
    } else {
        enableNextPage();
    }
}

function enablePreviousPage() {
    $("#previous-page").removeClass("disabled");
}

function disablePreviousPage() {
    if (!$("#previous-page").hasClass("disabled")) {
        $("#previous-page").addClass("disabled");
    }
}

function enableNextPage() {
    $("#next-page").removeClass("disabled");
}

function disableNextPage() {
    if (!$("#next-page").hasClass("disabled")) {
        $("#next-page").addClass("disabled");
    }
}


