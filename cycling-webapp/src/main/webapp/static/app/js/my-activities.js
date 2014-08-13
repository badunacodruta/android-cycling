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
        error: function() { displayErrorMessage("An error has occurred while trying to retrieve the activities count!") },
        contentType: "application/json"
    });
}

function getActivities() {
    $.ajax({
        type: "GET",
        url: "/api/v1/activities/info?pageNumber=" + pageNumber + "&pageSize=" + pageSize,
        success: populateTableWithActivities,
        error: function() { displayErrorMessage("An error has occurred while trying to retrieve the activities list!") },
        contentType: "application/json"
    });
}

//TODO: transform the enum values to some nicer strings
function populateTableWithActivities(response) {
    $("#activities-table tr td").remove();

    console.log(response);

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
                                            <td" + onClick + ">" + activity.state + "</td>\
                                            <td class='glyphicon glyphicon-trash' onclick='deleteActivity(" + activity.id + ")'></td>\
                                        </tr>"
        );
    }
}

function viewActivity(activityId) {
    window.location.href = "activity.html?id=" + activityId;
}

function deleteActivity(activityId) {
    console.log("delete " + activityId);
//    TODO
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


