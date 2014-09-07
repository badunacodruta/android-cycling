var pageSize = 5;
var pageNumber = 1;
var requests;

$(document).ready(function() {
    getRequests();
});

function getRequests() {
    $.ajax({
        type: "GET",
        url: "/api/v1/requests?joinRequestType=RECEIVED",
        success: updateRequests,
        error: function() { displayErrorMessage("An error has occurred while trying to retrieve the join request notifications!") },
        contentType: "application/json"
    });
}

function updateRequests(response) {
    console.log(response);
    requests = response;

    populateTableWithRequests();
}

function populateTableWithRequests() {
//TODO: transform the enum values to some nicer strings

    checkPreviousPage();
    checkNextPage();

    $("#requests-table tr td").remove();

    for (var i = 0; i + (pageNumber - 1 ) * pageSize < requests.length && i < pageSize; i++) {
        var request = requests[i + (pageNumber - 1 ) * pageSize];

        $("#requests-table").append( "<tr>\
                                            <td>" + request.activityName + "</td>\
                                            <td>" + new Date(request.startDate).yyyymmdd() + "</td>\
                                            <td>" + request.user.email + "</td>\
                                            <td> <img class=\"user-icon\" src=\""+ request.user.imageUrl +"\"></td>\
                                            <td class='glyphicon glyphicon-ok hover' onclick='acceptRequest(" + request.id + ")'></td>\
                                            <td class='glyphicon glyphicon-remove hover' onclick='declineRequest(" + request.id + ")'></td>\
                                        </tr>"
        );
    }
}

function acceptRequest(requestId) {
    console.log("accept " + requestId);

    $.ajax({
        type: "POST",
        url: "/api/v1/requests/accept",
        data: JSON.stringify(requestId),
        success: getRequests,
        error: function() { displayErrorMessage("An error has occurred while trying to accept the join request!") },
        contentType: "application/json"
    });
}

function declineRequest(requestId) {
    console.log("decline " + requestId);

    $.ajax({
        type: "POST",
        url: "/api/v1/requests/decline",
        data: JSON.stringify(requestId),
        success: getRequests,
        error: function() { displayErrorMessage("An error has occurred while trying to decline the join request!") },
        contentType: "application/json"
    });
}

function previousPage() {
    if (pageNumber > 1) {
        pageNumber--;
        populateTableWithRequests();
    }

    checkPreviousPage();
    checkNextPage();
}

function nextPage() {
    if (pageNumber * pageSize < requests.length) {
        pageNumber++;
        populateTableWithRequests();
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
    if (pageNumber * pageSize >= requests.length) {
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


