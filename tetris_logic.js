/* *********************************** Tetris ********************************* */  
var intervalId;
var canvas;
var context;

var WIDTH = 0;
var HEIGHT = 0;

var SCORE = 0;

var upKeyDown = false;
var downKeyDown = false;
var rightKeyDown = false;
var leftKeyDown = false;

var x = 0; //pixel coordinates
var y = 0;

var boardX = 7;
var boardY = 1;

var boardWidth = 15;
var boardHeight = 20;

var gameboard;

var SPLASH = 0;
var PLAYING_GAME = 1;
var PAUSED = 2;
var GAME_OVER = 3;
var GAME_WON = 8;

var gameState = SPLASH;

var rotateFlag = false;
var moveRightFlag = false;
var moveLeftFlag = false;
var moveDownFlag = false;
var rotateFlag = false;

var numRowsChecked = 0;
var blockReachedBottom = 0;
var blockNumber = 0;
var newBlockBoolean = 0;
   
var gameEnded = false;

var frameRATE = 30.0;
var timeCounter = 0.0;
var accelaration = 1.0;


var splashImg = new Image();
splashImg.src = 'splashScreen.jpg';


var Block = function(blockt, orient){
    this.blockLength = 20;
    this.shapeCode = "";
    this.blockType = blockt;
    this.orientation = orient;

   /*block types
    * 0 = 4x1 line
    * 1 = 2x2 block
    * 2 = z-block
    * 3 = s-block
    * 4 = T block
    * 5 = J block
    * 6 = L block
    */

    this.encodeBlockType = function(){
        if (this.blockType === 0){
          this.shapeCode = "0RRR";

        } else if (this.blockType === 1){
            this.shapeCode = "0RDL";

        } else if (this.blockType === 2){
            this.shapeCode = "0RDR";

        } else if (this.blockType === 3){
            this.shapeCode = "0LDL";

        } else if (this.blockType === 4){
            this.shapeCode = "0DLRR";

        } else if (this.blockType === 5){
            this.shapeCode = "0RRU";

        } else if (this.blockType === 6){
            this.shapeCode = "0DRR";

        }
    };


    this.printBlockType = function(){
        //console.log(this.shapeCode);
    };


    this.rotateBlockCode = function(){
        var newShapeCode = "";
        for(var i = 0; i < this.shapeCode.length; i++){
            if (this.shapeCode.charAt(i) === '0'){
                newShapeCode = newShapeCode + "R";

            } else if (this.shapeCode.charAt(i) === 'R'){
                newShapeCode = newShapeCode + "D";

            } else if (this.shapeCode.charAt(i) === 'L'){
                newShapeCode = newShapeCode + "U";

            } else if (this.shapeCode.charAt(i) === 'D'){
                newShapeCode = newShapeCode + "L";

            } else if (this.shapeCode.charAt(i) === 'U'){
                newShapeCode = newShapeCode + "R";
            }
        }

        this.shapeCode = newShapeCode;
        //console.log(this.shapeCode);
    };


   this.rotate = function(){
        this.orientation++;
        this.orientation = this.orientation % 4;
   }; 
};

var setup = function(){
    //console.log("SETUP");
    context = document.getElementById('canvas');
    canvas = context.getContext('2d');

    gameState = SPLASH;
    splashScreen();
    initializeGame();
};


/*
var printBoard = function(){
    for (var i = 0; i < boardHeight; i++){
        for (var j = 0; j < boardWidth; j++){
              console.log(gameboard[i][j]);
            }
        console.log(" ");
    }
};
*/


var initGameboard = function(cols, rows){
    //console.log("INITGAMEBOARD");
    gameboard = new Array(cols);

    for (var i = 0; i < cols; i++){
        gameboard[i] = new Array(rows)
    }

    for (var i = 0; i < cols; i++){
        for (var j = 0; j < rows; j++){
            gameboard[i][j] = 0;
        }
    }
}

var initializeGame = function(){
    //console.log("INITIALIZEGAME");
    gameboard = null;
    WIDTH = 300;
    HEIGHT = 400;
    
    blockNumber = 0;
    newBlockBoolean = 0;
    rotateFlag = false;
    SCORE = 0;
    numRowsChecked = 0;
    blockReachedBottom = 0;
    gameEnded = false;

    boardX = 7;
    boardY = 1;

    x = 0;
    y = 0;

    frameRATE = 30.0;
    timeCounter = 0.0;
    accelaration = 1.0;

    initGameboard(boardHeight, boardWidth);

    blockNumber = Math.floor(Math.random()*7);
    //console.log("blockNumber"+blockNumber);

    blck1 = new Block(blockNumber, 0);
    blck1.encodeBlockType();

    placeBlock(boardX, boardY, blck1.shapeCode);
    //printBoard();
};





/* ********************************** LOGIC ************************************ */
var placeBlock = function(xpos, ypos, code){
    //console.log("PLACEBLOCK");
    var squares = new Array(2);
    squares[0] = new Array(5);
    squares[1] = new Array(5);

    for (var i = 0; i < squares.length; i++){
        for (var j = 0; j < squares[i].length; j++){
            squares[i][j] = 0;
        }
    }

    var i = 0;
    while (i !== code.length ){
        if (code.charAt(i) === '0'){
            squares[0][i] = ypos;
            squares[1][i] = xpos;
        }else if (code.charAt(i) === 'R'){
            xpos++;
            squares[0][i] = ypos;
            squares[1][i] = xpos;
        } else if (code.charAt(i) === 'L'){
            xpos--;
            squares[0][i] = ypos;
            squares[1][i] = xpos;
        } else if (code.charAt(i) === 'D'){
            ypos++;
            squares[0][i] = ypos;
            squares[1][i] = xpos;
        } else if (code.charAt(i) === 'U'){
            ypos--;
            squares[0][i] = ypos;
            squares[1][i] = xpos;
        } 

        //console.log("xpos:"+xpos+"ypos:"+ypos);
        if (ypos >= boardHeight){
            //console.log("reached bottom");
            blockReachedBottom = 1;
            return 3;
        }
        
        if ((ypos < 0) || (ypos >= boardHeight) || (xpos < 0) || (xpos >= boardWidth)){
            //console.log("reached an edge!");
            return 0;
        }else{
            i++;
        }
    }
    
    //check for blocks already placed in this location
    for (var x = 0; x < i; x++){
        if (gameboard[squares[0][x]][squares[1][x]] === 1){
            //console.log("cannot place block, existing block already in this location");
            blockReachedBottom = 1;
            return 3;
        } 
    }

    for (var x = 0; x < i; x++){
        gameboard[squares[0][x]][squares[1][x]] = 1;
    }
    return 1;
};



var moveBlock = function(dir, xposition, yposition, code){
    var origX = xposition;
    var origY = yposition;

    if (erasePreviousBlockLocation(xposition, yposition, code) === 0){
        //console.log("could not erase prev block location after move");
        return 0;
    }

    //dir: 0 = left, 1 = right, 2 = down
    if (dir === 0){
        xposition = xposition - 1;
    }else if (dir === 1){
        xposition = xposition + 1;
    }else {
        yposition = yposition + 1;
    }

    var placeBlockReturnVal = placeBlock(xposition, yposition, code);

    if (placeBlockReturnVal === 1){
        boardX = xposition;
        boardY = yposition;
        return 1;
    }else{
        if (placeBlockReturnVal === 3){
            if (placeBlock(origX, origY, code) === 1){//$$$
                //console.log("couldnt move block, placed back in original pos");
            } else {
                //console.log("couldnt return block to original pos");
            } if (dir === 1 || dir === 0){
                //console.log("dont call new block?");
            } else { 
                newBlockBoolean = 1;
            }
            
            return 0;
            
        }else{
            if (placeBlock(origX, origY, code) == 1){
                 //console.log("couldnt move block, placed back in original pos");
            } else {
                //console.log("couldnt return block to original pos");
            }
            
            return 0;
        }
       
    }
};


var erasePreviousBlockLocation = function(xpos, ypos, code){
    //console.log("ERASEPREVIOUSBLOCKLOCATION");

    var squares = new Array(2);
    squares[0] = new Array(5);
    squares[1] = new Array(5);

    for (var i = 0; i < squares.length; i++){
        for (var j = 0; j < squares[i].length; j++){
            squares[i][j] = 0;
        }
    }

    var i = 0;
    while (i !== code.length ){
        if (code.charAt(i) === '0'){
            squares[0][i] = ypos;
            squares[1][i] = xpos;
        }else if (code.charAt(i) === 'R'){
            xpos++;
            squares[0][i] = ypos;
            squares[1][i] = xpos;
        } else if (code.charAt(i) === 'L'){
            xpos--;
            squares[0][i] = ypos;
            squares[1][i] = xpos;
        } else if (code.charAt(i) === 'D'){
            ypos++;
            squares[0][i] = ypos;
            squares[1][i] = xpos;
        } else if (code.charAt(i) === 'U'){
            ypos--;
            squares[0][i] = ypos;
            squares[1][i] = xpos;
        } 

        //console.log("xpos:"+xpos+"ypos:"+ypos);

        if ((ypos < 0) || (ypos >= boardHeight) || (xpos < 0) || (xpos >= boardWidth)){
            //console.log("cannot erase!");
            return 0;
        }else{
            i++;
        }
    }

    for (var x = 0; x < i; x++){
        gameboard[squares[0][x]] [squares[1][x]] = 0;
    }
    return 1;

};


var rotateBlock = function (blck, xpos, ypos){
    //console.log('ROTATEBLOCK');
    var originalCode = blck.shapeCode;
    var placeBlockReturnVal = 0;

    if (erasePreviousBlockLocation(xpos, ypos, blck.shapeCode) === 0){
        //console.log("error in erasing block rotation");
        return;
    }

    blck.rotateBlockCode();

    placeBlockReturnVal = placeBlock(xpos, ypos, blck.shapeCode);

    if (placeBlockReturnVal === 1){
        //console.log('successful rotation');
        return;
    }else{
    
        blck.shapeCode = originalCode;
        placeBlockReturnVal = placeBlock(xpos, ypos, blck.shapeCode);
        if (placeBlockReturnVal === 1){
            //console.log('rotation failed, return to original');
        }else if (placeBlockReturnVal === 0){
            //console.log('rotation failed, could not return to original');
        }else if (placeBlockReturnVal === 3){
           //console.log('rotation failed, reached bottom?');
           newBlockBoolean = 1;
        }
    }

    return;
};


var rowCheck = function(){
    //console.log('ROWCHECK');
    var isRowFull = 1; //assumes the row is full of 1's until it discovers a hole.
    
    //create a 2D array
    var tempGameboard = new Array(boardHeight);
    for (var i = 0; i < boardHeight; i++){
        tempGameboard[i] = new Array(boardWidth);
    }
    
    //initialize all tempGameboard to all zero rows
    for (var i = 0; i < boardHeight; i++){
        for (var j = 0; j < boardWidth; j++){
                tempGameboard[i][j] = 0;
        }
    }

    var numOfRowsToCopy = 0; 
    var completedRows = 0;

    for (var i = boardHeight-1; i >= 0 ; i--){
        for (var j = 0; j < boardWidth; j++){
            if (gameboard[i][j] !== 1){
                numOfRowsToCopy++;
                isRowFull = 0;
                break;
            } 
        }
       
       if (isRowFull === 0){
           isRowFull = 1;
           //copy entire row into tempGameBoard from bottom up
           for (var x = 0; x < boardWidth; x++){
               tempGameboard[boardHeight-numOfRowsToCopy][x] = gameboard[i][x];
          }
       } else {
             completedRows++;
       }
    }

    gameboard = tempGameboard;

    return completedRows;
                    
};



