function Star() {
    this.position = createVector(random(0, width),
                                 random(0, height));
    this.radius = random(0.5, 2);
    this.opacityMin = random(50, 150);
    this.opacityMax = random(150, 255);
    this.omega = random(0.003, 0.1);
    this.theta = random(0, TWO_PI);
}
Star.prototype.draw = function() {
    var d = this.radius * 2;
    var o = (Math.sin(this.omega * frameCount + this.theta) + 1) / 2 * 255;
    fill(255, o);
    ellipse(this.position.x, this.position.y, d, d);
};


var stars = []
var moon;

function preload() {
    moon = loadImage('static/img/moon.png')
}

function setup() {
    canvas = createCanvas(windowWidth, windowHeight);
    canvas.class("s_canvas");
    canvas.position(0, 0);
    noStroke();

    for (var i = 0; i < 100; i++) {
        stars.push(new Star());
    }
}

var moonInterval = 2000;
function draw() {
    background(0, 32, 64);

    // draw stars
    for (var i = 0, j = stars.length; i < j; i++) {
        stars[i].draw();
    }

    // draw the moon
    var stepTime = frameCount % moonInterval;
    image(moon, 
          map(stepTime, 0, moonInterval, width + 80, -80), 
          map(sin(stepTime * PI / moonInterval), 0, 1, 100, 50),
          80, 80);
}

function windowResized() {
    resizeCanvas(windowWidth, windowHeight);
}