// Global variables
var g_date = new Date();

function View() {
    this.$c;
    
    this.TEMPLATES = {
        HOME: '\
            <div class="s_page-home">\
                <h1 id="s_home-time">{{time}}</h1>\
                <h2>Welcome back <span id="s_name">{{name}}</span></h2>\
            </div>',
    }
    Mustache.parse(this.TEMPLATES.HOME);
}

View.prototype.init = function(user) {
    this.$c = $('#main_content_box');
    this.currentUser;

    var homeProp = {
        time: getFormattedTime(),
        name: ''
    };

    // render template
    var html = Mustache.render(this.TEMPLATES.HOME, homeProp);
    this.$c.html(html); 

    // reset intervals
    window.clearInterval(this.secondInterval);

    // set interval functiont to call every second
    this.secondInterval = setInterval(function() {
        $('#s_home-time').html(getFormattedTime());
    }, 60000);

    // Any JS->HTML bindings
    $ne_d = $('#newEntry-drag');
    $ne_d.on('input', function() {
        $('#newEntry-drag-pre').html($ne_d.val());
    });
    $ne_dur = $('#newEntry-duration');
    $ne_dur.on('input', function() {
        $('#newEntry-duration-pre').html($ne_dur.val());
    });

    // Adding entry
    var self = this;
    $('#saveEntryButton').on('click', function() {
        // send to server
        g_date = new Date();
        var bedtime = $("#newEntry-time").val().split(':');
        var outBedtime = $('#newEntry-outtime').val().split(':');
        var awake = $("#newEntry-awake").val().split(':');
        var duration = $("#newEntry-duration").val();
        var trytime = $("#newEntry-trime").val().split(':');
        var presleep = $("#newEntry-drag").val();
        var interrupt = $("#newEntry-inter").val();
        entry = {
            awakeHour: parseInt(awake[0]),
            awakeMinute: parseInt(awake[1]),

            comments: $("#newEntry-comments").val(),

            hourInBed: parseInt(bedtime[0]),
            minuteInBed: parseInt(bedtime[1]),

            hourOutBed: parseInt(outBedtime[0]),
            minuteOutBed: parseInt(outBedtime[1]),

            hourAsleep: Math.floor(duration),
            minuteAsleep: (parseFloat(duration) - parseInt(duration)) * 60,

            tryHour: parseInt(trytime[0]),
            tryMinute: parseInt(trytime[1]),

            preSleepHour: 0,
            preSleepMinute: parseInt(presleep),

            sleepInterrupt: parseInt(interrupt),

            year: g_date.getFullYear(),
            month: g_date.getMonth() + 1,
            day: g_date.getDate(),
        }

        $w = $('#newEntryWarning');
        $wp = $w.find('p');
        if (entry.bedtime == '' ||
            entry.bedtime_try == '' ||
            entry.time_of_awaken == '' ||
            entry.out_of_bed_time == '') {
            $w.show();
            $wp.html('Fill in all the time slots');
            return;
        }

        var sleepListRef = firebase.database().ref('users/' + self.currentUser.uid + '/sleep');
        var newSleepRef = sleepListRef.push();
        newSleepRef.set(entry);

        // Make modal go away
        $('#entryModal').modal('hide');
    });

};

View.prototype.updateUser = function(user) {
    this.currentUser = user;

    // Update name
    var displayName = user.displayName;
    if (displayName != null) {
        $('#s_name').html(displayName.split(' ')[0]);
    }

    // Get UID
    var uid = user.uid;
    if (uid == null) {
        console.error("UID is null: abort");
        return;
    }

    // Access data base
    var userData = firebase.database().ref('users/' + uid);
    userData.on('value', function(snapshot) {
        console.log(snapshot);
    });
};

View.prototype.setLogoutHandler = function(handler) {
    $('#logoutButton').on('click', function(e) {
        handler(e);
    });
};

window.onload = function() {

    // Access firebase
    initFirebase();
    var database = firebase.database();

    view = new View();
    view.init();

    // Bind handlers
    // Bind logout to sign out
    view.setLogoutHandler(function() {
        firebase.auth().signOut().then(function() {
            console.log('Sign out success');
        }).catch(function(error) {
            console.log(error);
        });
    });

    // Bind update
    firebase.auth().onAuthStateChanged(function(user) {
        if (user) {
            view.updateUser(user);
        } else {
            window.location = '/';
        }
    });
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
}