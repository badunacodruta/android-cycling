
//======= facebook related =============
//check if the user is logged in and retrieve it's facebook username
//this should be done for every page except the login page

window.fbAsyncInit = function() {
    FB.init({
        appId   : facebookAppId,
        oauth   : true,
        status  : true, // check login status
        cookie  : true, // enable cookies to allow the server to access the session
        xfbml   : true, // parse XFBML
        version : 'v2.0' // use version 2.0
    });

    checkLoginState();
};

function checkLoginState() {
  FB.getLoginStatus(function(response) {
    statusChangeCallback(response);
  });
}

function statusChangeCallback(response) {
  if (response.status === 'connected') {
    fetchFBInfo();
  } else {
    alert('Please log in first!');
  }
}

function fetchFBInfo() {
  FB.api('/me', function(response) {
    $('#username').text(response.name + " ");
  });
}

(function() {
    var e = document.createElement('script');
    e.src = document.location.protocol + '//connect.facebook.net/en_US/all.js';
    e.async = true;
    document.getElementById('fb-root').appendChild(e);
}());
//=========================================


//======= login/logout related =============

function goToLoginPage() {
    window.location.href = "../../../index.html";
}

function logout() {
    FB.logout(function(response) {
        $.ajax({
            type: "DELETE",
            url: "/api/v1/logout"
        });

        goToLoginPage();
    });
}
//=========================================


//======= maps related =============

function createMap() {
    var latlng = new google.maps.LatLng(44.4325, 26.1039);

    var mapOptions = {
        center: latlng,
        zoom: 13
    };

    map = new google.maps.Map(document.getElementById("map-canvas"), mapOptions);
};
//=========================================


//======= success/error messages =============

function displayErrorMessage(message) {
    $("#error-message").text(message);
    $("#error-message").show();
}

function hideErrorMessage() {
  $("#error-message").text("");
  $("#error-message").hide();
}

function displaySuccessMessage(message) {
  $("#success-message").text(message);
  $("#success-message").show();
}

function hideSuccessMessage() {
  $("#success-message").text("");
  $("#success-message").hide();
}

function hideMessages() {
  hideSuccessMessage();
  hideErrorMessage();
}
//=========================================


//======= date related =============

Date.prototype.yyyymmdd = function() {
    var yyyy = this.getFullYear().toString();
    var mm = (this.getMonth()+1).toString(); // getMonth() is zero-based
    var dd  = this.getDate().toString();
    return yyyy + "/" + (mm[1]?mm:"0"+mm[0]) + "/" + (dd[1]?dd:"0"+dd[0]);
};

Date.prototype.yyyymmddHHmm = function() {
    var HH = this.getHours().toString();
    var min = this.getMinutes().toString();
    return this.yyyymmdd() + " " + HH + ":" + min;
};

function getDate() {
    d = new Date();
    return d.yyyymmdd();
}
//=========================================