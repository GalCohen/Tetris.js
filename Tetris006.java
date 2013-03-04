import processing.core.*; 
import processing.xml.*; 

import java.awt.*; 

import java.applet.*; 
import java.awt.Dimension; 
import java.awt.Frame; 
import java.awt.event.MouseEvent; 
import java.awt.event.KeyEvent; 
import java.awt.event.FocusEvent; 
import java.awt.Image; 
import java.io.*; 
import java.net.*; 
import java.text.*; 
import java.util.*; 
import java.util.zip.*; 
import java.util.regex.*; 

public class Tetris006 extends PApplet {

/* *********************** tetris by Gal ******************** */


PImage splash;
int splashOn = 1;


Block blck1;
int x = 0; //pixel coordinates
int y = 0;

int boardX = 7;
int boardY = 1;

int boardWidth = 15;
int boardHeight = 20;

int[][] gameboard = new int[boardHeight][boardWidth];

int startTime = 0;
int startMin = 0;
int startSec = 0;
int currentTime = 0;

int blockNumber = 0;

float frameRATE = 30;
float timeCounter = 0;
float accelaration = 1;

int newBlockBoolean = 0; // 1 = block reached bottom, make a new one, 0 = block still moving down

int moveRightFlag = 0;
int moveLeftFlag = 0;
int moveDownFlag = 0;
int rotateFlag = 0;

int SCORE = 0;
int numRowsChecked = 0;

int blockReachedBottom = 0;

PFont fontA;

int gameEnded = 0;






public void setup(){
  frameRate(PApplet.parseInt(frameRATE));
  size(300, 400);

  splash = new PImage();
  splash = loadImage("data/splashScreen.jpg");

  initializeGame();


  //gameboard[9][12] = 1;
}


public void draw(){
  if (splashOn == 1){
    splashScreen();
    splashOn = 0; 
  }
  else{

    background(0);
    stroke(0,225,0);
    noFill();



    //drawGraphics();

    //currentTime = second() + minute()*60;

    timeCounter++;


    if (blockReachedBottom == 1){
      numRowsChecked = rowCheck();
      SCORE = SCORE + (numRowsChecked * 100);
      blockReachedBottom = 0;
      if (numRowsChecked > 0){
        accelaration = accelaration - 0.1f;
      }
    }


    if (newBlockBoolean == 1){
      // MAKE A NEW BLOCK HERE @@@@
      blockNumber = PApplet.parseInt(random(7));
      println("BLOCKNUMBER:" + blockNumber);
      blck1 = new Block(blockNumber, 0);  
      newBlockBoolean = 0;
      if (placeBlock(7, 1, blck1.shapeCode) == 1){
        println("new block created");
        x = 140; 
        y = 0;
        boardX = 7;
        boardY = 1;
        accelaration = accelaration + 0.05f;
      }
      else{
        gameEnded = 1;
      }
    }


    if (timeCounter > (frameRATE / accelaration)){
      moveBlock(2, boardX, boardY, blck1.shapeCode);
      printBoard();
      timeCounter = 0;
    } 
    //println(timeCounter);

    if (newBlockBoolean != 1){
      if (moveDownFlag == 1){
        y = y + 20;
        moveBlock(2, boardX, boardY, blck1.shapeCode);
        //printBoard();
        moveDownFlag = 0;
      }
      else if(moveRightFlag == 1){
        x = x + 20;
        moveBlock(1, boardX, boardY, blck1.shapeCode);
        //printBoard();
        moveRightFlag = 0;
      }
      else if(moveLeftFlag == 1){
        x = x - 20;
        moveBlock(0, boardX, boardY, blck1.shapeCode);
        //printBoard();
        moveLeftFlag = 0;
      }
      else if(rotateFlag == 1){
        rotateBlock(blck1, boardX, boardY);
        //printBoard();
        rotateFlag = 0;
      }

    }
    //println("SCORE:" + SCORE);
    drawGraphics();
    if (gameEnded == 1){
      endGame();
    }
    //blck1.display(x , y); 
  }

}
public int placeBlock(int xpos, int ypos,  String code){
  int i = 0;
  /*
  if ((xpos >= boardWidth) || (ypos >= boardHeight)){
   println("reach edge!! before placing attemptasd");
   newBlockBoolean = 1;
   return 0;
   }
   */

  //RETURN 1 IF SUCCESS, 0 IF FAILED, 3 IF NEW BLOCK NEEDS TO BE MADE

  int[][] squares = new int[2][5];

  //initialize squares matrix
  for(int f = 0; f < 2; f++){
    for (int h = 0; h < 5; h++){
      squares[f][h] = 0;
    }  
  }


  while (i != code.length()){

    if (code.charAt(i) == '0'){
      //gameboard[ypos][xpos] = 1;
      //box1[0] = ypos;
      //box1[1] = xpos;
      squares[0][i] = ypos;
      squares[1][i] = xpos;
    }
    else if(code.charAt(i) == 'R'){
      xpos++;
      //box2[0] = ypos; 
      //box2[1] = xpos;
      squares[0][i] = ypos;
      squares[1][i] = xpos;
      //println("R ");
    }
    else if(code.charAt(i) == 'L'){
      xpos--;
      //box3[0] = ypos; 
      //box3[1] = xpos;
      squares[0][i] = ypos;
      squares[1][i] = xpos;
      //println("L");
    }
    else if(code.charAt(i) == 'D'){
      ypos++;
      //box4[0] = ypos; 
      //box4[1] = xpos;
      squares[0][i] = ypos;
      squares[1][i] = xpos;
      //println("D");
    }
    else if(code.charAt(i) == 'U'){
      ypos--;
      //box5[0] = ypos; 
      //box5[1] = xpos;
      squares[0][i] = ypos;
      squares[1][i] = xpos;
      //println("U");
    }
    println("xpos:" + xpos + " ypos: "+ ypos);

    if (ypos >= boardHeight){
      println("reached bottom");
      /*
      if (newBlockBoolean != 1){
       newBlockBoolean = 1;
       }
       */
      //if (blockReachedBottom != 1){
      blockReachedBottom = 1; //  @@@@@@
      //}
      return 3;
    }

    println(code);

    if ((ypos < 0) || (ypos >= boardHeight) || (xpos < 0) || (xpos >= boardWidth)) { 
      println("reached an edge!");
      //background(255);
      return 0;
    }
    else{
      //gameboard[ypos][xpos] = 1;
      i++;
    } 
  }

  //check for blocks already placed in this location
  for (int x = 0; x < i; x++){
    if (gameboard[ squares[0][x]] [squares[1][x] ] == 1){
      println("cannot place block, existing block already in this location");
      /*
      if (newBlockBoolean != 1){
       newBlockBoolean = 1;
       
       }
       */
      //$$$
      blockReachedBottom = 1;
      return 3;
    }
  }  

  //place block
  for (int x = 0; x < i; x++){
    gameboard[ squares[0][x]] [squares[1][x] ] = 1;
  }
  return 1;
}



public void printBoard(){
  for (int i = 0; i < boardHeight; i++){
    for (int j = 0; j < boardWidth; j++){
      print(gameboard[i][j]);
    }
    println();
  }
}



public int moveBlock(int dir, int xposition,int yposition, String code){
  int origX = xposition;
  int origY = yposition;

  if (erasePreviousBlockLocation(xposition, yposition, code) == 0){
    println("could not erase prev block location after move");   
    return 0;
  }

  // dir: 0 = left, 1 = right, 2 = down
  if (dir == 0){
    xposition = xposition - 1;  
  }
  else if(dir == 1){
    xposition = xposition + 1;
  }
  else{
    yposition = yposition + 1;
  }

  int placeBlockReturnVal= placeBlock(xposition, yposition, code);

  if ( placeBlockReturnVal== 1){
    boardX = xposition;
    boardY = yposition;
    return 1;
  }
  else{
    if (placeBlockReturnVal == 3){
      if (placeBlock(origX, origY, code) == 1){//$$$
        println("couldnt move block, placed back in original pos");
      }
      else{
        println("couldnt return block to original pos");
      }
      if (dir == 1 || dir == 0){
        println("dont call new block?");
      } 
      else{ 
        newBlockBoolean = 1;
      }
      return 0;
    }
    else{
      if (placeBlock(origX, origY, code) == 1){
        println("couldnt move block, placed back in original pos");
      }
      else{
        println("couldnt return block to original pos");
      }
      return 0;
    }
  }   
}


public int erasePreviousBlockLocation(int xpos, int ypos, String code){
  int[][] squares = new int[2][5];
  int i = 0; 
  /*
 if ((xpos >= boardWidth) || (ypos >= boardHeight)){
   println("cannot erase, out of bounds BEFORE attempt");
   return 0;
   }
   */
  //initialize squares matrix
  for(int f = 0; f < 2; f++){
    for (int h = 0; h < 5; h++){
      squares[f][h] = 0;
    }  
  }

  while (i != code.length()){

    if (code.charAt(i) == '0'){
      squares[0][i] = ypos;
      squares[1][i] = xpos;
    }
    else if(code.charAt(i) == 'R'){
      xpos++;
      squares[0][i] = ypos;
      squares[1][i] = xpos;
      //println("R ");
    }
    else if(code.charAt(i) == 'L'){
      xpos--;
      squares[0][i] = ypos;
      squares[1][i] = xpos;
      //println("L");
    }
    else if(code.charAt(i) == 'D'){
      ypos++;
      squares[0][i] = ypos;
      squares[1][i] = xpos;
      //println("D");
    }
    else if(code.charAt(i) == 'U'){
      ypos--;
      squares[0][i] = ypos;
      squares[1][i] = xpos;
      //println("U");
    }
    println("xpos:" + xpos + " ypos: "+ ypos);
    if ((ypos < 0) || (ypos >= boardHeight) || (xpos < 0) || (xpos >= boardWidth)) {
      println("cannot erase");
      return 0;
    }
    else{
      i++;
    } 
  }
  for (int x = 0; x < i; x++){
    gameboard[ squares[0][x]] [squares[1][x] ] = 0;
  }
  return 1;

}


//rotate block
//erase old block, rotate code, try placing new block, if works, place, if not, place old block
public void rotateBlock(Block blck, int xpos, int ypos){
  String originalCode = blck.shapeCode;

  int placeBlockReturnVal;

  if (erasePreviousBlockLocation(xpos, ypos, blck.shapeCode) == 0){   
    println("error in erasing block in rotation");
    return;
  }

  blck.rotateBlockCode();
  placeBlockReturnVal =placeBlock(xpos, ypos, blck.shapeCode);
  if (placeBlockReturnVal == 1){
    println("succesful rotation");
    return;
    // }else if(placeBlockReturnVal == 3){
    // println("succesful rotation, reached bottom?");
    // newBlockBoolean = 1;
  }
  else{
    blck.shapeCode = originalCode;
    placeBlockReturnVal = placeBlock(xpos, ypos, blck.shapeCode);
    if (placeBlockReturnVal == 1){
      println("failed rotation, return to original");
    }
    else if (placeBlockReturnVal == 0){
      println("rotation failed, and could not return to original pos");
    }
    else if(placeBlockReturnVal == 3){
      println("rotation failed, reached bottom?");
      newBlockBoolean = 1;  
    }
  }
  return;
}


public int rowCheck(){
  int isRowFull = 1; //assumes the row is full of 1's until it discovers a hole.

  int[][] tempGameboard = new int[boardHeight][boardWidth];
  //initialize all tempGameboard to all zero rows
  for (int i = 0; i < boardHeight; i++){
    for (int j = 0; j < boardWidth; j++){
      tempGameboard[i][j] = 0;
    }
  }


  int numOfRowsToCopy = 0; 
  int completedRows = 0;

  for (int i = boardHeight-1; i >= 0 ; i--){
    for (int j = 0; j < boardWidth; j++){
      if (gameboard[i][j] != 1){
        numOfRowsToCopy++;
        isRowFull = 0;
        //println("found row to copy");
        break;
      } 
    }

    if (isRowFull == 0){
      isRowFull = 1;
      //copy entire row into tempGameBoard from bottom up
      for(int x = 0; x < boardWidth; x++){
        tempGameboard[boardHeight-numOfRowsToCopy][x] = gameboard[i][x];
      }
      //println("copied row to board");
    }
    else{
      completedRows++;
    }

  }

  gameboard = tempGameboard;
  /*
  if (completedRows >= 1){
   if (newBlockBoolean != 1){
   newBlockBoolean = 1;
   }
   }
   */
  //completedRows = boardHeight-numOfRowsToCopy; 
  //println("completed rows:" + completedRows);
  return completedRows;
}


public void drawGraphics(){
  //board height 400
  //board width 300
  //board array height 20
  //board array width 15
  // rect(xpos, ypos, blockLength, blockLength);
  noFill();
  rect(0, 0, 299, 399);

  stroke(0,225,0);
  fill(0,100,0);
  rect(0, 0, 300, 20);


  for (int i = 0; i < boardHeight; i++){
    for (int j = 0; j < boardWidth; j++){
      if (gameboard[i][j] == 1){
        rect(j * 20, i * 20 , 20, 20);
      } 
    }
  }

  fill(0, 220,0);
  textFont(fontA, 16);
  text("Score:", 10, 17);
  text(SCORE, 75, 18);
}


public void keyPressed(){

  if (key == CODED){
    if (keyCode == DOWN) { //s
      moveDownFlag = 1;

    } 
    else if (keyCode == LEFT) { //a
      moveLeftFlag = 1;


    } 
    else if (keyCode == RIGHT) { //d
      moveRightFlag = 1;

    } 
    else if (keyCode == UP) { //w
      rotateFlag = 1;

    }

  }  else if (key == 115) { //s
   moveDownFlag = 1;
   
   } 
   else if (key == 97) { //a
   moveLeftFlag = 1;
   
   
   } 
   else if (key== 100) { //d
   moveRightFlag = 1;
   
   } 
   else if (key== 119) { //w
   rotateFlag = 1;
   
   }
  else if(key == 32){ // spacebar

    initializeGame();

  }

  /*
    if (key == 115) { //s
   moveDownFlag = 1;
   
   } 
   else if (key == 97) { //a
   moveLeftFlag = 1;
   
   
   } 
   else if (key== 100) { //d
   moveRightFlag = 1;
   
   } 
   else if (key== 119) { //w
   rotateFlag = 1;
   
   }
   else if(key == 32){ // spacebar
   
   initializeGame();
   
   }
   */


}

public void initializeGame(){
  x = 0; //pixel coordinates
  y = 0;

  boardX = 7;
  boardY = 1;

  boardWidth = 15;
  boardHeight = 20;

  startTime = 0;
  startMin = 0;
  startSec = 0;
  currentTime = 0;

  blockNumber = 0;

  frameRATE = 30;
  timeCounter = 0;
  accelaration = 1;

  newBlockBoolean = 0; // 1 = block reached bottom, make a new one, 0 = block still moving down

  moveRightFlag = 0;
  moveLeftFlag = 0;
  moveDownFlag = 0;
  rotateFlag = 0;

  SCORE = 0;
  numRowsChecked = 0;

  blockReachedBottom = 0;

  gameEnded = 0;

  background(0);
  stroke(0,225,0);
  noFill();
  //rect(0, 0, 299, 399);
  fontA = loadFont("Ziggurat-HTF-Black-32.vlw");

  blockNumber = PApplet.parseInt(random(7));
  println("blocknumber:" + blockNumber);
  blck1 = new Block(blockNumber, 0);

  for (int i = 0; i < boardHeight; i++){
    for (int j = 0; j < boardWidth; j++){
      gameboard[i][j] = 0;
    }
  }

  placeBlock(boardX, boardY, blck1.shapeCode);
  printBoard();

  startSec = second();
  startMin = minute();
  startTime = startMin * 60 + startSec;
  currentTime = startTime;

  loop();
}

public void endGame(){
  noLoop();
  print("GAME OVER. SCORE:" + SCORE);

  fill(0, 90, 0);
  // noFill();
  rect(75, 150, 150, 80);
  fill(0, 220,0);
  textFont(fontA, 18);
  text("Game Over", 85, 175);
  textFont(fontA, 14);
  text("Hit the Spacebar", 82, 195);
  text("to play again", 82, 210);


  //text(SCORE, 75, 18);
}


public void splashScreen(){

  noLoop();
  println("TEST");
  image(splash, 0, 0);

}




class Block {
  int blockType;
  int orientation;
  int blockLength = 20;
  String shapeCode = "";

  /*block types
   0 = 4x1 line
   1 = 2x2 block
   2 = z-block
   3 = s-block
   4 = T block
   5 = J block
   6 = L block
   */

  Block(int blockt, int orient) {
    blockType = blockt;
    orientation = orient;
    encodeBlockType();
  }

  public void encodeBlockType(){
    if (blockType == 0){
      shapeCode = "0RRR";
    }
    else if(blockType == 1){
      shapeCode = "0RDL";
    }
    else if(blockType == 2){
      shapeCode = "0RDR";
    }
    else if(blockType == 3){
      shapeCode = "0LDL";
    }
    else if(blockType == 4){
      shapeCode = "0DLRR";
    }
    else if(blockType == 5){
      shapeCode = "0RRU";
    }
    else if(blockType == 6){
      shapeCode = "0DRR";
    }
  }

  public void printBlockType(){
    println(shapeCode);
  }

  public void rotateBlockCode(){
    int i = 0;
    String newShapeCode = "";
    while( i != shapeCode.length()){
      if (shapeCode.charAt(i) == '0'){
        newShapeCode = newShapeCode + "R";

      }
      else if(shapeCode.charAt(i) == 'R'){
        newShapeCode = newShapeCode + "D";

      }
      else if(shapeCode.charAt(i) == 'L'){
        newShapeCode = newShapeCode + "U";

      }
      else if(shapeCode.charAt(i) == 'D'){
        newShapeCode = newShapeCode + "L";

      }
      else if(shapeCode.charAt(i) == 'U'){
        newShapeCode = newShapeCode + "R";
      }
      i++;
    }

    shapeCode = newShapeCode;
    println(shapeCode);
  }

  public void rotate(){
    orientation++;
    orientation = orientation % 4;

  }

  public void display(int xpos, int ypos) {
    if (blockType == 0){
      if (orientation == 0 || orientation == 2){
        rect(xpos, ypos, blockLength, blockLength);
        rect(xpos+blockLength, ypos, blockLength, blockLength);
        rect(xpos+blockLength*2, ypos, blockLength, blockLength);
        rect(xpos+blockLength*3, ypos, blockLength, blockLength);

      }
      else{
        rect(xpos, ypos, blockLength, blockLength);
        rect(xpos, ypos+blockLength, blockLength, blockLength);
        rect(xpos, ypos+blockLength*2, blockLength, blockLength);
        rect(xpos, ypos+blockLength*3, blockLength, blockLength);

      }
    }
    else if (blockType == 1){
      rect(xpos, ypos, blockLength, blockLength);
      rect(xpos+blockLength, ypos, blockLength, blockLength);
      rect(xpos, ypos+blockLength, blockLength, blockLength);
      rect(xpos+blockLength, ypos+blockLength, blockLength, blockLength);

    }
    else if (blockType == 2){
      if (orientation == 0 || orientation == 2){
        /*
           . .
         . . 
         */
        rect(xpos, ypos, blockLength, blockLength);
        rect(xpos+blockLength, ypos, blockLength, blockLength);
        rect(xpos+blockLength, ypos+blockLength, blockLength, blockLength);
        rect(xpos+blockLength+blockLength, ypos+blockLength, blockLength, blockLength);
      }
      else{
        /*
         .
         . .
         .
         */
        rect(xpos, ypos, blockLength, blockLength);
        rect(xpos, ypos+blockLength, blockLength, blockLength);
        rect(xpos-blockLength, ypos, blockLength, blockLength);
        rect(xpos-blockLength, ypos-blockLength, blockLength, blockLength);      
      }   


    }
    else if (blockType == 3){
      if (orientation == 0 || orientation == 2){
        /*
        . .
         . .
         */
        rect(xpos, ypos, blockLength, blockLength);
        rect(xpos-blockLength, ypos, blockLength, blockLength);
        rect(xpos, ypos-blockLength, blockLength, blockLength);
        rect(xpos+blockLength, ypos-blockLength, blockLength, blockLength);
      }
      else{
        /*
          .
         . .
         .  
         */
        rect(xpos, ypos, blockLength, blockLength);
        rect(xpos-blockLength, ypos, blockLength, blockLength);
        rect(xpos-blockLength, ypos+blockLength, blockLength, blockLength);
        rect(xpos, ypos-blockLength, blockLength, blockLength);
      }
    }
    else if (blockType == 4){
      if (orientation == 0){
        /* 
         .
         . . .
         */
        rect(xpos, ypos, blockLength, blockLength);
        rect(xpos+blockLength, ypos, blockLength, blockLength);
        rect(xpos-blockLength, ypos, blockLength, blockLength);
        rect(xpos, ypos-blockLength, blockLength, blockLength);
      }
      else if (orientation == 1){
        /*
        .
         . .
         .
         */
        rect(xpos, ypos, blockLength, blockLength);
        rect(xpos, ypos-blockLength, blockLength, blockLength);
        rect(xpos, ypos+blockLength, blockLength, blockLength);
        rect(xpos+blockLength, ypos, blockLength, blockLength);
      }
      else if (orientation == 2){
        /*
        . . .
         .
         */
        rect(xpos, ypos, blockLength, blockLength);
        rect(xpos+blockLength, ypos, blockLength, blockLength);
        rect(xpos-blockLength, ypos, blockLength, blockLength);
        rect(xpos, ypos+blockLength, blockLength, blockLength);
      }
      else if (orientation == 3){
        /*
          .
         . .
         .
         */
        rect(xpos, ypos, blockLength, blockLength);
        rect(xpos, ypos-blockLength, blockLength, blockLength);
        rect(xpos, ypos+blockLength, blockLength, blockLength);
        rect(xpos-blockLength, ypos, blockLength, blockLength);
      }  

    }
    else if (blockType == 5){
      if (orientation == 0){
        /*
             .
         . . .
         */
        rect(xpos, ypos, blockLength, blockLength);
        rect(xpos+blockLength, ypos, blockLength, blockLength);
        rect(xpos-blockLength, ypos, blockLength, blockLength);
        rect(xpos+blockLength, ypos-blockLength, blockLength, blockLength);
      }
      else if (orientation == 1){
        /*
         .
         .
         . . 
         */
        rect(xpos, ypos, blockLength, blockLength);
        rect(xpos, ypos-blockLength, blockLength, blockLength);
        rect(xpos, ypos+blockLength, blockLength, blockLength);
        rect(xpos+blockLength, ypos+blockLength, blockLength, blockLength);
      }
      else if (orientation == 2){
        /*
         . . .
         .
         */
        rect(xpos, ypos, blockLength, blockLength);
        rect(xpos+blockLength, ypos, blockLength, blockLength);
        rect(xpos-blockLength, ypos, blockLength, blockLength);
        rect(xpos-blockLength, ypos+blockLength, blockLength, blockLength);

      }
      else if (orientation == 3){
        /*
         . .
         .
         .
         */
        rect(xpos, ypos, blockLength, blockLength);
        rect(xpos, ypos-blockLength, blockLength, blockLength);
        rect(xpos, ypos+blockLength, blockLength, blockLength);
        rect(xpos-blockLength, ypos-blockLength, blockLength, blockLength);
      }

    }
    else if (blockType == 6){
      if (orientation == 0){
        /*
         .
         . . .
         */
        rect(xpos, ypos, blockLength, blockLength);
        rect(xpos+blockLength, ypos, blockLength, blockLength);
        rect(xpos-blockLength, ypos, blockLength, blockLength);
        rect(xpos-blockLength, ypos-blockLength, blockLength, blockLength);
      }
      else if (orientation == 1){
        /*
        . .
         .
         .
         */
        rect(xpos, ypos, blockLength, blockLength);
        rect(xpos, ypos-blockLength, blockLength, blockLength);
        rect(xpos, ypos+blockLength, blockLength, blockLength);
        rect(xpos+blockLength, ypos-blockLength, blockLength, blockLength);
      }
      else if (orientation == 2){
        /*
        . . .
         .
         */
        rect(xpos, ypos, blockLength, blockLength);
        rect(xpos+blockLength, ypos, blockLength, blockLength);
        rect(xpos-blockLength, ypos, blockLength, blockLength);        
        rect(xpos+blockLength, ypos+blockLength, blockLength, blockLength);
      }
      else if (orientation == 3){
        /*
        .
         .
         . .
         */
        rect(xpos, ypos, blockLength, blockLength);
        rect(xpos, ypos-blockLength, blockLength, blockLength);
        rect(xpos, ypos+blockLength, blockLength, blockLength);
        rect(xpos-blockLength, ypos+blockLength, blockLength, blockLength);
      }
    }  
  }

}

  static public void main(String args[]) {
    PApplet.main(new String[] { "--bgcolor=#FFFFFF", "Tetris006" });
  }
}
