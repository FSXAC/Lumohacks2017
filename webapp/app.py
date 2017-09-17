from flask import Flask
from flask import render_template

app = Flask(__name__)

@app.route('/')
def index():
    return render_template('home.html')

@app.route('/sleep')
def sleep():
	return render_template('sleep.html')

if __name__ == "__main__":
    app.run(debug=True)