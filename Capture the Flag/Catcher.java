/******************************************************************************
  * 
  * COMP 1406, Assignment 8
  * Richard Xia, 101007519, 12/05/2016 
  *
  *  Compilation:  javac Catcher.java
  *  Execution:    java CaptureTheFlag
  * 
  *  Class Catcher implements the behavior of a Catcher.
  *  
  *  The goal of your Catcher player is to try and catch opposing players on the field. Your
  *  player should not chase after players on its own team. When player A catches an opponent
  *  (the catchOpponent method lets you know when you catch an opponent), say player B,
  *  player A will take player B to A’s jail.
  * 
  *  After your player catches and brings an opponent to jail, it then looks for a different
  *  opponent to catch if there are any.     
  */


import java.util.ArrayList;

public class Catcher extends Player {
  
  /** the player to catch */
  private Player target;
  
  /** the index of opponent team array corresponds to the current player to catch */
  int currentIdx;
  
  /** logic for this entity (change direction/speed)
    * <p>
    * logic is based on current playing field (which holds information about 
    * all entities on the field) and possibly state of this entity. 
    *  
    * The goal of your Catcher player is to try and catch opposing players on the field. Your
    * player should not chase after players on its own team. When player A catches an opponent
    * (the catchOpponent method lets you know when you catch an opponent), say player B,
    * player A will take player B to A’s jail.
    * 
    * After your player catches and brings an opponent to jail, it then looks for a different
    * opponent to catch if there are any. 
    * 
    * @param field is the current playing field
    * 
    */
  public void play(Field f) {    
    
    int team = getTeamId(f);
    boolean validTargetToCatch = false;
    ArrayList<Entity> opponent;
    
    if (!f.gameStillRunning())
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
      opponent = f.getTeam2();
    }
    else
    {
      opponent = f.getTeam1();
    }
    
    if (currentIdx == -1)
    {
      currentIdx = getTarget(opponent, f);                
    }
        
    if (currentIdx != -1)
    {
      target = (Player) opponent.get(currentIdx);
    }
    
    ArrayList<Player> captured = f.getTeamJail(this).getCaptured();
    
    // opponent team is empty. no opponent to capture
    if (currentIdx == -1)
    {      
      behaveAsDefaultRandomWalker(f); // behave as random walker if no opponent to capture
      displayJail(f, team, captured);
      return;
    }
    
    // if the initial target from last run does not carry the flag, try to find a target which carries flag in this run.
    if (!target.hasFlag())
    {
      int newIdx = getBetterTarget(opponent, f, currentIdx);
      if (newIdx != -1)
      {              
        // if found, update target
        Player bPlayer = (Player) opponent.get(newIdx);
        currentIdx = newIdx;
        target = bPlayer;
        currentIdx = newIdx;
        target = bPlayer;
      }
    }
    
    if (target.hasFlag()) // attempt to catch only if target is carrying the flag
    {
      validTargetToCatch = true;
    }           
    
    // if the current target is caught, add into the captured list and teleport to the jail of opponent.
    // set the next player in opponent team as the next target to catch if there is any.
    
    if (f.catchOpponent(this,target))
    {      
      // set in jail flag, and set speed to zero, and add to captured list 
      // if carry flag, need to drop the flag 
      // it is done after catchOpponent() call
      if (!captured.contains(target))
      {
        captured.add(target);
        target.setInJail(true);
        if (target.hasFlag()){
          target.setHasFlag(false);
          Flag flag = f.getOtherTeamFlag(target);
          flag.setSpeedX(0, flag.getId());
          flag.setSpeedY(0, flag.getId());
        }
      }
      
      validTargetToCatch = false;
      currentIdx = -1;
      displayJail(f, team, captured);      
      return;
    }
    
    // The algorithm to chase the target player
    // 1) find delta X and delta Y to reach target from this player
    // 2) set speed X and Y of this player according to the ratio of delta X and Y
    if (validTargetToCatch)
    {      
      
       int deltaX = Math.abs(target.getX() - getX());
       int deltaY = Math.abs(target.getY() - getY());
       double speedBase =  Math.abs(target.getSpeedX());
       if (Math.abs(target.getSpeedY()) > speedBase)
       {
         speedBase = Math.abs(target.getSpeedY());
       }
       
      double accelerated = 1;
      double distanceInArmLength = getDistanceInArmLength(this, target, f);
      // chase should move quicker than the target if it is far away from the target
      if (distanceInArmLength < 1)
      {
        accelerated = 0.01;
      } else if (distanceInArmLength < 2) {
        accelerated = 0.5;
      } else if (distanceInArmLength < 3) {
        accelerated = 0.8;
      } else if ( distanceInArmLength < 4) {            
        accelerated = 1.05; 
      } else {
        accelerated = 1.15; 
      }
      speedBase *= accelerated;                  
      
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
      this.speedY = newSpeedY;                           
    }    
    else 
    {    
      // if no target to catch, behave as default random walker until found new target
      behaveAsDefaultRandomWalker(f);      
    }
    
    // display the captured opponent players in jail
    displayJail(f, team, captured);
  }
  
  
  /** 
   * display the captured list in Jail
   * 
   * set the captured player to jail location and don't allow them to move.
   * 
   * @param field is the current playing field
   * @param team team 1 or 2 
   * @param captured the captured list
   * 
   * @return N/A
   */
  protected void displayJail(Field f, int team, ArrayList<Player> captured)    
  {
    
    // display the captured players of opponent team
    // set each captured player to jail 
    // set speed of captured player to zero
    
    int jailX = 0;
    int jailY = 0;
    
    if (team == 1)
    {
      jailX = f.getJail1Position()[0];
      jailY = f.getJail1Position()[1];
    }
    else if (team == 2)
    {
      jailX = f.getJail2Position()[0];
      jailY = f.getJail2Position()[1];        
    }     
    
    
    if (!captured.isEmpty()) 
    {
      for (Entity en : captured)
      {      
        en.x = jailX;
        en.y = jailY;
        en.speedX = 0;
        en.speedY = 0;        
      }
    }         
  }
  
  
  /** 
   * return the index of target tot capture.
   *  - player which has Flag
   *  - player in opponent territory (not in jail)
   *  - any player in opponent team which is not in jail
   * 
   * set the captured player to jail location and don't allow them to move.
   * 
   * @param opponent the opponent player list
   * @param f is the current playing field
   * 
   * 
   * @return array index if target is found; -1 if not found
   */
  protected int getTarget(ArrayList<Entity> opponent, Field f)
  {    
    // find player which has Flag
    for (int idx = 0; idx < opponent.size(); idx++)
    {
      Player p = (Player) opponent.get(idx);
      if (p.hasFlag())
      {
        return idx;        
      }
    }
    
    // find the 1st target to catch
    for (int idx = 0; idx < opponent.size(); idx++)
    {          
      Player p = (Player) opponent.get(idx);
      if (!p.isInJail())
      {
        if (f.isInOppositeTerritory(p))
        {
          return idx; 
        }            
      }
    }
    
    for (int idx = 0; idx < opponent.size(); idx++)
    {          
      Player p = (Player) opponent.get(idx);
      if (!p.isInJail())
      {           
        return idx;                 
      }
    }
    return -1;       
  }
  
  /** 
   * attempt to find a better target to capture. 
   * 
   * If the target found in last run dose not carry flag, a new target carrying target is found in this run, 
   * then we need to update the target to the player carrying flag.
   * 
   * @param opponent the opponent player list
   * @param f is the current playing field
   * @param currentIdx the current target index
   * 
   * 
   * @return array index if better target is found; -1 if not found
   */
  protected int getBetterTarget(ArrayList<Entity> opponent, Field f, int currentIdx)
  {    
    // if a target has flag is found
    if (target.hasFlag())
    {
      return -1;
    }
    for (int idx = 0; idx < opponent.size(); idx++){
      Player p = (Player) opponent.get(idx);
      if (p.hasFlag())
      {   
        return idx;        
      }
    }
    if (f.isInOppositeTerritory(target))
    {
      return -1;
    }
    
    // find the target which is in opponent territory and not in jail
    for (int idx = 0; idx < opponent.size(); idx++)
    {          
      Player p = (Player) opponent.get(idx);
      if (!p.isInJail())
      {
        if (f.isInOppositeTerritory(p))
        {
          return idx; 
        }            
      }
    }    
    return -1;    
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
  public Catcher(Field f, int side, String name, int number, String team,char symbol, double x, double y){
    super(f, side, name, number, team, symbol, x, y);
    this.speedX = Math.random()*4-2;
    this.speedY =  Math.random()*4-2;
    currentIdx = -1;
  }
}