var timeoutFakeRoll;
var timeouts = [];

var pigThreshold = 20;
var playerTurn = true;

var playerScore = 0;
var pigScore = 0;

var currentPoints = 0;

var sortField = 5;
var sortOrder = -1;

var startTime = new Date().getTime() / 1000;

var name = "nbd";

var arrows = ["field0", "field1", "field2", "field3", "field4", "field5"];

var gameAllowed = false;

var canAccept = false;

var enemy = null;
var role = 0;

toggleButtons(false);
gid("restart").disabled = true;
gid("set").disabled = true;
gid("register").disabled = true;

while(name.length < 4) {
    name = prompt("Enter your name (atleast 4 chars): ", "Guest");
    if (name == null) {
        name = "nbd";
    } else {
        name = name.trim();
    }
}

if(name.length > 3 && name != 'null') {
    gameAllowed = true;
    toggleButtons(true);
    canAccept = true;
    gid("restart").disabled = false;
    gid("set").disabled = false;
    gid("register").disabled = false;
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
        } else if (page == "pvpgame") {
            getRegisteredPlayers();
        } else if (page == "scorespage") {
            getData(gid("f1"));
        }
        gid(curPage).setAttribute("class", "noshow");
        setTimeout(function() {gid(page).setAttribute("class", "");}, 400);
        curPage = page;
    }
}

function fake_rolling(times) {
    var firstDice = Math.floor((Math.random() * 6) + 1);
    var secondDice = Math.floor((Math.random() * 6) + 1);
    setDice(1, firstDice, false);
    setDice(2, secondDice, false);
    if (times > 0) {
        timeoutFakeRoll = setTimeout(function() {fake_rolling(times-1);}, 100);
        timeouts.push(timeoutFakeRoll);
    } else {
        timeouts.push(setTimeout(roll(), 500));
    }
}

function online_roll() {
    var firstDice = Math.floor((Math.random() * 6) + 1);
    var secondDice = Math.floor((Math.random() * 6) + 1);
    setDice(1, firstDice, true);
    setDice(2, secondDice, true);
    console.log("hehe");
    timeouts.push(setTimeout(online_roll, 350));
}

function roll() {
    var firstDice = Math.floor((Math.random() * 6) + 1);
    var secondDice = Math.floor((Math.random() * 6) + 1);
    gid("dices").innerText = String(firstDice) + " and " + String(secondDice);
    setDice(1, firstDice, false);
    setDice(2, secondDice, false);

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
    var xhr = new XMLHttpRequest();
    var str = 'http://dijkstra.cs.ttu.ee/~Eduard.Netsajev/cgi-bin/saveresult.py?p1name=' + name
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
    if (arguments[1]) {
        for (var j = 0; j < arguments[0].length; j++) {
            document.getElementById('o' + arguments[0][j]).setAttribute("class", "dot clean");
        }
    } else {
        for (var i = 0; i < arguments[0].length; i++) {
            document.getElementById(arguments[0][i]).setAttribute("class", "dot clean");
        }
    }
}
function setDots() {
    if (arguments[1]) {
        for (var j = 0; j < arguments[0].length; j++) {
            document.getElementById('o' + arguments[0][j]).setAttribute("class", "dot");
        }
    } else {
        for (var i = 0; i < arguments[0].length; i++) {
            document.getElementById(arguments[0][i]).setAttribute("class", "dot");
        }
    }
}
function setDice(dice, num, o) {
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

    setDots(dotsToSet, o);
    unsetDots(dotsToUnset, o);
}

function setField(field, arrow) {
    pressArrow(arrow);
    sortField = field;
    getData(gid("f1"));
}

function pressArrow(arrow) {

    var arrowClass = arrow.className.split(" ");

    if (arrowClass[0] == "active") {
        if (arrowClass[2] == "arrow-down") {
            sortOrder = 1;
            arrow.className = "active glyphicon arrow-up";
        } else {
            sortOrder = -1;
            arrow.className = "active glyphicon arrow-down";
        }
    } else {
        arrow.className = "active glyphicon " + arrowClass[2];
        if (arrowClass[2] == "arrow-down") {
            sortOrder = -1;
        } else {
            sortOrder = 1;
        }
        var oldArrowClass = gid(arrows[sortField]).className.split(" ");
        gid(arrows[sortField]).className = "inactive glyphicon " + oldArrowClass[2];
    }
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
    var xhr = new XMLHttpRequest();
    var str = 'http://dijkstra.cs.ttu.ee/~Eduard.Netsajev/cgi-bin/getscores.py?p1=' + p1
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

        for (var j = 0; j < 5; j++) {

            var cell = row.insertCell(-1);
            cell.className = "tbody-tr-td";
            cell.innerHTML = pieces[j];
        }

        var datecell = row.insertCell(-1);
        datecell.className = "tbody-tr-td";
        datecell.innerHTML = timeConverter(pieces[5]);

    }

    if (nofound) {
        gid("errow").style.visibility = "visible"
    } else {
        gid("errow").style.visibility = "collapse"
    }

}

function timeConverter(UNIX_timestamp){
    var a = new Date(UNIX_timestamp*1000);
    var months = ['Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec'];
    var year = a.getFullYear();
    var month = months[a.getMonth()];
    var date = a.getDate();
    var hour = a.getHours();
    var min = a.getMinutes() < 10 ? '0' + a.getMinutes() : a.getMinutes();
    var sec = a.getSeconds() < 10 ? '0' + a.getSeconds() : a.getSeconds();
    var time = date + ' ' + month + ' ' + year + ' ' + hour + ':' + min + ':' + sec ;
    return time;
}

function getRegisteredPlayers() {
    // AJAX call
    // This is the client-side script
    // Initialize the Ajax request
    var xhr = new XMLHttpRequest();
    var str = 'http://dijkstra.cs.ttu.ee/~Eduard.Netsajev/cgi-bin/pvp.py?op=getlist';
    xhr.open('get', str);

    console.log(str);

    // Track the state changes of the request
    xhr.onreadystatechange = function(){
        // Ready state 4 means the request is done
        if(xhr.readyState === 4){
            // 200 is a successful return
            if(xhr.status === 200){
                console.log("GOT LIST OF REGISTERED PLAYERS");
                showPlayers(xhr.responseText.trim());  // 'This is the returned text.'
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

function registerPlayer() {
    var xhr = new XMLHttpRequest();
    var str = 'http://dijkstra.cs.ttu.ee/~Eduard.Netsajev/cgi-bin/pvp.py?op=register&name=' + name;
    xhr.open('get', str);
    console.log(str);
    xhr.onreadystatechange = function(){
        if(xhr.readyState === 4){
            if(xhr.status === 200){
                canAccept = false;
                gid("register").disabled = true;
                role = 1;
                getRegisteredPlayers();
                checkGame();
            }else{
                console.log('Error: '+xhr.status + '\n' + str); // An error occurred during the request
            }
        }
    }
    xhr.send(null);
    return false;
}

function showPlayers (data) {
//    console.log(data);
    var table = document.getElementById("players");
    table.innerHTML = "";
    var res = data.split("\n");

    var empty = true;

    for (var i = 0; i < res.length; i++) {
        var str = res[i].trim();
        if (str.length < 4) {
            continue;
        }
        if (empty) {
                table.innerHTML += "Available players:<br>"
            empty = false;
        }
        console.log(canAccept);
        if (canAccept) {
            table.innerHTML += '<button class="acceptbutton" onclick="pickPlayer(\'' + str + '\')">' + str + '</button>';
        } else {
            table.innerHTML += '<button class="acceptbutton" onclick="pickPlayer(\'' + str + '\')" disabled>' + str + '</button>';
        }
    }
}

function pickPlayer(pickedname) {
    var xhr = new XMLHttpRequest();
    var str = 'http://dijkstra.cs.ttu.ee/~Eduard.Netsajev/cgi-bin/pvp.py?op=startgame&name1=' + pickedname
        + '&name2=' + name + "&starttime=" + Math.floor(new Date().getTime() / 1000);
    xhr.open('get', str);
    console.log(str);
    xhr.onreadystatechange = function(){
        if(xhr.readyState === 4){
            if(xhr.status === 200){
                enemy = pickedname;
                role = -1;
                startPvP();
            }else{
                console.log('Error: '+xhr.status + '\n' + str); // An error occurred during the request
            }
        }
    }
    xhr.send(null);
    return false;
}

function checkGame() {
    var xhr = new XMLHttpRequest();
    var str = 'http://dijkstra.cs.ttu.ee/~Eduard.Netsajev/cgi-bin/pvp.py?op=check&name=' + name;
    xhr.open('get', str);
    console.log(str);
    xhr.onreadystatechange = function(){
        if(xhr.readyState === 4){
            if(xhr.status === 200){
                if (xhr.responseText == -1) {
                    timeouts.push(setTimeout(checkGame, 1000))
                    console.log("WAIT MORE");
                } else {
                    enemy = xhr.responseText.trim();
                    startPvP();
                    console.log(xhr.responseText);
                }
            }else{
                console.log('Error: '+xhr.status + '\n' + str); // An error occurred during the request
            }
        }
    }
    xhr.send(null);
    return false;
}

function startPvP() {
    console.log("START PVP");
    show("pvpbattle");
    gid("enemy").innerText = enemy;
    gid("enemy").style.visibility = "hidden";
    gid("oyou").style.visibility = "hidden";

    gid("pvpig").disabled = true;
    gid("pvp").disabled = true;
    gid("scores").disabled = true;

    otoggleButtons(false);
    timeouts.push(setTimeout(getStatus, 200));
}

function otoggleButtons(toBool) {
    if (toBool && gameAllowed) {
        gid("otake").disabled = false;
        gid("oroll").disabled = false;
    } else {
        gid("otake").disabled = true;
        gid("oroll").disabled = true;
    }
}

function pvp_roll() {
    otoggleButtons(false);
    online_roll();
    Roll();
    timeouts.push(setTimeout(getStatus, 500));
}

function otake() {
    otoggleButtons(false);
    Take();
    timeouts.push(setTimeout(getStatus, 200));
}

function new_game() {
// TODO maybe
}

function getStatus() {
    var xhr = new XMLHttpRequest();
    var str = 'http://dijkstra.cs.ttu.ee/~Eduard.Netsajev/cgi-bin/pvp.py?op=status&name=' + name + '&role=' + role;
    xhr.open('get', str);
    console.log(str);
    xhr.onreadystatechange = function(){
        if(xhr.readyState === 4){
            if(xhr.status === 200){
                clearTimers();

                var info = xhr.responseText.split(",");
                console.log(info);
                gid("ocurrent_score").innerText = String(info[0]);

                var d1 = parseInt(info[1]);
                var d2 = parseInt(info[2]);
                if (d1 > 0 && d2 > 0) {
                    setDice(1, d1, true);
                    setDice(2, d2, true);
                    gid("odices").innerText = String(d1) + " and " + String(d2);
                }

                gid("your_score").innerText = info[4];
                gid("his_score").innerText = info[5];

                var code = parseInt(info[3]);
                switch (code) {
                    case 100:
                        gid("omessages").innerText = "You won!";
                        gid("new_game").disabled = false;
                        break;
                    case -100:
                        gid("omessages").innerText = "You lost..";
                        gid("new_game").disabled = false;
                        break;
                    case 1:
                        gid("omessages").innerText = "Your turn!";
                        otoggleButtons(true);
                        break;
                    case -1:
                        gid("omessages").innerText = "Zzz...";
                        timeouts.push(setTimeout(getStatus, 500));
                        break;
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
    var xhr = new XMLHttpRequest();
    var str = 'http://dijkstra.cs.ttu.ee/~Eduard.Netsajev/cgi-bin/pvp.py?op=roll&name=' + name + '&role=' + role;
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

function Take() {
    var xhr = new XMLHttpRequest();
    var str = 'http://dijkstra.cs.ttu.ee/~Eduard.Netsajev/cgi-bin/pvp.py?op=take&name=' + name + '&role=' + role;
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