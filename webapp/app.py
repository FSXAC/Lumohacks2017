from flask import Flask
from flask import render_template

app = Flask(__name__)

@app.route('/')
def index():
    return render_template('unreg.html')

@app.route('/<name>')
def indexNamed(name):
    return render_template('home.html', py_name=name)

if __name__ == "__main__":
    app.run(debug=True)