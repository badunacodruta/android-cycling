
//======= load facebook sdk =============
window.fbAsyncInit = function() {
    FB.init({
        appId   : facebook_app_id,
        oauth   : true,
        status  : true, // check login status
        cookie  : true, // enable cookies to allow the server to access the session
        xfbml   : true, // parse XFBML
        version : 'v2.0' // use version 2.0
    });

    checkLoginState();
};

(function() {
    var e = document.createElement('script');
    e.src = document.location.protocol + '//connect.facebook.net/en_US/all.js';
    e.async = true;
    document.getElementById('fb-root').appendChild(e);

}());
//=========================================


function fetchFBInfo() {
    FB.api('/me', function(response) {
        $('#username').text(response.name + " ");
    });
}

function statusChangeCallback(response) {
    if (response.status === 'connected') {
        fetchFBInfo();
    } else {
        alert('Please log in first!');
    }
}

function checkLoginState() {
    FB.getLoginStatus(function(response) {
        statusChangeCallback(response);
    });
}

function goToLoginPage() {
    window.location.href = "login.html";
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








Date.prototype.yyyymmdd = function() {
    var yyyy = this.getFullYear().toString();
    var mm = (this.getMonth()+1).toString(); // getMonth() is zero-based
    var dd  = this.getDate().toString();
    return yyyy + "/" + (mm[1]?mm:"0"+mm[0]) + "/" + (dd[1]?dd:"0"+dd[0]); // padding
};

function getDate() {
    d = new Date();
    return d.yyyymmdd();
}
