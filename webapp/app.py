from flask import Flask
# from flask import jsonify

app = Flask(__name__)

@app.route("/")
def index():
    return("Hello Lumohacks! It's Slapp!")

if __name__ == "__main__":
    app.run(debug=True)