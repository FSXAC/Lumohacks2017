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
};

window.onload = function() {
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