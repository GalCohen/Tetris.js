


/**
* On Key down event. When the right of left keys are down, set the corresponding variables to true.
*/
function onKeyDown(e) {
	if (e.keyCode === 39 || e.keyCode === 65){
		//rightKeyDown = true;
        moveRightFlag = true;
	} else if (e.keyCode === 37 || e.keyCode === 68) {
		moveLeftFlag = true;
	} else if (e.keyCode === 38 || e.keyCode === 87) {
        rotateFlag = true;
    } else if (e.keyCode === 40 || e.keyCode === 83) {
        moveDownFlag = true;
    }
}




/**
* On Key up event. When the right of left keys are released, set the corresponding variables to false.
*/
function onKeyUp(e) {
	if (e.keyCode === 39 || e.keyCode === 65){
		 moveRightFlag = false;
	}else if (e.keyCode === 37 || e.keyCode === 68) {
  		moveLeftFlag = false;
	}else if (e.keyCode === 38 || e.keyCode === 87) {
        rotateFlag = false;
    }else if (e.keyCode === 40 || e.keyCode === 83){
        moveDownFlag = false;
    }
}



/**
* On Key Press event. When a key on the keyboard is pressed, call the corresponding functions.
*/
function onKeyPress(e){
	if (e.keyCode === 80){ //pause game
		if (gameState === PLAYING_GAME){
			gameState = PAUSED;
		}else if (gameState === PAUSED) {
			gameState = PLAYING_GAME;
			intervalId = setInterval(draw, 30);
		}
	}else if (e.keyCode === 32){ //restart game
		if (gameState === SPLASH){
			intervalId = setInterval(draw, 30);
            gameState = PLAYING_GAME;
		}else if (gameState === PLAYING_GAME || gameState === GAME_OVER ){
			initializeGame(); 
            gameState = PLAYING_GAME;
            //intervalId = setInterval(draw, 30);
		}
		
	}
}

$(document).keydown(onKeyDown);
$(document).keyup(onKeyUp);
$(document).keyup(onKeyPress);



window.addEventListener('load', setup, false);
