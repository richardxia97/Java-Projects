/******************************************************************************
 * 
 * COMP 1406, Assignment 7
 * Richard Xia, 101007519, 11/21/2016 
 *
 *  Compilation:  javac Chaser.java
 *  Execution:    java CaptureTheFlag
 * 
 *  Class Chaser implements the behavior of a Chaser.
 *  
 *  A chaser player will chase after a player on the opposing team.If your player catches up
 *  to the player, your player should continue to follow it. 
 * 
 *  A caller must call setTarget() to specify the target to chase.
 * 
 *  Uasage:
 *    p = new RandomWalker(f, 1, "Cat van Kittenish", 12, "blues", 'b', Math.random()*400+10, Math.random()*500+10);      
 *    q = new Chaser(f, 2, "Bunny El-Amin", 7, "reds", 'r', Math.random()*400+410, Math.random()*500+10);
 *    (Chaser) q).setTarget(p);              
 *
 */

import java.util.ArrayList;

public class Chaser extends Player {
  
  /** the player to chase */
  private Player target;
  
  /** indicate whether target is valid to chase */
  boolean validTarget;
  
  @Override 
  public void play(Field f){        
    if (!validTarget)      
    {
      return; 
    }
    
    // The algorithm to chase the target player
    // 1) find delta X and delta Y to reach target from this player
    // 2) set speed X and Y of this player according to the ratio of delta X and Y

    int deltaX = target.getX() - getX();
    int deltaY = target.getY() - getY();
    double speedBase = Math.abs(target.getX());
    if (Math.abs(target.getY()) > speedBase) {
      speedBase = Math.abs(target.getY());
    }
    
    // chase should move quicker than the target
    speedBase *= 1.2;
    double newSpeedX = speedBase;
    if (deltaY != 0){
        newSpeedX = speedBase * ((int) deltaX/deltaY);
    }       
    double newSpeedY = speedBase;
    
    // dierction
    if (target.getX() < getX())
    {
      newSpeedX *= (-1);
    }
        
    if (target.getY() < getY())
    {
      newSpeedY *= (-1);
    }     
        
    this.speedX = newSpeedX;
    this.speedY=  newSpeedY;        
  }
  
   /** Set target player to catch
    * 
    * @param target the target player to catch
    * 
    * @return N/A
    */
  public void setTarget(Player target)
  {
    this.target = target;
    validTarget = true;
  }

   /** Get target player to catch
    * 
    * @param void
    * 
    * @return target the target player to catch
    */
  public Player getTarget()
  {
    return this.target;
  }

  
  @Override
  public void update(Field field){}
  public Chaser(Field f, int side, String name, int number, String team,char symbol, double x, double y){
    super(f, side, name, number, team, symbol, x, y);
    this.speedX = Math.random()*4-2;
    this.speedY = Math.random()*4-2;   
    validTarget = false;
  }
}