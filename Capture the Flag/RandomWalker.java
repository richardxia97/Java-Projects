/******************************************************************************
 * 
 * COMP 1406, Assignment 7
 * Richard Xia, 101007519, 11/21/2016 
 *
 *  Compilation:  javac RandomWalker.java
 *  Execution:    java CaptureTheFlag
 * 
 * A RandomWalker player will continually walk in the playing field. When it reaches a
 * border of the playing field, it should “bounce” off the wall and continue walking and bouncing
 * off the borders. 
 * 
 */

public class RandomWalker extends Player {
  @Override 
  public void play(Field f){
    // When it reaches a border of the playing field, it should “bounce” off the wall and 
    // continue walking and bouncing off the borders. 
    if (this.x < f.minX) {
      speedX *= -1;
    }
    if (this.x > f.maxX) {
      speedX *= -1;
    }
    if (this.y < f.minY) {
      speedY *= -1;
    }
    if (this.y > f.maxY) {
      speedY *= -1;
    }
  }
  
  @Override
  public void update(Field field){}
  public RandomWalker(Field f, int side, String name, int number, String team,char symbol, double x, double y){
    super(f, side, name, number, team, symbol, x, y);
    this.speedX = Math.random()*4-2;
    this.speedY = Math.random()*4-2;
  }
}