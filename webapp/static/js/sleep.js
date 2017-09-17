// Global variables
var g_date = new Date();

function View() {
    this.$c;
    
    this.TEMPLATES = {
        HOME: '\
            <div class="s_page-home">\
                <h1 id="s_home-time">{{time}}</h1>\
                <h2>Welcome back {{name}}</h2>\
            </div>',
    }
    Mustache.parse(this.TEMPLATES.HOME);
}

View.prototype.init = function() {
    this.$c = $('#main_content_box');

    var homeProp = {
        time: getFormattedTime(),
        name: g_py_name
    };

    var html = Mustache.render(this.TEMPLATES.HOME, homeProp);
    this.$c.html(html); 

    // reset intervals
    window.clearInterval(this.secondInterval);

    // set interval functiont to call every second
    this.secondInterval = setInterval(function() {
        $('#s_home-time').html(getFormattedTime());
    }, 60000);

    // Set handler for logging out
    $('#logoutButton').on('click', function() {
        if (firebase != undefined) {
            firebase.auth().signOut().then(function() {
                console.log('Sign out success')
            }).catch(function(error) {
                console.log(error);
            });
        }
    });
};

window.onload = function() {

    // Access firebase
    initFirebase();
    var database = firebase.database();

    view = new View();
    view.init();
}

// Utility functions
function getFormattedTime() {
    g_date = new Date();
    var hours = g_date.getHours();
    var min = g_date.getMinutes();
    var sec = g_date.getSeconds();
    return (hours < 10 ? '0' : '') + hours + ':' +
           (min < 10 ? '0' : '') + min;
}

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

    firebase.auth().onAuthStateChanged(function(user) {
        if (!user) {
            window.location.href = '/';
        }
    });
}