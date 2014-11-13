/**
 * Created by Netšajev on 13/11/2014.
 */
var http = require('http');

var pigThreshold = 30;

var d1 = 5;
var d2 = 2;

function calc_points(d1, d2) {
    if ((d1 == 1) ^ (d2 == 1)) {
        return 0;
    } else if (d1 != d2) {
        return d1 + d2;
    } else if (d1 != 1) {
        return d1 * 4;
    } else {
        return 25;
    }
}

function pig_move(points) {
    var newpoints = 0;

    while((newpoints + points) < 100 && newpoints < pigThreshold) {
        d1 = Math.floor((Math.random() * 6) + 1);
        d2 = Math.floor((Math.random() * 6) + 1);

        var result = calc_points(d1, d2);
        if (result == 0) {
            console.log("failed");
            return 0;
        } else {
            newpoints += result;
        }
    }
    return newpoints;
}


http.createServer(function (req, res) {

    // Website you wish to allow to connect
    res.setHeader('Access-Control-Allow-Origin', 'http://127.0.0.1:1337');
    // Request methods you wish to allow
    res.setHeader('Access-Control-Allow-Methods', 'GET, POST, OPTIONS, PUT, PATCH, DELETE');
    // Request headers you wish to allow
    res.setHeader('Access-Control-Allow-Headers', 'X-Requested-With,content-type');

    var fs = require('fs');
    var url = require('url');

    if(req.url == "/favicon.ico") {
        return;
    }

    if(req.url == "/") {
        res.writeHead(200, {'Content-Type': 'text/html'});
        fs.readFile('BigPig.html', 'utf8', function (err,data) {
            if (err) {
                return console.log(err);
            }
            res.write(data);
            res.end();
        });
        return;
    } else if (req.url == "/pigstyle.css") {

        res.writeHead(200, {'Content-Type': 'text/css'});
        fs.readFile('pigstyle.css', 'utf8', function (err,data) {
            if (err) {
                return console.log(err);
            }
            res.write(data);
            res.end();
        });
        return;
    } else if (req.url == "/pigscript.js") {

        res.writeHead(200, {'Content-Type': 'text/javascript'});
        fs.readFile('pigscript.js', 'utf8', function (err,data) {
            if (err) {
                return console.log(err);
            }
            res.write(data);
            res.end();
        });
        return;
    }

    var url_parts = url.parse(req.url, true);
    var operation = url_parts["query"]["op"];

    res.writeHead(200, {'Content-Type': 'text/text'});

    var p404 = false;

    try {
        var name = url_parts["query"]["name"];
    } catch (e) {
        name = "nbd";
    }
    var filename = "./games/" + name + ".txt";

    switch (operation) {
        case "setthreshold":
            pigThreshold = parseInt(url_parts["query"]["value"]);
            console.log("New Threshold: " + pigThreshold);
            res.write("OK");
            res.end();
            break;
        case "newgame":
            console.log("Requesting to start a game");
            fs.writeFile(filename, name + ",0,0,1,0,5,2", function (err) {
                if (err) return console.log(err);
                console.log('Successfully started > ' + name + ",0,0,1,0,5,2");
                res.write(name + ",0,0,1,0,5,2");
                res.end();
            });
            break;
        case "roll":
            console.log("Rolling Stones");
            fs.readFile(filename, 'utf8', function (err,data) {
                if (err) {
                    return console.log(err);
                }
                d1 = Math.floor((Math.random() * 6) + 1);
                d2 = Math.floor((Math.random() * 6) + 1);

                var info = data.split(",");
                info[5] = d1;
                info[6] = d2;

                if ((d1 == 1) ^ (d2 == 1)) {
                    info[4] = 0;
                    info[3] = -1;
                    info.push(0);
                } else {
                    info[4] = parseInt(info[4]) + calc_points(d1, d2);
                }

                fs.writeFile(filename, info.join(), function (err) {
                    if (err) return console.log(err);
                    console.log('Successfully rolled ' + d1 + " " + d2);
                    res.write("OK");
                    res.end();
                });
            });
            break;
        case "take":
            fs.readFile(filename, 'utf8', function (err,data) {
                if (err) {
                    return console.log(err);
                }
                var info = data.split(",");
                info.push(info[4]);
                info[3] = -1;
                info[1] = parseInt(info[4]) + parseInt(info[1]);
                info[4] = 0;

                fs.writeFile(filename, info.join(), function (err) {
                    if (err) return console.log(err);
                    console.log('Successfully taken');
                    res.write("OK");
                    res.end();
                });
            });
            break;
        case "pigmove":
            console.log("Requesting to make turn");
            fs.readFile(filename, 'utf8', function (err,data) {
                if (err) {
                    return console.log(err);
                }
                var info = data.split(",");
                var points = parseInt(info[2]);
                var newP = pig_move(points);
                info.push(newP);
                info[5] = d1;
                info[6] = d2;
                info[2] = parseInt(info[2]) + newP;
                info[4] = 0;
                info[3] = 1;

                fs.writeFile(filename, info.join(), function (err) {
                    if (err) return console.log(err);
                    console.log('Successfully pig moved');
                    res.write("OK");
                    res.end();
                });
            });
            break;
        case "status":
            console.log("Requesting game status");
            fs.exists(filename, function(exists) {
                if (exists) {
                    fs.readFile(filename, 'utf8', function (err,data) {
                        if (err) {
                            return console.log(err);
                        }
                        console.log('Successfully read > ' + data);
                        res.write(data);
                        res.end();
                    });
                } else {
                    fs.writeFile(filename, name + ",0,0,1,0,5,2", function (err) {
                        if (err) return console.log(err);
                        console.log('Successfully created > ' + name + ",0,0,1,0,5,2");
                        res.write(name + ",0,0,1,0,5,2");
                        res.end();
                    });
                }
            });
            break;
        default:
            p404 = true;
            console.log("Unknown request");
            break;
    }
    if (p404) {
        res.write("404: Page not found - " + req.url);
        res.end();
    }
}).listen(1337, '127.0.0.1');
console.log('Server running at http://127.0.0.1:1337/');