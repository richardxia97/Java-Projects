/******************************************************************************
 * 
 * COMP 1406, Assignment 7
 * Richard Xia, 101007519, 11/21/2016 
 *
 *  Compilation:  javac Stopping.java
 *  Execution:    java CaptureTheFlag
 * 
 *  Class Stopping implements the behavior of a Stopping player.
 *  A Stopping player will initially move in a random direction and 
 *  stops moving when it reaches the border of the playing field (without leaving 
 *  the playing field).
 */

public class Stopping extends Player {
  @Override
  public void play(Field f){
    // play is the logic for the player
    // you should make changes to the player's speed here
    // you should not update position
    
    if( Math.random() < 0.5){
      this.speedX *= 0.99;
    }
    if( Math.random() < 0.0005){
      this.speedY *= -1;
    }
 
    //  Class Stopping implements the behavior of a Stopping player.
    //  A Stopping player will initially move in a random direction and 
    //  stops moving when it reaches the border of the playing field (without leaving 
    //  the playing field).
 
    if (this.x < f.minX || this.x > f.maxX || this.y < f.minY || this.y > f.maxY) 
    {     
     this.speedX = 0;
     this.speedY = 0;
    }
  }
  
  @Override
  public void update(Field field){}    
    
  public Stopping(Field f, int side, String name, int number, String team,char symbol, double x, double y){
    super(f, side, name, number, team, symbol, x, y);
    this.speedX = Math.random()*4-2;
    this.speedY = Math.random()*4-2;
  }
}