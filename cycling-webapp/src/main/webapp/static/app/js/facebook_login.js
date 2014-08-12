//======= load facebook sdk =============
window.fbAsyncInit = function() {
    FB.init({
        appId   : facebookAppId,
        oauth   : true,
        status  : true, // check login status
        cookie  : true, // enable cookies to allow the server to access the session
        xfbml   : true, // parse XFBML
        version : 'v2.0' // use version 2.0
    });
};

(function() {
    var e = document.createElement('script');
    e.src = document.location.protocol + '//connect.facebook.net/en_US/all.js';
    e.async = true;
    document.getElementById('fb-root').appendChild(e);

}());
//=========================================


function goToHomePage() {
    window.location.href = "home.html";
}

function appLogin(fbEmail) {
    fbData = JSON.stringify({ email: fbEmail });

    $.ajax({
        type: "POST",
        url: "/api/v1/login",
        data: fbData,
        success: goToHomePage,
        error: errorLogin,
        contentType: "application/json"
    });
}

function fetchFBInfo() {
    FB.api('/me', function(response) {
        appLogin(response.email);
    });
}

function errorLogin() {
    alert( "An error has occurred. Please try again!" );
}

function fbLogin(){
    FB.login(function(response) {
        if (response.status === 'connected') {
            fetchFBInfo();
        } else {
            errorLogin();
        }
    }, {
        scope: "public_profile,email"
    });
}
