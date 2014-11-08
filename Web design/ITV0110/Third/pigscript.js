var timeoutFakeRoll;
var timeouts = [];

var pigThreshold = 20;
var playerTurn = true;

var playerScore = 0;
var pigScore = 0;

var currentPoints = 0;


var startTime = new Date().getTime() / 1000;

var name = "nbd";

var gameAllowed = false;

toggleButtons(false);
gid("restart").disabled = true;
gid("set").disabled = true;

while(name.length < 4) {
    name = prompt("Enter your name (atleast 4 chars): ", "Guest").trim();
}

if(name.length > 3 && name != 'null') {
    gameAllowed = true;
    toggleButtons(true);
    gid("restart").disabled = false;
    gid("set").disabled = false;
}

var curPage = "piggame";
var hidden = true;

function show(page) {
    if (page !=curPage) {

        if (curPage == "piggame") {
            gid("you").style.visibility = "collapse";
            gid("pig").style.visibility = "collapse";
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
}

function fake_rolling(times) {
    var firstDice = Math.floor((Math.random() * 6) + 1);
    var secondDice = Math.floor((Math.random() * 6) + 1);
    setDice(1, firstDice);
    setDice(2, secondDice);
    if (times > 0) {
        timeoutFakeRoll = setTimeout(function() {fake_rolling(times-1);}, 100);
        timeouts.push(timeoutFakeRoll);
    } else {
        timeouts.push(setTimeout(roll(), 500));
    }
}

function roll() {
    var firstDice = Math.floor((Math.random() * 6) + 1);
    var secondDice = Math.floor((Math.random() * 6) + 1);
    gid("dices").innerText = String(firstDice) + " and " + String(secondDice);
    setDice(1, firstDice);
    setDice(2, secondDice);

    if ((firstDice == 1 || secondDice == 1) && (firstDice != secondDice)) {
        currentPoints = 0;
        toggleButtons(false);
        if (playerTurn) {
            setMessage("Unlucky fool!");
        } else {
            clearTimers();
            setMessage("No luck..");
        }
        timeouts.push(setTimeout(function() {wait_then_take(5);}, 100));
    }
    else if (firstDice == secondDice) {
        if (firstDice == 1) {
            currentPoints += 25;
        } else {
            currentPoints += firstDice * 4;
        }
        if (playerTurn) {
            setMessage("...");
            toggleButtons(true);
        } else {
            setMessage("He-he-he");
        }
    }
    else {
        currentPoints += firstDice + secondDice;
        setCurrentPoints(currentPoints);
        if (playerTurn) {
            toggleButtons(true);
        }
        return currentPoints;
    }
    setCurrentPoints(currentPoints);
    return currentPoints;
}


function wait_then_take(times) {
    if (times > 0) {
        timeouts.push(setTimeout(function() {wait_then_take(times-1);}, 200));
    } else {
        timeouts.push(setTimeout(take(), 500));
    }
}


function take() {
    if(playerTurn) {
        addPlayerScore(currentPoints);
        if (currentPoints > 0) {
            playerScore += currentPoints;
            setPlayerScore(playerScore);
            setMessage("My turn now..");
        }
        if (checkWin() == 0) {
            toggleButtons(false);
            playerTurn = false;
        } else {
            return;
        }
        pig_move(3);
    } else {
        addPigScore(currentPoints);
        if (currentPoints > 0) {
            pigScore += currentPoints;
            setPigScore(pigScore);
            setMessage("Enough for me..");
        }
        if (checkWin() == 0) {
            toggleButtons(true);
            playerTurn = true;
        }
    }
    currentPoints = 0;
    setCurrentPoints(currentPoints);
}

function pig_move(times) {

    if (times > 0) {
        timeouts.push(setTimeout(function() {pig_move(times-1);}, 100));
    } else {
        if (currentPoints < pigThreshold && !playerTurn && (pigScore + currentPoints) < 100) {
            fake_rolling(5);
            timeouts.push(setTimeout(function() {pig_move(10);}, 100));
        } else {
            take();
        }
    }
}

function checkWin() {
    if (playerScore >= 100) {
        stopGame();
        setMessage("Ok, you won.");
        return 1;
    }
    else if (pigScore >= 100) {
        stopGame();
        setMessage("You loser!");
        return -1;
    }
    else {
        return 0;
    }
}

function restart() {
    playerTurn = true;
    toggleButtons(true);
    currentPoints = 0;
    setCurrentPoints(currentPoints);
    pigScore = 0;
    playerScore = 0;
    setPigScore(pigScore);
    setPlayerScore(playerScore);
    clearTable();
    clearTimers();
    setMessage("Let's roll!");
    startTime = new Date().getTime() / 1000;
}

function toggleButtons(toBool) {
    if (toBool && gameAllowed) {
        gid("take").disabled = false;
        gid("roll").disabled = false;
    } else {
        gid("take").disabled = true;
        gid("roll").disabled = true;
    }
}

function stopGame() {
    var time = new Date().getTime() / 1000 - startTime;
    time = Math.floor(time);
    // AJAX call

    // This is the client-side script
// Initialize the Ajax request
    xhr = new XMLHttpRequest();
    str = 'http://dijkstra.cs.ttu.ee/~Eduard.Netsajev/cgi-bin/saveresult.py?p1name=' + name
        + '&p1score=' + playerScore + '&p2name=Pig' + '&p2score=' + pigScore + '&time=' + time + '&starttime=' + Math.floor(startTime);
    xhr.open('get', str);

// Track the state changes of the request
    xhr.onreadystatechange = function(){
        // Ready state 4 means the request is done
        if(xhr.readyState === 4){
            // 200 is a successful return
            if(xhr.status === 200){
                console.log(xhr.responseText); // 'This is the returned text.'
            }else{
                console.log('Error: '+xhr.status + '\n' + str); // An error occurred during the request
            }
        }
    }

// Send the request to python script
    xhr.send(null);
    // end of AJAX
    toggleButtons(false);
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
    pigThreshold = form.threshold.value;
    return false;
}

function setPlayerScore(points) {
    gid("player_score").innerText = String(points);
}

function addPlayerScore() {
    gid("you").innerHTML += "<br>"+ currentPoints;
    gid("you").style.visibility = "visible";
    hidden = false;
}

function addPigScore() {
    gid("pig").style.visibility = "visible";
    gid("pig").innerHTML += "<br>"+ currentPoints;
}

function setPigScore(points) {
    gid("pig_score").innerText = String(points);
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

var sortField = 5;
var sortOrder = 1;

function setField(field, arrow) {
    sortField = field;

    pressArrow(arrow);



    getData(gid("f1"));
}

var activeArrow = 5;

function pressArrow(arrow) {
    /*var lastar = gid("field" + activeArrow);
    var a = lastar.className.split(" ");
    if (a[0] == "inactive") {
        lastar.className = "active glyphicon " + a[2];
    } else {
        lastar.className = "inactive glyphicon " + a[2];
    }*/

}

function getData(form) {
    var p1 = form.player.value;
    var p2 = form.enemy.value;

    if (p1 == "name") {
        p1 = form.player_name.value.trim();
        if (p1.length < 1) {
            p1 = "Any";
        }
    } else if (p1 == "Me") {
        p1 = name;
    }
    if (p2 == "name") {
        p2 = form.enemy_name.value.trim();
        if (p2.length < 1) {
            p2 = "Any";
        }
    }

    // AJAX call

    // This is the client-side script
    // Initialize the Ajax request
    xhr = new XMLHttpRequest();
    str = 'http://dijkstra.cs.ttu.ee/~Eduard.Netsajev/cgi-bin/getscores.py?p1=' + p1
        + '&p2=' + p2 + '&sortfield=' + sortField + '&sortorder=' + sortOrder;
    xhr.open('get', str);

    console.log(str);

    // Track the state changes of the request
    xhr.onreadystatechange = function(){
        // Ready state 4 means the request is done
        if(xhr.readyState === 4){
            // 200 is a successful return
            if(xhr.status === 200){
                showScores(xhr.responseText.trim());  // 'This is the returned text.'
            }else{
                console.log('Error: '+xhr.status + '\n' + str); // An error occurred during the request
            }
        }
    }

// Send the request to python script
    xhr.send(null);
    // end of AJAX

    return false;
}

function showScores(data) {
    console.log(data);

    var table = document.getElementById("scoretable");

    while(table.rows.length > 1) {
        table.deleteRow(-1);
    }
    var res = data.split("\n");

    var nofound = true;

    for (var i = 0; i < res.length; i++) {

        var pieces = res[i].split(',');
        if (pieces.length < 6) {
            continue;
        }
        nofound = false;
        var row = table.insertRow(-1);

        if (i % 2 == 0) {
            row.className = "tbody-tr-2n";
        } else {
            row.className = "tbody-tr";
        }

        for (var j = 0; j < 6; j++) {

            var cell = row.insertCell(-1);
            cell.className = "tbody-tr-td";
            cell.innerHTML = pieces[j];
        }
    }

    if (nofound) {
        gid("errow").style.visibility = "visible"
    } else {
        gid("errow").style.visibility = "collapse"
    }

}