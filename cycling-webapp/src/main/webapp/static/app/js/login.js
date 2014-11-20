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

function fetchFBInfo() {
    FB.api('/me', function(response) {
        fetchFBImage(response.email);
    });
}

function fetchFBImage(email) {
//    TODO: decide on the image size (make it smaller)
    FB.api("/me/picture?width=10&height=10",  function(response) {
        appLogin(email, response.data.url);
    });
}

function appLogin(fbEmail, fbImageUrl) {
    fbData = JSON.stringify({ email: fbEmail, imageUrl :fbImageUrl });

    $.ajax({
        type: "POST",
        url: "/api/v1/login",
        data: fbData,
        success: goToHomePage,
        error: errorLogin,
        contentType: "application/json"
    });
}

function goToHomePage() {
    window.location.href = "my-activities.html";
}

function errorLogin() {
    alert( "An error has occurred. Please try again!" );
}


