var timeouts = [];

var curPage = "loginpage";
var hidden = true;

var name = "nbd";

function login() {
    name = gid("username").value;
    status(name);
    show('piggame');
}

function show(page) {
    if (curPage == "loginpage") {
        setTimeout(function() {gid("loginpage").style.display = "none";}, 400);
    }
    if (page == "piggame") {
        if (hidden) {
            gid("you").style.visibility = "hidden";
            gid("pig").style.visibility = "hidden";
        } else {
            gid("you").style.visibility = "visible";
            gid("pig").style.visibility = "visible";
        }
    }
    gid(curPage).setAttribute("class", "noshow");
    setTimeout(function() {gid(page).setAttribute("class", "");}, 400);
    curPage = page;
}

function online_roll() {
    var firstDice = Math.floor((Math.random() * 6) + 1);
    var secondDice = Math.floor((Math.random() * 6) + 1);
    setDice(1, firstDice, true);
    setDice(2, secondDice, true);
    timeouts.push(setTimeout(online_roll, 150));
}

function restart() {
    toggleButtons(true);
    setCurrentPoints(0);
    setPigScore(0);
    setPlayerScore(0);
    clearTable();
    clearTimers();
    setMessage("Let's roll!");
}

function toggleButtons(toBool) {
    if (toBool) {
        gid("take").disabled = false;
        gid("roll").disabled = false;
    } else {
        gid("take").disabled = true;
        gid("roll").disabled = true;
    }
}

function clearTimers() {
    for (var i=0; i<timeouts.length; i++) {
        clearTimeout(timeouts[i]);
    }
}

function clearTable() {
    gid("youscore").innerHTML = "<div id='you'>You</div>";
    gid("pigscore").innerHTML = "<div id='pig'>Pig</div>";
    gid("you").style.visibility = "hidden";
    gid("pig").style.visibility = "hidden";
    hidden = true;
}

function setCurrentPoints(points) {
    gid("current_score").innerText = String(points);
}

function setMessage(message) {
    gid("messages").innerText = message;
}

function setThreshold(form) {
    var xhr = new XMLHttpRequest();
    var str = 'http://localhost:1337/?op=setthreshold&value=' + form.threshold.value;
    xhr.open('get', str);
    console.log(str);
    xhr.onreadystatechange = function(){
        if(xhr.readyState === 4){
            if(xhr.status === 200){
                // all is OK
            }else{
                console.log('Error: '+xhr.status + '\n' + str); // An error occurred during the request
            }
        }
    }
    xhr.send(null);
    return false;
}

function addPlayerScore(points) {
    gid("you").innerHTML += "<br>"+ points;
    gid("you").style.visibility = "visible";
}

function addPigScore(points) {
    gid("pig").style.visibility = "visible";
    gid("pig").innerHTML += "<br>"+ points;
}

function gid(x) {
    return document.getElementById(x);
}
function unsetDots() {
    for (var i = 0; i < arguments[0].length; i++) {
        document.getElementById(arguments[0][i]).setAttribute("class", "dot clean");
    }
}
function setDots() {
    for (var i = 0; i < arguments[0].length; i++) {
        document.getElementById(arguments[0][i]).setAttribute("class", "dot");
    }
}
function setDice(dice, num) {
    var dotsToSet;
    var dotsToUnset;

    switch (num) {
        case 1:
            dotsToUnset = [1, 2, 3, 4, 6, 7, 8, 9];
            dotsToSet = [5];
            break;
        case 2:
            dotsToUnset = [2, 3, 4, 5, 6, 7, 8];
            dotsToSet = [1, 9];
            break;
        case 3:
            dotsToUnset = [2, 3, 4, 6, 7, 8];
            dotsToSet = [1, 5, 9];
            break;
        case 4:
            dotsToUnset = [2, 4, 5, 6, 8];
            dotsToSet = [1, 3, 7, 9];
            break;
        case 5:
            dotsToUnset = [2, 4, 6, 8];
            dotsToSet = [1, 3, 5, 7, 9];
            break;
        case 6:
            dotsToUnset = [2, 5, 8];
            dotsToSet = [1, 3, 4, 6, 7, 9];
            break;
    }

    for (var i = 0; i < dotsToSet.length; i++) {
        dotsToSet[i] += dice * 10;
    }
    for (var j = 0; j < dotsToUnset.length; j++) {
        dotsToUnset[j] += dice * 10;
    }

    setDots(dotsToSet);
    unsetDots(dotsToUnset);
}

function status() {
    var xhr = new XMLHttpRequest();
    var str = 'http://localhost:1337/?op=status&name=' + name;
    xhr.open('get', str);
    console.log(str);
    xhr.onreadystatechange = function(){
        if(xhr.readyState === 4){
            if(xhr.status === 200){
                clearTimers();

                var info = xhr.responseText.split(",");
                console.log(info);
                if (name.toUpperCase() != info[0].toUpperCase()) {
                    return;
                }
                var d1 = parseInt(info[5]);
                var d2 = parseInt(info[6]);
                setDice(1, d1);
                setDice(2, d2);
                gid("dices").innerText = String(d1) + " and " + String(d2);
                gid("pig_score").innerText = String(info[2]);
                gid("player_score").innerText = String(info[1]);
                gid("current_score").innerText = String(info[4]);

                clearTable();
                for (var i = 7; i < info.length; i++) {
                    if (i % 2 == 1) {
                        addPlayerScore(info[i]);
                    } else {
                        addPigScore(info[i]);
                    }
                }
                if (parseInt(info[1]) > 99) {
                    gid("messages").innerText = "You won!";
                } else if (parseInt(info[2]) > 99) {
                    gid("messages").innerText = "You lost..";
                } else {
                    var turn = parseInt(info[3]);
                    if (turn == 1) {
                        gid("messages").innerText = "Your turn!";
                        toggleButtons(true);
                    } else {
                        gid("messages").innerText = "Zzz...";
                        timeouts.push(setTimeout(status, 500));
                        timeouts.push(setTimeout(go_pig, 100));
                    }
                }

            }else{
                console.log('Error: '+xhr.status + '\n' + str); // An error occurred during the request
            }
        }
    }
    xhr.send(null);
    return false;
}

function Roll() {
    toggleButtons(false);
    online_roll();
    roll();
    timeouts.push(setTimeout(status, 500));
}

function Take() {
    toggleButtons(false);
    take();
    timeouts.push(setTimeout(status, 500));
}

function new_game() {
    clearTimers();
    newGame();
    setMessage("Let's roll!");
    status()
}

function roll() {
    var xhr = new XMLHttpRequest();
    var str = 'http://localhost:1337/?op=roll&name=' + name;
    xhr.open('get', str);
    console.log(str);
    xhr.onreadystatechange = function(){
        if(xhr.readyState === 4){
            if(xhr.status === 200){
                // all is OK
            }else{
                console.log('Error: '+xhr.status + '\n' + str); // An error occurred during the request
            }
        }
    }
    xhr.send(null);
    return false;
}

function take() {
    var xhr = new XMLHttpRequest();
    var str = 'http://localhost:1337/?op=take&name=' + name;
    xhr.open('get', str);
    console.log(str);
    xhr.onreadystatechange = function(){
        if(xhr.readyState === 4){
            if(xhr.status === 200){
                // all is OK
            }else{
                console.log('Error: '+xhr.status + '\n' + str); // An error occurred during the request
            }
        }
    }
    xhr.send(null);
    return false;
}

function go_pig() {
    var xhr = new XMLHttpRequest();
    var str = 'http://localhost:1337/?op=pigmove&name=' + name;
    xhr.open('get', str);
    console.log(str);
    xhr.onreadystatechange = function(){
        if(xhr.readyState === 4){
            if(xhr.status === 200){
                // all is OK
            }else{
                console.log('Error: '+xhr.status + '\n' + str); // An error occurred during the request
            }
        }
    }
    xhr.send(null);
    return false;
}


function newGame() {
    var xhr = new XMLHttpRequest();
    var str = 'http://localhost:1337/?op=newgame&name=' + name;
    xhr.open('get', str);
    console.log(str);
    xhr.onreadystatechange = function(){
        if(xhr.readyState === 4){
            if(xhr.status === 200){
                // Okay
            }else{
                console.log('Error: '+xhr.status + '\n' + str); // An error occurred during the request
            }
        }
    }
    xhr.send(null);
    return false;
}