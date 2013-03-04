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

  void encodeBlockType(){
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

  void printBlockType(){
    println(shapeCode);
  }

  void rotateBlockCode(){
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

  void rotate(){
    orientation++;
    orientation = orientation % 4;

  }

  void display(int xpos, int ypos) {
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

