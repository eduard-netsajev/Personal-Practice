var timeoutFakeRoll;
var timeouts = [];

var pigThreshold = 20;
var playerTurn = true;

var playerScore = 0;
var pigScore = 0;

var currentPoints = 0;

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

function stopGame() {
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
