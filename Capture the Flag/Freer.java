/******************************************************************************
  * 
  * COMP 1406, Assignment 8
  * Richard Xia, 101007519, 12/05/2016 
  *
  *  Compilation:  javac Freer.java
  *  Execution:    java CaptureTheFlag
  * 
  *  Class Freer implements the behavior of a Freer.
  *  
  *  The goal of your Freer player is to try free the captured player. If no one to free, it behaves as random walker.
  */


import java.util.ArrayList;

public class Freer extends Player {
  
  /** the index of opponent team array corresponds to the current player to catch */
  int currentIdx;
  
  /** 
   * Players that try to free their teammates from jail
   * 
   * @param field is the current playing field
   * 
   */
  public void play(Field f){
        
    int team = getTeamId(f);
    boolean validTargetToFree = false;
    ArrayList<Entity> myTeam;
    
    if (!f.gameStillRunning())
    {
      return;
    }
    
    if (isInJail())
    {      
      return;
    }
    
    if (team == -1)
    {
      return;  // should never occur
    }    
    
    // find opponent team
    if (team == 1)
    {
      myTeam = f.getTeam1();
    }
    else
    {
      myTeam = f.getTeam2();
    }
    
    if (currentIdx == -1)
    {
      currentIdx = getTarget(myTeam);                
    }
    
    
    if (currentIdx != -1)
    {
      validTargetToFree = true;
    }
    
    
    // opponent team is empty. no opponent to capture
    if (currentIdx == -1)
    {            
      // behave as random walker if no target to free
      behaveAsDefaultRandomWalker(f);
      return;
    }
    
    Jail jail = f.getOtherJail(this);
    ArrayList <Player> capturedPlayers = jail.getCaptured();
    
    // if the current target is caught, add into the captured list and teleport to the jail of opponent.
    // set the next player in opponent team as the next target to catch if there is any.
    
    for (int i=0; i < capturedPlayers.size(); i++ )
    {
      Player p = capturedPlayers.get(i);
      f.freeTeammate(this,p);
      if (capturedPlayers.isEmpty())
      {
        currentIdx = -1;
        validTargetToFree = false;
        return;
      }
    }            
    
    // The algorithm to chase the target player
    // 1) find delta X and delta Y to reach target from this player
    // 2) set speed X and Y of this player according to the ratio of delta X and Y
    if (validTargetToFree)
    {      
      int deltaX = Math.abs(jail.getX() - getX());
      int deltaY = Math.abs(jail.getY() - getY());
      
      double speedBase = Math.abs(Math.random()*4-2);                               
      double newSpeedX = speedBase;
      if (deltaY != 0){
        newSpeedX = speedBase * ((int) deltaX/deltaY);
      }      
      double newSpeedY = speedBase;
      
      // dierction
      if (jail.getX() < getX())
      {
        newSpeedX *= (-1);
      }
      
      if (jail.getY() < getY())
      {
        newSpeedY *= (-1);
      }     
      
      this.speedX = newSpeedX;
      this.speedY = newSpeedY;     
    }
    else {      
      // boundary bounce behavior 
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
  }
  
  
   /** 
    * Get any team player who is in jail.
    * 
    * 
    * @return index of team player who is in jail. -1 if no found.
    * 
    */
  protected int getTarget(ArrayList<Entity> myTeam)
  {    
    // find player who is in jail
    for (int idx = 0; idx < myTeam.size(); idx++)
    {
      Player p = (Player) myTeam.get(idx);
      if (p.isInJail())
      {  
        return idx;        
      }      
    }    
    return -1;       
  }
      
  @Override
  public void update(Field field){}
  public Freer(Field f, int side, String name, int number, String team,char symbol, double x, double y){
    super(f, side, name, number, team, symbol, x, y);
    this.speedX = Math.random()*4-2;
    this.speedY =  Math.random()*4-2;
    currentIdx = -1;
  }
}