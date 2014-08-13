__author__ = 'Netšajev'

from bottle import route, run, template

visits = 0


@route('/hello/<name>')
def index(name):
    global visits
    visits += 1
    return template('<b>Hello <u>{{name}}</u>. This page is visited \
                    for the {{time}}. time</b>!', name=name, time=visits)


@route('/hello/hell/<name>')
def index1(name):
    global visits
    visits += 1
    return template('<b>Dumb website<br>Hello <u>{{name}}</u>. This page is visited \
                    for the {{time}}. time</b>!', name=name, time=visits)


@route('/<routing:path>')
def hello(routing):
    return index1('Stranger')


run(host='localhost', port=8080, debug=True)