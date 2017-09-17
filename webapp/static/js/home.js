window.onload = function() {

    initFirebase();

    $('#signupButton').on('click', function() {
        var $n = $('#regName');
        var $e = $('#regEmail');
        var $p = $('#regPass');
        var $pc = $('#regPassConf');
        var $w = $('#regWarning');

        // Validation
        $w.hide();
        if ($n.val().length < 1) {
            $w.show();
            $w.find('p').html('Please enter your FULL name');
        }
        if (!validateEmail($e.val())) {
            $w.show();
            $w.find('p').html('Email is not valid');
            return;
        }
        if ($p.val() != $pc.val()) {
            $w.show();
            $w.find('p').html('Passwords do not match');
            return;
        }
        if ($p.val().length < 8) {
            $w.show();
            $w.find('p').html('Password need at least 8 characters');
            return;
        }

        // Hide entry
        $e.hide();
        $p.hide();
        $pc.hide();
        $n.hide();
        $('#regSpinner').show();

        // request database
        var email = str($e.val());
        var password = str($p.val());

        if (firebase != undefined) {
            firebase.auth().createUserWithEmailAndPassword(email, password).catch(function(error) {
                var errorCode = error.code;
                var errorMessage = error.message;

                if (error != undefined) {
                    $e.show();
                    $p.show();
                    $pc.show();
                    $('#regSpinner').hide();
                    $w.show();
                    $w.find('p').html(error.message);
                }
            });
        }
    });

    $('#signinButton').on('click', function() {
        $e = $('#loginEmail');
        $p = $('#loginPass');
        $sp = $('#loginSpinner');
        $w = $('#loginWarning');

        // Hide entry
        $e.hide();
        $p.hide();
        $sp.show();

        // Request database
        var email = str($e.val());
        var password = str($p.val());
        firebase.auth().signInWithEmailAndPassword(email, password).catch(function(error) {
            var errorCode = error.code;
            var errorMessage = error.message;

            if (error != undefined) {
                $e.show();
                $p.show();
                $sp.hide();
                $w.show();
                $w.find('p').html(error.message);
            } else {
                sessionStorage.setItem('email', email);
                sessionStorage.setItem('passw', password);
            }
        });
    });

    // User authentication
    firebase.auth().onAuthStateChanged(function(user) {
        if (user) {
            // User is signed in.
            user.updateProfile({
                displayName: str($('#regName').val())
            }).then(function() {
                console.log('name change success');
            }).catch(function(e) {
                console.log('creating error');
            });

            // Change to logged in page
            window.location = '/sleep';
        }
    });
}

// utility functions
function initFirebase() {
    var config = {
        apiKey: "AIzaSyCpukNeAZkRMjv9wisVzrDTDr1oLq2dklk",
        authDomain: "lumohacks2017.firebaseapp.com",
        databaseURL: "https://lumohacks2017.firebaseio.com",
        projectId: "lumohacks2017",
        storageBucket: "lumohacks2017.appspot.com",
        messagingSenderId: "1069429648890"
    };
    firebase.initializeApp(config);
}

function validateEmail(email) {
    var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(email);
}