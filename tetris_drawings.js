/* ******************************* DRAWINGS ************************************ */


var endGame = function(){
    //console.log("ENDGAME");
    //clearInterval(intervalId); 

    var w = 150;
    var h = 100;
    //green margin
    canvas.lineWidth = 1;
    canvas.strokeStyle = 'rgb(127, 255, 0)';
    canvas.strokeRect(WIDTH/2 - w/2, HEIGHT/2 - h/2, w, h);

    canvas.fillStyle = 'rgb(0, 100, 0)';
    canvas.fillRect(WIDTH/2 - w/2, HEIGHT/2 - h/2, w, h);

    
    canvas.font = '19pt Calibri';
    canvas.fillStyle = 'rgb(0, 220, 0)';
    canvas.fillText("Game Over!", WIDTH/2 -63 , HEIGHT/2 - 25);
 
    canvas.font = '14pt Calibri';
    canvas.fillText("Score: "+SCORE, WIDTH/2 - 55 , HEIGHT/2  );

    canvas.font = '12pt Calibri';
    canvas.fillText("Hit the Spacebar", WIDTH/2 -55 , HEIGHT/2 + 25);
    canvas.font = '12pt Calibri';
    canvas.fillText("to play again", WIDTH/2 -40 , HEIGHT/2 + 40);


};



var pauseGame = function(){
    //console.log("PAUSEGAME");
    clearInterval(intervalId);

    var w = 150;
    var h = 100;
    //green margin
    canvas.lineWidth = 1;
    canvas.strokeStyle = 'rgb(127, 255, 0)';
    canvas.strokeRect(WIDTH/2 - w/2, HEIGHT/2 - h/2, w, h);

    canvas.fillStyle = 'rgb(0, 100, 0)';
    canvas.fillRect(WIDTH/2 - w/2, HEIGHT/2 - h/2, w, h);

    
    canvas.font = '19pt Calibri';
    canvas.fillStyle = 'rgb(0, 220, 0)';
    canvas.fillText("Game Paused", WIDTH/2 -70 , HEIGHT/2 - 15);
 
    canvas.font = '16pt Calibri';
    canvas.fillText("Hit the P key", WIDTH/2 -55 , HEIGHT/2 + 15);
    canvas.font = '16pt Calibri';
    canvas.fillText("to unpause", WIDTH/2 -55 , HEIGHT/2 + 35);

};



var splashScreen = function(){
    console.log("SPLASHSCREEN");
	canvas.drawImage(splashImg, 0, 0, splashImg.width, splashImg.height);
};


/**
* Clears the canvas for the next iteration of the game loop
*/
var clear = function () {
	//draws the background
	canvas.fillStyle="black";
	canvas.fillRect(1,1,WIDTH-2,HEIGHT-2);
};



var drawGraphics = function(){

    //green margin
    canvas.lineWidth = 1;
    canvas.strokeStyle = 'rgb(127, 255, 0)';
    canvas.strokeRect(1, 1, 298, 398);

    //score bar
    canvas.fillStyle = 'rgb(0, 100, 0)';
    canvas.fillRect(2, 2, 300-4, 20);
    canvas.strokeStyle = 'rgb(127, 255, 0)';
    canvas.strokeRect(1, 1, 300-2, 20);

    
    for (var i = 0; i < boardHeight; i++){
        for (var j = 0; j < boardWidth; j++){
            if (gameboard[i][j] == 1){
                canvas.fillRect(j * 20-1, i * 20-1 , 20+1, 20+1);
                canvas.strokeRect(j * 20, i * 20 , 20, 20);
            } 
        }
    }

    canvas.font = '13pt Calibri';
    canvas.fillStyle = 'rgb(0, 220, 0)';
    canvas.fillText("Score:", 10, 17);
    canvas.fillText(SCORE, 75, 18);
    

};



var draw = function(){
    if (gameState === PLAYING_GAME){
        clear();

        timeCounter++;

        if (blockReachedBottom === 1){
            numRowsChecked = rowCheck();
            SCORE = SCORE + (numRowsChecked * 100);
            blockReachedBottom = 0;
            if (numRowsChecked > 0){
                //accelaration = accelaration - 0.1;
                accelaration = accelaration - (0.1 * numRowsChecked);
                if (accelaration <= 0){
                    accelaration = 0.1;
                }
            }
            //console.log("acc:"+accelaration);
        }    

        if (newBlockBoolean === 1){
            // MAKE A NEW BLOCK HERE @@@@
            blockNumber = Math.floor(Math.random()*7);
            //console.log("BLOCKNUMBER:" + blockNumber);
            blck1 = new Block(blockNumber, 0);  
            blck1.encodeBlockType();
            newBlockBoolean = 0;
            if (placeBlock(7, 1, blck1.shapeCode) === 1){
                //console.log("new block created");
                x = 140; 
                y = 0;
                boardX = 7;
                boardY = 1;
                accelaration = accelaration + 0.05;
                if (accelaration > 3){
                    accelaration = 3;
                }
            }else{
                gameEnded = true;
            }
       }
            
        if (timeCounter > (frameRATE / accelaration)){
            moveBlock(2, boardX, boardY, blck1.shapeCode);
            //printBoard();
            timeCounter = 0;
        }

        if (newBlockBoolean !== 1){
            if (moveDownFlag === true){
                y = y + 20;
                moveBlock(2, boardX, boardY, blck1.shapeCode);
                //printBoard();
                moveDownFlag = false;
            } else if (moveRightFlag === true){
                x = x + 20;
                moveBlock(1, boardX, boardY, blck1.shapeCode);
                //printBoard();
                moveRightFlag = false;
            } else if (moveLeftFlag === true){
                x = x - 20;
                moveBlock(0, boardX, boardY, blck1.shapeCode);
                //printBoard();
                moveLeftFlag = false;
            } else if (rotateFlag === true){
                rotateBlock(blck1, boardX, boardY);
                //printBoard();
                rotateFlag = false;
            } 
        }

       drawGraphics();

       if (gameEnded === true){
            gameState = GAME_OVER;
       }

    } else if (gameState === PAUSED){
        pauseGame(); 

    } else if (gameState === GAME_OVER){
        endGame(); 

    } else if (gameState === SPLASH){
        clear();
        splashScreen();  

    } 

};




